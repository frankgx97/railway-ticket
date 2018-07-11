package cn.guoduhao.TicketSystem.service.ticket;

import cn.guoduhao.TicketSystem.Models.Ticket;
import cn.guoduhao.TicketSystem.Models.Train;
import cn.guoduhao.TicketSystem.repository.TicketRepository;
import cn.guoduhao.TicketSystem.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.*;

@Service
public class TicketServiceImpl implements TicketService{

    //导入ticketRepository
    private final TicketRepository ticketRepository;

    private final TrainRepository trainRepository;

    @Autowired //无需实例化，交给Spring管理
    public TicketServiceImpl(TicketRepository ITicketRepository, TrainRepository ITrainRepository){
        this.ticketRepository = ITicketRepository;
        this.trainRepository = ITrainRepository;
    }

    @Override
    public Optional<Ticket> getTicketByUserId(String userId){
        return this.ticketRepository.findOneByUserId(userId);
    }

    @Override
    public Integer buyTicket_BJ_SH(Ticket newTicket){
        boolean isSuccess = buyRemanentTicket_BJ_SH(newTicket);
        if(isSuccess){
            return 1;
        }
        else{
            //ToDo 需要更改 进入NoSQL中查询含有顾客上车和下车站的trainNo, 再根据TrainNo检索
            List<Train> trains =
                trainRepository.findByDepartStationAndDestinationStation("北京","上海");
            Integer remanentTickets = trains.get(0).seatsTotal - trains.get(0).seatsSold;
            if(remanentTickets > 0){
                String startStation = newTicket.departStation;
                String arriveStation =newTicket.destinationStation;
                String defaultStation = createStations("北京","上海");
                String newStations = modifyStations(startStation,arriveStation,defaultStation);
                trains.get(0).seatsSold += 1;
                newTicket.stations = newStations;
                ticketRepository.save(newTicket);
                trainRepository.save(trains.get(0));
                return 1;
            }
            else{
                return 2;
            }
        }
    }

    //ToDo JB_SH推广时需要修改
    @Override
    public boolean buyRemanentTicket_BJ_SH(Ticket newTicket){
        //读出传入数据的起始站和目的站
        String startStation = newTicket.departStation;
        String arriveStation = newTicket.destinationStation;
        //ToDo JB_SH推广时需要修改
        List<Ticket> targetTickets = searchRemanentTicket_BJ_SH(startStation,arriveStation);
        //搜索符合条件的剩余票
        if(!targetTickets.isEmpty()){
            String targetStations = targetTickets.get(0).stations;
            String newStations = modifyStations(startStation,arriveStation,targetStations);
            if (newStations.equals(targetStations)){ //若stations未发生改动
                return false; // 则返回 表示修改失败 购票失败 剩余的票中已经没有满足需求的分段票了
            }
            else{
                //同时更新新票和已经购入票的stations字段
                newTicket.stations = newStations;
                targetTickets.get(0).stations = newStations;
                //ToDo:改
                if (newStations.equals(modifyStations("北京","上海",""))){
                    Optional<Train> train = trainRepository.findOneByTrainNo(targetTickets.get(0).trainNo);
                    if(train.isPresent()){
                        train.get().seatsSold += 1;
                        trainRepository.save(train.get());
                    }
                    return true; //表示需要在上层函数中从总票数里卖出一张
                }

                //将这两张票在数据库中更新
                ticketRepository.save(newTicket);
                ticketRepository.save(targetTickets.get(0));
                return true; //返回 1购票成功 表示此次购票可以在剩余的票中解决
            }
        }
        return false; // 返回 0购票失败 剩余的票中已经没有满足需求的分段票了
    }

    //ToDo 添加北京至上海的分段查找功能 如果铁路线路过多需要使用数据库存储。具体实现逻辑在石墨文档
    @Override
    public List<Ticket> searchRemanentTicket_BJ_SH(String startStation, String arriveStation){
        List<Ticket> targetTickets = new ArrayList<>();
        //ToDo 此处推广时需要修改
        List<Ticket> tickets =
                ticketRepository.findByDepartStationAndDestinationStation("北京","上海");
        //ToDo 此处推广时需要修改
        Integer totalStations = StringToStationNum_BJ_SH("上海"); //总站数
        Integer startNum = StringToStationNum_BJ_SH(startStation); // 乘客上车站
        Integer arriveNum = StringToStationNum_BJ_SH(arriveStation); //乘客下车站
        Integer remanNum = totalStations - arriveNum; //距离终点站的站数(用于组合正则)

        //使用String组合出对应的正则表达式
        String patternStations = "";
        patternStations = "[01]{" + startNum.toString() + "}";
        for(int i = startNum; i < arriveNum ; i++){
            patternStations = patternStations + "0";
        }
        patternStations = patternStations + "[01]{" + remanNum.toString() + "}";

        //利用正则遍历车票
        for(int i = 0; i < tickets.size() ;i++){
            boolean isMatch = Pattern.matches(patternStations,tickets.get(i).stations);
            if(isMatch){
                targetTickets.add(tickets.get(i));
            }
        }
        //返回符合情况的List
        return targetTickets;
    }

    @Override
    public String modifiedTicketStation(Ticket ticket){
        return modifyStations(ticket.departStation,ticket.destinationStation,ticket.stations);
    }

    //传入: String depart 站名
    //输出: stations
    //ToDo 分站购票中，如果有多条火车线路，需要在这里修改StringToStationNum_BJ_SH成别的对应关系
    //或者做个switch，多加一个参数，让函数选择哪条线路的车票对应关系
    private String modifyStations(String departStation,String destinationStation,String stations){
        Integer departNum = StringToStationNum_BJ_SH(departStation);
        Integer destinationNum = StringToStationNum_BJ_SH(destinationStation);
        return modifyString(departNum,destinationNum,stations);
    }

    //分段式构建
    //北京-石家庄-邯郸-郑州
    //    0      0    0
    //以下是用于分段购票的实现函数
    public String modifyString(Integer departNum , Integer destinationNum,String stations) {
        //若选择的起始站比终点站还远 或 起始站与终点站相同
        if (departNum >= destinationNum) {
            //直接返回stations 不予处理
            return stations;
        }
        Integer stationsLength = stations.length();
        //若选择的起始站和终点站位置超过范围
        if(departNum<0 || destinationNum>(stationsLength)){
            //直接返回stations 不予处理
            return stations;
        }
        //新的stations信息
        char[] newStations = stations.toCharArray();
//        newStations[departNum] = '1';
//        newStations[destinationNum] = '1';
        for(Integer temp = departNum  ; temp < destinationNum ; temp++){
            if(stations.charAt(temp) == '1'){
                return stations;
            }
            newStations[temp] = '1';
        }
        //返回新的stations信息
        return Arrays.toString(newStations).replaceAll("[\\[\\]\\s,]", "");
    }

    private String createStations(String departStation,String destinationaStation){
        Integer departNum = StringToStationNum_BJ_SH(departStation);
        Integer destinationNum = StringToStationNum_BJ_SH(destinationaStation);
        return createStations(departNum,destinationNum);
    }

    private String createStations(Integer departNum,Integer destinationNum){
        String defaultStation = "";
        for(int i = departNum; i < destinationNum ; i++){
            defaultStation = defaultStation + "0";
        }
        return defaultStation;
    }

    //北京-上海的火车站数
    // 0 北京 1 天津西 2 沧州 3 德州 4 徐州 5 南京 6 镇江 7 常州 8 无锡 9 苏州 10 上海
    /**
    * @author 田昕峣
     * @param stationName
     *  车站名
     * @return homoNum
     *  转换好的对应车站序号
    * */
    //ToDo 此函数可放入数据库，以简化代码
    private Integer StringToStationNum_BJ_SH(String stationName){
        Integer homoNum;
        switch (stationName){
            case"北京": homoNum = 0; break;
            case"天津西": homoNum = 1; break;
            case"沧州": homoNum = 2; break;
            case"德州": homoNum = 3; break;
            case"徐州": homoNum = 4; break;
            case"南京": homoNum = 5; break;
            case"镇江": homoNum = 6; break;
            case"常州": homoNum = 7; break;
            case"无锡": homoNum = 8; break;
            case"苏州": homoNum = 9; break;
            case"上海": homoNum = 10; break;
            default:homoNum = -1;break;
        }
        return homoNum;
    }
}

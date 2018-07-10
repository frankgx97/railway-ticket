package cn.guoduhao.TicketSystem.service.ticket;

import cn.guoduhao.TicketSystem.Models.Ticket;
import cn.guoduhao.TicketSystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{

    //导入ticketRepository
    private final TicketRepository ticketRepository;

    @Autowired //无需实例化，交给Spring管理
    public TicketServiceImpl(TicketRepository ITicketRepository){
        this.ticketRepository = ITicketRepository;
    }

    @Override
    public Optional<Ticket> getTicketByUserId(String userId){
        return this.ticketRepository.findOneByUserId(userId);
    }

    //以下是用于分段购票的实现函数
    public String modifyString(Integer departNum , Integer destinationNum,String stations) {
        //若选择的起始站比终点站还远 或 起始站与终点站相同
        if (departNum >= destinationNum) {
            //直接返回stations 不予处理
            return stations;
        }
        Integer stationsLength = stations.length();
        //若选择的起始站和终点站位置超过范围
        if(departNum<0 || destinationNum>(stationsLength-1)){
            //直接返回stations 不予处理
            return stations;
        }
        //新的stations信息
        char[] newStations = stations.toCharArray();
        newStations[departNum] = '1';
        newStations[destinationNum] = '1';
        for(Integer temp = departNum + 1 ; temp < destinationNum ; temp++){
            if(stations.charAt(temp) == '1'){
                return stations;
            }
            newStations[temp] = '1';
        }
        //返回新的stations信息
        return Arrays.toString(newStations).replaceAll("[\\[\\]\\s,]", "");
    }

//    public boolean modifyStations(){
//
//    }
}

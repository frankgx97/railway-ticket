package cn.guoduhao.TicketSystem.service;

import cn.guoduhao.TicketSystem.Models.Ticket;
import cn.guoduhao.TicketSystem.Models.Train;
import cn.guoduhao.TicketSystem.Models.TrainStationMap;
import cn.guoduhao.TicketSystem.Models.User;
import cn.guoduhao.TicketSystem.repository.TrainRepository;
import cn.guoduhao.TicketSystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@EnableJms
public class OrderService {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    public String placeOrder(String userId, Integer trainId, String depart, String desination){
        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = new Ticket();
        Optional<Train> train = trainRepository.findOneById(trainId);
        Optional<User> user = userRepository.findOneById(userId);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.getTime());


        ticket.id = UUID.randomUUID().toString();
        ticket.name = user.get().getName();
        ticket.departStation = depart;//train.get().departStation;
        ticket.departTime = train.get().departTime;
        ticket.destinationStation = desination;//train.get().destinationStation;
        //ToDo:更改票价
        ticket.expense = train.get().expense;
        ticket.seat = this.generateSeatNo();
        ticket.trainNo = train.get().trainNo;
        ticket.trainId = train.get().id;
        ticket.status = 0;
        ticket.timestamp = timestamp.getTime()/1000;
        ticket.userId = userId;
        ticket.orderId = "0";
        ticket.version = "0";

        try{
            String orderJson = mapper.writeValueAsString(ticket);
            this.pushQueue(orderJson);
            return ticket.id;
        }catch(Exception e){
            return "ERR";
        }
    }

    private int pushQueue(String orderJson){
        this.jmsMessagingTemplate.convertAndSend("orders", orderJson);
        return 0;
    }

    public List<Ticket> readOrdersFromRedis(String userId){
        Set<String> keys = this.stringRedisTemplate.keys("*"+userId+"*");
        List<String> jsonList = this.stringRedisTemplate.opsForValue().multiGet(keys);

        List<Ticket> ticketList = new ArrayList<>();
        for(int i=0;i<jsonList.size();i++){
            ObjectMapper mapper = new ObjectMapper();
            try{
                Ticket ticket = mapper.readValue(jsonList.get(i), Ticket.class);
                ticketList.add(ticket);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return ticketList;
    }

    public String checkOrderStatus(String ticketId, String userId){
        try{
            Set<String> keys = this.stringRedisTemplate.keys(ticketId+"*");
            List<String> jsonList = this.stringRedisTemplate.opsForValue().multiGet(keys);
            if (jsonList.isEmpty()){
                return"{\"result\":false}";
            }else{
                return"{\"result\":true}";
            }
        }catch(Exception e){
            return"{\"result\":false}";
        }
    }

    private String generateSeatNo(){
        Random random = new Random();
        String alphabet = "ABCDEF";
        String carriage = Integer.toString(random.nextInt(15)+1);
        String seat = Integer.toString(random.nextInt(19)+1);
        Character seatNo = alphabet.charAt(random.nextInt(5)+1);
        return carriage+" - "+seat+seatNo;
    }

    //车站信息的相关操作
    //query使用Criteria.where("stations").is("北京")这种格式制定键值对，在上层函数中使用
    //返回一个TrainStationMap对象
    private TrainStationMap findOne(Query query, String collectionName){
        return mongoTemplate.findOne(query,TrainStationMap.class,collectionName);
    }

    // 返回一个List<TrainStationMap>
    private List<TrainStationMap> find(Query query,String collectionName){
        return mongoTemplate.find(query,TrainStationMap.class,collectionName);
    }

    //输入上车站和下车站，返回trainNo
    public List<TrainStationMap> findAllByDepartStaitonAndDestinationStation(String departStation,String destinationStation){
        Query query = new Query();
        query.addCriteria(
                Criteria.where("stations").exists(true).andOperator(
                    Criteria.where("stations").is(departStation),
                       Criteria.where("stations").is(destinationStation)
                )
        );
        //System.out.println("query - " + query.toString());
        return this.find(query,"Stations"); //mongoDB中的Collation名称
    }

    //输入trainNo返回相应的车站信息对象
    public TrainStationMap findOneByTrainNo(String trainNo){
        Query query = new Query(Criteria.where("trainNo").is(trainNo));
        return this.findOne(query,"Stations");
    }

    //输入站名和trainNo,返回此站名对应的index
    public Integer stationNameToInteger(String stationName , String trainNo){
        TrainStationMap stationInfo = this.findOneByTrainNo(trainNo);
        if(stationInfo != null){
            return stationInfo.stations.indexOf(stationName);
        }
        else{
            return -1;
        }
    }

}

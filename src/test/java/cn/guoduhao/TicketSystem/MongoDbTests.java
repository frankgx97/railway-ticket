package cn.guoduhao.TicketSystem;

import cn.guoduhao.TicketSystem.Models.Ticket;
import cn.guoduhao.TicketSystem.Models.TrainStationMap;
import cn.guoduhao.TicketSystem.repository.MongoDbRepositories.TicketMongoRepository;
import cn.guoduhao.TicketSystem.repository.MongoDbRepositories.TrainMongoRepository;
import cn.guoduhao.TicketSystem.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;



@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDbTests {

    @Autowired
    TicketMongoRepository ticketMongoRepository;

    @Autowired
    OrderService orderService;

    @Test
    public void mongoDbWriteTest(){
        Ticket ticket = new Ticket();
        ticket.id = UUID.randomUUID().toString();
        ticket.trainNo = "G1";
        ticket.departStation = "北京";
        ticket.destinationStation = "上海";
        ticket.departTime = "2018-09-01-11:00:00";
        ticket.expense = 0;
        ticket.name = "test";
        ticket.orderId = "00000001";
        ticket.seat = "A1";
        ticket.status = 0;
        ticket.userId = "4028abda64709466016470952b6b0000";
        ticketMongoRepository.save(ticket);
    }

//    @Test
//    @Bean
//    public void mongoDbSearchTest(){
//        Query query = new Query(Criteria.where("stations").is("北京"));
//        TrainStationMap stationInfo = orderService.findOne(query,"Stations");
//        System.out.println(stationInfo.trainNo);
//    }
//
//    @Test
//    @Bean
//    public void mongoDbSearchAllTest(){
//        Query query = new Query(Criteria.where("stations").is("北京"));
//        List<TrainStationMap> stationInfos = orderService.find(query,"Stations");
//        System.out.println(stationInfos.size());
//        System.out.println(stationInfos.get(0).trainNo);
//    }

    @Test
    @Bean
    public void mongoDbSearchAllTest(){
        List<TrainStationMap> stationInfos =
                orderService.findAllByDepartStaitonAndDestinationStation("天津西","苏州");
        System.out.println(stationInfos.size());
    }


}

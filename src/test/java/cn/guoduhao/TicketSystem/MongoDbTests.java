package cn.guoduhao.TicketSystem;

import cn.guoduhao.TicketSystem.Models.ConsumerModels.Ticket;
import cn.guoduhao.TicketSystem.repository.MongoDbRepositories.TicketMongoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDbTests {

    @Autowired
    TicketMongoRepository ticketMongoRepository;

    @Test
    public void mongoDbWriteTest(){
        Ticket ticket = new Ticket();
        ticket.id = 0;
        ticket.trainId = "G1";
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
}

package cn.guoduhao.TicketSystem;

import cn.guoduhao.TicketSystem.Models.Ticket;
import cn.guoduhao.TicketSystem.repository.TicketRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketSystemApplicationTests {
	@Autowired
	TicketRepository ticketRepository;

	@Test
	@Bean
	//基础测试：Ticket表连接测试
	public void TicketRepoBasicTest(){
		Ticket ticket = new Ticket();
		ticket.name = "张三";
		ticket.orderId = "G2SC02SE23DA20180709TI135500";
		ticket.departTime = "2018-07-10-13:55:00";
		ticket.departStation = "北京";
		ticket.destinationStation = "武汉";
		ticket.expense = 355;
		ticket.status = 1;
		ticket.trainNo = "G2";
		ticket.seat = "SC23SE23"; // Section 02 Seat 23 第二节车厢 23号座位
		ticket.stations = "11111111111111";
		ticket.version = "0";

		ticketRepository.save(ticket);
	}

    @Test
    @Bean
    //add测试
    public void TicketRepoAdd(){
        Ticket ticket = new Ticket();
        ticket.name = "李志伟";
        ticket.orderId = "G1SC02SE23DA20180711TI135500";
        ticket.departTime = "2018-07-11-13:55:00";
        ticket.departStation = "北京";
        ticket.destinationStation = "上海";
        ticket.expense = 355;
        ticket.status = 1;
        ticket.userId ="3721";
        ticket.trainNo = "G2";
        ticket.seat = "SC23SE23"; // Section 02 Seat 23 第二节车厢 23号座位
        ticket.stations = "11111111111";
        ticket.version = "0";

        ticketRepository.save(ticket);
    }

    @Test
    @Bean
    public void TicketSearchTestById(){
        //按id查找存在的票据
        Optional<Ticket> ticket1 = ticketRepository.findOneById(86);
        //注意，调用get前需先调用isPresent检验是否存在。
        if(ticket1.isPresent()) {
            String stations1 = ticket1.get().stations;
            System.out.println(stations1);
        }

        //按id查找不存在的票据
        Optional<Ticket> ticket2 = ticketRepository.findOneById(0);
        if(ticket2.isPresent()) {
            String stations2 = ticket2.get().stations;
            System.out.println(stations2);
        }

        //按id再次查找存在的票据
        Optional<Ticket> ticket3 = ticketRepository.findOneById(86);
        if(ticket3.isPresent()) {
            String stations3 = ticket3.get().stations;
            System.out.println(stations3);
        }

    }

    @Test
    @Bean
    //删除测试
    public void TicketRepoDelete(){
	    String userId = "3721";
        ticketRepository.deleteByUserId(userId);
    }

    @Test
    @Bean
    //更新测试
    public void TicketRepoUpdate(){
        Optional<Ticket> ticket1 = ticketRepository.findOneByUserId("3721");
        if(ticket1.isPresent()){
            ticket1.get().stations = "11110000000000";
            ticketRepository.save(ticket1.get());
        }
        else{
            System.out.println("不存在相应的数据！");
        }
    }



//	@Test
//    @Bean
//    public void TicketSearchTestByUserId(){
//        Optional<Ticket> ticket1 = ticketRepository.findOneByUserId(86);
//        if(ticket1.isPresent()){
//
//        }
//    }



//	public void contextLoads() {
//	}

}

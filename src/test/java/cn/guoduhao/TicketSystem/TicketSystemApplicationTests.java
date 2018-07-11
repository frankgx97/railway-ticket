package cn.guoduhao.TicketSystem;

import cn.guoduhao.TicketSystem.Models.Ticket;
import cn.guoduhao.TicketSystem.repository.TicketRepository;
import cn.guoduhao.TicketSystem.service.ticket.TicketServiceImpl;
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
        ticket.stations = "11110000000";
        ticket.version = "0";

        ticketRepository.save(ticket);
    }

//    @Test
//    @Bean
//    public void TicketSearchTestById(){
//        //按id查找存在的票据
//        Optional<Ticket> ticket1 = ticketRepository.findOneById();
//        //注意，调用get前需先调用isPresent检验是否存在。
//        if(ticket1.isPresent()) {
//            String stations1 = ticket1.get().stations;
//            System.out.println(stations1);
//        }
//
//        //按id查找不存在的票据
//        Optional<Ticket> ticket2 = ticketRepository.findOneById(0);
//        if(ticket2.isPresent()) {
//            String stations2 = ticket2.get().stations;
//            System.out.println(stations2);
//        }
//
//        //按id再次查找存在的票据
//        Optional<Ticket> ticket3 = ticketRepository.findOneById(86);
//        if(ticket3.isPresent()) {
//            String stations3 = ticket3.get().stations;
//            System.out.println(stations3);
//        }
//
//    }

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
            ticket1.get().stations = "111133332220";
            ticketRepository.save(ticket1.get());
        }
        else{
            System.out.println("不存在相应的数据！");
        }
    }

    @Test
    @Bean
    public void TicketFuncModify(){
        Optional<Ticket> ticket = ticketRepository.findOneByUserId("3721");
        if(ticket.isPresent()){
            ticket.get().userId = "3722";
            ticket.get().name = "刘伯";
            ticket.get().departStation = "镇江";
            ticket.get().destinationStation = "上海";
            TicketServiceImpl ts = new TicketServiceImpl(ticketRepository);
            ticket.get().stations = ts.modifiedTicketStation(ticket.get());
            ticketRepository.save(ticket.get());
        }

    }

    @Test
    @Bean
    public void TicketFuncModifyString(){
        TicketServiceImpl ts = new TicketServiceImpl(ticketRepository);
        String test1,test2,test3,test4,test5,test6,test7,test8,test9;
        test1 = ts.modifyString(0,3,"0000");
        test2 = ts.modifyString(0,0,"0000");
        test3 = ts.modifyString(3,0,"0000");
        test4 = ts.modifyString(1,2,"0000");
        test5 = ts.modifyString(-1,5,"0000");
        test6 = ts.modifyString(10,-9,"0000");
        test7 = ts.modifyString(2,3,"1110");
        test8 = ts.modifyString(0,1,"0111");
        test9 = ts.modifyString(1,7,"1100000111");
        System.out.println("Test1:"+test1);
        System.out.println("Test2:"+test2);
        System.out.println("Test3:"+test3);
        System.out.println("Test4:"+test4);
        System.out.println("Test5:"+test5);
        System.out.println("Test6:"+test6);
        System.out.println("Test7:"+test7);
        System.out.println("Test8:"+test8);
        System.out.println("Test9:"+test9);
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

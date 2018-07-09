package cn.guoduhao.TicketSystem;

import cn.guoduhao.TicketSystem.Models.ConsumerModels.Ticket;
import cn.guoduhao.TicketSystem.repository.TicketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

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
		ticket.departTime = "2018-07-09-13:55:00";
		ticket.departStation = "北京";
		ticket.destinationStation = "武汉";
		ticket.expense = 355;
		ticket.status = 1;
		ticket.trainId = "G2";
		ticket.seat = "SC23SE23"; // Section 02 Seat 23 第二节车厢 23号座位
		ticket.stations = "11111111111111";
		ticket.version = "0";

		ticketRepository.save(ticket);
	}

	@Test
	@Bean
	public void TicketSearchTest(){
		//查找存在的票据
		Optional<Ticket> ticket1 = ticketRepository.findOneById(86);
		//注意，调用get前需先调用isPresent检验是否存在。
		if(ticket1.isPresent()) {
			String stations1 = ticket1.get().stations;
			System.out.println(stations1);
		}

		//查找不存在的票据
		Optional<Ticket> ticket2 = ticketRepository.findOneById(0);
		if(ticket2.isPresent()) {
			String stations2 = ticket2.get().stations;
			System.out.println(stations2);
		}

		//再次查找存在的票据
		Optional<Ticket> ticket3 = ticketRepository.findOneById(86);
		if(ticket3.isPresent()) {
			String stations3 = ticket3.get().stations;
			System.out.println(stations3);
		}

	}

//	public void contextLoads() {
//	}

}

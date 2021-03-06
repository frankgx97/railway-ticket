package cn.guoduhao.TicketSystem;

import cn.guoduhao.TicketSystem.Models.Train;
import cn.guoduhao.TicketSystem.repository.TrainRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OracleTests {
    @Autowired
    TrainRepository trainRepository;

    @Test
    @Bean
    public void oracleDbTest(){
        Train train = new Train();
        train.trainNo = "G2";
        train.departStation = "北京";
        train.destinationStation = "武汉";
        train.departTime = "2018-09-01-11:00:00";
        train.seatsTotal = 500;
        train.seatsSold = 70;
        train.expense = 700;
        trainRepository.save(train);
    }
}

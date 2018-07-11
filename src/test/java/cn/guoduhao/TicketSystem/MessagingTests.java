package cn.guoduhao.TicketSystem;

import cn.guoduhao.TicketSystem.Models.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.jms.annotation.EnableJms;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJms
public class MessagingTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void redisTest() {
        this.stringRedisTemplate.opsForValue().set("aaa", "111");
    }

    @Test
    public void redisGetTest(){
        Set<String> keys = this.stringRedisTemplate.keys("*4028abda64709466016470952b6b0000");
        List<String> jsonList = this.stringRedisTemplate.opsForValue().multiGet(keys);
        List<Ticket> ticketList = new ArrayList<>();

        for(int i=0;i<jsonList.size();i++){
            ObjectMapper mapper = new ObjectMapper();
            try{
                Ticket ticket = mapper.readValue(jsonList.get(i), Ticket.class);
                System.out.println("x");
                ticketList.add(ticket);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        for(int i=0;i<ticketList.size();i++){
            System.out.println(ticketList.get(i).departStation);

        }
    }

    @Test
    public void activemqTest(){
        this.jmsMessagingTemplate.convertAndSend("orders", "Hello");
    }

}

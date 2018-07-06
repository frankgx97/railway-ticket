package cn.guoduhao.TicketSystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.jms.annotation.EnableJms;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJms
public class MessagingTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Test
    public void redisTest() {
        this.stringRedisTemplate.opsForValue().set("aaa", "111");
    }

    @Test
    public void activemqTest(){
        this.jmsMessagingTemplate.convertAndSend("orders", "Hello");
    }

}

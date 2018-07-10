package cn.guoduhao.TicketSystem.service;

import cn.guoduhao.TicketSystem.Models.Ticket;
import cn.guoduhao.TicketSystem.Models.Train;
import cn.guoduhao.TicketSystem.Models.User;
import cn.guoduhao.TicketSystem.repository.TrainRepository;
import cn.guoduhao.TicketSystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

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

    public int placeOrder(String userId, Integer trainId){
        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = new Ticket();
        Optional<Train> train = trainRepository.findOneById(trainId);
        Optional<User> user = userRepository.findOneById(userId);
        ticket.id = UUID.randomUUID().toString();
        ticket.name = user.get().getName();
        ticket.departStation = train.get().departStation;
        ticket.departTime = train.get().departTime;
        ticket.destinationStation = train.get().destinationStation;
        ticket.expense = 0;
        ticket.seat = "A1";
        ticket.trainNo = train.get().trainNo;
        ticket.trainId = train.get().id;
        ticket.status = 0;
        ticket.userId = userId;

        try{
            String orderJson = mapper.writeValueAsString(ticket);
            this.pushQueue(orderJson);
            return 0;
        }catch(Exception e){
            return 1;
        }
    }

    private int pushQueue(String orderJson){
        this.jmsMessagingTemplate.convertAndSend("orders", orderJson);
        return 0;
    }

    public List<Ticket> readOrdersFromRedis(String userId){
        Set<String> keys = this.stringRedisTemplate.keys("*"+userId);
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
}

package cn.guoduhao.TicketSystem.service;

import cn.guoduhao.TicketSystem.Models.ConsumerModels.Ticket;
import cn.guoduhao.TicketSystem.Models.ConsumerModels.Train;
import cn.guoduhao.TicketSystem.Models.User;
import cn.guoduhao.TicketSystem.repository.TrainRepository;
import cn.guoduhao.TicketSystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@EnableJms
public class OrderService {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public int placeOrderService(String userId, Integer trainId){
        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = new Ticket();
        Optional<Train> train = trainRepository.findOneById(trainId);
        Optional<User> user = userRepository.findOneById(userId);
        ticket.name = user.get().getName();
        ticket.departStation = train.get().departStation;
        ticket.departTime = train.get().departTime;
        ticket.destinationStation = train.get().destinationStation;
        ticket.expense = 0;
        ticket.seat = "A1";
        ticket.trainId = train.get().trainId;
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
}

package cn.guoduhao.TicketSystem.service.ticket;

import cn.guoduhao.TicketSystem.Models.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    //Optional<Ticket> getTicketByUserId(String userId);

    String modifiedTicketStation(Ticket ticket);

    //List<Ticket> searchRemanentTicket_BJ_SH(String startStation, String arriveStation);

    //boolean buyRemanentTicket_BJ_SH(Ticket newTicket);

    //Integer buyTicket_BJ_SH(Ticket newTicket);

    //计算到站时间的函数 返回时间
    String culculateSchedule(String stationName,Integer trainId);

    List<String> mapToTrainNo(String departStation,String destinationStation);

    //票价算法
    float countFee(String departStation,String destinationStation,String trainNo);

}

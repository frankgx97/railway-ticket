package cn.guoduhao.TicketSystem.service.ticket;

import cn.guoduhao.TicketSystem.Models.Ticket;

import java.util.Optional;

public interface TicketService {

    Optional<Ticket> getTicketByUserId(String userId);






}

package cn.guoduhao.TicketSystem.repository;

import cn.guoduhao.TicketSystem.Models.ConsumerModels.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}



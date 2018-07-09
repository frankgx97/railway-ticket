package cn.guoduhao.TicketSystem.repository;

import cn.guoduhao.TicketSystem.Models.ConsumerModels.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Ticket> findOneById(Integer id);

}



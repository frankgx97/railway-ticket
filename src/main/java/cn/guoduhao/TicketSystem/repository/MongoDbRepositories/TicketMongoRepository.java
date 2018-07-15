package cn.guoduhao.TicketSystem.repository.MongoDbRepositories;

import cn.guoduhao.TicketSystem.Models.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketMongoRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByUserId(String userId);
}

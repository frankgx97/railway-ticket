package cn.guoduhao.TicketSystem.repository.MongoDbRepositories;

import cn.guoduhao.TicketSystem.Models.ConsumerModels.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketMongoRepository extends MongoRepository<Ticket, String> {
}
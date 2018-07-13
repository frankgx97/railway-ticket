package cn.guoduhao.TicketSystem.repository.MongoDbRepositories;


import cn.guoduhao.TicketSystem.Models.TrainStationMap;
import cn.guoduhao.TicketSystem.Models.Train;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrainMongoRepository extends MongoRepository<TrainStationMap,String>{



}

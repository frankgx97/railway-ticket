package cn.guoduhao.TicketSystem.repository.MongoDbRepositories;


import cn.guoduhao.TicketSystem.Models.TrainStationMap;
import cn.guoduhao.TicketSystem.Models.Train;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface TrainMongoRepository extends MongoRepository<TrainStationMap,String>{

    //TrainStationMap findOne(Query query,String collectonName);

}

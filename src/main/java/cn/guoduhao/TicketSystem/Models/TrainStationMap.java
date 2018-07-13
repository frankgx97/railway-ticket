package cn.guoduhao.TicketSystem.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class TrainStationMap {
    @Id
    @GeneratedValue(generator="system-uuid", strategy= GenerationType.AUTO)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id ;
    public String trainNo;
    public List<String> stationsInfo ;

    public TrainStationMap(){};

}

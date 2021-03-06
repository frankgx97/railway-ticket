package cn.guoduhao.TicketSystem.repository;

import cn.guoduhao.TicketSystem.Models.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainRepository extends JpaRepository<Train, Long> {
    List<Train> findByTrainNo(String trainNo);
    Optional<Train> findOneById(Integer id);
    //List<Train> findByDepartStationAndDestinationStationAndDepartTime(String departStation, String destinationStation, String departTime);
    List<Train> findByDepartStationAndDestinationStation(String departStation, String destinationStation);
    List<Train> findByDepartStationAndDestinationStationAndDepartTimeContaining(String departStation, String destinationStation, String departTime);
}

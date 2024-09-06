package dev.cah1r.geminiservice.transit.stop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

  @Query("SELECT stop FROM Stop stop JOIN FETCH stop.line")
  List<Stop> findAllStopsWithLine();

  List<Stop> findAllByLineId(Long lineId);
}

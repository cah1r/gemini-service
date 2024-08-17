package dev.cah1r.geminiservice.transit.stop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

  @Query("SELECT stop FROM Stop stop JOIN FETCH stop.line")
  List<Stop> findAllStopsWithLine();

  @Modifying
  @Query("UPDATE Stop s SET s.lineOrder = :lineOrder WHERE s.id = :id")
  void updateStopsOrder(@Param("id") Long id, @Param("lineOrder") Integer lineOrder);
}

package dev.cah1r.geminiservice.transit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LineRepository extends JpaRepository<Line, Long> {

  boolean existsByDescription(String description);

  @Query("""
      SELECT DISTINCT l FROM Line l
      JOIN FETCH l.lineStops s
      LEFT JOIN FETCH s.schedules
      ORDER BY l.description ASC
      """)
  List<Line> findAllWithStopsAndSchedules();
}

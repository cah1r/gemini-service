package dev.cah1r.geminiservice.transit.route;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {

  @Query("""
      SELECT r FROM Route r
      JOIN FETCH r.startStop origin
      JOIN FETCH  r.endStop destination
      WHERE LOWER(origin.town) LIKE LOWER(CONCAT('%', :keyword, '%'))
      OR LOWER(destination.town) LIKE LOWER(CONCAT('%', :keyword, '%'))
      OR LOWER(origin.details) LIKE LOWER(CONCAT('%', :keyword, '%'))
      OR LOWER(destination.details) LIKE LOWER(CONCAT('%', :keyword, '%'))
      ORDER BY origin.lineOrder ASC, destination.lineOrder DESC
      """)
  Page<Route> getAllRoutesWithStops(@Param("keyword") String keyword, Pageable pageable);
}

package dev.cah1r.geminiservice.transit.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketsBundleRepository extends JpaRepository<TicketsBundle, UUID> {

  @Query("""
          SELECT tb FROM TicketsBundle tb
          JOIN FETCH tb.routes routes
          JOIN FETCH routes.startStop
          JOIN FETCH routes.endStop
      """)
  List<TicketsBundle> getAllTicketsBundlesWithRoutesAndStops();

}

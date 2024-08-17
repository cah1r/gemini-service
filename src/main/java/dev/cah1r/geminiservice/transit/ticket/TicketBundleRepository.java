package dev.cah1r.geminiservice.transit.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketBundleRepository extends JpaRepository<TicketsBundle, Long> {}

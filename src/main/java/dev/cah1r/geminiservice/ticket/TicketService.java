package dev.cah1r.geminiservice.ticket;

import static dev.cah1r.geminiservice.ticket.TicketBundle.toTicketBundle;

import dev.cah1r.geminiservice.error.exception.RouteNotFoundException;
import dev.cah1r.geminiservice.ticket.dto.CreateTicketBundleDto;
import dev.cah1r.geminiservice.transit.route.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
class TicketService {

  private final TicketBundleRepository ticketBundleRepository;
  private final RouteService routeService;

  Flux<TicketBundle> getActiveTicketBundles() {
    return ticketBundleRepository.findByIsActive(true);
  }

  Mono<TicketBundle> addTicketBundle(CreateTicketBundleDto createTicketBundleDto) {
    return routeService
        .findRoute(createTicketBundleDto.initialCity(), createTicketBundleDto.finalStop())
        .switchIfEmpty(Mono.error(new RouteNotFoundException(createTicketBundleDto.initialCity(), createTicketBundleDto.finalStop())))
        .flatMap(route -> saveTicketBundleInDb(toTicketBundle(createTicketBundleDto, route)))
        .doOnError(t -> log.error("An error occurred while saving ticket bundle", t));
  }

  private Mono<TicketBundle> saveTicketBundleInDb(TicketBundle ticketBundle) {
    return ticketBundleRepository
        .save(ticketBundle)
        .doOnNext(data -> log.info("Ticket bundle {} has been saved in db", data.toString()));
  }
}

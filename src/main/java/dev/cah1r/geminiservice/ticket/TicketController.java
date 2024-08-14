package dev.cah1r.geminiservice.ticket;

import dev.cah1r.geminiservice.ticket.dto.CreateTicketBundleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping("/tickets")
@RestController
@RequiredArgsConstructor
class TicketController {

  private final TicketService ticketService;

  @GetMapping("/getActiveBundles")
  Flux<TicketBundle> getActiveTicketBundles() {
    log.info("Fetching ticket bundles from db");
    return ticketService.getActiveTicketBundles();
  }

  @PostMapping("/addTicketBundle")
  Mono<TicketBundle> addTicketBundle(@RequestBody CreateTicketBundleDto createTicketBundleDto) {
    return ticketService.addTicketBundle(createTicketBundleDto);
  }
}

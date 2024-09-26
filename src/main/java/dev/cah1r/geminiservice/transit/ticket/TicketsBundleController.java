package dev.cah1r.geminiservice.transit.ticket;

import dev.cah1r.geminiservice.transit.ticket.dto.BundleStatusDto;
import dev.cah1r.geminiservice.transit.ticket.dto.CreateTicketsBundleDto;
import dev.cah1r.geminiservice.transit.ticket.dto.TicketsBundleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/bundles")
@RequiredArgsConstructor
public class TicketsBundleController {
  
  private final TicketsBundleService ticketsBundleService;

  @GetMapping
  ResponseEntity<List<TicketsBundleDto>> getAllTicketsBundles() {
    return ResponseEntity.ok(ticketsBundleService.getAllTicketsBundles());
  }

  @PostMapping
  ResponseEntity<TicketsBundleDto> createTicketsBundle(@RequestBody CreateTicketsBundleDto dto) {
    TicketsBundleDto createdTicketsBundle = this.ticketsBundleService.createTicketsBundle(dto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdTicketsBundle.id())
        .toUri();

    return ResponseEntity.created(location).body(createdTicketsBundle);
  }

  @DeleteMapping
  ResponseEntity<Void> deleteTicketsBundle(@RequestParam UUID id) {
    ticketsBundleService.deleteTicketsBundleById(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/set-active-status")
  ResponseEntity<Void> setActiveStatus(@PathVariable UUID id, @RequestBody BundleStatusDto request) {
    ticketsBundleService.setActiveStatus(id, request);
    return ResponseEntity.ok().build();
  }
}

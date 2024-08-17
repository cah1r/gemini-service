package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.transit.route.dto.CreateRouteDto;
import dev.cah1r.geminiservice.transit.route.dto.RouteDto;
import dev.cah1r.geminiservice.transit.route.dto.RouteStatusDto;
import dev.cah1r.geminiservice.transit.route.dto.TicketAvailabilityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/route")
public class RouteController {

  private final RouteService routeService;

  @PostMapping("/create")
  ResponseEntity<UUID> createRoute(@RequestBody CreateRouteDto createRouteDto) {
    return ResponseEntity.ok(routeService.createRoute(createRouteDto));
  }

  @GetMapping("/get-all")
  Page<RouteDto> getAllRoutes(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
    return routeService.getAllRoutes(keyword, page, size);
  }

  @PatchMapping("/set-status")
  Boolean changeStatus(@RequestBody RouteStatusDto routeDto) {
    return routeService.setStatus(routeDto);
  }

  @DeleteMapping("/delete/{id}")
  ResponseEntity<Void> deleteRoute(@PathVariable UUID id) {
    routeService.deleteRoute(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/set-ticket-availability/{id}")
  Boolean setTicketAvailability(@PathVariable UUID id, @RequestBody TicketAvailabilityDto dto) {
    return routeService.setTicketAvailability(id, dto);
  }
}

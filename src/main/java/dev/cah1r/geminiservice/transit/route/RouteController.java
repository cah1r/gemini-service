package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.transit.route.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routes")
public class RouteController {

  private final RouteService routeService;
  private final RouteStatusService routeStatusService;

  @PostMapping
  ResponseEntity<UUID> createRoute(@RequestBody CreateRouteDto createRouteDto) {
    return ResponseEntity.ok(routeService.createRoute(createRouteDto));
  }

  @GetMapping(params = {"keyword", "page", "size"})
  Page<RouteDto> getAllRoutes(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
    return routeService.getAllRoutes(keyword, page, size);
  }

  @GetMapping(params = {"lineId", "stopAId", "stopBId"})
  RouteViewDto findRouteForStops(@RequestParam Long lineId, @RequestParam Long stopAId, @RequestParam Long stopBId) {
    return routeService.findRouteByStopsId(lineId, stopAId, stopBId);
  }

  @PatchMapping("/{id}/set-status")
  Boolean changeStatus(@PathVariable UUID id, @RequestBody RouteStatusDto routeDto) {
    return routeStatusService.setStatus(id, routeDto);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteRoute(@PathVariable UUID id) {
    routeService.deleteRoute(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("{id}/set-ticket-availability")
  Boolean setTicketAvailability(@PathVariable UUID id, @RequestBody TicketAvailabilityDto dto) {
    return routeStatusService.setTicketAvailability(id, dto);
  }
}

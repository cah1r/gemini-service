package dev.cah1r.geminiservice.transit.driver;

import dev.cah1r.geminiservice.transit.driver.dto.CreateDriverDto;
import dev.cah1r.geminiservice.transit.driver.dto.DriverDto;
import dev.cah1r.geminiservice.transit.driver.dto.DriverStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/drivers")
class DriverController {

  private final DriverService driverService;

  @GetMapping
  List<DriverDto> getAllDrivers() {
    return driverService.getAllDrivers();
  }

  @PostMapping
  ResponseEntity<UUID> createDriver(@RequestBody CreateDriverDto createDriverDto) {
    UUID id = driverService.createDriver(createDriverDto);
    log.info("[{}] Successfully created driver {} {}", id, createDriverDto.firstName(), createDriverDto.lastName());

    return ResponseEntity.created(URI.create(format("/api/v1/admin/drivers/%s", id))).build();
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteDriver(@PathVariable UUID id) {
    driverService.deleteDriver(id);
    log.info("[{}] Successfully deleted driver", id);

    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/set-active-status")
  ResponseEntity<Void> setDriverActiveStatus(@PathVariable UUID id, @RequestBody DriverStatusDto dto) {
    driverService.setDriverActiveStatus(id, dto);
    return ResponseEntity.ok().build();
  }
}

package dev.cah1r.geminiservice.transit.car;

import dev.cah1r.geminiservice.transit.car.dto.CarDto;
import dev.cah1r.geminiservice.transit.car.dto.CreateCarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/cars")
@RequiredArgsConstructor
class CarController {

  private final CarService carService;

  @GetMapping
  List<CarDto> getAllCars() {
    return carService.getAllCars();
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  ResponseEntity<Long> createCar(@RequestBody CreateCarDto dto) {
    Long carId = carService.createNewCar(dto);
    URI uri = buildUriWithId(carId);

    return ResponseEntity.created(uri).body(carId);
  }

  private static URI buildUriWithId(Long carId) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(carId)
        .toUri();
  }
}

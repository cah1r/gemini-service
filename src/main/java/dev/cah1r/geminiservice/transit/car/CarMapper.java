package dev.cah1r.geminiservice.transit.car;

import dev.cah1r.geminiservice.transit.car.dto.CarDto;
import dev.cah1r.geminiservice.transit.car.dto.CreateCarDto;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CarMapper {

  public static Car toCar(CreateCarDto dto) {
    Car car = new Car();
    car.setCapacity(dto.capacity());
    car.setRegistration(dto.registration());
    car.setIdNumber(dto.idNumber());
    car.setName(dto.name());
    return car;
  }

  public static CarDto toCarDto(Car car) {
    return new CarDto(car.getId(), car.getRegistration(), car.getCapacity(), car.getIdNumber(), car.getName());
  }
}

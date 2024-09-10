package dev.cah1r.geminiservice.transit.car;

import dev.cah1r.geminiservice.error.exception.CarNotFoundException;
import dev.cah1r.geminiservice.error.exception.CarParametersNotUniqueException;
import dev.cah1r.geminiservice.transit.car.dto.CarDto;
import dev.cah1r.geminiservice.transit.car.dto.CreateCarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CarService {

  private final CarRepository carRepository;


  public List<CarDto> getAllCars() {
    return carRepository.findAll()
        .stream()
        .map(CarMapper::toCarDto)
        .toList();
  }

  public Long createNewCar(CreateCarDto dto) {
    if (carRepository.existsByRegistrationOrIdNumber(dto.registration(), dto.idNumber())) {
      throw new CarParametersNotUniqueException(dto);
    }
    return carRepository.save(CarMapper.toCar(dto)).getId();
  }

  public void deleteCar(Long id) {
    carRepository.findById(id).ifPresentOrElse(
        carRepository::delete,
        () -> { throw new CarNotFoundException(format("Couldn't delete car with id: %s as it is not present in db", id)); }
    );
  }

}

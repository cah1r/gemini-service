package dev.cah1r.geminiservice.transit.driver;

import dev.cah1r.geminiservice.error.exception.DriverAlreadyExistsException;
import dev.cah1r.geminiservice.error.exception.DriverNotFoundException;
import dev.cah1r.geminiservice.transit.driver.dto.CreateDriverDto;
import dev.cah1r.geminiservice.transit.driver.dto.DriverDto;
import dev.cah1r.geminiservice.transit.driver.dto.DriverStatusDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
class DriverService {

  private final DriverRepository driverRepository;

  List<DriverDto> getAllDrivers() {
    return driverRepository
        .findAll()
        .stream()
        .map(DriverMapper::toDriverDto)
        .toList();
  }

  @Transactional
  public UUID createDriver(CreateDriverDto createDriverDto) {
    driverRepository.findDriverByPhoneNumber(createDriverDto.phoneNumber())
        .ifPresent(driver -> {
          log.info("Failed creating new driver {} {}. Phone number: {} already exists in db which supposed to be unique",
              createDriverDto.firstName(), createDriverDto.lastName(), createDriverDto.phoneNumber());
          throw new DriverAlreadyExistsException(createDriverDto.phoneNumber());
        });

    return driverRepository
        .save(DriverMapper.toDriver(createDriverDto))
        .getId();
  }

  @Transactional
  public void deleteDriver(UUID id) {
    driverRepository.findById(id).ifPresentOrElse(
        driverRepository::delete,
        () -> {throw new DriverNotFoundException(id);}
    );
  }

  @Transactional
  public void setDriverActiveStatus(UUID id, DriverStatusDto dto) {
    driverRepository.findById(id)
        .ifPresentOrElse(driver -> setStatusAndSave(dto, driver),
            () -> {
              log.error("Error setting status for driver with id: {}. Driver has not been found in db", id);
              throw new DriverNotFoundException(id);
            }
        );
  }

  private void setStatusAndSave(DriverStatusDto dto, Driver driver) {
    driver.setIsActive(dto.isActive());
    driverRepository.save(driver);
    log.info("{} {} status has been set to: {}",
        driver.getFirstName(), driver.getLastName(), TRUE.equals(driver.getIsActive()) ? "active" : "inactive");
  }
}

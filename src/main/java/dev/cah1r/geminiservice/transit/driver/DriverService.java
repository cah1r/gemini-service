package dev.cah1r.geminiservice.transit.driver;

import dev.cah1r.geminiservice.error.exception.DriverAlreadyExistsException;
import dev.cah1r.geminiservice.error.exception.DriverNotFoundException;
import dev.cah1r.geminiservice.transit.driver.dto.CreateDriverDto;
import dev.cah1r.geminiservice.transit.driver.dto.DriverDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class DriverService {

  private final DriverRepository driverRepository;
  private final DriverMapper driverMapper;

  List<DriverDto> getAllDrivers() {
    return driverRepository
        .findAll()
        .stream()
        .map(driverMapper::toDriverDto)
        .toList();
  }

  UUID createDriver(CreateDriverDto createDriverDto) {
    log.info("Received driver data to create: {}", createDriverDto);
    driverRepository.findDriverByPhoneNumber(createDriverDto.phoneNumber())
        .ifPresent(driver -> {
          log.info("Failed creating new driver {} {}. Phone number: {} already already exists in db",
              createDriverDto.firstName(), createDriverDto.lastName(), createDriverDto.phoneNumber());
          throw new DriverAlreadyExistsException(createDriverDto.phoneNumber());
        });

    return driverRepository
        .save(driverMapper.toDriver(createDriverDto))
        .getId();
  }

  void deleteDriver(UUID id) {
    driverRepository.findById(id)
        .ifPresentOrElse(
            driverRepository::delete,
            () -> {throw new DriverNotFoundException(id);}
        );
  }

  @Transactional
  public void setDriverActiveStatus(UUID id, boolean isActive) {
    driverRepository.findById(id)
        .ifPresentOrElse(driver -> {
              driver.setIsActive(isActive);
              driverRepository.save(driver);
            }, () -> { throw new DriverNotFoundException(id); }
        );
  }
}

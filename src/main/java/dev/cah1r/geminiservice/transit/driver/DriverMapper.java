package dev.cah1r.geminiservice.transit.driver;

import dev.cah1r.geminiservice.transit.driver.dto.CreateDriverDto;
import dev.cah1r.geminiservice.transit.driver.dto.DriverDto;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DriverMapper {

  public static Driver toDriver(CreateDriverDto createDriverDto) {
    Driver driver = new Driver();
    driver.setFirstName(createDriverDto.firstName());
    driver.setLastName(createDriverDto.lastName());
    driver.setPhoneNumber(createDriverDto.phoneNumber());
    driver.setIsActive(createDriverDto.isActive());
    return driver;
  }

  public static DriverDto toDriverDto(Driver driver){
    return new DriverDto(
        driver.getId(),
        driver.getFirstName(),
        driver.getLastName(),
        driver.getPhoneNumber(),
        driver.getIsActive()
    );
  }
}

package dev.cah1r.geminiservice.transit.driver;

import dev.cah1r.geminiservice.transit.driver.dto.CreateDriverDto;
import dev.cah1r.geminiservice.transit.driver.dto.DriverDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {

  Driver toDriver(CreateDriverDto createDriverDto);
  DriverDto toDriverDto(Driver driver);
}

package dev.cah1r.geminiservice.error.exception;

import dev.cah1r.geminiservice.transit.car.dto.CreateCarDto;

public class CarParametersNotUniqueException extends EntityAlreadyExistsException {

  public CarParametersNotUniqueException(CreateCarDto dto) {
    super(String.format("Couldn't create new car with registration: %S and id: %s. At least one parameter exists in db",
        dto.registration(), dto.idNumber()));
  }
}

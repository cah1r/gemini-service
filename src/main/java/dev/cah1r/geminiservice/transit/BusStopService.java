package dev.cah1r.geminiservice.transit;

import static dev.cah1r.geminiservice.transit.dto.CreateBusStopDto.toBusStop;

import dev.cah1r.geminiservice.error.exception.BusStopAlreadyExistsException;
import dev.cah1r.geminiservice.error.exception.UpdateException;
import dev.cah1r.geminiservice.transit.dto.BusStopDto;
import dev.cah1r.geminiservice.transit.dto.CreateBusStopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusStopService {

  private final BusStopRepository busStopRepository;

  Mono<BusStopDto> createBusStop(@RequestBody CreateBusStopDto createBusStopDto) {
    return busStopRepository
        .findFirstByCityAndDetails(createBusStopDto.city(), createBusStopDto.details())
        .switchIfEmpty(busStopRepository.save(toBusStop(createBusStopDto)))
        .flatMap(busStop -> Mono.error(new BusStopAlreadyExistsException(createBusStopDto.city(), createBusStopDto.details())));
  }

  Mono<Void> deleteBusStop(@RequestParam String id) {
    return busStopRepository
        .deleteById(id)
        .doOnSuccess(result -> log.info("Bus stop with id: {} has been removed from database", id))
        .doOnError(err -> log.error("Error removing bus stop with id: {} from database with exception:", id, err));
  }

  Flux<BusStopDto> getAllBusStops() {
    return busStopRepository
        .findAll()
        .map(BusStopDto::toBusStopDto)
        .doOnError(err -> log.error("Couldn't load bus stops from db with error:", err));
  }

  Mono<BusStopDto> updateBusStop(@RequestBody BusStopDto busStopDto) {
    return busStopRepository
        .findById(busStopDto.id())
        .switchIfEmpty(Mono.error(new UpdateException(String.format("[%s] Couldn't update bus stop %s - %s as it wasn't found in db",
                busStopDto.id(), busStopDto.city(), busStopDto.details()))))
        .flatMap(busStop -> updateAndSaveInDb(busStop, busStopDto));
  }

  private Mono<BusStopDto> updateAndSaveInDb(BusStop busStop, BusStopDto busStopDto) {
    busStop.setCity(busStopDto.city());
    busStop.setDetails(busStopDto.details());

    return busStopRepository.save(busStop).map(BusStopDto::toBusStopDto);
  }
}

package dev.cah1r.geminiservice.transit.busstop;

import dev.cah1r.geminiservice.error.exception.BusStopAlreadyExistsException;
import dev.cah1r.geminiservice.error.exception.LineNotExistsException;
import dev.cah1r.geminiservice.error.exception.UpdateException;
import dev.cah1r.geminiservice.transit.Line;
import dev.cah1r.geminiservice.transit.LineService;
import dev.cah1r.geminiservice.transit.busstop.dto.BusStopDto;
import dev.cah1r.geminiservice.transit.busstop.dto.GetBusStopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.cah1r.geminiservice.transit.busstop.CreateBusStopDto.toBusStop;
import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusStopService {

  private final BusStopRepository repository;
  private final LineService lineService;

  public Flux<BusStop> findAllById(Set<String> busStopIds) {
    return repository.findAllById(busStopIds);
  }

  public Mono<BusStop> createBusStop(@RequestBody CreateBusStopDto createBusStopDto) {
    return lineService.findLineById(createBusStopDto.lineId())
            .flatMap(foundLine -> saveInDbWhenNotExists(createBusStopDto, foundLine))
            .switchIfEmpty(Mono.error(new LineNotExistsException()));
  }

  private Mono<BusStop> saveInDbWhenNotExists(CreateBusStopDto createBusStopDto, Line line) {
    return repository
            .existsByCityAndDetailsAndLines_Id(createBusStopDto.city(), createBusStopDto.details(), createBusStopDto.lineId())
            .flatMap(result -> TRUE.equals(result)
                    ? Mono.error(new BusStopAlreadyExistsException(createBusStopDto))
                    : repository.save(toBusStop(createBusStopDto, line)));
  }

  public Mono<Void> deleteBusStop(@RequestParam String id) {
    return repository
        .deleteById(id)
        .doOnSuccess(result -> log.info("Bus stop with id: {} has been removed from database", id))
        .doOnError(err -> log.error("Error removing bus stop with id: {} from database with exception:", id, err));
  }

  public Flux<BusStopDto> getAllBusStops() {
    return repository.findAll()
            .map(BusStop::toBusStopDto)
            .doOnNext(data -> log.info("Fetched bus stops from db: {}", data))
            .doOnError(err -> log.error("Couldn't load bus stops from db with error:", err));
  }

  public Mono<BusStop> updateBusStop(@RequestBody BusStopDto busStop) {
    return null;
  }

  public Mono<BusStop> findBusStopById(String id) {
    return repository
        .findById(id)
        .doOnNext(busStop -> log.info("Found bus stop in: {} for id: {}", busStop.getCity(), id))
        .doOnError(e -> log.error("Could not find bus stop for id: {}", id));
  }
}

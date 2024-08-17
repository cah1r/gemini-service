package dev.cah1r.geminiservice.transit.stop;

import dev.cah1r.geminiservice.error.exception.BusStopNotFoundException;
import dev.cah1r.geminiservice.transit.stop.dto.CreateStopDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopWithLineDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopWithSchedulesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static dev.cah1r.geminiservice.transit.stop.dto.CreateStopDto.toBusStop;

@Slf4j
@Service
@RequiredArgsConstructor
public class StopService {

  private final StopRepository stopRepository;

  @Transactional
  public Stop createStop(@RequestBody CreateStopDto createStopDto) {
    return stopRepository.save(toBusStop(createStopDto));
  }

  @Transactional
  public void deleteBusStop(@RequestParam Long id) {
    if (!stopRepository.existsById(id)) {
      throw new BusStopNotFoundException(id);
    }
    stopRepository.deleteById(id);
  }

  List<StopWithLineDto> getAllBusStops() {
    return stopRepository.findAllStopsWithLine()
        .stream()
        .map(StopMapper::toStopWithLineDto)
        .toList();
  }

  public Optional<Stop> findStopById(Long id) {
    return stopRepository.findById(id);
  }

  @Transactional
  public void updateStopsOrder(Set<StopWithSchedulesDto> stops) {
    stops.forEach(stop -> stopRepository.updateStopsOrder(stop.id(), stop.lineOrder()));
  }
}
package dev.cah1r.geminiservice.transit.stop;

import dev.cah1r.geminiservice.error.exception.StopNotFoundException;
import dev.cah1r.geminiservice.transit.stop.dto.CreateStopDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopByLineDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
      throw new StopNotFoundException(id);
    }
    stopRepository.deleteById(id);
  }

  List<StopDto> getAllBusStops() {
    return stopRepository.findAll()
        .stream()
        .map(StopMapper::toStopDto)
        .toList();
  }

  public List<StopByLineDto> getAllBusStopsByLine(Long lineId) {
    return stopRepository.findAllByLineId(lineId)
        .stream()
        .map(stop -> StopMapper.toStopByLineDto(stop, lineId))
        .toList();
  }

  public List<Stop> findStopsByIds(List<Long> ids) {
    return stopRepository.findAllById(ids);
  }

  @Transactional
  public void updateStopsOrder(List<StopByLineDto> stops) {
    Map<Long, Integer> idsWithLineOrder = stops
        .stream()
        .collect(Collectors.toMap(StopByLineDto::id, StopByLineDto::lineOrder));

    List<Stop> stopsToUpdate = stopRepository.findAllById(idsWithLineOrder.keySet());
    validateFoundStops(stopsToUpdate, idsWithLineOrder);
    stopsToUpdate.forEach(stop -> stop.setLineOrder(idsWithLineOrder.get(stop.getId())));
    stopRepository.saveAll(stopsToUpdate);
  }

  private static void validateFoundStops(List<Stop> stops, Map<Long, Integer> idsMap) {
    if (idsMap.size() != stops.size()) {
      Set<Long> foundIds = stops.stream()
          .map(Stop::getId)
          .collect(Collectors.toSet());

      List<Long> missingIds = idsMap.keySet().stream()
          .filter(id -> !foundIds.contains(id))
          .toList();

      log.error("Error during update stops order. Couldn't found Stops with ids: {}", missingIds);
      throw new StopNotFoundException(missingIds);
    }
  }
}
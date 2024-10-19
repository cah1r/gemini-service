package dev.cah1r.geminiservice.transit.tariff;

import dev.cah1r.geminiservice.transit.stop.StopService;
import dev.cah1r.geminiservice.transit.stop.dto.StopByLineDto;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static dev.cah1r.geminiservice.utils.TariffExcelUtils.*;
import static java.util.Comparator.comparingInt;

@Slf4j
@Service
@AllArgsConstructor
public class TariffExcelGenerator {

  private static final String SHEET_LABEL = "Cennik";

  private final StopService stopService;

  public void generateExcelTemplate(Long lineId, HttpServletResponse response) throws IOException {
    List<StopByLineDto> stops = getStopsByDistinctTown(lineId);

    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet(SHEET_LABEL);
      setColumnsWidth(stops.size(), sheet, COLUMN_WIDTH);
      createTable(stops, sheet);

      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

      ServletOutputStream outputStream = response.getOutputStream();
      workbook.write(outputStream);
      outputStream.close();
    }
  }

  private List<StopByLineDto> getStopsByDistinctTown(Long lineId) {
    return stopService.getAllBusStopsByLine(lineId)
        .stream()
        .filter(distinctByKey(StopByLineDto::town))
        .sorted(comparingInt(StopByLineDto::lineOrder))
        .toList();
  }

  private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(keyExtractor.apply(t));
  }

  private void createTable(List<StopByLineDto> stops, Sheet sheet) {
    CellStyle evenRowTown = createBackgroundStyleWithWhiteFont(sheet, BLUE_1);
    CellStyle oddRowTown = createBackgroundStyleWithWhiteFont(sheet, BLUE_2);

    for (int i = 0; i < stops.size(); i++) {
      Row row = sheet.createRow(i);
      setTownCell(stops, sheet, row, i, oddRowTown, evenRowTown);
      formatEmptyCells(row, i);
    }
  }

  private static void setTownCell(List<StopByLineDto> stops, Sheet sheet, Row row, int rowIndex, CellStyle oddRowTown, CellStyle evenRowTown) {
    Cell cell = row.createCell(rowIndex);
    cell.setCellValue(stops.get(rowIndex).town());
    cell.setCellStyle(rowIndex %2 == 0 ? oddRowTown : evenRowTown);
    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, rowIndex, rowIndex + EXTRA_TOWN_CELLS));
  }

  private static void formatEmptyCells(Row row, int rowNo) {
    for (int i = 0; i < rowNo; i++) {
      Cell cell = row.createCell(i);
      if (rowNo % 2 == 0) {
        cell.setCellStyle(addCellBorder(row.getSheet().getWorkbook().createCellStyle()));
      } else {
        cell.setCellStyle(createBackgroundStyleWithBorder(row.getSheet(), GRAY));
      }
    }
  }

}


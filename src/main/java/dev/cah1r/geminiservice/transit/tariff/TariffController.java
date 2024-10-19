package dev.cah1r.geminiservice.transit.tariff;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/tariffs")
@AllArgsConstructor
public class TariffController {

  private final TariffExcelGenerator tariffExcelGenerator;

  @GetMapping("/generate-excel-template")
  public void generateTariffExcel(@RequestParam Long lineId, HttpServletResponse response) throws IOException {
    tariffExcelGenerator.generateExcelTemplate(lineId, response);
  }
}

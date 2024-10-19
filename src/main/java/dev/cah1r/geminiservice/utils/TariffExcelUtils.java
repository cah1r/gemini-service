package dev.cah1r.geminiservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.Color;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TariffExcelUtils {

  public static final XSSFColor GRAY = new XSSFColor(new Color(230, 230, 230), null);
  public static final XSSFColor BLUE_1 = new XSSFColor(new Color(13, 77, 114), null);
  public static final XSSFColor BLUE_2 = new XSSFColor(new Color(41, 106, 143), null);

  public static final int COLUMN_WIDTH = 1300;
  public static final int EXTRA_TOWN_CELLS = 3;

  public static void setColumnsWidth(int count, Sheet sheet, int width) {
    for (int i = 0; i < count + EXTRA_TOWN_CELLS; i++) {
      sheet.setColumnWidth(i, width);
    }
  }

  public static CellStyle createBackgroundStyleWithWhiteFont(Sheet sheet, XSSFColor backgroundColor) {
    Font font = sheet.getWorkbook().createFont();
    font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
    CellStyle style = sheet.getWorkbook().createCellStyle();
    style.setFillForegroundColor(backgroundColor);
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setFont(font);

    return style;
  }

  public static CellStyle createBackgroundStyleWithBorder(Sheet sheet, XSSFColor backgroundColor) {
    CellStyle style = sheet.getWorkbook().createCellStyle();
    style.setFillForegroundColor(backgroundColor);
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    return addCellBorder(style);
  }

  public static CellStyle addCellBorder(CellStyle style) {
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setBorderTop(BorderStyle.THIN);
    style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
    style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
    style.setRightBorderColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
    style.setTopBorderColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
    style.setAlignment(HorizontalAlignment.CENTER);
    return style;
  }
}

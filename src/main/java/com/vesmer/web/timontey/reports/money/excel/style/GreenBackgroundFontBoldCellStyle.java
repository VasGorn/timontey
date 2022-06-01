package com.vesmer.web.timontey.reports.money.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class GreenBackgroundFontBoldCellStyle extends ExcelCellStyle{
	private static final short FONT_SIZE = 12;
	private static final short BLACK_COLOR = IndexedColors.BLACK.getIndex();

	public GreenBackgroundFontBoldCellStyle(Workbook workbook) {
		super(workbook);
	}

	@Override
	protected CellStyle createCellStyle() {
		CellStyle fillGreenBold = workbook.createCellStyle();
        fillGreenBold.setAlignment(HorizontalAlignment.CENTER);
        fillGreenBold.setVerticalAlignment(VerticalAlignment.CENTER);
        fillGreenBold.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        fillGreenBold.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        fillGreenBold.setFont(getHeaderFontBlack());
        setAllBorder(fillGreenBold, BorderStyle.THIN);
        return fillGreenBold;
	}
		
	private Font getHeaderFontBlack() {
		Font headerFontBlack = workbook.createFont();
        headerFontBlack.setBold(true);
        headerFontBlack.setFontHeightInPoints(FONT_SIZE);
        headerFontBlack.setColor(BLACK_COLOR);
        return headerFontBlack;
	}

}

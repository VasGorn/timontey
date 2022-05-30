package com.vesmer.web.timontey.reports.money.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public final class HeaderGreenCellStyle extends ExcelCellStyle {
	private static final short FONT_SIZE = 12;
	private static final short GREEN_COLOR = IndexedColors.GREEN.getIndex();

	public HeaderGreenCellStyle(Workbook workbook) {
		super(workbook);
	}

	@Override
	protected CellStyle createCellStyle() {
        CellStyle headerGreen = workbook.createCellStyle();
        headerGreen.setFont(boldGreenFont());
		return headerGreen;
	}
		
	private Font boldGreenFont() {
		Font headerFontGreen = workbook.createFont();
        headerFontGreen.setBold(true);
        headerFontGreen.setFontHeightInPoints(FONT_SIZE);
        headerFontGreen.setColor(GREEN_COLOR);
        return headerFontGreen;
	}
}

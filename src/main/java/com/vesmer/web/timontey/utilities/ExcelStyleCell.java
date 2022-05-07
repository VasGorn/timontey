package com.vesmer.web.timontey.utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelStyleCell {
	private Workbook workbook;
	private CellStyle main;
	private CellStyle headerGreen;
	private CellStyle headerBlackCenter;
	private CellStyle blackCenter;
	private CellStyle blackBoldLeft;
	private CellStyle fillGreen;
	private CellStyle fillGreenBold;
	
	public ExcelStyleCell(Workbook workbook) {
		this.workbook = workbook;
	}
		
	public CellStyle getMain() {
		if (main != null) {
			return main;
		}
        main = workbook.createCellStyle();
        return main;
	}
		
	public CellStyle getHeaderGreen() {
		if (headerGreen != null) {
			return headerGreen;
		}
        headerGreen = workbook.createCellStyle();
        headerGreen.setFont(getHeaderFontGreen());
        return headerGreen;
	}
		
	private Font getHeaderFontGreen() {
		Font headerFontGreen = workbook.createFont();
        headerFontGreen.setBold(true);
        headerFontGreen.setFontHeightInPoints((short) 12);
        headerFontGreen.setColor(IndexedColors.GREEN.getIndex());
        return headerFontGreen;
	}

}

package com.vesmer.web.timontey.reports.money.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class HeaderBlackCenterCellStyle extends ExcelCellStyle{
	private static final short FONT_SIZE = 12;
	private static final short BLACK_COLOR = IndexedColors.BLACK.getIndex();

	public HeaderBlackCenterCellStyle(Workbook workbook) {
		super(workbook);
	}

	@Override
	protected CellStyle createCellStyle() {
		CellStyle headerBlackCenter =  workbook.createCellStyle();
        headerBlackCenter.setAlignment(HorizontalAlignment.CENTER);
        headerBlackCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        headerBlackCenter.setFont(fontHeaderBlack());
        setAllBorder(headerBlackCenter, BorderStyle.THIN);
        return headerBlackCenter;
	}
		
	private Font fontHeaderBlack() {
		Font headerFontBlack = workbook.createFont();
        headerFontBlack.setBold(true);
        headerFontBlack.setFontHeightInPoints(FONT_SIZE);
        headerFontBlack.setColor(BLACK_COLOR);
        return headerFontBlack;
	}

}

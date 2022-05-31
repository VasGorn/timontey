package com.vesmer.web.timontey.reports.money.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class BlackLeftCellStyle extends ExcelCellStyle{
	private static final short FONT_SIZE = 12;
	private static final short BLACK_COLOR = IndexedColors.BLACK.getIndex();

	public BlackLeftCellStyle(Workbook workbook) {
		super(workbook);
	}

	@Override
	protected CellStyle createCellStyle() {
		CellStyle blackBoldLeft =  workbook.createCellStyle();
        blackBoldLeft.setAlignment(HorizontalAlignment.LEFT);
        blackBoldLeft.setVerticalAlignment(VerticalAlignment.CENTER);
        blackBoldLeft.setWrapText(true);
        blackBoldLeft.setFont(fontBlackLeft());
        setAllBorder(blackBoldLeft, BorderStyle.THIN);
        return blackBoldLeft;
	}
		
	private Font fontBlackLeft() {
		Font headerFontBlack = workbook.createFont();
        headerFontBlack.setBold(true);
        headerFontBlack.setFontHeightInPoints(FONT_SIZE);
        headerFontBlack.setColor(BLACK_COLOR);
        return headerFontBlack;
	}

}

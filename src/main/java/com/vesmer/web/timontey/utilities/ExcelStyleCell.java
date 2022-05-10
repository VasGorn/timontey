package com.vesmer.web.timontey.utilities;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
		
	public CellStyle getHeaderBlackCenter() {
		if (headerBlackCenter != null) {
			return headerBlackCenter;
		}
        headerBlackCenter =  workbook.createCellStyle();
        headerBlackCenter.setAlignment(HorizontalAlignment.CENTER);
        headerBlackCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        headerBlackCenter.setFont(getHeaderFontBlack());
        setAllBorder(headerBlackCenter, BorderStyle.THIN);
        return headerBlackCenter;
	}
		
	private Font getHeaderFontGreen() {
		Font headerFontGreen = workbook.createFont();
        headerFontGreen.setBold(true);
        headerFontGreen.setFontHeightInPoints((short) 12);
        headerFontGreen.setColor(IndexedColors.GREEN.getIndex());
        return headerFontGreen;
	}
		
	private Font getHeaderFontBlack() {
		Font headerFontBlack = workbook.createFont();
        headerFontBlack.setBold(true);
        headerFontBlack.setFontHeightInPoints((short) 12);
        headerFontBlack.setColor(IndexedColors.BLACK.getIndex());
        return headerFontBlack;
	}
		
	private void setAllBorder(CellStyle cellStyle, BorderStyle borderStyle){
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setBorderRight(borderStyle);
        cellStyle.setBorderLeft(borderStyle);
    }

}

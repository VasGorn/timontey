package com.vesmer.web.timontey.reports.money.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class GreenBackgroundCellStyle extends ExcelCellStyle{

	public GreenBackgroundCellStyle(Workbook workbook) {
		super(workbook);
	}

	@Override
	protected CellStyle createCellStyle() {
		CellStyle fillGreen = workbook.createCellStyle();
        fillGreen.setAlignment(HorizontalAlignment.CENTER);
        fillGreen.setVerticalAlignment(VerticalAlignment.CENTER);
        fillGreen.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        fillGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setAllBorder(fillGreen, BorderStyle.THIN);
        return fillGreen;
	}

}

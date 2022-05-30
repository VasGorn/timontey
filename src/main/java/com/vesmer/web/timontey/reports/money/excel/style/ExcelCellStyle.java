package com.vesmer.web.timontey.reports.money.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class ExcelCellStyle {
	protected final Workbook workbook;
	
	public ExcelCellStyle(Workbook workbook) {
		this.workbook = workbook;
	}
	
	public abstract CellStyle cellStyle();
}

package com.vesmer.web.timontey.reports.money.excel.style;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class ExcelCellStyle {
	protected final Workbook workbook;
	protected final List<CellStyle> cachedStyle = new ArrayList<>(1);
	
	public ExcelCellStyle(Workbook workbook) {
		this.workbook = workbook;
	}
	
	public abstract CellStyle cellStyle();
}

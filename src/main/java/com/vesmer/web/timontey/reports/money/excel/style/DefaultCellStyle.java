package com.vesmer.web.timontey.reports.money.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public final class DefaultCellStyle extends ExcelCellStyle{
	
	public DefaultCellStyle(Workbook workbook) {
		super(workbook);
	}

	@Override
	protected CellStyle createCellStyle() {
		return this.workbook.createCellStyle();
	}
}

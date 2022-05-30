package com.vesmer.web.timontey.reports.money.excel.style;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public final class DefaultCellStyle extends ExcelCellStyle{
	private final List<CellStyle> cachedStyle = new ArrayList<>(1);
	
	public DefaultCellStyle(Workbook workbook) {
		super(workbook);
	}

	@Override
	public CellStyle cellStyle() {
		if(cachedStyle.isEmpty()) {
			CellStyle main = this.workbook.createCellStyle();
			this.cachedStyle.add(main);
			return main;
		}
		return this.cachedStyle.get(0);
	}
}

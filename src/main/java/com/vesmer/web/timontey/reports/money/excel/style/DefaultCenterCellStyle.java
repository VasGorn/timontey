package com.vesmer.web.timontey.reports.money.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class DefaultCenterCellStyle extends ExcelCellStyle{

	public DefaultCenterCellStyle(Workbook workbook) {
		super(workbook);
	}

	@Override
	protected CellStyle createCellStyle() {
		CellStyle defaultCenter =  workbook.createCellStyle();
        defaultCenter.setAlignment(HorizontalAlignment.CENTER);
        defaultCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        setAllBorder(defaultCenter, BorderStyle.THIN);
        return defaultCenter;
	}

}

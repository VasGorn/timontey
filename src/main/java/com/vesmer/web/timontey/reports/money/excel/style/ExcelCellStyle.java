package com.vesmer.web.timontey.reports.money.excel.style;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class ExcelCellStyle {
	protected final Workbook workbook;
	protected final List<CellStyle> cachedStyle = new ArrayList<>(1);
	
	public ExcelCellStyle(Workbook workbook) {
		this.workbook = workbook;
	}
	
	public CellStyle cellStyle() {
		if(this.cachedStyle.isEmpty()) {
			this.cachedStyle.add(createCellStyle()); 
		}
		return this.cachedStyle.get(0);
	}

	protected abstract CellStyle createCellStyle();
		
	protected void setAllBorder(CellStyle cellStyle, BorderStyle borderStyle){
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setBorderRight(borderStyle);
        cellStyle.setBorderLeft(borderStyle);
    }

}

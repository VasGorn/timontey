package com.vesmer.web.timontey.utilities;

import org.apache.poi.ss.usermodel.CellStyle;
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

}

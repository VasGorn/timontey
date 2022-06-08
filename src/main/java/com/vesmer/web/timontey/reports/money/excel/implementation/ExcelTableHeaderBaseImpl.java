package com.vesmer.web.timontey.reports.money.excel.implementation;

import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.vesmer.web.timontey.reports.money.excel.ExcelTableHeader;
import com.vesmer.web.timontey.reports.money.excel.style.ExcelCellStyle;
import com.vesmer.web.timontey.reports.money.excel.style.HeaderBlackCenterCellStyle;

public final class ExcelTableHeaderBaseImpl implements ExcelTableHeader{
	private final ExcelCellStyle headerBlackCenter;
	
	private final int rowStartIndex;
	private final int columnStartIndex;
	private final int maxColumnWidth;
	private final String expensesTitle;
	
	public ExcelTableHeaderBaseImpl(Workbook workbook) {
		this(new HeaderBlackCenterCellStyle(workbook),
				6,
				1,
				2000,
				"Expenses");
	}

	public ExcelTableHeaderBaseImpl(ExcelCellStyle headerBlackCenter, int rowStartIndex,
			int columnStartIndex, int maxColumnWidth, String expensesTitle) {
		this.headerBlackCenter = headerBlackCenter;
		this.rowStartIndex = rowStartIndex;
		this.columnStartIndex = columnStartIndex;
		this.maxColumnWidth = maxColumnWidth;
		this.expensesTitle = expensesTitle;
	}

	@Override
	public void writeTableHeader(Sheet sheet, short numMonth, short year) {
		CellStyle cellStyle = headerBlackCenter.cellStyle();

       	int daysInMonth = getDaysInMonth(numMonth, year);
       	String month = Month.values()[numMonth - 1].getDisplayName(TextStyle.FULL, Locale.getDefault());
		
		sheet.addMergedRegion(new CellRangeAddress(6, 7, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, daysInMonth));

		Row tableHeaderRow = sheet.createRow(rowStartIndex);
        setStyleInRow(tableHeaderRow, daysInMonth, cellStyle);
        writeToCell(tableHeaderRow, 0, expensesTitle, cellStyle);
        writeToCell(tableHeaderRow, 1, month, cellStyle);
        
        Row daysRow = sheet.createRow(rowStartIndex + 1);
        setStyleInRow(daysRow, daysInMonth, cellStyle);
		int columnIndex = columnStartIndex;
		for(int i = 1; i <= daysInMonth; ++i) {
			writeToCell(daysRow, columnIndex, i, cellStyle);
			sheet.setColumnWidth(columnIndex, maxColumnWidth);
            ++columnIndex;
		}
	}
			
	private void writeToCell(Row row, int numColumn, String value, CellStyle style) {
		Cell cell = row.createCell(numColumn);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
		
	private void writeToCell(Row row, int numColumn, int value, CellStyle style) {
		Cell cell = row.createCell(numColumn);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
			
	private int getDaysInMonth(short numMonth, short year) {
		YearMonth yearMonth = YearMonth.of(year, numMonth);
		return yearMonth.lengthOfMonth();
	}
		
	private void setStyleInRow(Row row, int maxNumColumn, CellStyle cellStyle){
        for(int i = 0; i <= maxNumColumn; ++i){
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
        }
    }
}

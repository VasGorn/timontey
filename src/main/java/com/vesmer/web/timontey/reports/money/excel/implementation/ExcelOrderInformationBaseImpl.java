package com.vesmer.web.timontey.reports.money.excel.implementation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.reports.money.excel.ExcelOrderInformation;
import com.vesmer.web.timontey.reports.money.excel.style.DefaultCellStyle;
import com.vesmer.web.timontey.reports.money.excel.style.ExcelCellStyle;
import com.vesmer.web.timontey.reports.money.excel.style.HeaderGreenCellStyle;

public final class ExcelOrderInformationBaseImpl implements ExcelOrderInformation {
	private final QuotaMoney quotaMoney;
	private final ExcelCellStyle headerStyle;
	private final ExcelCellStyle defaultStyle;
	private final int rowStartIndex;
	private final int columnStartIndex;
	private final int maxColumnWidth;

	public ExcelOrderInformationBaseImpl(Workbook workbook, QuotaMoney quotaMoney) {
		this(quotaMoney, 
				new HeaderGreenCellStyle(workbook), 
				new DefaultCellStyle(workbook), 
				1, 
				0, 
				5000);
	}

	public ExcelOrderInformationBaseImpl(QuotaMoney quotaMoney, 
			ExcelCellStyle headerStyle, ExcelCellStyle defaultStyle,
			int rowStartIndex, int columnStartIndex, int maxColumnWidth) {
		this.quotaMoney = quotaMoney;
		this.headerStyle = headerStyle;
		this.defaultStyle = defaultStyle;
		this.rowStartIndex = rowStartIndex;
		this.columnStartIndex = columnStartIndex;
		this.maxColumnWidth = maxColumnWidth;
	}

	@Override
	public void writeOrderInformation(Sheet sheet) {
		sheet.setColumnWidth(columnStartIndex, maxColumnWidth);
		Map<String, String> headerMap = headerMap();
		int i = 1;
		for (Map.Entry<String, String> element : headerMap.entrySet()) {
			Row row = sheet.createRow(rowStartIndex + i);
			writeToCell(row, columnStartIndex, element.getKey(), headerStyle.cellStyle());
			writeToCell(row, columnStartIndex + 1, element.getValue(), defaultStyle.cellStyle());
			++i;
		}
	}

	private void writeToCell(Row row, int numColumn, String value, CellStyle style) {
		Cell cell = row.createCell(numColumn);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}

	private Map<String, String> headerMap() {
		Map<String, String> headerMap = new LinkedHashMap<String, String>(4);
		Order order = quotaMoney.getOrder();
		headerMap.put("ORDER", order.toString());
		headerMap.put("ADDRESS", order.getAddress());
		headerMap.put("DESCRIPTION", order.getDescription());
		headerMap.put("PERFORMER", quotaMoney.getEmployee().toString());
		return headerMap;
	}
}

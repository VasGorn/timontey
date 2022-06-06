package com.vesmer.web.timontey.reports.money.excel.implementation;

import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.vesmer.web.timontey.domain.Expenses;
import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.reports.money.excel.ExcelExpenses;
import com.vesmer.web.timontey.reports.money.excel.style.DefaultCellStyle;
import com.vesmer.web.timontey.reports.money.excel.style.DefaultCenterCellStyle;
import com.vesmer.web.timontey.reports.money.excel.style.ExcelCellStyle;
import com.vesmer.web.timontey.reports.money.excel.style.GreenBackgroundCellStyle;

public final class ExcelExpensesBaseImpl implements ExcelExpenses{
	private final MoneySpend moneySpend;
	private final ExcelCellStyle approveMoneyStyle;
	private final ExcelCellStyle notApproveMoneyStyle;
	private final ExcelCellStyle defaultStyle;
	private final int rowStartIndex;
	private final int columnStartIndex;
	
	public ExcelExpensesBaseImpl(Workbook workbook, MoneySpend moneySpend) {
		this(moneySpend,
				new GreenBackgroundCellStyle(workbook),
				new DefaultCenterCellStyle(workbook),
				new DefaultCellStyle(workbook),
				8, 
				0);
	}

	public ExcelExpensesBaseImpl(MoneySpend moneySpend, ExcelCellStyle approveMoneyStyle,
			ExcelCellStyle notApproveMoneyStyle, ExcelCellStyle defaultStyle, 
			int rowStartIndex, int columnStartIndex) {
		this.moneySpend = moneySpend;
		this.approveMoneyStyle = approveMoneyStyle;
		this.notApproveMoneyStyle = notApproveMoneyStyle;
		this.defaultStyle = defaultStyle;
		this.rowStartIndex = rowStartIndex;
		this.columnStartIndex = columnStartIndex;
	}

	@Override
	public void writeExpenses(Sheet sheet) {
		int rowNum = rowStartIndex;
		Set<Short> daySet = moneySpend.getUniqueDays();
		Set<Expenses> expensesSet = moneySpend.getUniqueExpense();
		for(Expenses e: expensesSet) {
			Row expenseRow = sheet.createRow(rowNum);
			writeToCell(expenseRow, columnStartIndex, e.getName(), defaultStyle.cellStyle());
			for(Short numDay: daySet) {
				float moneySum = moneySpend.getSumMoney(e, numDay);
				if(moneySum < 0.01f) {
					continue;
				}

				if(moneySpend.isSumApprove(e, numDay)) {
					writeToCell(expenseRow, numDay, moneySum, approveMoneyStyle.cellStyle());
				} else {
					writeToCell(expenseRow, numDay, moneySum, notApproveMoneyStyle.cellStyle());
				}
			}
			++rowNum;
		}
	}
		
	private void writeToCell(Row row, int numColumn, String value, CellStyle style) {
		Cell cell = row.createCell(numColumn);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
		
	private void writeToCell(Row row, int numColumn, double value, CellStyle style) {
		Cell cell = row.createCell(numColumn);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
}

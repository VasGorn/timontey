package com.vesmer.web.timontey.view;

import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.vesmer.web.timontey.domain.Expenses;
import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.domain.ReportMoney;
import com.vesmer.web.timontey.utilities.ExcelStyleCell;

public class ExcelMoneyView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, 
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReportMoney reportMoney = (ReportMoney) model.get("reportMoney");
		String filename = reportMoney.getName();
        response.setHeader("Content-Disposition", "attachment; filename=\""
        		+ filename + ".xls\"");
        
        List<MoneySpend> moneySpendList = reportMoney.getMoneySpendList();
        ExcelStyleCell styleCell = new ExcelStyleCell(workbook);
        
        for(MoneySpend mSpend: moneySpendList) {
   	     	Sheet sheet = workbook.createSheet(mSpend.getQuotaMoney()
   	     			.getEmployee().toString());
        	sheet.setColumnWidth(0, 5000);
        	writeOrderInformation(sheet, mSpend.getQuotaMoney(), styleCell);
        	
        	if (mSpend.getMoneyExpenseList().size() < 1) {
        		continue;
        	}
   	      	short numMonth = (short) (reportMoney.getNumMonth() + 1);
        	short year = reportMoney.getYear();
        	writeTableHeader(sheet, year, numMonth, styleCell);
        	writeData(sheet, mSpend, styleCell);
        }
	}
		
	private void writeOrderInformation(Sheet sheet, QuotaMoney quotaMoney, ExcelStyleCell style) {
		Row row;
		
		row = sheet.createRow(1);
		writeToCell(row, 0, Header.ORDER.getString(), style.getHeaderGreen());
		writeToCell(row, 1, quotaMoney.getOrder().getName(), style.getMain());

		row = sheet.createRow(2);
		writeToCell(row, 0, Header.ADDRESS.getString(), style.getHeaderGreen());
		writeToCell(row, 1, quotaMoney.getOrder().getAddress(), style.getMain());

		row = sheet.createRow(3);
		writeToCell(row, 0, Header.DESCRIPTION.getString(), style.getHeaderGreen());
		writeToCell(row, 1, quotaMoney.getOrder().getDescription(), style.getMain());

		row = sheet.createRow(4);
		writeToCell(row, 0, Header.PERFORMER.getString(), style.getHeaderGreen());
		writeToCell(row, 1, quotaMoney.getEmployee().toString(), style.getMain());
	}
	
		
	private void writeTableHeader(Sheet sheet, short year, short numMonth, ExcelStyleCell style) {
		CellStyle headerBlackCenter = style.getHeaderBlackCenter();
       	int daysInMonth = getDaysInMonth(numMonth, year);
       	String month = Month.values()[numMonth - 1].getDisplayName(TextStyle.FULL, Locale.getDefault());
		Row tableHeaderRow = sheet.createRow(6);
		
		sheet.addMergedRegion(new CellRangeAddress(6, 7, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, daysInMonth));
        
        setStyleInRow(tableHeaderRow, daysInMonth, headerBlackCenter);
        writeToCell(tableHeaderRow, 0, "Expenses", headerBlackCenter);
        writeToCell(tableHeaderRow, 1, month, headerBlackCenter);
        
        Row daysRow = sheet.createRow(7);
        setStyleInRow(daysRow, daysInMonth, headerBlackCenter);
		int columnCount = 1;
		for(int i = 1; i <= daysInMonth; ++i) {
			writeToCell(daysRow, columnCount, i, headerBlackCenter);
			sheet.setColumnWidth(columnCount, 2000);
            ++columnCount;
		}
	}
		
	private void writeData(Sheet sheet, MoneySpend mSpend, 
			ExcelStyleCell style) {
		int rowNum = 8;
		Set<Short> daySet = mSpend.getUniqueDays();
		Set<Expenses> expensesSet = mSpend.getUniqueExpense();
		for(Expenses e: expensesSet) {
			Row expenseRow = sheet.createRow(rowNum);
			writeToCell(expenseRow, 0, e.getName(), style.getMain());
			for(Short numDay: daySet) {
				float moneySum = mSpend.getSumMoney(e, numDay);
				if(moneySum < 0.01f) {
					continue;
				}

				if(mSpend.isSumApprove(e, numDay)) {
					writeToCell(expenseRow, numDay, moneySum, style.getFillGreen());
				} else {
					writeToCell(expenseRow, numDay, moneySum, style.getBlackCenter());
				}
			}
			++rowNum;
		}
	}
	
	private void setStyleInRow(Row row, int maxNumColumn, CellStyle cellStyle){
        for(int i = 0; i <= maxNumColumn; ++i){
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
        }
    }
		
	private int getDaysInMonth(short numMonth, short year) {
		YearMonth yearMonth = YearMonth.of(year, numMonth);
		return yearMonth.lengthOfMonth();
	}
	
	private enum Header{
		ORDER("ORDER"),
		ADDRESS("ADDRESS"),
		DESCRIPTION("DESCRIPTION"),
		PERFORMER("PERFORMER");
		
		private String str;
		
		Header(String str) {
			this.str = str;
		}
		
		public String getString() {
			return str;
		}
	}

}

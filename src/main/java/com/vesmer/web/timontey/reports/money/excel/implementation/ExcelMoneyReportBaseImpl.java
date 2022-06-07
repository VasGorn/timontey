package com.vesmer.web.timontey.reports.money.excel.implementation;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.domain.ReportMoney;
import com.vesmer.web.timontey.reports.money.excel.ExcelExpenses;
import com.vesmer.web.timontey.reports.money.excel.ExcelMoneyReport;
import com.vesmer.web.timontey.reports.money.excel.ExcelOrderInformation;
import com.vesmer.web.timontey.reports.money.excel.ExcelTableHeader;

public final class ExcelMoneyReportBaseImpl implements ExcelMoneyReport {
	private final Workbook workbook;
	private final ReportMoney reportMoney;
	private final ExcelOrderInformation excelOrderInformation;
	private final ExcelTableHeader excelTableHeader;
	private final ExcelExpenses excelExpenses;

	public ExcelMoneyReportBaseImpl(Workbook workbook, ReportMoney reportMoney,
			ExcelOrderInformation excelOrderInformation,
			ExcelTableHeader excelTableHeader,
			ExcelExpenses excelExpenses) {
		this.workbook = workbook;
		this.reportMoney = reportMoney;
		this.excelOrderInformation = excelOrderInformation;
		this.excelTableHeader = excelTableHeader;
		this.excelExpenses = excelExpenses;
	}
	
	@Override
	public Workbook excelReport() {
		List<MoneySpend> moneySpendList = reportMoney.getMoneySpendList();
        
        for(MoneySpend mSpend: moneySpendList) {
        	QuotaMoney quotaMoney = mSpend.getQuotaMoney();
   	     	Sheet sheet = workbook.createSheet(quotaMoney.getEmployee().toString());

   	     	excelOrderInformation.writeOrderInformation(sheet);
        	
        	if (mSpend.getMoneyExpenseList().size() < 1) {
        		continue;
        	}
        	excelTableHeader.writeTableHeader(sheet);
        	excelExpenses.writeExpenses(sheet);
        }
        
        return workbook;
	}
}

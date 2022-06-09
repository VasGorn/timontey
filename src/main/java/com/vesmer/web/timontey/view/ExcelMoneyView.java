package com.vesmer.web.timontey.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.vesmer.web.timontey.domain.ReportMoney;
import com.vesmer.web.timontey.reports.money.excel.ExcelExpenses;
import com.vesmer.web.timontey.reports.money.excel.ExcelMoneyReport;
import com.vesmer.web.timontey.reports.money.excel.ExcelOrderInformation;
import com.vesmer.web.timontey.reports.money.excel.ExcelTableHeader;
import com.vesmer.web.timontey.reports.money.excel.implementation.ExcelExpensesBaseImpl;
import com.vesmer.web.timontey.reports.money.excel.implementation.ExcelMoneyReportBaseImpl;
import com.vesmer.web.timontey.reports.money.excel.implementation.ExcelOrderInformationBaseImpl;
import com.vesmer.web.timontey.reports.money.excel.implementation.ExcelTableHeaderBaseImpl;

public class ExcelMoneyView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, 
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReportMoney reportMoney = (ReportMoney) model.get("reportMoney");
		String filename = reportMoney.getName();
        response.setHeader("Content-Disposition", "attachment; filename=\""
        		+ filename + ".xls\"");
       
        ExcelOrderInformation orderInfo = 
        		new ExcelOrderInformationBaseImpl(workbook);
        ExcelTableHeader header = 
        		new ExcelTableHeaderBaseImpl(workbook);
        ExcelExpenses expenses = 
        		new ExcelExpensesBaseImpl(workbook);
        ExcelMoneyReport report = 
        		new ExcelMoneyReportBaseImpl(workbook, 
        				reportMoney, orderInfo, header, expenses);
        workbook = report.excelReport();
	}
}

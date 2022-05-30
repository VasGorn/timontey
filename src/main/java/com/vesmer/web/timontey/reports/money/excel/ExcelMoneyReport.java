package com.vesmer.web.timontey.reports.money.excel;

import org.apache.poi.ss.usermodel.Workbook;

import com.vesmer.web.timontey.domain.ReportMoney;

public interface ExcelMoneyReport {
	Workbook excelReport(ReportMoney reportMoney);
}

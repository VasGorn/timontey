package com.vesmer.web.timontey.reports.money.excel;

import org.apache.poi.ss.usermodel.Sheet;

import com.vesmer.web.timontey.domain.QuotaMoney;

public interface ExcelOrderInformation {
	void writeOrderInformation(Sheet sheet, QuotaMoney quotaMoney);
}

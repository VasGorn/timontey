package com.vesmer.web.timontey.reports.money.excel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.domain.QuotaMoney;

public interface ExcelOrderInformation {
	void writeOrderInformation(Sheet sheet, Map<String, String> headerMap);
	
	final class Smart{
		private final ExcelOrderInformation excelOrderInformation;
		
		public Smart(ExcelOrderInformation excelOrderInformation) {
			this.excelOrderInformation = excelOrderInformation; 
		}

		public void writeOrderInformation(Sheet sheet, QuotaMoney quotaMoney) {
			Map<String, String> headerMap = headerMap(quotaMoney);
			this.excelOrderInformation.writeOrderInformation(sheet, headerMap);
		}

		private Map<String, String> headerMap(QuotaMoney quotaMoney) {
			Map<String, String> headerMap = new LinkedHashMap<String, String>(4);
			Order order = quotaMoney.getOrder();
			headerMap.put("ORDER", order.toString());
			headerMap.put("ADDRESS", order.getAddress());
			headerMap.put("DESCRIPTION", order.getDescription());
			headerMap.put("PERFORMER", quotaMoney.getEmployee().toString());
			return headerMap;
		}
	}
}

package com.vesmer.web.timontey.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.vesmer.web.timontey.domain.MoneySpend;
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

}

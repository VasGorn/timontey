<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Money report</h1>

<form action="report-money/excelReportMoney" method="get" id="form_report_money">
	<fieldset>
		
		<div class="row">
    		<div class="form-group col-md-6">
				<label for="select_order" class="form-label">Order:</label> 
				<select class="form-select" id="select_order" name="orderId">
					<option selected disabled hidden value="0">Please select order...</option>
				</select>
    		</div>
    		
    		<div class="form-group col-md-6">
				<label for="manager_name" class="form-label">Manager:</label> 
				<input readonly type="text" class="form-control" id="manager_name"
				value="${managerName}"> 
				<input type="hidden" id="manager_id" value="${managerId}">
    		</div>
  		</div>
  		
		<hr>

		<div class="mb-3">
			<label for="description" class="form-label">Description:</label>
			<textarea class="form-control" id="description" readonly
				rows="4" placeholder="Select order..." required></textarea>
		</div>
		<div class="mb-3">
			<label for="address" class="form-label">Address:</label> <input
				type="text" class="form-control" id="address" readonly
				placeholder="Select order...">
		</div>

		<hr>
		
		<div class="row">
    		<div class="form-group col-md-6">
				<label for="select_year" class="form-label">Year:</label>
				<select class="form-select" id="select_year" name="year">
					<c:forEach items="${years}" var="year">
						<option value="${year}">${year}</option>
					</c:forEach>
				</select>
    		</div>
    		
    		<div class="form-group col-md-6">
    			<label for="select_month" class="form-label">Month:</label> 
    			<select class="form-select" id="select_month" name="numMonth">
					<c:forEach items="${months}" var="month">
						<option value="${month.key}">${month.value}</option>
					</c:forEach>
				</select>
    		</div>
  		</div>
		
		<div class="mb-3">
			<label for="file_name" class="form-label">File name:</label> 
			<input type="text" class="form-control" id="filename" name="filename" 
				placeholder="Enter a file name...">
		</div>

		<button id="btn_create_report" type="submit" class="btn btn-success" disabled>Create report</button>
	</fieldset>
</form>

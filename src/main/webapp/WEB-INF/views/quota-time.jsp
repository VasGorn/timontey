<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Quota of time</h1>

<form>
	<fieldset>
		<div class="mb-3">
			<label for="select_order" class="form-label">Order:</label> <select
				class="form-select" id="select_order">
				<option selected disabled hidden value="0">Please select order...</option>
			</select>
		</div>

		<hr>

		<div class="mb-3">
			<label for="order_name" class="form-label">Order name:</label> <input
				type="text" class="form-control" id="order_name" name="name" readonly
				placeholder="Select order...">
		</div>
		<div class="mb-3">
			<label for="manager_name" class="form-label">Manager:</label> <input
				readonly type="text" class="form-control" id="manager_name"
				value="${managerName}"> <input type="hidden" id="manager_id"
				name="id" value="${managerId}">
		</div>
		<div class="mb-3">
			<label for="description" class="form-label">Description:</label>
			<textarea class="form-control" id="description" name="description" readonly
				rows="4" placeholder="Select order..." required></textarea>
		</div>
		<div class="mb-3">
			<label for="address" class="form-label">Address:</label> <input
				type="text" class="form-control" id="address" name="address" readonly
				placeholder="Select order...">
		</div>

		<hr>
		
		<div class="row" style="padding-bottom: 10px;">
    		<div class="form-group col-md-6">
				<label for="select_role" class="form-label">Role:</label> <select
					class="form-select" id="select_role">
					<option selected disabled hidden value="0">Please select role...</option>
				</select>
    		</div>
    		
    		<div class="form-group col-md-6">
				<label for="select_employee" class="form-label">Employee:</label> <select
					class="form-select" id="select_employee" disabled>
					<option value="0" selected disabled hidden>Please select employee...</option>
				</select>
    		</div>
		</div>    		

		<div class="mb-3">
			<label for="select_work_type" class="form-label">Work type:</label> <select
				class="form-select" id="select_work_type" disabled>
				<option value="0" selected disabled hidden>Please select work type...</option>
			</select>
		</div>
	
		<div class="row" style="padding-bottom: 25px;">
    		<div class="form-group col-md-4">
				<label for="select_year" class="form-label">Year:</label> <select
					class="form-select" id="select_year">
					<c:forEach items="${years}" var="year">
						<option value="${year}">${year}</option>
					</c:forEach>
				</select>
    		</div>
    		
    		<div class="form-group col-md-4">
				<label for="select_month" class="form-label">Month:</label> <select
					class="form-select" id="select_month">
					<c:forEach items="${months}" var="month">
						<option value="${month.key}">${month.value}</option>
					</c:forEach>
				</select>
    		</div>
    		
    		<div class="form-group col-md-4">
				<label for="hours_limit" class="form-label">Time limit: 
				</label> <input type="number" name="stepTime" step="1" min="0"
					max="100000" value="100" class="form-control" id="hours_limit"
					required>
    		</div>
 
  		</div>
		
		<button id="add" type="button" class="btn btn-success" disabled>Add</button>
		<button id="update" type="button" class="btn btn-primary" disabled>Update</button>
		<button id="delete" type="button" class="btn btn-danger" disabled>Delete</button>
	</fieldset>
</form>

<c:if test="${not empty message}">
	<p>"${message}"</p>
</c:if>

<table id="table" data-toggle="table" data-height="600"
	data-click-to-select="true" data-search="true">
	<thead>
		<tr>
			<th data-field="state" data-checkbox="true"></th>
			<th data-field="employee">Employee</th>
			<th data-field="workType">Work type</th>
			<th data-field="hours">Time limit</th>
		</tr>
	</thead>
</table>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Work day approve</h1>

<form>
	<fieldset>
		<input type="hidden" id="manager_id" name="managerId" value="${managerId}">
		<input type="hidden" id="year" name="year" value="${year}">
		<input type="hidden" id="num_month" name="numMonth" value="${numMonth}">

		<div class="mb-3">
			<label for="select_order" class="form-label">Order:</label> <select
				class="form-select" id="select_order">
				<option selected disabled hidden value="0">Please select order...</option>
			</select>
		</div>
		
		<div class="row">
    		<div class="form-group col-md-6">
      			<label for="order_balance_hours">Order time balance, hours</label>
      			<input type="number" class="form-control" id="order_balance_hours" readonly>
    		</div>
    		
    		<div class="form-group col-md-6">
      			<label for="order_limit_hours">Order limit time, hours</label>
      			<input type="number" class="form-control" id="order_limit_hours" readonly>
    		</div>
  		</div>

		<hr>

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
		
		<div class="mb-3">
			<label for="select_employee" class="form-label">Performer:</label> <select
				class="form-select" id="select_employee" disabled>
				<option value="0" selected disabled hidden>Please select performer...</option>
			</select>
		</div>
		
		<hr>
		
		<div class="row">
    		<div class="form-group col-md-4">
      			<label for="select_day_of_month">Day of month:</label>
				<select class="form-select" id="select_day_of_month">
					<option value="0" selected disabled hidden>Select day...</option>
				</select>
    		</div>
    		
    		<div class="form-group col-md-8">
      			<label for="select_worktype">Work type:</label>
				<select class="form-select" id="select_worktype">
				<option value="0" selected disabled hidden>Select work type...</option>
				</select>
    		</div>
  		</div>
  		
		<hr>
  		
  		<div class="row">
    		<div class="form-group col-md-6">
      			<label for="balance_hours">Time balance, hours</label>
      			<input type="number" class="form-control" id="balance_hours" readonly>
    		</div>
    		
    		<div class="form-group col-md-6">
      			<label for="limit_hours">Limit time, hours</label>
      			<input type="number" class="form-control" id="limit_hours" readonly>
    		</div>
  		</div>
  		
		<hr>
		
		<div class="row mb-3">
    		<div class="form-group col-md-6">
      			<label for="select_work_hours">Time, hours</label>
				<select class="form-select" id="select_work_hours">
					<c:forEach items="${hoursList}" var="hour">
						<option value="${hour}">${hour}</option>
					</c:forEach>
				</select>
    		</div>
    		
    		<div class="form-group col-md-6">
      			<label for="select_overtime">Overtime, hours</label>
				<select class="form-select" id="select_overtime">
					<c:forEach items="${overtimeList}" var="hour">
						<option value="${hour}" ${hour == 0 ? 'selected="selected"' : ''}>${hour}</option>
					</c:forEach>
				</select>
    		</div>
  		</div>
  		
  		<button id="approve" type="button" class="btn btn-success" disabled>Approve</button>
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
			<th data-field="workHours">Work hours</th>
			<th data-field="overtime">Overtime</th>
			<th data-field="approve">Approve</th>
		</tr>
	</thead>
</table>


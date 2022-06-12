<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Quota of money</h1>

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
					class="form-select" id="select_employee">
					<option value="0" selected disabled hidden>Please select employee...</option>
				</select>
    		</div>
    	</div>	

		<div class="mb-3">
			<label for="money_limit" class="form-label">Money limit: 
			</label> <input type="number" name="stepTime" step="0.01" min="0.00"
				max="100000000.00" value="10000.00" class="form-control" id="money_limit"
				required>
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
			<th data-field="order">Order</th>
			<th data-field="employee">Employee</th>
			<th data-field="moneyLimit">Money limit</th>
		</tr>
	</thead>
</table>

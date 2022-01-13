<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Spending of money</h1>

<form>
	<fieldset>
		<input type="hidden" id="employee_id" name="employeeId" value="${employeeId}">

		<div class="mb-3">
			<label for="select_order" class="form-label">Order:</label> <select
				class="form-select" id="select_order">
				<option selected disabled hidden value="0">Please select order...</option>
			</select>
		</div>
		
		<div class="row">
    		<div class="form-group col-md-6">
      			<label for="order_balance_hours">Order money balance, UAN</label>
      			<input type="number" class="form-control" id="order_balance_money"
      			 step="0.01" min="0.00" max="100000000.00" readonly>
    		</div>
    		
    		<div class="form-group col-md-6">
      			<label for="order_limit_hours">Order money limit, UAN</label>
      			<input type="number" class="form-control" id="order_limit_money"
      			 step="0.01" min="0.00" max="100000000.00" readonly>
    		</div>
  		</div>

		<hr>

		<div class="mb-3">
			<label for="manager_name" class="form-label">Manager:</label> <input
				readonly type="text" class="form-control" id="manager_name"
				placeholder="Select order..."> 
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
		
		<div class="mb-3">
			<label for="select_expenses" class="form-label">Expenses type:</label> <select
				class="form-select" id="select_expenses">
			<option selected disabled hidden value="0">Please select expenses...</option>
			</select>
   		</div>
    		
		<div class="mb-3">
   			<label for="money_spend">Amount of money, UAN</label>
   			<input type="number" class="form-control" id="money_spend"
   			 step="0.01" min="0.00" max="100000000.00" value="2000.00" >
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
			<th data-field="expenses">Expenses</th>
			<th data-field="date">Date</th>
			<th data-field="money">Amount of money</th>
			<th data-field="approve" data-checkbox="false">Approve</th>
		</tr>
	</thead>
</table>

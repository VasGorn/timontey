<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>User list management</h1>

<form id="user_form" action="/timontey/rest/expenses/" method="post">
	<fieldset>
		<div class="mb-3">
			<label for="username" class="form-label">Username:</label> <input
				id="username" type="text" class="form-control" name="username"
				placeholder="Please enter username..." required>
		</div>

		<div class="mb-3">
			<label for="password" class="form-label">Password: </label> <input
				type="password" class="form-control" id="password" name="password"
				placeholder="Please enter password..." required>
		</div>

		<div class="mb-3">
			<label for="confirm_password" class="form-label">Repeat
				password: </label> <input type="password" class="form-control"
				id="confirm_password" placeholder="Please repeat password..."
				required>
		</div>

		<fieldset class="row mb-3">
			<legend class="col-form-label">Choose role:</legend>
			<div id="check_role">

			</div>
		</fieldset>

		<div class="mb-3">
			<label for="select_employee" class="form-label">Employee:</label> <select
				class="form-select" id="select_employee">
				<option value="0" selected disabled hidden>Please select employee...</option>
			</select>
		</div>

		<button id="add" type="button" class="btn btn-success">Add</button>
		<button id="update" type="button" class="btn btn-primary">Update</button>
		<button id="delete" type="button" class="btn btn-danger">Delete</button>
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
			<th data-field="username">Username</th>
			<th data-field="employee">Employee</th>
			<th data-field="role">Role</th>
		</tr>
	</thead>
</table>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Expenses</h1>

<form id="expenses_form" action="/timontey/rest/expenses/" method="post">
	<fieldset>
		<div class="mb-3">
			<input type="hidden" name="id" class="form-control" value="0">
			<label for="name" class="form-label">Type of expenses:</label> <input
				type="text" name="name" class="form-control" id="name"
				placeholder="Please enter type name..." required>
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
			<th data-field="id">ID</th>
			<th data-field="name">NAME</th>
		</tr>
	</thead>
</table>

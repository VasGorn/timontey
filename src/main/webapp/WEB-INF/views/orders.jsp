<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Orders list</h1>

<form>
	<fieldset>
		<div class="mb-3">
			<label for="order_name" class="form-label">Order name:</label> <input
				type="text" class="form-control" id="order_name" name="name"
				placeholder="Please enter order name..." required>
		</div>
		<div class="mb-3">
			<label for="manager_name" class="form-label">Manager:</label> <input readonly
				type="text" class="form-control" id="manager_name" value="${managerName}">
				<input type="hidden" id="manager_id" name="id" value="${managerId}">
		</div>
		<div class="mb-3">
			<label for="description" class="form-label">Description:</label> <textarea
				class="form-control" id="description" name="description" rows="4"
				placeholder="Please enter description..." required></textarea>
		</div>
		<div class="mb-3">
			<label for="address" class="form-label">Address:</label> <input
				type="text" class="form-control" id="address" name="address"
				placeholder="Please enter address..." required>
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
			<th data-field="name">Order name</th>
			<th data-field="description">Description</th>
			<th data-field="address">Address</th>
		</tr>
	</thead>
</table>

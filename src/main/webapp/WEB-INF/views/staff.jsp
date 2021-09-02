<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Staff list</h1>

<form id="staff_form" action="/timontey/rest/staff/" method="post">
	<fieldset>
		<div class="mb-3">
			<label for="last_name" class="form-label">Last name:</label> <input
				type="text" class="form-control" id="last_name" name="lastName"
				placeholder="Please enter last name..." required>
		</div>
		<div class="mb-3">
			<label for="first_name" class="form-label">First name:</label> <input
				type="text" class="form-control" id="first_name" name="firstName"
				placeholder="Please enter first name..." required>
		</div>
		<div class="mb-3">
			<label for="middle_name" class="form-label">Middle name:</label> <input
				type="text" class="form-control" id="middle_name" name="middleName"
				placeholder="Please middle name..." required>
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
			<th data-field="lastName">Last name</th>
			<th data-field="firstName">First name</th>
			<th data-field="middleName">Middle name</th>
		</tr>
	</thead>
</table>



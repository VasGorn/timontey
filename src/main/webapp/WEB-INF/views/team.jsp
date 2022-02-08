<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Team list</h1>

<form>
	<fieldset>
		<div class="mb-3">
			<label for="performer_name" class="form-label">Performer:</label> <input readonly
				type="text" class="form-control" id="performer_name" value="${performerName}">
				<input type="hidden" id="performer_id" name="id" value="${performerId}">
		</div>
	
		<div class="mb-3">
			<label for="select_employee" class="form-label">Employee:</label> <select
				class="form-select" id="select_employee">
				<option selected disabled hidden>Please select employee...</option>
			</select>
		</div>

		<button id="add" type="button" class="btn btn-success" disabled>Add</button>
		<button id="delete" type="button" class="btn btn-danger" disabled>Delete</button>

	</fieldset>
</form>

<c:if test="${not empty message}">
	<p>"${message}"</p>
</c:if>

<table id="table" data-toggle="table" data-height="500"
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


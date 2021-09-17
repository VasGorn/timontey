<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Work type list</h1>

<form>
	<fieldset>
		<div class="mb-3">
			<label for="select_role" class="form-label">Role:</label> <select
				class="form-select" id="role_select">
				<option selected disabled hidden>Please select role...</option>
			</select>
		</div>

		<div class="mb-3">
			<input type="hidden" name="id" class="form-control" value="0">
			<label for="work_type" class="form-label">Work type name:</label> <input
				type="text" name="workTypeName" class="form-control" id="work_type"
				placeholder="Please enter work type name..." required>
		</div>

		<button id="add" type="button" class="btn btn-success">Add</button>
		<button id="update" type="button" class="btn btn-primary">Update</button>
		<button id="delete" type="button" class="btn btn-danger">Delete</button>

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
			<th data-field="id">ID</th>
			<th data-field="workTypeName">NAME</th>
		</tr>
	</thead>
</table>




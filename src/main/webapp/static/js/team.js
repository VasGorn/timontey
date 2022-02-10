var btnAdd = document.getElementById("add");
var btnDelete = document.getElementById("delete");

var hPerformerId = document.getElementById("performer_id");
var selectEmployee = document.getElementById("select_employee");

var URL_REST_TEAM = "/timontey/rest/team";
var URL_REST_STAFF = "/timontey/rest/staff";

var $table = $('#table');
var checkedRows = [];

$table.bootstrapTable({ data: [] });

setEmployeesToSelect();
loadTeamToTable(parseInt(hPerformerId.value));

btnAdd.addEventListener("click", btnAddClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

selectEmployee.addEventListener("change", (event) => {
	setButtonsState(checkedRows.length);
});

function setEmployeesToSelect() {
	$.ajax({
		type: "GET",
		url: URL_REST_STAFF + "/all",
		data: null,
		success: function(employeeArray) {
			console.log(employeeArray);
			
			for(let i = 0; i < employeeArray.length; ++i){
				let employee = employeeArray[i];
				const opt = document.createElement("option");
				opt.value = employee.id;
				opt.innerHTML = employee.lastName + " " + employee.firstName
								+ " " + employee.middleName;
				selectEmployee.appendChild(opt);
			}
		}
	});
}

function loadTeamToTable(performerId) {
	$.ajax({
		type: "GET",
		url: URL_REST_TEAM + "/performer/" + performerId,
		data: null,
		success: function(team) {
			let employeeArray = team.employeeList;
			$table.bootstrapTable('load', employeeArray);
		}
	});
}

function btnAddClicked() {
	let employeeId = parseInt(selectEmployee.value);
	let performerId = parseInt(hPerformerId.value);
	
	if (isExist(employeeId)) {
		alert("Employee already in team!");
		return;
	}
	
	if (employeeId <= 0) {
		return;
	}

	let team = getTeamObject(performerId, employeeId);
	
	postRequest(team);
}

function btnDeleteClicked() {
	let performerId = parseInt(hPerformerId.value);
	let employeeIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id
	});

	for (let i = 0; i < employeeIds.length; ++i) {
		console.log(employeeIds[i]);
		deleteRequest(performerId, employeeIds[i]);
	}
}

function postRequest(newTeam) {
	$.ajax({
		type: "POST",
		url: URL_REST_TEAM,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(newTeam),
		success: function(team) {
			let employeeArray = team.employeeList;
			$table.bootstrapTable('load', employeeArray);
		}
	});
}

function deleteRequest(performerId, employeeId){
	$.ajax({
		type: "DELETE",
		url: URL_REST_TEAM + "/performer/" + performerId + "/employee/" + employeeId,
		async: true,
		data: null,
		success: function() {
			let array = [];
			array.push(employeeId);
			$table.bootstrapTable('remove', {
				field: 'id',
				values: array 
			});
			
			deleteCheckedRow(employeeId, checkedRows);
			setButtonsState(checkedRows.length);
		}
	});
}

function isExist(employeeId) {
	let tableData = $table.bootstrapTable('getData');
	let isExist = false;
	tableData.forEach((element, index) => {
		if (element.id === employeeId) {
			isExist = true;
		}
	});
	return isExist;
}

function getTeamObject(performerId, employeeId) {
	let performer = new Object();
	performer.id = performerId;
	
	let employeeList = [];
	let employee = new Object();
	employee.id = employeeId;
	employeeList.push(employee);
	
	let team = new Object();
	team.performer = performer;
	team.employeeList = employeeList;

	return team;
}

function setButtonsState(numRowSelected){
	if (numRowSelected < 1) {
		btnAdd.disabled = false;
		btnUpdate.disabled = true;
		btnDelete.disabled = true;
		return;
	}
	if (numRowSelected === 1) {
		btnAdd.disabled = true;
		btnUpdate.disabled = false;
		btnDelete.disabled = false;
		return;
	}
	if (numRowSelected > 1) {
		btnAdd.disabled = true;
		btnUpdate.disabled = true;
		btnDelete.disabled = false;
	}
}

function deleteCheckedRow(id, checkedRows){
	for(let i = 0; i < checkedRows.length; ++i){
		if(id === checkedRows[i].id){
			checkedRows.splice(i,1);
		}
	}
}

$table.on('check.bs.table', function(e, row) {
	checkedRows.push(row);
	setButtonsState(checkedRows.length);
	
	selectEmployee.value = row.id;
	console.log(checkedRows);
});

$table.on('uncheck.bs.table', function(e, row) {
	checkedRows.forEach((element, index) => {
		if (element.id === row.id) {
			checkedRows.splice(index, 1);
		}
	});
	setButtonsState(checkedRows.length);
	
	console.log(checkedRows);
});

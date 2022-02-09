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

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

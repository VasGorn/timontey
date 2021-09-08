var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var txtLastName = document.getElementById("last_name");
var txtFirstName = document.getElementById("first_name");
var txtMiddleName = document.getElementById("middle_name");

var URL_REST = "/timontey/rest/staff/";

var $table = $('#table');
var checkedRows = [];

btnUpdate.disabled = true;
btnDelete.disabled = true;

getDataForTable();

function getDataForTable() {
	$.ajax({
		type: "GET",
		url: URL_REST + "all",
		data: null,
		success: function(resp) {
			console.log(resp);
			setDataToTable(resp);
		}
	});
}

function setDataToTable(data) {
	$table.bootstrapTable({ data: data })
}

function isInTable(lastName, firstName, middleName) {
	let tableData = $table.bootstrapTable('getData');
	let isExist = false;
	tableData.forEach((element, index) => {
		if (element.lastName === lastName &&
			element.firstName === firstName &&
			element.middleName === middleName ) {
			isExist = true;
		}
	});
	return isExist;
}

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

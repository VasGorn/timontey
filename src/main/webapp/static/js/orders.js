var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var txtOrderName = document.getElementById("order_name");
var hiddenManagerId = document.getElementById("manager_id");
var txtDescription = document.getElementById("description");
var txtAddress = document.getElementById("address");

var URL_REST_ORDERS = "/timontey/rest/orders";

var $table = $('#table');
var checkedRows = [];

btnUpdate.disabled = true;
btnDelete.disabled = true;

getDataForTable(hiddenManagerId.value);

function getDataForTable(managerID) {
	$.ajax({
		type: "GET",
		url: URL_REST_ORDERS + "/" + managerID,
		data: null,
		success: function(resp) {
			console.log(resp);
			$table.bootstrapTable({ data: resp });
		}
	});
}

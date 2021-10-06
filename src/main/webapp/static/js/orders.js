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

btnAdd.addEventListener("click", btnAddClicked, false);

getDataForTable(hiddenManagerId.value);

function btnAddClicked() {
	let orderName = txtOrderName.value.trim();
	let description = txtDescription.value;
	let address = txtAddress.value.trim();
	
	let isExist = isInTable(orderName);
	if (isExist) {
		alert("This order name already exist!");
		return;
	}
	
	let manager = new Object();
	manager.id = parseInt(hiddenManagerId.value);
	
	let order = new Object();
	order.name = orderName;
	order.manager = manager;
	order.description = description;
	order.address = address;

	postDataToServer(order);
}

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

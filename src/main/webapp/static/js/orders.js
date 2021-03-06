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
btnUpdate.addEventListener("click", btnUpdateClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

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

function btnUpdateClicked() {
	let orderName = txtOrderName.value.trim();
	let description = txtDescription.value;
	let address = txtAddress.value;
	
	let order = checkedRows[0];
	let index = findIndexInTable(order);
	
	if(orderName == order.name && 
		description == order.description && 
		address == order.address){
		alert("Order not changed!");
		return;
	}
	
	order.name = orderName;
	order.description = description;
	order.address = address;

	putDataToServer(order, index);
}

function btnDeleteClicked() {
	let orderIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
	
	clearTextFields();
	
	for (let i = 0; i < orderIds.length; ++i) {
		console.log(orderIds[i]);
		deleteDataOnServer(orderIds[i]);
	}
}

function postDataToServer(order) {
	$.ajax({
		type: "POST",
		url: URL_REST_ORDERS,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(order),
		success: function(resp) {
			console.log(resp);

			let rows = [];
			rows.push(resp);
			$table.bootstrapTable('append', rows);
			$table.bootstrapTable('scrollTo', 'bottom');
			
			clearTextFields();
		}
	});
}

function putDataToServer(order, index) {
	$.ajax({
		type: "PUT",
		url: URL_REST_ORDERS,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(order),
		success: function(resp) {
			console.log(resp);

			$table.bootstrapTable('updateRow', {
				index: index,
				row: resp
			});
		}
	});
}

function deleteDataOnServer(orderId) {
	$.ajax({
		type: "DELETE",
		url: URL_REST_ORDERS + "/" + orderId,
		async: true,
		data: null,
		success: function() {
			let array = [];
			array.push(orderId);
			$table.bootstrapTable('remove', {
				field: 'id',
				values: array 
			});
			
			for(let i = 0; i < checkedRows.length; ++i){
				if(orderId === checkedRows[i].id){
					checkedRows.splice(i,1);
				}
			}
			
			setButtonDisabled(checkedRows.length);
		}
	});
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

function isInTable(orderName) {
	let tableData = $table.bootstrapTable('getData');
	let isExist = false;
	tableData.forEach((element, index) => {
		if (element.name === orderName) {
			isExist = true;
		}
	});
	return isExist;
}

function findIndexInTable(row) {
	let tableData = $table.bootstrapTable('getData');
	let index = 0;

	for (let i = 0; i < tableData.length; ++i) {
		if (row.id === tableData[i].id) {
			index = i;
			break;
		}
	}
	return index;
}

function clearTextFields(){
	txtOrderName.value = "";
	txtDescription.value = "";
	txtAddress.value = "";
}

$table.on('check.bs.table', function(e, row) {
	checkedRows.push(row);
	setButtonDisabled(checkedRows.length);
	txtOrderName.value = row.name;
	txtDescription.value = row.description;
	txtAddress.value = row.address;
	console.log(checkedRows);
});

$table.on('uncheck.bs.table', function(e, row) {
	checkedRows.forEach((element, index) => {
		if (element.id === row.id) {
			checkedRows.splice(index, 1);
		}
	});
	setButtonDisabled(checkedRows.length);
	clearTextFields();
	console.log(checkedRows);
});

function setButtonDisabled(len) {
	if (len < 1) {
		btnAdd.disabled = false;
		btnUpdate.disabled = true;
		btnDelete.disabled = true;
		return;
	}
	if (len === 1) {
		btnAdd.disabled = true;
		btnUpdate.disabled = false;
		btnDelete.disabled = false;
		return;
	}
	if (len > 1) {
		btnAdd.disabled = true;
		btnUpdate.disabled = true;
		btnDelete.disabled = false;
	}
}

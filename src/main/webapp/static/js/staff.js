var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var txtLastName = document.getElementById("last_name");
var txtFirstName = document.getElementById("first_name");
var txtMiddleName = document.getElementById("middle_name");

var URL_REST = "/timontey/rest/staff/";

var $table = $('#table');
var checkedRows = [];

btnAdd.addEventListener("click", btnAddClicked, false);
btnUpdate.addEventListener("click", btnUpdateClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

btnUpdate.disabled = true;
btnDelete.disabled = true;

getDataForTable();

function btnAddClicked() {
	let lastName = txtLastName.value;
	let firstName = txtFirstName.value;
	let middleName = txtMiddleName.value;
	
	let isExist = isInTable(lastName, firstName, middleName);
	if (isExist) {
		alert("This value already exist!");
		return;
	}

	let employee = getFormData($('#staff_form'));
	console.log(employee);
	postDataToServer(employee);
}

function btnUpdateClicked() {
	let newLastName = txtLastName.value;
	let newFirstName = txtFirstName.value;
	let newMiddleName = txtMiddleName.value;
	
	let item = checkedRows[0];
	let index = findIndexInTable(item);

	let isExist = isInTable(newLastName, newFirstName, newMiddleName);
	if (isExist) {
		alert("This value already exist!");
		return;
	}

	item.lastName = newLastName;
	item.firstName = newFirstName;
	item.middleName = newMiddleName;

	patchDataToServer(item, index);
}

function postDataToServer(employee) {
	$.ajax({
		type: "POST",
		url: URL_REST,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(employee),
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

function patchDataToServer(employee, index) {
	$.ajax({
		type: "PATCH",
		url: URL_REST + employee.id,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(employee),
		success: function(resp) {
			console.log(resp);

			$table.bootstrapTable('updateRow', {
				index: index,
				row: resp
			});
		}
	});
}

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

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

function clearTextFields(){
	txtLastName.value = "";
	txtFirstName.value = "";
	txtMiddleName.value = "";
}

$table.on('check.bs.table', function(e, row) {
	checkedRows.push({ id: row.id, lastName: row.lastName, firstName: row.firstName,
					middleName: row.middleName});
	setButtonDisabled(checkedRows.length);
	txtLastName.value = row.lastName;
	txtFirstName.value = row.firstName;
	txtMiddleName.value = row.middleName;
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

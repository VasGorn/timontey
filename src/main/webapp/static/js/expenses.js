var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");
var textName = document.getElementById("name");

var URL_REST = "/timontey/rest/expenses/";

var $table = $('#table');
var checkedRows = [];

btnUpdate.disabled = true;
btnDelete.disabled = true;

btnAdd.addEventListener("click", btnAddClicked, false);
btnUpdate.addEventListener("click", btnUpdateClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

getDataForTable();

function btnAddClicked() {
	let stringName = textName.value;
	let isExist = isInTable(stringName);

	if (isExist) {
		alert("This value already exist!");
		return;
	}

	let jsonData = getFormData($('#expenses_form'));
	console.log(jsonData);
	postDataToServer(jsonData);
}

function btnUpdateClicked() {
	let newName = textName.value;
	let item = checkedRows[0];
	let index = findIndexInTable(item);

	let isExist = isInTable(newName);
	if (isExist) {
		alert("This value already exist!");
		return;
	}

	item.name = newName;

	patchDataToServer(item, index)
}

function btnDeleteClicked() {
	let ids = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id
	});

	for (let i = 0; i < ids.length; ++i) {
		console.log(ids[i]);
		deleteDataOnServer(ids[i]);
	}
	textName.value = "";	
}

function postDataToServer(json) {
	$.ajax({
		type: "POST",
		url: URL_REST,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(json),
		success: function(resp) {
			console.log(resp);

			let rows = [];
			rows.push(resp);
			$table.bootstrapTable('append', rows);
			$table.bootstrapTable('scrollTo', 'bottom');
		}
	});
}

function patchDataToServer(json, index) {
	$.ajax({
		type: "PATCH",
		url: URL_REST + json.id,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(json),
		success: function(resp) {
			console.log(resp);

			$table.bootstrapTable('updateRow', {
				index: index,
				row: resp
			});
		}
	});
}

function deleteDataOnServer(id) {
	$.ajax({
		type: "DELETE",
		url: URL_REST + id,
		async: true,
		data: null,
		success: function() {
			let array = [];
			array.push(id);
			$table.bootstrapTable('remove', {
				field: 'id',
				values: array 
			});
			
			checkedRows=[];
			setButtonDisabled(checkedRows.length);
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

function isInTable(name) {
	let tableData = $table.bootstrapTable('getData');
	let isExist = false;
	tableData.forEach((element, index) => {
		if (element.name === name) {
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

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

$table.on('check.bs.table', function(e, row) {
	checkedRows.push({ id: row.id, name: row.name });
	setButtonDisabled(checkedRows.length);
	textName.value = row.name;
	console.log(checkedRows);
});

$table.on('uncheck.bs.table', function(e, row) {
	checkedRows.forEach((element, index) => {
		if (element.id === row.id) {
			checkedRows.splice(index, 1);
		}
	});
	setButtonDisabled(checkedRows.length);
	textName.value = "";
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

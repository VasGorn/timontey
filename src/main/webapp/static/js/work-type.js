var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var roleSelect = document.getElementById("role_select");
var txtWorkType = document.getElementById("work_type");

var URL_REST_ROLE = "/timontey/rest/role";
var URL_REST_WORK = "/timontey/rest/work-type";

var ROLES = [];

var $table = $('#table');
var checkedRows = [];

$table.bootstrapTable({ data: [] });

btnAdd.disabled = true;
btnUpdate.disabled = true;
btnDelete.disabled = true;

btnAdd.addEventListener("click", btnAddClicked, false);
btnUpdate.addEventListener("click", btnUpdateClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

setDataToSelectRole();

function btnAddClicked() {
	let stringName = txtWorkType.value.trim();
	let isExist = isInTable(stringName);
	let roleId = parseInt(roleSelect.value);

	if (isExist) {
		alert("This value already exist!");
		return;
	}
	
	if (stringName.length < 3){
		alert("Name must be larger then 3 characters!");
		return;
	}

	let workType = new Object();
	workType.workTypeName = stringName;
	
	postDataToServer(workType, roleId);
}

function btnUpdateClicked() {
	let newName = txtWorkType.value;
	let roleId = parseInt(roleSelect.value);
	let item = checkedRows[0];
	let index = findIndexInTable(item);

	let isExist = isInTable(newName);
	if (isExist) {
		alert("This value already exist!");
		return;
	}

	item.workTypeName = newName;
	console.log("to sent: " + item);

	patchDataToServer(item, roleId, index)
}

function btnDeleteClicked() {
	let roleId = parseInt(roleSelect.value);
	let workTypeIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id
	});

	for (let i = 0; i < workTypeIds.length; ++i) {
		console.log(workTypeIds[i]);
		deleteDataOnServer(roleId, workTypeIds[i]);
	}
}

roleSelect.addEventListener("change", (event) => {
	let roleId = parseInt(event.target.value);
	let workTypes = [];
		
	btnAdd.disabled = false;
	btnUpdate.disabled = true;
	btnDelete.disabled = true;

	for(let i = 0; i < ROLES.length; ++i){
		if(roleId === ROLES[i].id){
			workTypes = ROLES[i].workTypes;
			break;
		}
	}
	$table.bootstrapTable('load', workTypes);
	checkedRows = [];
	txtWorkType.value = "";
});

function postDataToServer(workType, roleId) {
	$.ajax({
		type: "POST",
		url: URL_REST_WORK + "/role/" + roleId,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(workType),
		success: function(resp) {
			let data = [];
			
			for(let i = 0; i < ROLES.length; ++i){
				if(roleId === ROLES[i].id){
					ROLES[i].workTypes.push(resp);
					data = ROLES[i].workTypes;
					break;
				}
			}
			$table.bootstrapTable('load', data);
			$table.bootstrapTable('scrollTo', 'bottom');
			txtWorkType.value = "";
		}
	});
}

function patchDataToServer(workType, roleId, index) {
	let workTypeId = workType.id;
	$.ajax({
		type: "PATCH",
		url: URL_REST_WORK + "/" + workTypeId + "/role/" + roleId,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(workType),
		success: function(resp) {
			console.log(resp);

			$table.bootstrapTable('updateRow', {
				index: index,
				row: resp
			});
		}
	});
}

function deleteDataOnServer(roleId, workTypeId) {
	$.ajax({
		type: "DELETE",
		url: URL_REST_WORK + "/" + workTypeId + "/role/" + roleId,
		async: true,
		data: null,
		success: function() {
			let array = [];
			array.push(workTypeId);
			$table.bootstrapTable('remove', {
				field: 'id',
				values: array 
			});
			
			checkedRows=[];
			txtWorkType.value = "";
			setButtonDisabled(checkedRows.length);
		}
	});
}

function setDataToSelectRole(){
	$.ajax({
		type: "GET",
		url: URL_REST_ROLE + "/all",
		data: null,
		success: function(resp) {
			console.log(resp);
			ROLES = resp;
			
			for(let i = 0; i < ROLES.length; ++i){
				const opt = document.createElement("option");
				opt.value = ROLES[i].id;
				opt.innerHTML = ROLES[i].roleName;
				roleSelect.appendChild(opt);
				
				setWorkTypesToRole(ROLES[i]);
			}
		}
	});
}

function setWorkTypesToRole(role){
	$.ajax({
		type: "GET",
		url: URL_REST_WORK + "/role/" + role.id,
		data: null,
		success: function(resp) {
			role.workTypes = resp;
		}
	});
}

function isInTable(name) {
	let tableData = $table.bootstrapTable('getData');
	let isExist = false;
	tableData.forEach((element, index) => {
		if (element.workTypeName === name) {
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

$table.on('check.bs.table', function(e, row) {
	checkedRows.push({ id: row.id, workTypeName: row.workTypeName });
	setButtonDisabled(checkedRows.length);
	txtWorkType.value = row.workTypeName;
	console.log(checkedRows);
});

$table.on('uncheck.bs.table', function(e, row) {
	checkedRows.forEach((element, index) => {
		if (element.id === row.id) {
			checkedRows.splice(index, 1);
		}
	});
	setButtonDisabled(checkedRows.length);
	txtWorkType.value = "";
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

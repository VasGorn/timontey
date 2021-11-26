var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var selectOrder = document.getElementById("select_order");
var txtOrderName = document.getElementById("order_name");
var hiddenManagerId = document.getElementById("manager_id");
var txtDescription = document.getElementById("description");
var txtAddress = document.getElementById("address");

var selectRole = document.getElementById("select_role");
var selectEmployee = document.getElementById("select_employee");

var numMoneyLimit = document.getElementById("money_limit");

var URL_REST_ROLE = "/timontey/rest/role";
var URL_REST_EMPLOYEE = "/timontey/rest/staff";
var URL_REST_ORDER = "/timontey/rest/orders";
var URL_REST_QUOTA_MONEY = "/timontey/rest/quota-money";

var ROLES = [];
var ORDERS = [];

var $table = $('#table');
var checkedRows = [];

btnAdd.addEventListener("click", btnAddClicked, false);
btnUpdate.addEventListener("click", btnUpdateClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

$table.bootstrapTable({ data: [] });

setOrdersToSelect();
setRolesToSelect();
setQuotaMoneyToTable(hiddenManagerId.value);

selectRole.addEventListener("change", (event) => {
	let roleId = parseInt(event.target.value);
	let staff = [];
		
	btnAdd.disabled = true;
	btnUpdate.disabled = true;
	btnDelete.disabled = true;

	for(let i = 0; i < ROLES.length; ++i){
		if(roleId === ROLES[i].id){
			staff = ROLES[i].staff;
			break;
		}
	}
	
	removeStaffFromSelect();
	setStaffToSelect(staff);
	
	$table.bootstrapTable('uncheckAll');
	checkedRows = [];
});

selectOrder.addEventListener("change", (event) => {
	let orderId = parseInt(event.target.value);
	
	setOrderInfo(orderId);
		
	btnAdd.disabled = true;
	btnUpdate.disabled = true;
	btnDelete.disabled = true;

	$table.bootstrapTable('uncheckAll');
	checkedRows = [];
	setButtonsState();
});

selectEmployee.addEventListener("change", (event) => {
	$table.bootstrapTable('uncheckAll');
	checkedRows = [];

	setButtonsState();
});

function btnAddClicked(){
	let newQuotaMoneyRow = getQuotaMoneyFromForm();

	if(isInTable(newQuotaMoneyRow)){
		alert("This employee already have money limit on the selected order!");
		return;
	}
	
	if(newQuotaMoneyRow.moneyLimit < 0.0){
		alert("Money limit must be greater then zero!");
		return;
	}
	
	let quotaMoney = getModelQuotaMoney(newQuotaMoneyRow);
	postDataToServer(quotaMoney);
}

function btnUpdateClicked(){
	let newQuotaMoneyRow = getQuotaMoneyFromForm();
	let oldQuotaMonetRow = checkedRows[0];

	let index = findIndexInTable(oldQuotaMonetRow);
	
	if(newQuotaMoneyRow.orderId != oldQuotaMonetRow.orderId){
		alert("Order cannot be changed!");
		return;
	}
	
	if(newQuotaMoneyRow.employeeId != oldQuotaMonetRow.employeeId){
		alert("Employee cannot be changed!");
		return;
	}
	
	if(newQuotaMoneyRow.moneyLimit < 0.0){
		alert("Money limit must be greater then zero!");
		return;
	}
	
	let quotaMoney = getModelQuotaMoney(newQuotaMoneyRow);
	quotaMoney.id = oldQuotaMonetRow.id;
	putDataToServer(quotaMoney, index);
}

function btnDeleteClicked(){
	let quotaMoneyIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
	
	for (let i = 0; i < quotaMoneyIds.length; ++i) {
		deleteDataOnServer(quotaMoneyIds[i]);
	}
}

function postDataToServer(quotaMoney){
	$.ajax({
		type: "POST",
		url: URL_REST_QUOTA_MONEY,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(quotaMoney),
		success: function(resp) {
			let rows = [];
			let quotaMoneyRow = qMoneyRowMapper(resp);
			rows.push(quotaMoneyRow);
			$table.bootstrapTable('append', rows);
			$table.bootstrapTable('scrollTo', 'bottom');
		}
	});
}

function putDataToServer(quotaMoney, index){
	$.ajax({
		type: "PUT",
		url: URL_REST_QUOTA_MONEY + '/' + quotaMoney.id,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(quotaMoney),
		success: function(resp) {
			console.log(resp);
			let quotaMoneyRow = qMoneyRowMapper(resp);

			$table.bootstrapTable('updateRow', {
				index: index,
				row: quotaMoneyRow 
			});
		}
	});
}

function deleteDataOnServer(quotaMoneyId) {
	$.ajax({
		type: "DELETE",
		url: URL_REST_QUOTA_MONEY + '/' + quotaMoneyId,
		async: true,
		data: null,
		success: function() {
			let array = [];
			array.push(quotaMoneyId);
			$table.bootstrapTable('remove', {
				field: 'id',
				values: array 
			});
			
			for(let i = 0; i < checkedRows.length; ++i){
				if(quotaMoneyId === checkedRows[i].id){
					checkedRows.splice(i,1);
				}
			}
			
			setButtonsState(checkedRows.length);
		}
	});
}

function setOrdersToSelect(){
	let managerId = hiddenManagerId.value;
	
	$.ajax({
		type: "GET",
		url: URL_REST_ORDER + "/" + managerId,
		data: null,
		success: function(resp) {
			ORDERS = resp;
			
			for(let i = 0; i < ORDERS.length; ++i){
				const opt = document.createElement("option");
				opt.value = ORDERS[i].id;
				opt.innerHTML = ORDERS[i].name;
				selectOrder.appendChild(opt);
			}
		}
	});
}

function setRolesToSelect(){
	$.ajax({
		type: "GET",
		url: URL_REST_ROLE + "/all",
		data: null,
		success: function(resp) {
			ROLES = resp;
			
			for(let i = 0; i < resp.length; ++i){
				const opt = document.createElement("option");
				opt.value = resp[i].id;
				opt.innerHTML = resp[i].roleName;
				selectRole.appendChild(opt);
				
				setEmployeesToRole(ROLES[i]);
			}
		}
	});
}

function setEmployeesToRole(role){
	$.ajax({
		type: "GET",
		url: URL_REST_EMPLOYEE + "/role/" + role.id,
		data: null,
		success: function(resp) {
			role.staff = resp;
		}
	});
}

function setQuotaMoneyToTable(managerId){
	$.ajax({
		type: "GET",
		url: URL_REST_QUOTA_MONEY + "/manager/" + managerId,
		data: null,
		success: function(resp) {
			let qMoneyRowArray = [];
			for(let i = 0; i < resp.length; ++i){
				let quotaMoney = resp[i];
				let quotaMoneyRow = qMoneyRowMapper(quotaMoney);
				qMoneyRowArray.push(quotaMoneyRow);
			}
			
			$table.bootstrapTable('append', qMoneyRowArray);
		}
	});
}

function qMoneyRowMapper(quotaMoney){
	let quotaMoneyRow = new Object();
	quotaMoneyRow.id = quotaMoney.id;

	quotaMoneyRow.order = quotaMoney.order.name;
	quotaMoneyRow.orderId = quotaMoney.order.id;

	quotaMoneyRow.employee	= quotaMoney.employee.lastName + ' ' 
							+ quotaMoney.employee.firstName + ' '
							+ quotaMoney.employee.middleName;
	quotaMoneyRow.employeeId = quotaMoney.employee.id;
	
	quotaMoneyRow.moneyLimit = quotaMoney.moneyLimit;
	return quotaMoneyRow;
}

function getQuotaMoneyFromForm(){
	let newQuotaMoneyRow = new Object();
	newQuotaMoneyRow.orderId = selectOrder.value;
	newQuotaMoneyRow.employeeId = selectEmployee.value;
	newQuotaMoneyRow.managerId = hiddenManagerId.value;

	let moneyLimit = parseFloat(numMoneyLimit.value);
	moneyLimit = Math.round(moneyLimit * 100) / 100;
	numMoneyLimit.value = moneyLimit;
	
	newQuotaMoneyRow.moneyLimit = moneyLimit;
	
	return newQuotaMoneyRow;
}

function isInTable(quotaMoneyRow){
	let tableData = $table.bootstrapTable('getData');
	let isExist = false;
	tableData.forEach((element, index) => {
		if (element.orderId == quotaMoneyRow.orderId &&
			element.employeeId == quotaMoneyRow.employeeId) {
			isExist = true;
		}
	});
	return isExist;
}

function getModelQuotaMoney(quotaMoneyRow){
	let quotaMoney = new Object();
	let employee = new Object();
	let order = new Object();
	
	order.id = quotaMoneyRow.orderId;
	employee.id = quotaMoneyRow.employeeId;
	
	quotaMoney.order = order;
	quotaMoney.employee = employee;
	quotaMoney.moneyLimit = quotaMoneyRow.moneyLimit;

	return quotaMoney;
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

function setButtonsState(checkedRowsLength){
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(selectEmployee.value);
	
	if(orderId == 0 || employeeId == 0){
		btnAdd.disabled = true;
		btnUpdate.disabled = true;
		btnDelete.disabled = true;
		return;
	} 
	
	if (checkedRowsLength < 1) {
		btnAdd.disabled = false;
		btnUpdate.disabled = true;
		btnDelete.disabled = true;
		return;
	}
	if (checkedRowsLength === 1) {
		btnAdd.disabled = true;
		btnUpdate.disabled = false;
		btnDelete.disabled = false;
		return;
	}
	if (checkedRowsLength > 1) {
		btnAdd.disabled = true;
		btnUpdate.disabled = true;
		btnDelete.disabled = false;
	}

}

function removeStaffFromSelect(){
	const length = selectEmployee.options.length;
	selectEmployee.value = 0;
	
	for(let i = 1; i < length; ++i){
		selectEmployee.removeChild(selectEmployee.options[1]);
	}
}

function setStaffToSelect(staff){
	for(let i = 0; i < staff.length; ++i){
		const opt = document.createElement("option");
		opt.value = staff[i].id;
		opt.innerHTML = staff[i].lastName + ' ' + staff[i].firstName + ' ' + staff[i].middleName;
		selectEmployee.appendChild(opt);
	}
}

function setOrderInfo(orderId){
	let order;
	for(let i = 0; i < ORDERS.length; ++i){
		if(orderId === ORDERS[i].id){
			order = ORDERS[i];
			break;
		}
	}

	txtOrderName.value = order.name;
	txtDescription.value = order.description;
	txtAddress.value = order.address;
}

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

$table.bootstrapTable({ data: [] });

setOrdersToSelect();
setRolesToSelect();
setQuotaMoneyToTable(hiddenManagerId.value);

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

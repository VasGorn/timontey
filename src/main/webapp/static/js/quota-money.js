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

$table.bootstrapTable({ data: [] });

setOrdersToSelect();
setRolesToSelect();
setQuotaMoneyToTable(hiddenManagerId.value);

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

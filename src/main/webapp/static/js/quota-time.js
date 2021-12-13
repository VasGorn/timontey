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
var selectWorkType = document.getElementById("select_work_type");
var selectYear = document.getElementById("select_year");
var selectMonth = document.getElementById("select_month");
var numHours = document.getElementById("hours_limit");

var numMoneyLimit = document.getElementById("money_limit");

var URL_REST_ROLE = "/timontey/rest/role";
var URL_REST_EMPLOYEE = "/timontey/rest/staff";
var URL_REST_WORK = "/timontey/rest/work-type";
var URL_REST_ORDER = "/timontey/rest/orders";
var URL_REST_QUOTA_TIME = "/timontey/rest/quota-time";

var ROLES = [];
var ORDERS = [];

var $table = $('#table');
var checkedRows = [];

$table.bootstrapTable({ data: [] });

setOrdersToSelect();
setRolesToSelect();
selectCurrentMonth();

selectOrder.addEventListener("change", (event) => {
	let orderId = parseInt(event.target.value);
	let numMonth = parseInt(selectMonth.value) + 1;
	let year = parseInt(selectYear.value);
	
	setOrderInfo(orderId);

	$table.bootstrapTable('uncheckAll');
	checkedRows = [];
	setButtonsState(checkedRows.length);
	setSelectsState();
	setQuotaTimeToTable(orderId, numMonth, year);
});

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
				setWorkTypes(ROLES[i]);
			}
		}
	});
}

function selectCurrentMonth(){
	const date = new Date();
	selectMonth.value = date.getMonth();
}

function setEmployeesToRole(role){
	$.ajax({
		type: "GET",
		url: URL_REST_EMPLOYEE + "/role/" + role.id,
		data: null,
		success: function(resp) {
			resp.forEach((element) => {
				element.toString = element.lastName + ' ' 
								 + element.firstName + ' ' 
								 + element.middleName;
			});
			role.staff = resp;
		}
	});
}

function setWorkTypes(role){
	$.ajax({
		type: "GET",
		url: URL_REST_WORK + "/role/" + role.id,
		data: null,
		success: function(resp) {
			resp.forEach((element) => {
				element.toString = element.workTypeName; 
			});
			role.workTypes = resp;
		}
	});
}

var btnApprove = document.getElementById("approve");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var hManagerId = document.getElementById("manager_id");
var hYear = document.getElementById("year");
var hNumMonth = document.getElementById("num_month");

var selectOrder = document.getElementById("select_order");
var txtDescription = document.getElementById("description");
var txtAddress = document.getElementById("address");

var numOrderBalanceTime = document.getElementById("order_balance_hours");
var numOrderLimitTime = document.getElementById("order_limit_hours");

var selectEmployee = document.getElementById("select_employee");
var numWorkTypeBalanceTime = document.getElementById("balance_hours");
var numWorkTypeLimitTime = document.getElementById("limit_hours");

var selectDayOfMonth = document.getElementById("select_day_of_month");
var selectWorkType = document.getElementById("select_worktype");
var selectWorkHour = document.getElementById("select_work_hours");
var selectOvertime = document.getElementById("select_overtime");

var $table = $('#table');

var URL_REST_QUOTA_TIME = "/timontey/rest/quota-time";
var URL_REST_STAFF = "/timontey/rest/staff";
var URL_REST_WORK_DAY = "/timontey/rest/work-day";

var QUOTA_TIME_ARRAY = [];

var checkedRows = [];

$table.bootstrapTable({ data: [] });

setOrdersToSelect(parseInt(hManagerId.value), parseInt(hYear.value),
				  parseInt(hNumMonth.value));
	
function setOrdersToSelect(managerId, year, numMonth){
	console.log(numMonth);
	$.ajax({
		type: "GET",
		url: URL_REST_QUOTA_TIME + "/manager/" + managerId + "/month/" + numMonth 
								 + "/year/" + year,
		data: null,
		success: function(quotaTimeArray) {
			QUOTA_TIME_ARRAY = quotaTimeArray;
			
			let ordersArray = getUniqueOrders(quotaTimeArray);
			let employeeArray = getUniqueEmployees(quotaTimeArray);
			
			for (let i = 0; i < ordersArray.length; ++i) {
				const opt = document.createElement("option");
				opt.value = ordersArray[i].id;
				opt.innerHTML = ordersArray[i].name;
				selectOrder.appendChild(opt);
			}
			
			for (let j = 0; j < employeeArray.length; ++j) {
				let employeeId = employeeArray[j].id;
				setHoursSpend(employeeId, year, numMonth);
			}
			console.log(QUOTA_TIME_ARRAY);
		}
	});
}

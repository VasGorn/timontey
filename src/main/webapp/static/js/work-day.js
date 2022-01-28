var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var hEmployeeId = document.getElementById("employee_id");
var hYear = document.getElementById("year");
var hNumMonth = document.getElementById("num_month");

var selectOrder = document.getElementById("select_order");
var txtManagerName = document.getElementById("manager_name");
var txtDescription = document.getElementById("description");
var txtAddress = document.getElementById("address");

var numOrderBalanceTime = document.getElementById("order_balance_hours");
var numOrderLimitTime = document.getElementById("order_limit_hours");

var selectWorkType = document.getElementById("select_work_type");
var numWorkTypeBalanceTime = document.getElementById("balance_hours");
var numWorkTypeLimitTime = document.getElementById("limit_hours");

var selectDayOfMonth = document.getElementById("select_day_of_month");
var selectEmployee = document.getElementById("select_employee");
var selectWorkHour = document.getElementById("select_work_hours");
var selectOvertime = document.getElementById("select_overtime");

var $table = $('#table');

var URL_REST_QUOTA_TIME = "/timontey/rest/quota-time";
var URL_REST_TEAM = "/timontey/rest/team";
var URL_REST_WORK_DAY = "/timontey/rest/work-day";

var QUOTA_TIME_ARRAY = [];

var workTypeHoursArray = [];
var checkedRows = [];

$table.bootstrapTable({ data: [] });

setOrdersToSelect(parseInt(hEmployeeId.value), parseInt(hYear.value),
				  parseInt(hNumMonth.value));

function setOrdersToSelect(employeeId, year, numMonth){
	console.log(numMonth);
	$.ajax({
		type: "GET",
		url: URL_REST_QUOTA_TIME + "/employee/" + employeeId + "/month/" + numMonth 
								 + "/year/" + year,
		data: null,
		success: function(resp) {
			QUOTA_TIME_ARRAY = resp;
			
			console.log(resp);
			
			for(let i = 0; i < QUOTA_TIME_ARRAY.length; ++i){
				const opt = document.createElement("option");
				opt.value = QUOTA_TIME_ARRAY[i].id;
				opt.innerHTML = QUOTA_TIME_ARRAY[i].order.name;
				selectOrder.appendChild(opt);
			}
			
			getSpendedTimeArray(employeeId, year, numMonth);
		}
	});
}

function getSpendedTimeArray(employeeId, year, numMonth){
	$.ajax({
		type: "GET",
		url: URL_REST_WORK_DAY + "/employee/" + employeeId + "/month/" + numMonth 
								 + "/year/" + year,
		data: null,
		success: function(workDayList) {
			addWorkDayListToQuota(workDayList);
		}
	});
}

function addWorkDayListToQuota(hoursSpendArray){
	for(let i = 0; i < QUOTA_TIME_ARRAY.length; ++i){
		let workTypeHoursArray = QUOTA_TIME_ARRAY[i].workTypeHours;
		for(let j = 0; j < workTypeHoursArray.length; ++j){
			let workTypeHours = workTypeHoursArray[j];
				
			for(let k = 0; k < hoursSpendArray.length; ++k){
				if(hoursSpendArray[k].workTypeHours.id === workTypeHours.id){
					workTypeHours.workDayList = hoursSpendArray[k].workDayList;
				}	
			}
		}
	}
}

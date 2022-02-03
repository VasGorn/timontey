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

btnAdd.addEventListener("click", btnAddClicked, false);
btnUpdate.addEventListener("click", btnUpdateClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

setOrdersToSelect(parseInt(hEmployeeId.value), parseInt(hYear.value),
				  parseInt(hNumMonth.value));
setEmployeesToSelect(parseInt(hEmployeeId.value));

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

function setEmployeesToSelect(masterId){
	$.ajax({
		type: "GET",
		url: URL_REST_TEAM + "/performer/" + masterId,
		data: null,
		success: function(team) {
			let employeeArray = team.employeeList;	
			for(let i = 0; i < employeeArray.length; ++i){
				let employee = employeeArray[i];
				const opt = document.createElement("option");
				opt.value = employee.id;
				opt.innerHTML = employee.lastName + " " + employee.firstName
								+ " " + employee.middleName;
				selectEmployee.appendChild(opt);

				if(employee.id === parseInt(hEmployeeId.value)){
					selectEmployee.value = employee.id;
				}
			}
		}
	});
}

function btnAddClicked(){
	let hoursSpend = getDataFromForm();
	
	let tableData = $table.bootstrapTable('getData');
	for(let i = 0; i < tableData.length; ++i) {
		let row = tableData[i];
		if (hoursSpend.workTypeHours.id === row.workTypeQuotaId &&
				hoursSpend.workDayList[0].employee.id === row.employeeId) {
			alert("Double record!");
			return;
		}
	}
	postDataToServer(hoursSpend);
}

function btnUpdateClicked(){
	let newHoursSpend = getDataFromForm();
	let oldWorkDayRow = checkedRows[0];
	newHoursSpend.workDayList[0].id = oldWorkDayRow.id;
	
	let tableData = $table.bootstrapTable('getData');
	for(let i = 0; i < tableData.length; ++i) {
		let row = tableData[i];
		if (newHoursSpend.workTypeHours.id === row.workTypeQuotaId &&
				newHoursSpend.workDayList[0].employee.id === row.employeeId) {
			alert("Double record!");
			return;
		}
	}
	
	let index = findIndexInTable(oldWorkDayRow);
	putDataToServer(newHoursSpend, index);
}

function btnDeleteClicked(){
	let workDayIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
	
	for (let i = 0; i < workDayIds.length; ++i) {
		deleteDataOnServer(workDayIds[i]);
	}
}

function postDataToServer(hoursSpend){
	$.ajax({
		type: "POST",
		url: URL_REST_WORK_DAY,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(hoursSpend),
		success: function(resp) {
			addWordDayToArray(resp);
			updateOrderHours();
			updateWorkTypeHours(parseInt(selectWorkType.value));

			let workTypeQuota = resp.workTypeHours;
			let workDay = resp.workDayList[0];
			let workDayRow = workDayToRow(workTypeQuota, workDay);

			$table.bootstrapTable('append', workDayRow);
			$table.bootstrapTable('scrollTo', 'bottom');
		}
	});
}

function putDataToServer(newHoursSpend, index){
	let workDayId = newHoursSpend.workDayList[0].id;
	$.ajax({
		type: "PUT",
		url: URL_REST_WORK_DAY + '/' + workDayId,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(newHoursSpend),
		success: function(resp) {
			console.log(resp);
			let workTypeQuota = resp.workTypeHours;
			let workDay = resp.workDayList[0];
			
			let workDayRow = workDayToRow(workTypeQuota, workDay);
			updateWorkTypeQuotaArray(workTypeQuota, workDay);
			
			updateOrderHours();
			updateWorkTypeHours(workTypeQuota.id);
			
			$table.bootstrapTable('uncheckAll');
			checkedRows = [];

			$table.bootstrapTable('updateRow', {
				index: index,
				row: workDayRow 
			});
		}
	});
}

function deleteDataOnServer(workDayId) {
	$.ajax({
		type: "DELETE",
		url: URL_REST_WORK_DAY + '/' + workDayId,
		async: true,
		data: null,
		success: function() {
			let array = [];
			array.push(workDayId);
			$table.bootstrapTable('remove', {
				field: 'id',
				values: array 
			});
			
			console.log(workDayId);
			
			for(let i = 0; i < checkedRows.length; ++i){
				if(workDayId === checkedRows[i].id){
					checkedRows.splice(i,1);
				}
			}
			
			deleteWorkTypeQuotaArray(workDayId);
			updateOrderHours();
			updateWorkTypeHours(parseInt(selectWorkType.value));
			
			setButtonsState(checkedRows.length);
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

function getDataFromForm(){
	let workTypeHours = new Object();
	workTypeHours.id = parseInt(selectWorkType.value);
	
	let employee = new Object();
	employee.id = parseInt(selectEmployee.value);

	let workDay = new Object();
	workDay.employee = employee;
	workDay.numDay = parseInt(selectDayOfMonth.value);
	workDay.workHours = parseInt(selectWorkHour.value);
	workDay.overtime = parseInt(selectOvertime.value);
	workDay.isApprove = false;
	
	let workDayArray = [];
	workDayArray.push(workDay);

	let hoursSpend = new Object();
	hoursSpend.workTypeHours = workTypeHours;
	hoursSpend.workDayList = workDayArray;
	
	return hoursSpend;
}

function addWordDayToArray(hoursSpend){
	let workTypeQuota = hoursSpend.workTypeHours;
	let workDay = hoursSpend.workDayList[0];
	for(let i = 0; i < workTypeHoursArray.length; ++i){
		if(workTypeQuota.id === workTypeHoursArray[i].id){
			workTypeHoursArray[i].workDayList.push(workDay);
		}
	}
}

function updateWorkTypeQuotaArray(newWorkTypeQuota, newWorkDay){
	for(let i = 0; i < workTypeHoursArray.length; ++i){
		let oldWorkTypeQuota = workTypeHoursArray[i];
		let workDayArray = workTypeHoursArray[i].workDayList;
		let isUpdate = false;	

		for(let j = 0; j < workDayArray.length; ++j){
			let oldWorkDay = workDayArray[j];
			if(oldWorkDay.id === newWorkDay.id && 
				oldWorkTypeQuota.id === newWorkTypeQuota.id){
				workDayArray[j] = newWorkDay;
				isUpdate = true;
			} else if (oldWorkDay.id === newWorkDay.id && 
						oldWorkTypeQuota.id != newWorkTypeQuota.id){
				workDayArray.splice(j, 1);
			}
		}
		
		if(isUpdate === false && oldWorkTypeQuota.id === newWorkTypeQuota.id){
			workDayArray.push(newWorkDay);
		}
	}
}

function deleteWorkTypeQuotaArray(workDayId){
	for(let i = 0; i < workTypeHoursArray.length; ++i){
		let workDayArray = workTypeHoursArray[i].workDayList;

		for(let j = 0; j < workDayArray.length; ++j){
			if(workDayId === workDayArray[j].id){
				workDayArray.splice(j, 1);
			}
		}
	}
}

function updateOrderHours(){
	let orderHoursLimit = 0;
	let orderHoursBalance = 0;
	for(let j = 0; j < workTypeHoursArray.length; ++j){
		orderHoursLimit += workTypeHoursArray[j].hours;
		orderHoursBalance += getSumSpendHoursOnWorkType(workTypeHoursArray[j]);
	}
	numOrderLimitTime.value = orderHoursLimit;
	numOrderBalanceTime.value = orderHoursBalance;
}

function updateWorkTypeHours(workHoursId){
	for(let i = 0; i < workTypeHoursArray.length; ++i){
		if(workHoursId === workTypeHoursArray[i].id){
			numWorkTypeLimitTime.value = workTypeHoursArray[i].hours;
			numWorkTypeBalanceTime.value = getSumSpendHoursOnWorkType(workTypeHoursArray[i]);
			break;
		}
	}
}

function getSumSpendHoursOnWorkType(workTypeHours){
	let workDayArray = workTypeHours.workDayList;
	let hoursSpend = 0;
	for(let i = 0; i < workDayArray.length; ++i){
		hoursSpend += (workDayArray[i].workHours + workDayArray[i].overtime);
	}
	return hoursSpend;
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

function workDayToRow(workTypeQuota, workDay){
	let row = new Object();

	row.workType = workTypeQuota.workType.workTypeName;
	row.workTypeQuotaId = workTypeQuota.id;
	let employee = workDay.employee;
	row.id = workDay.id;
	row.employee = employee.lastName + " " + employee.firstName 
				+ " " + employee.middleName;
	row.employeeId = employee.id;
	row.workHours = workDay.workHours;
	row.overtime = workDay.overtime;
	row.approve = workDay.approve;
	
	return row;
}

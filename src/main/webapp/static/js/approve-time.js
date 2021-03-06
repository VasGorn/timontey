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

btnApprove.addEventListener("click", btnApproveClicked, false);
btnUpdate.addEventListener("click", btnUpdateClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

setOrdersToSelect(parseInt(hManagerId.value), parseInt(hYear.value),
				  parseInt(hNumMonth.value));

selectOrder.addEventListener("change", (event) => {
	let orderId = parseInt(event.target.value);
	let employeeArray = getEmployeeArray(QUOTA_TIME_ARRAY, orderId);
	
	setOrderInfo(QUOTA_TIME_ARRAY, orderId);
	
	removeOptionsFromSelect(selectEmployee);
	setEmployeesToSelect(employeeArray, selectEmployee);
	
	selectEmployee.disabled = false;

	updateOrderHours(QUOTA_TIME_ARRAY, orderId);

	resetNumWorkTypeHours();
	removeOptionsFromSelect(selectWorkType);
	removeOptionsFromSelect(selectDayOfMonth);
	
	$table.bootstrapTable('removeAll');
});

selectEmployee.addEventListener("change", (event) => {
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(event.target.value);
	let workTypeQuotaArray = getWorkTypeQuotaArray(QUOTA_TIME_ARRAY, orderId, employeeId);
	
	removeOptionsFromSelect(selectWorkType);
	setWorkTypeToSelect(workTypeQuotaArray, selectWorkType);
	
	let numDayArray = getNumDayArray(workTypeQuotaArray);
	numDayArray.sort();

	removeOptionsFromSelect(selectDayOfMonth);
	if (numDayArray.length >= 1){
		setNumDayToSelect(numDayArray, selectDayOfMonth);
		selectDayOfMonth.value = numDayArray[0];
		loadWorkDayToTable(workTypeQuotaArray, numDayArray[0]);
	}
});

selectDayOfMonth.addEventListener("change", (event) => {
	let numDay = parseInt(event.target.value);
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(selectEmployee.value);
	
	let workTypeQuotaArray = getWorkTypeQuotaArray(QUOTA_TIME_ARRAY, orderId, employeeId);
	loadWorkDayToTable(workTypeQuotaArray, numDay);
});

selectWorkType.addEventListener("change", (event) => {
	let workTypeQuotaId = parseInt(event.target.value);
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(selectEmployee.value);

	let workTypeQuotaArray = getWorkTypeQuotaArray(QUOTA_TIME_ARRAY, orderId, employeeId);
	
	updateWorkTypeHours(workTypeQuotaArray, workTypeQuotaId);
});

function btnApproveClicked() {
	let workDayIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
	
	for (let i = 0; i < workDayIds.length; ++i) {
		requestToApprove(workDayIds[i]);
	}
}

function btnUpdateClicked() {
	let oldWorkDayRow = checkedRows[0];
	let oldSumHours = oldWorkDayRow.workHours + oldWorkDayRow.overtime;
	
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(selectEmployee.value);
	let workTypeQuotaArray = getWorkTypeQuotaArray(QUOTA_TIME_ARRAY, orderId, employeeId);
	let workTypeQuotaId = parseInt(selectWorkType.value);
	let limitHours = getLimitOnType(workTypeQuotaArray, workTypeQuotaId);
	let balanceHours = getBalanceOnType(workTypeQuotaArray, workTypeQuotaId);
	
	let newSumHours = parseInt(selectWorkHour.value) + parseInt(selectOvertime.value);
	
	if (workTypeQuotaId === oldWorkDayRow.workTypeQuotaId) {
		if (balanceHours  + newSumHours > limitHours) {
			alert("New value is above limit!");
			return;
		}
	} else {
		if (balanceHours - oldSumHours + newSumHours > limitHours) {
			alert("New value is above limit!");
			return;
		}
	}
	
	if (parseInt(selectWorkHour.value) <= 0 || parseInt(selectOvertime.value) < 0) {
		alert("Wrong values of hours!");
		return
	}

	btnUpdate.disabled = true;

	let newHoursSpend = getDataFromForm(oldWorkDayRow.employeeId, oldWorkDayRow.numDay);
	newHoursSpend.workTypeHours.id = workTypeQuotaId;
	newHoursSpend.workDayList[0].id = oldWorkDayRow.id;
	
	let index = findIndexInTable(oldWorkDayRow);
	
	putRequest(newHoursSpend, index);
}

function btnDeleteClicked() {
	let workDayIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
	
	for (let i = 0; i < workDayIds.length; ++i) {
		deleteRequest(workDayIds[i]);
	}
}
	
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

function requestToApprove(workDayId){
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(selectEmployee.value);
	$.ajax({
		type: "PUT",
		url: URL_REST_WORK_DAY + '/approve/' + workDayId,
		data: null,
		success: function(resp) {
			console.log(resp);
			
			let workTypeQuotaArray = getWorkTypeQuotaArray(QUOTA_TIME_ARRAY, orderId, employeeId);
			approveInArray(workTypeQuotaArray, workDayId);
			
			let index = approveRowInTable(workDayId);
			$table.bootstrapTable('uncheck', index);
			
			deleteCheckedRow(workDayId, checkedRows);
			setButtonsState(checkedRows.length);
		}
	});
}

function putRequest(newHoursSpend, index) {
	let workDayId = newHoursSpend.workDayList[0].id;
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(selectEmployee.value);
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
			
			let workTypeQuotaArray = getWorkTypeQuotaArray(QUOTA_TIME_ARRAY, orderId, employeeId);
			
			updateWorkTypeQuotaArray(workTypeQuotaArray, workTypeQuota, workDay);
			
			updateOrderHours(QUOTA_TIME_ARRAY, orderId);
			updateWorkTypeHours(workTypeQuotaArray, workTypeQuota.id);
			
			$table.bootstrapTable('uncheckAll');
			checkedRows = [];
			setButtonsState(checkedRows.length);

			$table.bootstrapTable('updateRow', {
				index: index,
				row: workDayRow 
			});
		}
	});
}

function deleteRequest(workDayId) {
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(selectEmployee.value);
	let workTypeQuotaId = parseInt(selectWorkType.value);
	
	$.ajax({
		type: "DELETE",
		url: URL_REST_WORK_DAY + '/' + workDayId,
		async: true,
		data: null,
		success: function() {
			let array = [workDayId];
			$table.bootstrapTable('remove', {
				field: 'id',
				values: array 
			});
			console.log(workDayId);
			
			let workTypeQuotaArray = getWorkTypeQuotaArray(QUOTA_TIME_ARRAY, orderId, employeeId);
			deleteInArray(workTypeQuotaArray, workDayId);
			
			updateOrderHours(QUOTA_TIME_ARRAY, orderId);
			updateWorkTypeHours(workTypeQuotaArray, workTypeQuotaId)
			
			deleteCheckedRow(workDayId, checkedRows);
			setButtonsState(checkedRows.length);
		}
	});
}

function setHoursSpend(employeeId, year, numMonth){
	$.ajax({
		type: "GET",
		url: URL_REST_WORK_DAY + "/employee/" + employeeId + "/month/" + numMonth 
								 + "/year/" + year,
		data: null,
		success: function(hoursSpendArray) {
			addWorkDayListToQuota(hoursSpendArray);
		}
	});
}

function getEmployeeArray(quotaTimeArray, orderId){
	let array = [];
	for (let i = 0; i < quotaTimeArray.length; ++i) {
		if (orderId === quotaTimeArray[i].order.id){
			let employee = quotaTimeArray[i].employee;
			array.push(employee);
		}
	}
	return array;
}

function getWorkTypeQuotaArray(quotaTimeArray, orderId, employeeId){
	for (let i = 0; i < quotaTimeArray.length; ++i) {
		if (orderId === quotaTimeArray[i].order.id 
			&& employeeId === quotaTimeArray[i].employee.id) {
				
			let workTypeQuotaList = quotaTimeArray[i].workTypeHours;
			return workTypeQuotaList;
			
		}
	}
}

function getUniqueOrders(quotaTimeArray){
	let orders = [];
	for (let i = 0; i < quotaTimeArray.length; ++i) {
		let order = quotaTimeArray[i].order;
		let isUnique = true;
		for (let j = 0; j < orders.length; ++j) {
			if(orders[j].id === order.id) {
				isUnique = false;	
				break;
			}
		}
		if (isUnique) {
			orders.push(order);
		}
	}
	return orders;
}

function getUniqueEmployees(quotaTimeArray){
	let array = [];
	for (let i = 0; i < quotaTimeArray.length; ++i) {
		let employee = quotaTimeArray[i].employee;
		let isUnique = true;
		for (let j = 0; j < array.length; ++j) {
			if(array[j].id === employee.id) {
				isUnique = false;	
				break;
			}
		}
		if (isUnique) {
			array.push(employee);
		}
	}
	return array;
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

function approveInArray(workTypeQuotaArray, workDayId){
	for (let i = 0; i < workTypeQuotaArray.length; ++i) {
		let workDayList = workTypeQuotaArray[i].workDayList;
		for (let j = 0; j < workDayList.length; ++j) {
			if (workDayId === workDayList[j].id) {
				workDayList[j].approve = true;
			}
		}
	}
}

function updateWorkTypeQuotaArray(workTypeQuotaArray, newWorkTypeQuota, newWorkDay){
	for(let i = 0; i < workTypeQuotaArray.length; ++i){
		let oldWorkTypeQuota = workTypeQuotaArray[i];
		let workDayArray = workTypeQuotaArray[i].workDayList;
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

function deleteInArray(workTypeQuotaArray, workDayId) {
	for (let i = 0; i < workTypeQuotaArray.length; ++i) {
		let workDayList = workTypeQuotaArray[i].workDayList;
		for (let j = 0; j < workDayList.length; ++j) {
			if (workDayId === workDayList[j].id) {
				workDayList.splice(j, 1);
			}
		}
	}
}

function approveRowInTable(id) {
	let tableData = $table.bootstrapTable('getData');
	let index = 0;

	for (let i = 0; i < tableData.length; ++i) {
		if (id === tableData[i].id) {
			$table.bootstrapTable('updateCell', {index: i, field: 'approve', value: 'true'})
			index = i;
			break;
		}
	}
	return index;
}

function getNumDayArray(workTypeQuotaList){
	let numDaySet = new Set();
	for (let j = 0; j < workTypeQuotaList.length; ++j) {
		let workDayList = workTypeQuotaList[j].workDayList;
		for (let k = 0; k < workDayList.length; ++k) {
			if (workDayList[k].approve === false) {
				numDaySet.add(workDayList[k].numDay);
			}
		}
	}
	return [...numDaySet];
}

function setOrderInfo(quotaTimeArray, orderId){
	let order;
	for(let i = 0; i < quotaTimeArray.length; ++i){
		if(orderId === quotaTimeArray[i].order.id){
			order = quotaTimeArray[i].order;
			break;
		}
	}
	txtDescription.value = order.description;
	txtAddress.value = order.address;
}

function removeOptionsFromSelect(select){
	const length = select.options.length;
	select.value = 0;
	
	for(let i = 1; i < length; ++i){
		select.removeChild(select.options[1]);
	}
}

function setEmployeesToSelect(objectArray, select){
	for(let i = 0; i < objectArray.length; ++i){
		const opt = document.createElement("option");
		opt.value = objectArray[i].id;
		opt.innerHTML = objectArray[i].lastName + ' ' 
						+ objectArray[i].firstName + ' '
						+ objectArray[i].middleName;
		select.appendChild(opt);
	}
}

function setWorkTypeToSelect(objectArray, select) {
	for(let i = 0; i < objectArray.length; ++i){
		const opt = document.createElement("option");
		opt.value = objectArray[i].id;
		opt.innerHTML = objectArray[i].workType.workTypeName;
		select.appendChild(opt);
	}
}

function setNumDayToSelect(objectArray, select){
	for(let i = 0; i < objectArray.length; ++i){
		const opt = document.createElement("option");
		opt.value = objectArray[i];
		opt.innerHTML = objectArray[i];
		select.appendChild(opt);
	}
}

function updateOrderHours(quotaTimeArray, orderId) {
	let orderHoursLimit = 0;
	let orderHoursBalance = 0;
	for (let i = 0; i < quotaTimeArray.length; ++i) {
		if (orderId != quotaTimeArray[i].order.id) {
			continue;
		}
		let workTypeQuotaArray = quotaTimeArray[i].workTypeHours;
		for(let j = 0; j < workTypeQuotaArray.length; ++j){
			orderHoursLimit += workTypeQuotaArray[j].hours;
			orderHoursBalance += getSumSpendHoursOnWorkType(workTypeQuotaArray[j]);
		}
	}
	numOrderLimitTime.value = orderHoursLimit;
	numOrderBalanceTime.value = orderHoursBalance;
}

function updateWorkTypeHours(workTypeQuotaArray, workTypeQuotaId){
	for(let i = 0; i < workTypeQuotaArray.length; ++i){
		if(workTypeQuotaId === workTypeQuotaArray[i].id){
			numWorkTypeLimitTime.value = workTypeQuotaArray[i].hours;
			numWorkTypeBalanceTime.value = getSumSpendHoursOnWorkType(workTypeQuotaArray[i]);
			break;
		}
	}
}

function getSumSpendHoursOnWorkType(workTypeQuota){
	let workDayArray = workTypeQuota.workDayList;
	let hoursSpend = 0;
	for(let i = 0; i < workDayArray.length; ++i){
		hoursSpend += (workDayArray[i].workHours + workDayArray[i].overtime);
	}
	return hoursSpend;
}

function getLimitOnType(workTypeQuotaArray, workTypeQuotaId) {
	for(let j = 0; j < workTypeQuotaArray.length; ++j){
		if (workTypeQuotaId === workTypeQuotaArray[j].id) {
			return workTypeQuotaArray[j].hours;
		}
	}
}

function getBalanceOnType(workTypeQuotaArray, workTypeQuotaId) {
	for(let j = 0; j < workTypeQuotaArray.length; ++j){
		if (workTypeQuotaId === workTypeQuotaArray[j].id) {
			return getSumSpendHoursOnWorkType(workTypeQuotaArray[j])	
		}
	}
}

function resetNumWorkTypeHours(){
	numWorkTypeLimitTime.value = "";
	numWorkTypeBalanceTime.value = "";
}

function loadWorkDayToTable(workTypeQuotaList, numDay) {
	let rowArray = [];
	for(let i = 0; i < workTypeQuotaList.length; ++i){
		let workTypeQuota = workTypeQuotaList[i];
		let workDayArray = workTypeQuotaList[i].workDayList;
		for(let j = 0; j < workDayArray.length; ++j){
			let workDay = workDayArray[j];
			if(workDay.numDay === numDay){
				let workDayRow = workDayToRow(workTypeQuota, workDay);
				rowArray.push(workDayRow);
			}
		}
	}
	$table.bootstrapTable('load', rowArray);
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

function deleteCheckedRow(id, checkedRows){
	for(let i = 0; i < checkedRows.length; ++i){
		if(id === checkedRows[i].id){
			checkedRows.splice(i,1);
		}
	}
}

function setButtonsState(numRowSelected){
	if (numRowSelected < 1) {
		btnApprove.disabled = true;
		btnUpdate.disabled = true;
		btnDelete.disabled = true;
		return;
	}
	if (numRowSelected === 1) {
		btnApprove.disabled = false;
		btnUpdate.disabled = false;
		btnDelete.disabled = false;
		return;
	}
	if (numRowSelected > 1) {
		btnApprove.disabled = false;
		btnUpdate.disabled = true;
		btnDelete.disabled = false;
	}
}

$table.on('check.bs.table', function(e, row) {
	checkedRows.push(row);
	setButtonsState(checkedRows.length);
	setSelectValue(row);
	
	let orderId = parseInt(selectOrder.value);
	let employeeId = parseInt(selectEmployee.value);
	let workTypeQuotaArray = getWorkTypeQuotaArray(QUOTA_TIME_ARRAY, orderId, employeeId);
	updateWorkTypeHours(workTypeQuotaArray, row.workTypeQuotaId);

	console.log(checkedRows);
});

$table.on('uncheck.bs.table', function(e, row) {
	checkedRows.forEach((element, index) => {
		if (element.id === row.id) {
			checkedRows.splice(index, 1);
		}
	});
	setButtonsState(checkedRows.length);
	
	console.log(checkedRows);
});

function setSelectValue(row){
	let workTypeQuotaId = row.workTypeQuotaId;
	
	let time = row.workHours;
	let overtime = row.overtime;
	
	selectWorkType.value = workTypeQuotaId;
	selectWorkHour.value = time;
	selectOvertime.value = overtime;
}

function getDataFromForm(employeeId, numDay){
	let hoursSpend = new Object();
	let workTypeHours = new Object();
	let workDay = new Object();
	let workDayArray = [];
	
	workTypeHours.id = parseInt(selectWorkType.value);
	
	let employee = new Object();
	employee.id = employeeId;
	workDay.employee = employee;
	workDay.numDay = numDay;
	workDay.workHours = parseInt(selectWorkHour.value);
	workDay.overtime = parseInt(selectOvertime.value);
	workDay.isApprove = false;
	
	workDayArray.push(workDay);
	
	hoursSpend.workTypeHours = workTypeHours;
	hoursSpend.workDayList = workDayArray;
	
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

var btnApprove = document.getElementById("approve");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var hManagerId = document.getElementById("manager_id");

var selectOrder = document.getElementById("select_order");
var txtDescription = document.getElementById("description");
var txtAddress = document.getElementById("address");

var numOrderBalanceMoney = document.getElementById("order_balance_money");
var numOrderLimitMoney = document.getElementById("order_limit_money");

var selectExpenses = document.getElementById("select_expenses");
var numMoneySpend = document.getElementById("money_spend");

var $table = $('#table');

var URL_REST_SPEND_MONEY = "/timontey/rest/spend-money";
var URL_REST_EXPENSES = "/timontey/rest/expenses";

var ORDER_MONEY_ARRAY = [];
var LIMIT_MONEY = 0.0;
var BALANCE_MONEY = 0.0;

var checkedRows = [];

$table.bootstrapTable({ data: [] });

btnApprove.addEventListener("click", btnApproveClicked, false);
btnUpdate.addEventListener("click", btnUpdateClicked, false);
btnDelete.addEventListener("click", btnDeleteClicked, false);

setOrdersToSelect(parseInt(hManagerId.value));
setExpensesToSelect();

selectOrder.addEventListener("change", (event) => {
	let orderId = parseInt(event.target.value);
	let order;
	let spendList;
	
	for(let i = 0; i < ORDER_MONEY_ARRAY.length; ++i){
		if(orderId === ORDER_MONEY_ARRAY[i].order.id){
			order = ORDER_MONEY_ARRAY[i].order;
			spendList = ORDER_MONEY_ARRAY[i].spendList;
			updateOrderMoney(ORDER_MONEY_ARRAY[i]);
			break;
		}
	}

	setOrderInfo(order);
	loadSpendMoneyToTable(spendList);
});

function btnApproveClicked(){
	let spendeMoneyIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
	
	for (let i = 0; i < spendeMoneyIds.length; ++i) {
		approveRequest(spendeMoneyIds[i]);
	}
}

function btnUpdateClicked(){
	let oldSpendMoneyRow = checkedRows[0];

	let newMoneyValue = roundTwoDigit(parseFloat(numMoneySpend.value));
	let oldMoneyValue = oldSpendMoneyRow.money;
	
	if(newMoneyValue <= 0.0){
		alert("Amount of money must be positive number!");
		return;
	}
	
	if(newMoneyValue + (BALANCE_MONEY - oldMoneyValue) > LIMIT_MONEY){
		alert("Amount of money is above limit!");
		return;
	}

	numMoneySpend.value = newMoneyValue;
	btnUpdate.disabled = true;

	let newMoneySpend = getDataFromForm(oldSpendMoneyRow.employeeId);
	newMoneySpend.quotaMoney.id = oldSpendMoneyRow.quotaId;
	let newMoneySpendExpense = newMoneySpend.moneyExpenseList[0];
	newMoneySpendExpense.id = oldSpendMoneyRow.id;
	
	let index = findIndexInTable(oldSpendMoneyRow);
	putRequest(newMoneySpendExpense, index);
}

function btnDeleteClicked(){
	let spendExpenseIds = $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
	
	for (let i = 0; i < spendExpenseIds.length; ++i) {
		deleteRequest(spendExpenseIds[i]);
	}
}

function approveRequest(spendId){
	let orderId = parseInt(selectOrder.value);
	$.ajax({
		type: "PUT",
		url: URL_REST_SPEND_MONEY + '/approve/' + spendId,
		data: null,
		success: function(resp) {
			console.log(resp);
			
			approveSpendInArray(orderId, spendId);
			let index = approveRowInTable(spendId);
			
			$table.bootstrapTable('uncheck', index);
			
			deleteCheckedRow(spendId, checkedRows);

			setButtonsState(checkedRows.length);
		}
	});
}

function putRequest(newSpendMoney, index){
	let spendId = newSpendMoney.id;
	let orderId = parseInt(selectOrder.value);
	$.ajax({
		type: "PUT",
		url: URL_REST_SPEND_MONEY + '/' + spendId,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(newSpendMoney),
		success: function(resp) {
			console.log(resp);
			let spendExpense = resp;
			
			updateSpendInArray(spendExpense, orderId);

			let newRow = checkedRows[0];
			checkedRows = [];
			setButtonsState(checkedRows.length);
			$table.bootstrapTable('uncheckAll');
			
			newRow.expenses = spendExpense.expenses.name;
			newRow.expensesId = spendExpense.expenses.id;
			newRow.money = spendExpense.money;
			newRow.approve = spendExpense.approve;
			
			$table.bootstrapTable('updateRow', {
				index: index,
				row: newRow 
			});
		}
	});
}

function deleteRequest (spendId) {
	let orderId = parseInt(selectOrder.value);
	$.ajax({
		type: "DELETE",
		url: URL_REST_SPEND_MONEY + '/' + spendId,
		async: true,
		data: null,
		success: function() {
			let array = [];
			array.push(spendId);
			$table.bootstrapTable('remove', {
				field: 'id',
				values: array 
			});
			
			console.log(spendId);
			
			for(let i = 0; i < checkedRows.length; ++i){
				if (spendId === checkedRows[i].id) {
					checkedRows.splice(i,1);
				}
			}
			
			deleteSpendInArray(orderId, spendId);
			setButtonsState(checkedRows.length);
		}
	});
}

function setOrdersToSelect(managerId){
	$.ajax({
		type: "GET",
		url: URL_REST_SPEND_MONEY + "/manager/" + managerId,
		data: null,
		success: function(resp) {
			ORDER_MONEY_ARRAY = toSpendOrderMoney(resp);
			
			console.log(resp);
			console.log(ORDER_MONEY_ARRAY);
			
			for(let i = 0; i < ORDER_MONEY_ARRAY.length; ++i){
				const opt = document.createElement("option");
				opt.value = ORDER_MONEY_ARRAY[i].order.id;
				opt.innerHTML = ORDER_MONEY_ARRAY[i].order.name;
				selectOrder.appendChild(opt);
			}
		}
	});
}

function setExpensesToSelect(){
	$.ajax({
		type: "GET",
		url: URL_REST_EXPENSES + "/all" ,
		data: null,
		success: function(resp) {
			
			for(let i = 0; i < resp.length; ++i){
				let expenses = resp[i];
				const opt = document.createElement("option");
				opt.value = expenses.id;
				opt.innerHTML = expenses.name ;
				selectExpenses.appendChild(opt);
			}
		}
	});
}

function updateSpendInArray(spend, orderId){
	for(let i = 0; i < ORDER_MONEY_ARRAY.length; ++i){
		if(orderId === ORDER_MONEY_ARRAY[i].order.id){
			let spendList = ORDER_MONEY_ARRAY[i].spendList;
			for(let j = 0; j < spendList.length; ++j){
				if (spend.id === spendList[j].id) {
					spendList[j].expenses = spend.expenses;
					spendList[j].money = spend.money;
					spendList[j].approve = spend.approve;
				}
			}
			updateOrderMoney(ORDER_MONEY_ARRAY[i]);
			break;
		}
	}
}

function deleteSpendInArray(orderId, spendId){
	for(let i = 0; i < ORDER_MONEY_ARRAY.length; ++i){
		if(orderId === ORDER_MONEY_ARRAY[i].order.id){
			let spendList = ORDER_MONEY_ARRAY[i].spendList;
			for(let j = 0; j < spendList.length; ++j){
				if (spendId === spendList[j].id) {
					spendList.splice(j, 1);
				}
			}
			updateOrderMoney(ORDER_MONEY_ARRAY[i]);
			break;
		}
	}
}

function updateOrderMoney(spendOrderMoney){
	LIMIT_MONEY = spendOrderMoney.moneyLimit;
	BALANCE_MONEY = 0.0;
	for(let i = 0; i < spendOrderMoney.spendList.length; ++i){
		BALANCE_MONEY += parseFloat(spendOrderMoney.spendList[i].money);
	}
	numOrderLimitMoney.value = roundTwoDigit(LIMIT_MONEY);
	numOrderBalanceMoney.value = roundTwoDigit(BALANCE_MONEY);
}

function setOrderInfo(order){
	txtDescription.value = order.description;
	txtAddress.value = order.address;
}

function loadSpendMoneyToTable(spendList){
	let rowArray = [];
	for(let i = 0; i < spendList.length; ++i){
		let row = spendToRow(spendList[i]);
		rowArray.push(row);
	}
	
	$table.bootstrapTable('load', rowArray);
}

function approveSpendInArray(orderId, spendId){
	for(let i = 0; i < ORDER_MONEY_ARRAY.length; ++i){
		if(orderId === ORDER_MONEY_ARRAY[i].order.id){
			let spendList = ORDER_MONEY_ARRAY[i].spendList;
			for(let j = 0; j < spendList.length; ++j){
				if (spendId === spendList[j].id) {
					spendList[j].approve = true;
				}
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

function deleteCheckedRow(spendId, checkedRows){
	for(let i = 0; i < checkedRows.length; ++i){
		if(spendId === checkedRows[i].id){
			checkedRows.splice(i,1);
		}
	}
}

function setButtonsState(numRowSelected) {
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

function roundTwoDigit(number){
	let newValue = Math.round(number * 100.0) / 100.0;
	return newValue;
}

function getDataFromForm(employeeId){
	let quotaMoney = new Object();
	let employee = new Object();
	employee.id = employeeId;
	quotaMoney.employee = employee;

	let moneySpendExpense = new Object();
	let expenses = new Object();
	expenses.id = parseInt(selectExpenses.value);
	moneySpendExpense.expenses = expenses;
	moneySpendExpense.money = parseFloat(numMoneySpend.value);
	moneySpendExpense.approve = false;
	
	let moneyExpenseList = [];
	moneyExpenseList.push(moneySpendExpense);
	
	let moneySpend = new Object();
	moneySpend.quotaMoney = quotaMoney;
	moneySpend.moneyExpenseList = moneyExpenseList;
	
	return moneySpend;
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

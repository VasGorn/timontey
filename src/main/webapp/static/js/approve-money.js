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

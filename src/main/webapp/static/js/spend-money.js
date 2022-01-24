var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var hEmployeeId = document.getElementById("employee_id");

var selectOrder = document.getElementById("select_order");
var txtManagerName = document.getElementById("manager_name");
var txtDescription = document.getElementById("description");
var txtAddress = document.getElementById("address");

var numOrderBalanceMoney = document.getElementById("order_balance_money");
var numOrderLimitMoney = document.getElementById("order_limit_money");

var selectExpenses = document.getElementById("select_expenses");
var numMoneySpend = document.getElementById("money_spend");

var $table = $('#table');

var URL_REST_SPEND_MONEY = "/timontey/rest/spend-money";
var URL_REST_EXPENSES = "/timontey/rest/expenses";

var QUOTA_MONEY_ARRAY = [];
var LIMIT_MONEY = 0.0;
var BALANCE_MONEY = 0.0;

var checkedRows = [];

$table.bootstrapTable({ data: [] });

btnAdd.addEventListener("click", btnAddClicked, false);

setOrdersToSelect(parseInt(hEmployeeId.value));
setExpensesToSelect();

selectOrder.addEventListener("change", (event) => {
	let moneyQuotId = parseInt(event.target.value);
	let order;
	let moneyExpenseList;
	
	for(let i = 0; i < QUOTA_MONEY_ARRAY.length; ++i){
		if(moneyQuotId === QUOTA_MONEY_ARRAY[i].quotaMoney.id){
			order = QUOTA_MONEY_ARRAY[i].quotaMoney.order;
			moneyExpenseList = QUOTA_MONEY_ARRAY[i].moneyExpenseList;
			updateOrderMoney(QUOTA_MONEY_ARRAY[i].quotaMoney, moneyExpenseList);
			break;
		}
	}

	setOrderInfo(order);
	loadMoneyExpenseToTable(moneyExpenseList);
});

function setOrdersToSelect(employeeId){
	$.ajax({
		type: "GET",
		url: URL_REST_SPEND_MONEY + "/employee/" + employeeId,
		data: null,
		success: function(resp) {
			QUOTA_MONEY_ARRAY = resp;
			console.log(resp);
			
			for(let i = 0; i < QUOTA_MONEY_ARRAY.length; ++i){
				const opt = document.createElement("option");
				opt.value = QUOTA_MONEY_ARRAY[i].quotaMoney.id;
				opt.innerHTML = QUOTA_MONEY_ARRAY[i].quotaMoney.order.name;
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

function btnAddClicked(){
	let money = roundTwoDigit(parseFloat(numMoneySpend.value));
	numMoneySpend.value = money;
	
	if(money <= 0.0){
		alert("Amount of money must be positive number!");
		return;
	}
	
	if(money + BALANCE_MONEY > LIMIT_MONEY){
		alert("Amount of money is above limit!");
		return;
	}

	btnAdd.disabled = true;
	let moneySpend = getDataFromForm();
	postDataToServer(moneySpend);
}

function roundTwoDigit(number){
	let newValue = Math.round(number * 100.0) / 100.0;
	return newValue;
}

function getDataFromForm(){
	let quotaMoney = new Object();
	quotaMoney.id = parseInt(selectOrder.value);
	let employee = new Object();
	employee.id = parseInt(hEmployeeId.value);
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

function postDataToServer(spendMoney){
	$.ajax({
		type: "POST",
		url: URL_REST_SPEND_MONEY,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: JSON.stringify(spendMoney),
		success: function(resp) {
			addSpendMoneyToArray(resp);

			let spendExpense = resp.moneyExpenseList[0];
			let spendExpenseRow = spendExpenseToRow(spendExpense);

			$table.bootstrapTable('append', spendExpenseRow);
			$table.bootstrapTable('scrollTo', 'bottom');
			console.log(resp);
			btnAdd.disabled = false;
		}
	});
}

function addSpendMoneyToArray(spendMoney){
	let moneyQuotaId = spendMoney.quotaMoney.id;
	let moneyExpense = spendMoney.moneyExpenseList[0];
	for(let i = 0; i < QUOTA_MONEY_ARRAY.length; ++i){
		if(moneyQuotaId === QUOTA_MONEY_ARRAY[i].quotaMoney.id){
			let moneyExpenseList = QUOTA_MONEY_ARRAY[i].moneyExpenseList;
			moneyExpenseList.push(moneyExpense);
			updateOrderMoney(QUOTA_MONEY_ARRAY[i].quotaMoney, 
							 QUOTA_MONEY_ARRAY[i].moneyExpenseList);
			break;
		}
	}
}

function updateOrderMoney(quotaMoney, moneyExpenseList){
	LIMIT_MONEY = quotaMoney.moneyLimit;
	BALANCE_MONEY = 0.0;
	for(let i = 0; i < moneyExpenseList.length; ++i){
		BALANCE_MONEY += parseFloat(moneyExpenseList[i].money);
	}
	numOrderLimitMoney.value = roundTwoDigit(LIMIT_MONEY);
	numOrderBalanceMoney.value = roundTwoDigit(BALANCE_MONEY);
}

function setOrderInfo(order){
	let manager = order.manager;

	txtManagerName.value = manager.lastName + ' ' + manager.firstName + ' ' 
						   + manager.middleName;
	txtDescription.value = order.description;
	txtAddress.value = order.address;
}

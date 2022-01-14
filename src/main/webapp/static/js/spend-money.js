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

setOrdersToSelect(parseInt(hEmployeeId.value));

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

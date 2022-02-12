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

setOrdersToSelect(parseInt(hManagerId.value));

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

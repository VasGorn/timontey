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

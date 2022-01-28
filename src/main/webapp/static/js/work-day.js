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

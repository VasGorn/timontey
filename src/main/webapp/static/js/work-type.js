var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var roleSelect = document.getElementById("role_select");
var txtWorkType = document.getElementById("work_type");

var URL_REST_ROLE = "/timontey1/rest/role/";
var URL_REST_WORK = "/timontey1/rest/work-type/";

var ROLES = [];

var $table = $('#table');
var checkedRows = [];

$table.bootstrapTable({ data: [] });

btnAdd.disabled = true;
btnUpdate.disabled = true;
btnDelete.disabled = true;

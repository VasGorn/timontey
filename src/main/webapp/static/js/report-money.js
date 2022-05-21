var btnDownload = document.getElementById("create_report");
var formReportTime = document.getElementById("form_report_money");

var selectOrder = document.getElementById("select_order");
var hiddenManagerId = document.getElementById("manager_id");
var txtDescription = document.getElementById("description");
var txtAddress = document.getElementById("address");

var selectYear = document.getElementById("select_year");
var selectMonth = document.getElementById("select_month");

var txtFilename = document.getElementById("filename");

var btnCreateReport = document.getElementById("btn_create_report");

var ORDERS = [];

var URL_REST_ORDER = "/timontey/rest/orders";

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

setOrdersToSelect();
setSelectedMonth();

function setOrdersToSelect(){
	let managerId = hiddenManagerId.value;
	
	$.ajax({
		type: "GET",
		url: URL_REST_ORDER + "/" + managerId,
		data: null,
		success: function(orders) {
			for(let i = 0; i < orders.length; ++i){
				const opt = document.createElement("option");
				opt.value = orders[i].id;
				opt.innerHTML = orders[i].name;
				selectOrder.appendChild(opt);
			}
			ORDERS = orders;
		}
	});
}

function setSelectedMonth(){
	const date = new Date();
	selectMonth.value = date.getMonth();
}

selectOrder.addEventListener("change", (event) => {
	let orderId = parseInt(event.target.value);
	setOrderInfo(orderId);
	txtFilename.value = getFilename();
	btnCreateReport.disabled = false;
});

selectYear.addEventListener("change", (event) => {
	if (selectOrder.value < 1){
		return;
	}
	txtFilename.value = getFilename();
});

selectMonth.addEventListener("change", (event) => {
	if (selectOrder.value < 1){
		return;
	}
	txtFilename.value = getFilename();
});

function formSubmit(event) {
	if (txtFilename.value.trim().length < 1) {
		event.preventDefault();
		alert("Enter file name!");
	}
}

function setOrderInfo(orderId){
	let order;
	for(let i = 0; i < ORDERS.length; ++i){
		if(orderId === ORDERS[i].id){
			order = ORDERS[i];
			break;
		}
	}
	txtDescription.value = order.description;
	txtAddress.value = order.address;
}

function getFilename() {
	let orderName = selectOrder.options[selectOrder.selectedIndex].text;
	let year = selectYear.value;
	let month = parseInt(selectMonth.value) + 1;  
	return (orderName + '-' + year + '-' + month);
}

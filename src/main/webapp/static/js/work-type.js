var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var roleSelect = document.getElementById("role_select");
var txtWorkType = document.getElementById("work_type");

var URL_REST_ROLE = "/timontey/rest/role";
var URL_REST_WORK = "/timontey/rest/work-type";

var ROLES = [];

var $table = $('#table');
var checkedRows = [];

$table.bootstrapTable({ data: [] });

btnAdd.disabled = true;
btnUpdate.disabled = true;
btnDelete.disabled = true;

setDataToSelectRole();

function setDataToSelectRole(){
	$.ajax({
		type: "GET",
		url: URL_REST_ROLE + "/all",
		data: null,
		success: function(resp) {
			console.log(resp);
			ROLES = resp;
			
			for(let i = 0; i < ROLES.length; ++i){
				const opt = document.createElement("option");
				opt.value = ROLES[i].id;
				opt.innerHTML = ROLES[i].roleName;
				roleSelect.appendChild(opt);
				
				setWorkTypesToRole(ROLES[i]);
			}
		}
	});
}

function setWorkTypesToRole(role){
	$.ajax({
		type: "GET",
		url: URL_REST_WORK + "/role/" + role.id,
		data: null,
		success: function(resp) {
			role.workTypes = resp;
		}
	});
}

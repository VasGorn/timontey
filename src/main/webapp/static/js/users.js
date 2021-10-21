var txtUsername = document.getElementById("username");
var txtPassword = document.getElementById("password");
var txtRepeatPassword = document.getElementById("confirm_password");

var checkBoxContainer = document.getElementById("check_role");
var selectEmployee = document.getElementById("select_employee");
var checkBoxRoles;

var btnAdd = document.getElementById("add");
var btnUpdate = document.getElementById("update");
var btnDelete = document.getElementById("delete");

var $table = $('#table');
var checkedRows = [];

var URL_REST_ROLE = "/timontey/rest/role/";
var URL_REST_STAFF = "/timontey/rest/staff/";
var URL_REST_USERS = "/timontey/rest/users/";

btnAdd.disabled = true;
btnUpdate.disabled = true;
btnDelete.disabled = true;

setDataToSelectRole();

function setDataToSelectRole() {
	$.ajax({
		type: "GET",
		url: URL_REST_ROLE + "all",
		data: null,
		success: function(resp) {
			console.log(resp);
			for (let i = 0; i < resp.length; ++i) {
				const newDiv = document.createElement("div");
				newDiv.classList.add("form-check");
				newDiv.classList.add("form-check-inline");

				const checkbox = document.createElement("input");
				checkbox.type = "checkbox";
				checkbox.name = "roles";
				checkbox.value = resp[i].id;
				checkbox.classList.add("form-check-input");

				const label = document.createElement("label");
				label.htmlFor = "roles";
				label.classList.add("form-check-label");

				label.appendChild(document.createTextNode(resp[i].roleName));
				newDiv.appendChild(checkbox);
				newDiv.appendChild(label);

				checkBoxContainer.appendChild(newDiv);
			}
			checkBoxRoles = document.getElementsByName("roles");
		}
	});
}

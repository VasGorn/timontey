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

btnAdd.addEventListener("click", btnAddClicked, false);

setDataToSelectRole();
setDataToSelectEmployee();
getDataForTable();

selectEmployee.addEventListener("change", (event) => {
	btnAdd.disabled = false;
});

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

function setDataToSelectEmployee() {
	$.ajax({
		type: "GET",
		url: URL_REST_STAFF + "all",
		data: null,
		success: function(resp) {
			console.log(resp);
			for (let i = 0; i < resp.length; ++i) {
				const opt = document.createElement("option");
				opt.value = resp[i].id;

				let lastName = resp[i].lastName;
				let firstName = resp[i].firstName;
				let middleName = resp[i].middleName;
				let fullName = lastName + " " + firstName + " " + middleName;

				opt.innerHTML = fullName;
				selectEmployee.appendChild(opt);
			}
		}
	});
}

function getDataForTable() {
	$.ajax({
		type: "GET",
		url: URL_REST_USERS + "all",
		data: null,
		success: function(resp) {
			console.log(resp);
			let rows = [];
			for (let i = 0; i < resp.length; ++i) {
				let user = resp[i];
				let userRow = toUserRow(user);
				rows.push(userRow);
			}
			setDataToTable(rows);
		}
	});
}

function btnAddClicked() {
	const password = txtPassword.value.trim();
	const repeatPass = txtRepeatPassword.value.trim();
	
	if (password != repeatPass) {
		alert("Password and confirmation password do not match.");
		return;
	}

	let roles = getRoles();

	if (roles.length < 1) {
		alert("Please select role...");
		return;
	}
	
	let user = createUserFromForm();

	if(isUserExist(user)){
		alert("Username or employee already exist!")	
		return;
	}

	postDataToServer(user);
}

function setDataToTable(data) {
	$table.bootstrapTable({ data: data });
}

function toUserRow(user) {
	let userRow = new Object();
	userRow.username = user.username;
	userRow.id = user.id;
	userRow.employee = user.lastName + ' ' + user.firstName + ' ' + user.middleName;
	userRow.roleArray = user.roles;

	let strRoles = "";
	for (let i = 0; i < user.roles.length; ++i) {
		if (i < (user.roles.length - 1)) {
			strRoles += (user.roles[i].roleName + ", ");
		} else {
			strRoles += user.roles[i].roleName;
		}
	}

	userRow.role = strRoles;
	return userRow;
}

function getRoles(){
	const lenOfRoles = checkBoxRoles.length;
	let roles = [];
	for (let i = 0; i < lenOfRoles; ++i) {
		if (checkBoxRoles[i].checked) {
			let role = new Object();
			role.id = parseInt(checkBoxRoles[i].value);
			roles.push(role);
		}
	}
	return roles;
}

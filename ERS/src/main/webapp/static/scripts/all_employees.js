let token = sessionStorage.getItem("token");
let userApproveButtons; 
let userDenyButtons;
let getEmployeesUrl = "http://localhost:8080/ERS/api/company_employees";
let getEmpUrl = "http://localhost:8080/ERS/api/set_employee";
let updateUserUrl = "http://localhost:8080/ERS/update_user";
let deleteUserUrl = "http://localhost:8080/ERS/delete_user";

document.getElementById("nav-emp").addEventListener("click", navEmp)
document.getElementById("nav-man").addEventListener("click", navMan)
//document.getElementById("nav-admin").addEventListener("click", navAdmin)
//document.getElementById("nav-comp").addEventListener("click", navComp)

function navEmp(){
	let permissionLevel = token.split(":")[1];
	if(permissionLevel == 0){
		window.location.href = "http://localhost:8080/ERS/employee_home";
	}
	else if(permissionLevel == 1){
		window.location.href = "http://localhost:8080/ERS/all_employees";
	}
	else if(permissionLevel == 2){
		window.location.href = "http://localhost:8080/ERS/all_employees";
	}
	else{
		alert("You are not signed in. Log in or create an account.");
		window.location.href = "http://localhost:8080/ERS/home";
	}
}
function navMan(){
	let permissionLevel = token.split(":")[1];
	if(permissionLevel == 0){
		alert("You do not have access to manager pages.");
		window.location.href = "http://localhost:8080/ERS/employee_home";
	}
	else if(permissionLevel == 1){
		window.location.href = "http://localhost:8080/ERS/manager_home";
	}
	else if(permissionLevel == 2){
		window.location.href = "http://localhost:8080/ERS/all_managers";
	}
	else{
		alert("You are not signed in. Log in or create an account.");
		window.location.href = "http://localhost:8080/ERS/home";
	}
}
function navAdmin(){
	let permissionLevel = token.split(":")[1];
	if(permissionLevel == 0){
		alert("You do not have access to admin pages.");
		window.location.href = "http://localhost:8080/ERS/employee_home";
	}
	else if(permissionLevel == 1){
		alert("You do not have access to admin pages.");
		window.location.href = "http://localhost:8080/ERS/manager_home";
	}
	else if(permissionLevel == 2){
		window.location.href = "http://localhost:8080/ERS/administrator_home";
	}
	else{
		alert("You are not signed in. Log in or create an account.");
		window.location.href = "http://localhost:8080/ERS/home";
	}
}
function navComp(){
	let permissionLevel = token.split(":")[1];
	if(permissionLevel == 0){
		alert("You do not have access to company pages.");
		window.location.href = "http://localhost:8080/ERS/employee_home";
	}
	else if(permissionLevel == 1){
		alert("You do not have access to company pages.");
		window.location.href = "http://localhost:8080/ERS/manager_home";
	}
	else if(permissionLevel == 2){
		window.location.href = "http://localhost:8080/ERS/my_company";
	}
	else{
		window.location.href = "http://localhost:8080/ERS/companie_home";
	}
}
function logout(){
	sessionStorage.token = null;
}

function sendAjaxGet(url, callback){
	
	let xhr = new XMLHttpRequest();
	
	xhr.open("GET", url);
	xhr.setRequestHeader("Authorization",token);
	
	xhr.onreadystatechange = function(){
		
		if(xhr.readyState===4 && xhr.status===200){
			callback(xhr.response);
		}
	}
	
	xhr.send();
}
function sendAjaxPut(url, body, callback){

	let xhr = new XMLHttpRequest();
	xhr.open("PUT", url);
	
	xhr.onreadystatechange = function(){
		
		if(xhr.readyState===4 && xhr.status===200){
			callback(xhr.response);
		}
	}
	
	xhr.setRequestHeader("Authorization",token);
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.send(body);
}
function sendAjaxDelete(url, body, callback){
	console.log(body);
	let xhr = new XMLHttpRequest();
	xhr.open("DELETE", url);
	xhr.onreadystatechange = function(){
		if(xhr.readyState===4 && xhr.status===200){
			callback(body);
		}
	}
	xhr.setRequestHeader("Authorization", token);
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.send(body);
}

function setEmployees(response){
	
	let tokenArr = token.split(":");
	let manEmail = tokenArr[0];
	
	let responseArray = JSON.parse(response);
	let users = responseArray[0];
	let allInfo = responseArray[1];
	let unapprovedAccounts = document.getElementById("unapproved-accounts");
	let employees = document.getElementById("employee-list");
	
	for(let i = 0; i < users.length; i++ ){
		let row = document.createElement("tr");
		let nameTd = document.createElement("td");
		let emailTd = document.createElement("td");
		let positionTd = document.createElement("td");
		let name = allInfo[i].firstName +" "+allInfo[i].lastName;
		let email = users[i].email;
		let position = allInfo[i].position;
		row.id = "user"+users[i].id;
		nameTd.innerHTML = name;
		emailTd.innerHTML = email;
		positionTd.innerHTML = position;
		row.appendChild(nameTd);
		row.appendChild(emailTd);
		row.appendChild(positionTd);
		
		 if(users[i].approved === false){
			let approveButton = document.createElement("button");
			let denyButton = document.createElement("button");
			approveButton.innerHTML = "Approve";
			denyButton.innerHTML = "Deny"
			approveButton.className = "btn btn-success user-approve";
			denyButton.className = "btn btn-danger user-deny";
			approveButton.id = `app-${users[i].id}`;
			denyButton.id = `deny-${users[i].id}`;
			let appTd = document.createElement("td");
			let denTd = document.createElement("td");
			appTd.appendChild(approveButton);
			denTd.appendChild(denyButton);
			row.appendChild(appTd);
			row.appendChild(denTd);
			unapprovedAccounts.appendChild(row);
		}
		else{
			let phoneTd = document.createElement("td");
			let addressTd = document.createElement("td");
			let address = allInfo[i].address;
			let phone = allInfo[i].phone;
			phoneTd.innerHTML = phone;
			addressTd.innerHTML = address;
			row.appendChild(nameTd);
			row.appendChild(emailTd);
			row.appendChild(positionTd);
			row.appendChild(phoneTd);
			row.appendChild(addressTd);
			employees.appendChild(row);
		}
	}
	buttonSet();
}
function approveUser(){
	let id = this.id.replace("app-", "");
	let body = `true&${id}`;
	sendAjaxPut(updateUserUrl, body, moveUser);
}
function denyUser(){
	let id = this.id.split("-")[1];
	let body = `id=${id}`;
	sendAjaxDelete(deleteUserUrl, body, removeUser);
}
function moveUser(response){
	
	response = JSON.parse(response);
	
	let updatedUser = response[0];
	let updatedInfo = response[1];
	let id = updatedUser.id;
	let unapprovedAccounts = document.getElementById("unapproved-accounts");
	let employees = document.getElementById("employee-list");
	let user = document.getElementById("user"+id);
	let appButton = document.getElementById("app-"+id);
	let denyButton = document.getElementById("deny-"+id);
	let appParentNode = appButton.parentNode;
	let denyParentNode = denyButton.parentNode;
	appButton.parentNode.removeChild(appButton);
	denyButton.parentNode.removeChild(denyButton);
	user.parentNode.removeChild(user);
	appParentNode.innerHTML = updatedInfo.phone;
	denyParentNode.innerHTML = updatedInfo.address;
	employees.appendChild(user);
}
function removeUser(body){
	let id = body.split("=")[1];
	document.getElementById("");
	let user = document.getElementById("user"+id);
	user.parentNode.removeChild(user);
}
function buttonSet(){
	 userApproveButtons = document.getElementsByClassName("user-approve")
	 userDenyButtons = document.getElementsByClassName("user-deny");
	 
	 for(let uab of userApproveButtons){
		uab.addEventListener("click", approveUser);
		}
	 for(let udb of userDenyButtons){
		udb.addEventListener("click", denyUser); 
	 } 
}
sendAjaxGet(getEmployeesUrl, setEmployees);
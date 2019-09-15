let token = sessionStorage.getItem("token");
let setEmpUrl = "http://localhost:8080/ERS/api/set_employee";
let setReimbUrl = "http://localhost:8080/ERS/api/emp_reimbursements";
let getLastReimbUrl = "http://localhost:8080/ERS/api/last_reimbursement";
let editInfoUrl = "http://localhost:8080/ERS/update_info";

document.getElementById("nav-emp").addEventListener("click", navEmp)
document.getElementById("nav-man").addEventListener("click", navMan)
document.getElementById("nav-admin").addEventListener("click", navAdmin)
document.getElementById("nav-comp").addEventListener("click", navComp)
document.getElementById("request-reimbursement").addEventListener("click", submitReimbReq);
document.getElementById("logout").addEventListener("click", logout);
document.getElementById("edit-info").addEventListener("click", unhideInfo);
document.getElementById("submit-edit").addEventListener("click", editInfo);

function navEmp(){
	let permissionLevel = token.split(":")[1];
	if(permissionLevel == 0){
		window.location.href = "http://localhost:8080/ERS/employee_home";
	}
	else if(permissionLevel == 1){
		window.location.href = "http://localhost:8080/ERS/static/all_employees";
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
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState===4 && xhr.status===200){
			callback(xhr.response);
		}
	}
	
	xhr.setRequestHeader("Authorization",token);
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
	xhr.setRequestHeader("Content-Type","application/json");
	
	xhr.send(body);
}

function setEmployee(employeeJSON){
	if(employeeJSON){
		let employeeInfo = JSON.parse(employeeJSON);
		let tokArr = token.split(":");
		let email = tokArr[0];
		
	//document.getElementById("employee-photo").innerHtml.value += employeeInfo.imgSrc;	
	document.getElementById("name").innerHTML += employeeInfo.firstName +" "+employeeInfo.lastName;
	document.getElementById("position").innerHTML += employeeInfo.position;
	document.getElementById("email").innerHTML += " "+email;
	document.getElementById("phone").innerHTML += " "+ employeeInfo.phone;
	document.getElementById("address").innerHTML += " "+employeeInfo.address;
	
	let firstName = document.getElementById("edit-first-name");
	let lastName = document.getElementById("edit-last-name");
	let phone = document.getElementById("edit-phone");
	let position = document.getElementById("edit-position");
	let address = document.getElementById("edit-address");
	
	firstName.value = employeeInfo.firstName;
	lastName.value = employeeInfo.lastName;
	phone.value = employeeInfo.phone;
	position.value = employeeInfo.position;
	address.value = employeeInfo.address;

	} else {
		console.log("issue getting users");
	}
	sendAjaxGet(setReimbUrl, setReimb);
}

function submitReimbReq(){
	
	let xhr= new XMLHttpRequest();
	let url = "http://localhost:8080/ERS/new_reimbursement";
	let amount = document.getElementById("amount").value; 
	let date = document.getElementById("date").value;
	let reason = document.getElementById("reason").value;
	
	let reimbursement = `amt=${amount}&date=${date}&reason=${reason}`;
	
	document.getElementById("amount").value = null;
	document.getElementById("date").value = null;
	document.getElementById("reason").value = null;
	
	xhr.open("POST", url);
	
	xhr.onreadystatechange = function(){
		
		if(xhr.readyState === 4 && xhr.status == 200){
			
			sendAjaxGet(getLastReimbUrl, getLastReimb);
		}
		
	}
	
	xhr.setRequestHeader("Authorization", token);
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.send(reimbursement);
}

function setReimb(reimbJSON){

	let reimbursements = JSON.parse(reimbJSON);
	let pending = document.getElementById("pending-reimbursements");
	let resolved = document.getElementById("resolved-reimbursements");
	
	for(let reimbursement of reimbursements){
		
		let row = document.createElement("tr");
		let amt = document.createElement("td");
		let date = document.createElement("td");
		let reason = document.createElement("td");
		let status = document.createElement("td");
		amt.innerHTML = reimbursement.amt;
		date.innerHTML = formatDate(reimbursement.date);
		reason.innerHTML = reimbursement.reason;
		
		if (reimbursement.approved || reimbursement.denied){
			if(reimbursement.approved === false){
				status.innerHTML = "Denied"
			}
			else{
				status.innerHTML = "Approved"
			}
			row.appendChild(amt);
			row.appendChild(date);
			row.appendChild(reason);
			row.appendChild(status);
			resolved.appendChild(row);
		}
		else{
			status.innerHTML = "Pending";
			row.appendChild(amt);
			row.appendChild(date);
			row.appendChild(reason);
			row.appendChild(status);
			pending.appendChild(row);
			
		}
	}
}

function getLastReimb(reimbJSON){
	
	let reimb = JSON.parse(reimbJSON);
	let pending = document.getElementById("pending-reimbursements");
	let row = document.createElement("tr");
	let amt = document.createElement("td");
	let date = document.createElement("td");
	let reason = document.createElement("td");
	let status = document.createElement("td");
	amt.innerHTML = reimb.amt;
	date.innerHTML = formatDate(reimb.date);
	reason.innerHTML = reimb.reason;
	status.innerHTML = "Pending";
	row.appendChild(amt);
	row.appendChild(date);
	row.appendChild(reason);
	row.appendChild(status);
	pending.appendChild(row);
}

function formatDate(date){
	let month = date.monthValue;
	let day = date.dayOfMonth;
	let year = date.year;
	let dateString = `${month}/${day}/${year}`;

	return dateString;
	
}

function unhideInfo(){
	
	let hiddenForm = document.getElementById("hidden-edit-info");
	
	if(hiddenForm.style.display == "none"){
		hiddenForm.style.display = "block";
	}
	else{
		hiddenForm.style.display = "none";
	}
	
}

function editInfo(){
	let firstName = document.getElementById("edit-first-name").value;
	let lastName = document.getElementById("edit-last-name").value;
	let phone = document.getElementById("edit-phone").value;
	let position = document.getElementById("edit-position").value;
	let address = document.getElementById("edit-address").value;
	
	let body = {
			firstName: firstName,
			lastName: lastName,
			phone: phone,
			position: position,
			address: address
	}
	
	body = JSON.stringify(body);
	
	sendAjaxPut(editInfoUrl, body, resetInfo);
}

function resetInfo(response){
	document.getElementById("name").innerHTML = null;
	document.getElementById("position").innerHTML = null;
	document.getElementById("email").innerHTML = null;
	document.getElementById("phone").innerHTML = null;
	document.getElementById("address").innerHTML = null;
	
	sendAjaxGet(setEmpUrl, setEmployee);
}

sendAjaxGet(setEmpUrl, setEmployee);



let token = sessionStorage.getItem("token");

let reimbApproveButtons;
let reimbDenyButtons;
let getEmployeesUrl = "http://localhost:8080/ERS/api/company_employees";
let getReimbursementUrl = "http://localhost:8080/ERS/api/company_reimbursements";
let updateReimbUrl = "http://localhost:8080/ERS/update_reimbursement";
let managerInfoUrl = "http://localhost:8080/ERS/api/set_employee";

document.getElementById("nav-emp").addEventListener("click", navEmp);
document.getElementById("nav-man").addEventListener("click", navMan);
document.getElementById("nav-admin").addEventListener("click", navAdmin);
document.getElementById("nav-comp").addEventListener("click", navComp);
document.getElementById("logout").addEventListener("click", logout);

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

sendAjaxGet(getReimbursementUrl, setReimbursements);

function buttonSet(){
	 reimbButtons = document.getElementsByClassName("reimb-btn");
	 
	 for(let rab of reimbButtons){
		 rab.addEventListener("click", decideReimb);
	 }
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
			callback(body);
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

function setReimbursements(response){
	let responseArray = JSON.parse(response);
	let pendingReimbursements = document.getElementById("pending-reimbursements");
	let resolvedReimbursements = document.getElementById("resolved-reimbursements");
	
	for (let reimbursement of responseArray){
		let row = document.createElement("tr");
		row.id = "reimbursement"+ reimbursement.id;
		let amount = document.createElement("td");
		let date = document.createElement("td");
		let reason = document.createElement("td");
		let status = document.createElement("td");
		let app = document.createElement("td");
		let den = document.createElement("td");
		amount.innerHTML = reimbursement.amt;
		date.innerHTML = formatDate(reimbursement.date);
		reason.innerHTML = reimbursement.reason;
		
		if(reimbursement.approved === false && reimbursement.denied === false){
			
			let approveButton = document.createElement("button");
			let denyButton = document.createElement("button");
			status.innerHTML = "Pending"
			approveButton.innerHTML = "Approve";
			denyButton.innerHTML = "Deny"
			approveButton.className = "btn btn-success reimb-btn";
			denyButton.className = "btn btn-danger reimb-btn";
			approveButton.id = `reimb-app-${reimbursement.id}`;
			denyButton.id = `reimb-den-${reimbursement.id}`;
			app.appendChild(approveButton);
			den.appendChild(denyButton);
			
			row.appendChild(amount);
			row.appendChild(date);
			row.appendChild(reason);
			row.appendChild(status);
			row.appendChild(app);
			row.appendChild(den);
			pendingReimbursements.appendChild(row);
		}
		
		else{
			row.appendChild(amount);
			row.appendChild(date);
			row.appendChild(reason);
			if(reimbursement.approved === true){
				status.innerHTML = "Approved";
			}
			else{
				status.innerHTML = "Denied"
			}
			row.appendChild(status);
			resolvedReimbursements.appendChild(row);
		}
	}
	buttonSet();
	sendAjaxGet(managerInfoUrl, setInfo);
}

function decideReimb(){
	let id = this.id.split("-")[2];
	let appOrDen = this.id.split("-")[1];
	let body = `${id}:${appOrDen}`;
	sendAjaxPut(updateReimbUrl, body, moveReimb);
}


function moveReimb(body){
	let id = body.split(":")[0];
	let reimb = document.getElementById("reimbursement"+id);
	let resolved = document.getElementById("resolved-reimbursements");
	let reimbAppBtn = document.getElementById("reimb-app-"+id);
	let reimbDenBtn = document.getElementById("reimb-den-"+id);
	reimbAppBtn.parentNode.removeChild(reimbAppBtn);
	reimbDenBtn.parentNode.removeChild(reimbDenBtn);
	reimb.parentNode.removeChild(reimb);
	resolved.appendChild(reimb);
}

function formatDate(date){
	let month = date.monthValue;
	let day = date.dayOfMonth;
	let year = date.year;
	let dateString = `${month}/${day}/${year}`;

	return dateString;
	
}

function setInfo(response){
	let employeeInfo = JSON.parse(response);
	let tokArr = token.split(":");
	let email = tokArr[0];
			
	document.getElementById("name").innerHTML += employeeInfo.firstName +" "+employeeInfo.lastName;
	document.getElementById("position").innerHTML += employeeInfo.position;
	document.getElementById("email").innerHTML += " "+email;
	document.getElementById("phone").innerHTML += " "+ employeeInfo.phone;
	document.getElementById("address").innerHTML += " "+employeeInfo.address;
	
}
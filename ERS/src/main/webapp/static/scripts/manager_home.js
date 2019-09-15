document.getElementById("logout").addEventListener("click", logout);
let userApproveButtons; 
let userDenyButtons;
let reimbApproveButtons;
let reimbDenyButtons;
let getReimbursementUrl = "http://localhost:8080/ERS/api/company_reimbursements";
let getEmployeesUrl = "http://localhost:8080/ERS/api/company_employees";
let updateUserUrl = "http://localhost:8080/ERS/update_user";
let updateReimbUrl = "http://localhost:8080/ERS/update_reimbursement";
let deleteUserUrl = "http://localhost:8080/ERS/delete_user";
let token = sessionStorage.token;
sendAjaxGet(getEmployeesUrl, setEmployees);

function buttonSet(){
	 userApproveButtons = document.getElementsByClassName("user-approve")
	 userDenyButtons = document.getElementsByClassName("user-deny");
	 reimbButtons = document.getElementsByClassName("reimb-btn");
	 
	 for(let uab of userApproveButtons){
		uab.addEventListener("click", approveUser);
		}
	 for(let udb of userDenyButtons){
		udb.addEventListener("click", denyUser); 
	 }
	 for(let rab of reimbButtons){
		 rab.addEventListener("click", decideReimb);
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
		let row = document.createElement("row");
		let nameTd = document.createElement("td");
		let emailTd = document.createElement("td");
		let positionTd = document.createElement("td");
		let name = allInfo[i].firstName +" "+allInfo[i].lastName;
		let email = users[i].email;
		let position = users[i].position;
		row.id = "user"+users[i].id;
		nameTd.innerHTML = name;
		emailTd.innerHTML = email;
		positionTd.innerHTML = position;
		row.appendChild(nameTd);
		row.appendChild(emailTd);
		row.appendChild(positionTd);
		
		if(users[i].email === manEmail){
			setInfo(users[i], allInfo[i]);
		}
		else if(users[i].approved === false){
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
	sendAjaxGet(getReimbursementUrl, setReimbursements);
}

function setInfo(user, info){
	let infoContainer = document.getElementById("info-container");
	let name = document.createElement("p");
	let email = document.createElement("p");
	let address = document.createElement("p");
	let phone = document.createElement("p");
	let position = document.createElement("p");
	
	name.innerHTML = info.firstName +" "+info.lastName;
	email.innerHTML = user.email;
	address.innerHTML = info.address;
	phone.innerHTML = info.phone;
	position.innerHTML = info.position;
	
	infoContainer.appendChild(name);
	infoContainer.appendChild(email);
	infoContainer.appendChild(address);
	infoContainer.appendChild(phone);
	infoContainer.appendChild(position);
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



function decideReimb(){
	let id = this.id.split("-")[2];
	let appOrDen = this.id.split("-")[1];
	let body = `${id}:${appOrDen}`;
	sendAjaxPut(updateReimbUrl, body, moveReimb);
}

function denyReimb(){
	console.log(this);
}

function moveUser(body){
	
	let id = body.split("&")[1];
	let unapprovedAccounts = document.getElementById("unapproved-accounts");
	let employees = document.getElementById("employee-list");
	let user = document.getElementById("user"+id);
	let appButton = document.getElementById("app-"+id);
	let denyButton = document.getElementById("deny-"+id);
	appButton.parentNode.removeChild(appButton);
	denyButton.parentNode.removeChild(denyButton);
	user.parentNode.removeChild(user);
	employees.appendChild(user);
}

function removeUser(body){
	let id = body.split("=")[1];
	document.getElementById("");
	let user = document.getElementById("user"+id);
	user.parentNode.removeChild(user);
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

document.getElementById("request-reimbursement").addEventListener("click", submitReimbReq);
document.getElementById("logout").addEventListener("click", logout);

function logout(){
	sessionStorage.token = null;
}

let setEmpUrl = "http://localhost:8080/ERS/api/set_employee";
let setReimbUrl = "http://localhost:8080/ERS/api/emp_reimbursements";
let getLastReimbUrl = "http://localhost:8080/ERS/api/last_reimbursement"
let token = sessionStorage.getItem("token");

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
sendAjaxGet(setEmpUrl, setEmployee);



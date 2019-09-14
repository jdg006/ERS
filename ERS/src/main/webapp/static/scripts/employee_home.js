document.getElementById("request-reimbursement").addEventListener("click", submitReimbReq);

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
			console.log("in here");
			sendAjaxGet(getLastReimbUrl, getLastReimb);
		}
		
	}
	
	xhr.setRequestHeader("Authorization", token);
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.send(reimbursement);
}

function setReimb(reimbJSON){

	let reimbursements = JSON.parse(reimbJSON);
	let pendingOl = document.getElementById("pending-reimbursements");
	let resolvedOl = document.getElementById("resolved-reimbursements");
	
	for(let reimbursement of reimbursements){
		let newLi = document.createElement("li");
		newLi.innerHTML += reimbursement.amt;
		
		if (reimbursement.approved || reimbursement.denied){
			
			resolvedOl.appendChild(newLi);
		}
		else{
			
			pendingOl.appendChild(newLi);
			
		}
	}
	
}

function getLastReimb(reimbJSON){
	
	let reimb = JSON.parse(reimbJSON);
	let pendingOl = document.getElementById("pending-reimbursements");
	let newLi = document.createElement("li");
	newLi.innerHTML += reimb;
	pendingOl.appendChild(newLi);
}

sendAjaxGet(setEmpUrl, setEmployee);



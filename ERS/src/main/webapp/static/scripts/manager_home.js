let getReimbursementUrl = "http://localhost:8080/ERS/api/company_reimbursements";
let getEmployeesUrl = "http://localhost:8080/ERS/api/company_employees";
let token = sessionStorage.token;

sendAjaxGet(getReimbursementUrl, setReimbursements);
sendAjaxGet(getEmployeesUrl, setEmployees);

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

function setReimbursements(response){
	console.log(response);
}

function setEmployees(response){
	let responseArray = JSON.parse(response);
	let users = responseArray[0];
	let allInfo = responseArray[1];
}



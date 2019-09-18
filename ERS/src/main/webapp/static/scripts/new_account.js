let token = sessionStorage.getItem("token");
let getUrl = "http://localhost:8080/ERS/api/companies";

document.getElementById("nav-emp").addEventListener("click", navEmp);
document.getElementById("nav-man").addEventListener("click", navMan);
//document.getElementById("nav-admin").addEventListener("click", navAdmin);
//document.getElementById("nav-comp").addEventListener("click", navComp);
document.getElementById("create").addEventListener("click", sendAjaxPost);
document.getElementById("logout").addEventListener("click", logout);

function navEmp(){
	let permissionLevel = token.split(":")[1];
	if(permissionLevel == 0){
		window.location.href = "http://localhost:8080/ERS/employee_home";
	}
	else if(permissionLevel == 1){
		window.location.href = "http://localhost:8080/ERS/static/all_employees";
	}
	else if(permissionLevel == 2){
		window.location.href = "http://localhost:8080/ERS/static/all_employees";
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
	
	xhr.send();
}

function sendAjaxPost(){
	let postUrl = "http://localhost:8080/ERS/new_account";
	
	let xhr = new XMLHttpRequest(); 
	xhr.open("POST", postUrl);
	//let requestBody = null;
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState===4 && xhr.status===200){
			window.location.href = "http://localhost:8080/ERS/home";
		}
		else if (xhr.readyState===4 && xhr.status===500){
			alert("That email is already take please try another.");
			document.getElementById("email").value = null;
		}
	}
	
	let email = document.getElementById("email").value;
	let password = document.getElementById("password").value;
	let companyId = document.getElementById("company").value;
	let permissionLevel = document.getElementById("account-type").value;
	
	let firstName = document.getElementById("first-name").value;
	let lastName = document.getElementById("last-name").value;
	let phone = document.getElementById("phone").value;
	let position = document.getElementById("position").value;
	let address = document.getElementById("address").value;
	
	
	
	let requestBody = `email=${email}&password=${password}&companyId=${companyId}&permissionLevel=${permissionLevel}&firstName=${firstName}&lastName=${lastName}&phone=${phone}&position=${position}&address=${address}`;
	
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.send(requestBody);
	
}

function setCompanies(response){

  let parentSelect = document.getElementById("company");	
  let companies = JSON.parse(response);
  for (company of companies){
	  let newOption = document.createElement("option");
	  newOption.innerHTML = company.name;
	  newOption.value = company.id;
	  parentSelect.appendChild(newOption);
  }
  
}


function requestAccount(response){
	
	console.log("here");
	let email = document.getElementById("email").value;
	let password = document.getElementById("password").value;
	let companyId = document.getElementById("company").value;
	let permissionLevel = document.getElementById("account-type").value;
	
	let firstName = document.getElementById("first-name").value;
	let lastName = document.getElementById("last-name").value;
	let phone = document.getElementById("phone").value;
	let position = document.getElementById("position").value;
	let address = document.getElementById("address").value;
	
	
	
	let requestBody = `email=${email}&password=${password}&companyId=${companyId}
	permissionLevel=${permissionLevel}&firstName=${firstName}&lastName=${lastName}
	&phone=${phone}&position=${position}&address=${address}`;
	
	return requestBody;
	
}

sendAjaxGet(getUrl, setCompanies);
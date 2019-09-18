let token = sessionStorage.getItem("token");

document.getElementById("nav-emp").addEventListener("click", navEmp)
document.getElementById("nav-man").addEventListener("click", navMan)
//document.getElementById("nav-admin").addEventListener("click", navAdmin)
//document.getElementById("nav-comp").addEventListener("click", navComp)

function navEmp(){
	
	let permissionLevel;
	
	if(token != null){
		
		permissionLevel = token.split(":")[1];
	}
	else {
		
	  permissionLevel = -1;
	}
	

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
	let permissionLevel;
	
	if(token != null){
		
		permissionLevel = token.split(":")[1];
	}
	else {
		
	  permissionLevel = -1;
	}
	
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
let permissionLevel;
	
	if(token != null){
		
		permissionLevel = token.split(":")[1];
	}
	else {
		
	  permissionLevel = -1;
	}
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
let permissionLevel;
	
	if(token != null){
		
		permissionLevel = token.split(":")[1];
	}
	else {
		
		permissionLevel = -1;
	}
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


document.getElementById("login").addEventListener("click", requestLogin)
document.getElementById("logout").addEventListener("click", logout);

function logout(){
	sessionStorage.token = null;
}

function requestLogin(){
	
	let url = "http://localhost:8080/ERS/login";
	let xhr = new XMLHttpRequest();
	
	xhr.open("POST", url);
	
	xhr.onreadystatechange = function (){
		if(xhr.readyState === 4 && xhr.status === 200){
			
			let authorization = xhr.getResponseHeader("Authorization");
			sessionStorage.setItem("token", authorization);
			let tokenArray = authorization.split(":");
			if(tokenArray[1] == 0){
				window.location.href = "http://localhost:8080/ERS/employee_home";
			}
			else if(tokenArray[1] == 1){
				window.location.href = "http://localhost:8080/ERS/manager_home";
			}
			else if(tokenArray[1] == 2){
				window.location.href = "http://localhost:8080/ERS/administrator_home";
			}
			else{
				window.location.href = "http://localhost:8080/ERS/home";
			}
			
		}
		else if(xhr.readyState === 4 && xhr.status === 401){
			alert("Either your credentials are incorrect or your account has not been approved or has been denied. Please try again or contact your manager.");
		}
	}
	
	let email = document.getElementById("email").value;
	let password = document.getElementById("password").value;
	
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded")
	
	let requestBody = `email=${email}&password=${password}`;
	
	xhr.send(requestBody);
	
}

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
	}
	console.log("here4");
	let email = document.getElementById("email").value;
	let password = document.getElementById("password").value;
	
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded")
	
	let requestBody = `email=${email}&password=${password}`;
	
	xhr.send(requestBody);
	
}

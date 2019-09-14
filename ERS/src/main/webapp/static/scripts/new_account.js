let getUrl = "http://localhost:8080/ERS/api/companies";


document.getElementById("create").addEventListener("click", sendAjaxPost);


sendAjaxGet(getUrl, setCompanies);

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

let requestUrl = "http://localhost:8080/ERS/api/users";

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

function displayUsers(userJSON){
	if(userJSON){
		let users = JSON.parse(userJSON);
		
		let table = document.getElementById("user-table");
		table.hidden = false;
		
		for(let user of users){
			let newRow = document.createElement("tr");
			let email = user.email;
			
			newRow.innerHTML = `<td>${user.id}</td><td>${user.email}</td>`;
			table.appendChild(newRow);
			
		}
	} else {
		console.log("issue getting users");
	}
}


sendAjaxGet(requestUrl, displayUsers);
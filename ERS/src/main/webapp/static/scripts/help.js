document.getElementById("logout").addEventListener("click", logout);

function logout(){
	sessionStorage.token = null;
}
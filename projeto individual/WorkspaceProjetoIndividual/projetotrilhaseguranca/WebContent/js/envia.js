function ValidaDados() {
	var senhaembase64 = btoa(document.frmLogin.senha.value);
	
	document.frmLogin.senha.value = senhaembase64;
	
	return true;
}
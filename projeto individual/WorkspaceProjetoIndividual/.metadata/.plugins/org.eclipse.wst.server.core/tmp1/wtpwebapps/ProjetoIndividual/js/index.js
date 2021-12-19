function validaFormLogin(){
	var nome = document.frmLogin.idLogin.value;
	var senha = document.frmLogin.idPassword.value;

	if(nome==""){
		alert("Preencha corretamente o campo de Nome!")
		document.frmfaleconosco.idLogin.focus();
		return false;
	}
	if(senha==""){
		alert("Preencha corretamente o campo de Senha!")
		document.frmfaleconosco.idPassword.focus();
		return false;
	}
}
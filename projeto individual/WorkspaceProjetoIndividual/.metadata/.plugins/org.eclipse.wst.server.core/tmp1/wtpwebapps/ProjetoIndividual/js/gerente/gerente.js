
BIBLIOTECA = new Object ();

$(document).ready(function(){
	//Vai carregar o menu e o rodapé
	
	$("nav").load("/ProjetoIndividual/pages/gerente/geralCadastro/menu.html");
	$("footer").load("/ProjetoIndividual/pages/gerente/geralCadastro/footer.html");
	
	BIBLIOTECA.exibirAviso = function(aviso){
		var modal = {
			title: "Aviso",
			height: 250,
			width: 400,
			modal: true,
			buttons: {
				"OK": function(){
					$(this).dialog("close");
				}
			}
		};
		$("#modalAviso").html(aviso);
		$("#modalAviso").dialog(modal);
	};
	
	BIBLIOTECA.exibirPergunta = function(pergunta, id, local){
		var modalPergunta = {
				title: "Confirma a Ação?",
				height: 250,
				width: 400,
				modal: true,
				buttons: {
					"Confirmar": function(){
						BIBLIOTECA.local.excluir(id);
						$(this).dialog("close");
					},
				
					"Cancelar": function(){
						$(this).dialog("close");

					}
				},
				close: function(){
					return false;
				}
		};
		
		$("#modalPergunta").html(pergunta);
		return $("#modalPergunta").dialog(modalPergunta);
	};


});
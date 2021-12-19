BIBLIOTECA.categoria = new Object();

$(document).ready(function(){
	
	BIBLIOTECA.categoria.cadastrar = function(){
		var categoria = new Object();
		categoria.nmCategoria = document.frmCategoria.nomeCat.value;
		if(categoria.nmCategoria==""){
			alert("Preencha corretamente o nome da categoria")
		}else{
			$.ajax({
				type: "POST",
				url: "/ProjetoIndividual/rest/categoria/inserir",
				data: JSON.stringify(categoria),
				success: function(msg) {
					BIBLIOTECA.exibirAviso(msg);
					BIBLIOTECA.categoria.buscar();
					$("#frmCategoria").trigger("reset");
				},
				error: function (info){
					alert("Erro não identificado");
				}	
			});
		}
	};
	
	BIBLIOTECA.categoria.buscar = function(){
		var valorBusca = $("#inputBuscaFunc").val();
		
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/categoria/buscarPorNome",
			data: "valorBusca="+valorBusca,
			success: function(dados){
				dados = JSON.parse(dados);
				$("#listaCategoria").html(BIBLIOTECA.categoria.exibir(dados));
		
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao consultar as categorias: "+info.status+" - "+info.statusText);
			}
		
		});
	};
	
	BIBLIOTECA.categoria.exibir = function(listaCategoria){
		
		var tabela = "<table>" +
		"<tr>" +
		"<th>Id</th>" +
		"<th>Categoria</th>" +
		"<th>Ações</th>" +
		"</tr>";
		
		if(listaCategoria != undefined && listaCategoria.length > 0){
			
			for (var i=0; i<listaCategoria.length; i++){
				tabela += "<tr>" +
						"<td>"+listaCategoria[i].id+"</td>" +
						"<td>"+listaCategoria[i].nome+"</td>" +
							"<td>" +
								"<a onclick=\"BIBLIOTECA.categoria.exibirEdicao('"+listaCategoria[i].id+"')\"><img src='../../../imgs/edit.png'></a>" +
								"<a onclick=\"BIBLIOTECA.categoria.confirmarPergunta('"+listaCategoria[i].id+"')\"><img src='../../../imgs/delete.png'></a>" +
							"</td>" +
						"</tr>"
			}
			
		} else if(listaCategoria == ""){
			tabela += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>"
		}
		tabela += "</table>";
		
		return tabela;
	};
	
	BIBLIOTECA.categoria.excluir = function(id){
		$.ajax({
			type: "DELETE",
			url: "/ProjetoIndividual/rest/categoria/excluir/"+id,
			success: function(msg){
				BIBLIOTECA.exibirAviso(msg);
				BIBLIOTECA.categoria.buscar();
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao excluir categoria: "+info.status+" - "+info.statusText);
			}
		});
	};
	
	BIBLIOTECA.categoria.confirmarPergunta = function(id){
		var pergunta = "Deseja mesmo excluir essa categoria?";
		var resposta = BIBLIOTECA.categoria.exibirPergunta(pergunta, id);
		
	};
	
	BIBLIOTECA.categoria.exibirPergunta = function(pergunta, id){
		var modalPergunta = {
				title: "Confirma a Ação?",
				height: 250,
				width: 400,
				modal: true,
				buttons: {
					"Confirmar": function(){
						BIBLIOTECA.categoria.excluir(id);
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

	
	BIBLIOTECA.categoria.exibirEdicao = function(id){
		
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/categoria/buscarPorId/",
			data: "id="+id,
			success: function(categoria){				
				document.frmEditaCategoria.idCategoria.value = categoria.id;
				document.frmEditaCategoria.selCategoriaEdicao.value = categoria.nmCategoria;
				
				var modalEditaCategoria = {
					title: "Editar Categoria",
					height: 300,
					width: 400,
					modal: true,
					buttons:{
						"Salvar": function(){
							BIBLIOTECA.categoria.editar();
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					close: function(){}
				};
				
				$("#modalEditaCategoria").dialog(modalEditaCategoria);
				
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao buscar a categoria para edição: "+info.status+" - "+info.statusText);
			}
		});
	}
	
	BIBLIOTECA.categoria.editar = function(){
		var categoria = new Object();
		
		categoria.id = document.frmEditaCategoria.idCategoria.value;
		categoria.nmCategoria = document.frmEditaCategoria.selCategoriaEdicao.value;
		
		if(categoria.nmCategoria==""){
			alert("Preencha corretamente o nome da categoria")
		}else{
			
			$.ajax({
				
				type: "PUT",
				url: "/ProjetoIndividual/rest/categoria/alterar",
				data: JSON.stringify(categoria),
				success: function(msg){
					BIBLIOTECA.exibirAviso(msg);
					BIBLIOTECA.categoria.buscar();
					$("#modalEditaCategoria").dialog("close");
				},
				error: function(info){
					alert("erro");
					BIBLIOTECA.exibirAviso("Erro ao editar a categoria: "+info.status+" - "+info.statusText);
				}
			});
		}

		
	}
	
	BIBLIOTECA.categoria.buscar();
	
});
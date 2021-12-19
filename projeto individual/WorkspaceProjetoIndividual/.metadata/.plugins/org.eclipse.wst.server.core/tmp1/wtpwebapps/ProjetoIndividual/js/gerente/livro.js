BIBLIOTECA.livro = new Object();


$(document).ready(function(){

	
	BIBLIOTECA.livro.carregarProdutos = function(id){
		
		if(id!=undefined){
			select = "#selCategoriaEdicao";
		}else{
			select = "#selCategoria";
		}
		
		$.ajax({
			type:"GET",
			url:"/ProjetoIndividual/rest/livro/buscar",
			success:function(categorias){
				if(categorias!=""){
					$(select).html("");
					var option = document.createElement("option");
					option.setAttribute ("value","");
					option.innerHTML = ("Escolha");
					$(select).append(option);
					
					console.log("categorias.length");
					console.log(categorias.length);
					
					
					for (var i = 0; i<categorias.length; i++){
						console.log("CATEGORIAS ID");
						console.log(categorias[i]);
						
						console.log("CATEGORIAAAAA ID");
						console.log(id);

						
						var option = document.createElement("option");
						option.setAttribute ("value", categorias[i].id);
						
						if((id!=undefined)&&(id==categorias[i].id)){
							option.setAttribute("selected", "selected");
						}
							
						option.innerHTML = (categorias[i].nmCategoria);
						$(select).append(option);
					}
					
				}else{
					$(select).html("");
					
					var option = document.createElement("option");
					option.setAttribute ("value","");
					option.innerHTML = ("Cadastre uma categoria primeiro!");
					$(select).append(option);
					$(select).addClass("aviso");
				}
			},
			error:function(){
				BIBLIOTECA.exibirAviso("Erro ao buscar as categorias: "+ info.status + " - "+ info.statusText);
				
				$(select).html("");
				var option = document.createElement("option");
				option.setAttribute ("value","0");
				option.innerHTML = ("Erro ao carregar categorias!");
				$(select).append(option);
				$(select).addClass("aviso");
				
				
			}
		});
	};
	
	BIBLIOTECA.livro.cadastrar = function(){
		var livro = new Object();
		livro.nmLivro = document.frmLivro.nomeLivro.value;
		//livro.anoLivro = document.frmLivro.anoLivro.value;
		livro.autLivro = document.frmLivro.autorLivro.value;
		livro.revLivro = document.frmLivro.revLivro.value;
		livro.catLivro = document.frmLivro.CategoriaId.value;
		livro.statusLivro = 0;
		
		var diaMes = "-01-01";
		var anoCompleto = document.frmLivro.anoLivro.value + diaMes;
		
		console.log(anoCompleto);
		
		console.log(livro);
		
		livro.anoLivro = anoCompleto;
		
		if(livro.nmLivro=="" || livro.anoLivro=="" || livro.anoLivro=="" || livro.autLivro=="" || livro.revLivro=="" || livro.catLivro.value=="" || livro.catLivro.value==0 || livro.revLivro<0){
			alert("Preencha corretamente todas as informações do Livro")
		}else{
			$.ajax({
				type: "POST",
				url: "/ProjetoIndividual/rest/livro/inserir",
				data: JSON.stringify(livro),
				success: function(msg) {
					BIBLIOTECA.livro.exibirAviso(msg);
					BIBLIOTECA.livro.buscar();
					//BIBLIOTECA.livro.buscar();
					$("#frmLivro").trigger("reset");
				},
				error: function (info){
					alert("Erro não identificado");
					$("#frmLivro").trigger("reset");
					BIBLIOTECA.livro.buscar();
					BIBLIOTECA.livro.carregarProdutos();
				}	
			});
		}
		BIBLIOTECA.livro.buscar();
	};
	
	BIBLIOTECA.livro.exibirAviso = function(aviso){
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
	
	BIBLIOTECA.livro.buscar = function(){
		var valorBusca = $("#inputBuscaFunc").val();
		
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/livro/buscarPorNome",
			data: "valorBusca="+valorBusca,
			success: function(dados){
				dados = JSON.parse(dados);
				$("#listaLivro").html(BIBLIOTECA.livro.exibir(dados));
		
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao consultar os livros: "+info.status+" - "+info.statusText);
			}
		
		});
	};
	
	BIBLIOTECA.livro.exibir = function(listaLivro){
		console.log("Lista Livro");
		console.log(listaLivro);
		
		var tabela = "<table>" +
		"<tr>" +
		"<th>Id</th>" +
		"<th>Categoria</th>" +
		"<th>Livro</th>" +
		"<th>Ano</th>" +
		"<th>Autor</th>" +
		"<th>Revisão</th>" +
		"<th>Status</th>" +
		"<th>Ações</th>" +
		"</tr>";
		
		if(listaLivro != undefined && listaLivro.length > 0){
			
			for (var i=0; i<listaLivro.length; i++){
				
				var anoCompleto = listaLivro[i].ano;				
				var anoFormatado = anoCompleto.substr(0,4)

				
				var status = "";
				
				if (listaLivro[i].status_fisico==0){
					status="Disponível";
				}else if(listaLivro[i].status_fisico==1){
					status="Estragado";
				}else{
					status="Em concerto";
				}
				
				tabela += "<tr>" +
						"<td>"+listaLivro[i].id+"</td>" +
						"<td>"+listaLivro[i].nomeCategoria+"</td>" +
						"<td>"+listaLivro[i].nome+"</td>" +
						"<td>"+anoFormatado+"</td>" +
						"<td>"+listaLivro[i].autor+"</td>" +
						"<td>"+listaLivro[i].revisao+"</td>" +
						"<td>"+status+"</td>" +
							"<td>" +
								"<a onclick=\"BIBLIOTECA.livro.exibirEdicao('"+listaLivro[i].id+"')\"><img src='../../../imgs/edit.png'></a>" +
								"<a onclick=\"BIBLIOTECA.livro.confirmarPergunta('"+listaLivro[i].id+"')\"><img src='../../../imgs/delete.png'></a>" +
							"</td>" +
						"</tr>"
			}
			
		} else if(listaLivro == ""){
			tabela += "<tr><td colspan='8'>Nenhum registro encontrado</td></tr>"
		}
		tabela += "</table>";
		
		return tabela;
	};
	
	BIBLIOTECA.livro.excluir = function(id){
		$.ajax({
			type: "DELETE",
			url: "/ProjetoIndividual/rest/livro/excluir/"+id,
			success: function(msg){
				BIBLIOTECA.exibirAviso(msg);
				BIBLIOTECA.livro.buscar();
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao excluir categoria: "+info.status+" - "+info.statusText);
			}
		});
	};
	
	BIBLIOTECA.livro.confirmarPergunta = function(id){
		var pergunta = "Deseja mesmo excluir essa categoria?";
		var resposta = BIBLIOTECA.livro.exibirPergunta(pergunta, id);
	};	
	
	BIBLIOTECA.livro.exibirPergunta = function(pergunta, id){
		var modalPergunta = {
				title: "Confirma a Ação?",
				height: 250,
				width: 400,
				modal: true,
				buttons: {
					"Confirmar": function(){
						BIBLIOTECA.livro.excluir(id);
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
	
	BIBLIOTECA.livro.exibirEdicao = function(id){
		
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/livro/buscarPorId/",
			data: "id="+id,
			success: function(livro){
				console.log("OBJETO LIVRO");
				console.log(livro);
				document.frmEditaLivro.idLivro.value = livro.id;
				
				console.log("livro.catLivro");
				console.log(livro.catLivro);

				
				BIBLIOTECA.livro.carregarProdutos(livro.catLivro);
				
				document.frmEditaLivro.seLivroEdicao.value = livro.nmLivro;
				//document.frmEditaLivro.anoLivro.value = livro.anoLivro;
				document.frmEditaLivro.autorLivro.value = livro.autLivro;
				document.frmEditaLivro.revisaoLivro.value = livro.revLivro;
				
				
				var anoCompleto = livro.anoLivro;				
				var anoFormatado = anoCompleto.substr(0,4)

				document.frmEditaLivro.anoLivro.value = anoFormatado;
								
				if(livro.statusLivro==0){
					document.frmEditaLivro.statusLivro.value = 0;
				}else if(livro.statusLivro==1){
					document.frmEditaLivro.statusLivro.value = 1;
				}else{
					document.frmEditaLivro.statusLivro.value = 2;
				}

				var modalEditaLivro = {
					title: "Editar Livro",
					height: 350,
					width: 450,
					modal: true,
					buttons:{
						"Salvar": function(){
							BIBLIOTECA.livro.editar();
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					close: function(){}
				};
				
				$("#modalEditaLivro").dialog(modalEditaLivro);
				
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao buscar o livro para edição: "+info.status+" - "+info.statusText);
			}
		});
	}
	
	BIBLIOTECA.livro.editar = function(){
		var livro = new Object();
		
		livro.nmLivro = document.frmEditaLivro.seLivroEdicao.value;
		livro.catLivro = document.frmEditaLivro.selCategoriaEdicao.value;
		//livro.anoLivro = document.frmEditaLivro.anoLivro.value;
		livro.autLivro = document.frmEditaLivro.autorLivro.value;
		livro.revLivro = document.frmEditaLivro.revisaoLivro.value;
		
		var diaMes = "-01-01";
		var anoCompleto = document.frmEditaLivro.anoLivro.value + diaMes;

		livro.anoLivro = anoCompleto;
				
		console.log(livro.catLivro);
		
		livro.id = document.frmEditaLivro.idLivro.value;
		livro.statusLivro = document.frmEditaLivro.statusLivro.value;
		
		if(livro.nmLivro=="" || anoCompleto=="" || livro.autLivro=="" || livro.revLivro=="" || livro.catLivro==0 || livro.statusLivro=="" || livro.revLivro<0){
			alert("Preencha corretamente as informações do livro")
		}else{
			
			$.ajax({
				
				type: "PUT",
				url: "/ProjetoIndividual/rest/livro/alterar",
				data: JSON.stringify(livro),
				success: function(msg){
					BIBLIOTECA.exibirAviso(msg);
					BIBLIOTECA.livro.buscar();
					$("#modalEditaLivro").dialog("close");
				},
				error: function(info){
					alert("erro");
					BIBLIOTECA.exibirAviso("Erro ao editar o Livro: "+info.status+" - "+info.statusText);
				}
			});
		}

		
	}
	
	function onlynumber(evt) {
		   var theEvent = evt || window.event;
		   var key = theEvent.keyCode || theEvent.which;
		   key = String.fromCharCode( key );
		   //var regex = /^[0-9.,]+$/;
		   var regex = /^[0-9.]+$/;
		   if( !regex.test(key) ) {
		      theEvent.returnValue = false;
		      if(theEvent.preventDefault) theEvent.preventDefault();
		   }
		}

	
	BIBLIOTECA.livro.carregarProdutos();
	BIBLIOTECA.livro.buscar();
	
});
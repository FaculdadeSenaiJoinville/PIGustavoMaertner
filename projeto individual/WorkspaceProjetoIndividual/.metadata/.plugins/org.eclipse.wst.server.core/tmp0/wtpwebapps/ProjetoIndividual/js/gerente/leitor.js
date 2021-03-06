BIBLIOTECA.leitor = new Object();


$(document).ready(function(){
	
	BIBLIOTECA.leitor.cadastrar = function(){
						
		var leitor = new Object();
		
		
		leitor.nome = document.frmLeitor.nomeLeitor.value;
		leitor.cpf = document.frmLeitor.cpf.value;
		leitor.rg = document.frmLeitor.rg.value;
		leitor.data_nasc = document.frmLeitor.date.value;
		leitor.nomeMae = document.frmLeitor.nomeMae.value;
		leitor.email = document.frmLeitor.email.value;
		leitor.telefone_principal = document.frmLeitor.telefone.value;
		leitor.telefone_sec = document.frmLeitor.telefoneSec.value;
		leitor.cidade = document.frmLeitor.cidade.value;
		leitor.bairro = document.frmLeitor.bairro.value;
		leitor.rua = document.frmLeitor.rua.value;
		leitor.numero = document.frmLeitor.numero.value;
		leitor.cep = document.frmLeitor.cep.value;
		
		console.log(leitor);
		
		if(leitor.nome=="" || leitor.cpf=="" || leitor.rg=="" || leitor.data_nasc=="" || leitor.telefone_principal==0 || leitor.telefone_sec=="" || leitor.email=="" || leitor.cidade=="" || leitor.bairro=="" || leitor.rua=="" || leitor.numero=="" || leitor.cep=="" || leitor.login=="" || leitor.senha==""){
			alert("Preencha corretamente as informações do Funcionário")
		}else{			
			$.ajax({
				type: "POST",
				url: "/ProjetoIndividual/rest/leitor/inserir",
				data: JSON.stringify(leitor),
				success: function(msg) {
					BIBLIOTECA.exibirAviso(msg);
					BIBLIOTECA.leitor.buscar();
					$("#frmLeitor").trigger("reset");
				},
				error: function (info){
					alert("Erro não identificado");
				}	
			});
		}
		BIBLIOTECA.leitor.buscar();
	};

		
	BIBLIOTECA.leitor.buscar = function(){
		var valorBusca = $("#inputBuscaFunc").val();
		
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/leitor/buscar",
			data: "valorBusca="+valorBusca,
			success: function(dados){
				dados = JSON.parse(dados);
				$("#listaLeitor").html(BIBLIOTECA.leitor.exibir(dados));
		
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao consultar os leitores: "+info.status+" - "+info.statusText);
			}
		
		});
	};
	
	BIBLIOTECA.leitor.exibir = function(listLeitor){
		
		var tabela = "<table>" +
		"<tr>" +
		"<th>Nome</th>" +
		"<th>Data de Nascimento</th>" +
		"<th>Id</th>" +
		"<th>Ações</th>" +
		"</tr>";
		
		if(listLeitor != undefined && listLeitor.length > 0){
			
			for (var i=0; i<listLeitor.length; i++){
				tabela += "<tr>" +
						"<td>"+listLeitor[i].nome+"</td>" +
						"<td>"+listLeitor[i].data_nasc+"</td>" +
						"<td>"+listLeitor[i].id+"</td>" +
							"<td>" +
								"<a onclick=\"BIBLIOTECA.leitor.exibirEdicao('"+listLeitor[i].id+"')\"><img src='../../../imgs/edit.png'></a>" +
								"<a onclick=\"BIBLIOTECA.leitor.confirmarPergunta('"+listLeitor[i].id+"')\"><img src='../../../imgs/delete.png'></a>" +
							"</td>" +
						"</tr>"
			}
			
		} else if(listLeitor == ""){
			tabela += "<tr><td colspan='8'>Nenhum registro encontrado</td></tr>"
		}
		tabela += "</table>";
		
		return tabela;
	};
	
	BIBLIOTECA.leitor.excluir = function(id){
		$.ajax({
			type: "DELETE",
			url: "/ProjetoIndividual/rest/leitor/excluir/"+id,
			success: function(msg){
				BIBLIOTECA.exibirAviso(msg);
				BIBLIOTECA.leitor.buscar();
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao excluir leitor: "+info.status+" - "+info.statusText);
			}
		});
	};
	
	BIBLIOTECA.leitor.confirmarPergunta = function(id){
		var pergunta = "Deseja mesmo excluir esse leitor?";
		var resposta = BIBLIOTECA.leitor.exibirPergunta(pergunta, id);
	};	
	
	
	BIBLIOTECA.leitor.exibirPergunta = function(pergunta, id){
		var modalPergunta = {
				title: "Confirma a Ação?",
				height: 250,
				width: 400,
				modal: true,
				buttons: {
					"Confirmar": function(){
						BIBLIOTECA.leitor.excluir(id);
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
	
	BIBLIOTECA.leitor.exibirEdicao = function(id){
		
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/leitor/buscarPorId/",
			data: "id="+id,
			success: function(leitor){
				console.log(leitor);
				
				document.frmEditaLeitor.idFunc.value = leitor.id;
				document.frmEditaLeitor.nomeEdicao.value = leitor.nome;
				document.frmEditaLeitor.cpfFunc.value = leitor.cpf;
				document.frmEditaLeitor.rgFunc.value = leitor.rg;
				document.frmEditaLeitor.nascFunc.value = leitor.data_nasc;
				document.frmEditaLeitor.fone1Func.value = leitor.telefone_principal;
				document.frmEditaLeitor.fone2Func.value = leitor.telefone_sec;
				document.frmEditaLeitor.email.value = leitor.email;
				document.frmEditaLeitor.cidadeFunc.value = leitor.cidade;
				document.frmEditaLeitor.bairroFunc.value = leitor.bairro;
				document.frmEditaLeitor.ruaFunc.value = leitor.rua;
				document.frmEditaLeitor.NumeroFunc.value = leitor.numero;
				document.frmEditaLeitor.cepFunc.value = leitor.cep;
				document.frmEditaLeitor.maeFunc.value = leitor.nomeMae;
				document.frmEditaLeitor.idEndereco.value = leitor.id_endereco;

								
				var modalEditaLeitor = {
					title: "Editar Leitor",
					height: 600,
					width: 450,
					modal: true,
					buttons:{
						"Salvar": function(){
							BIBLIOTECA.leitor.editar();
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					close: function(){}
				};
				
				$("#modalEditaLeitor").dialog(modalEditaLeitor);
				
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao buscar o leitor para edição: "+info.status+" - "+info.statusText);
			}
		});
	}
	
	BIBLIOTECA.leitor.editar = function(){
		var leitor = new Object();
		
		leitor.id = document.frmEditaLeitor.idFunc.value;
		leitor.nome = document.frmEditaLeitor.nomeEdicao.value;
		leitor.cpf = document.frmEditaLeitor.cpfFunc.value;
		leitor.rg = document.frmEditaLeitor.rgFunc.value;
		leitor.data_nasc = document.frmEditaLeitor.nascFunc.value;
		leitor.telefone_principal = document.frmEditaLeitor.fone1Func.value;
		leitor.telefone_sec = document.frmEditaLeitor.fone2Func.value;
		leitor.email = document.frmEditaLeitor.email.value;
		leitor.cidade = document.frmEditaLeitor.cidadeFunc.value;
		leitor.bairro = document.frmEditaLeitor.bairroFunc.value;
		leitor.rua = document.frmEditaLeitor.ruaFunc.value;
		leitor.numero = document.frmEditaLeitor.NumeroFunc.value;
		leitor.cep = document.frmEditaLeitor.cepFunc.value;
		leitor.nomeMae = document.frmEditaLeitor.maeFunc.value;
		leitor.id_endereco = document.frmEditaLeitor.idEndereco.value;



		
		if(leitor.nome=="" || leitor.cpf=="" || leitor.rg=="" || leitor.data_nasc=="" || leitor.telefone_principal==0 || leitor.telefone_sec=="" || leitor.email=="" || leitor.cidade=="" || leitor.bairro=="" || leitor.rua=="" || leitor.numero=="" || leitor.cep=="" || leitor.login=="" || leitor.senha==""){
			alert("Preencha corretamente as informações do Leitor")
		}else{
			
			$.ajax({
				
				type: "PUT",
				url: "/ProjetoIndividual/rest/leitor/alterar",
				data: JSON.stringify(leitor),
				success: function(msg){
					console.log("salvee");
					BIBLIOTECA.exibirAviso(msg);
					BIBLIOTECA.leitor.buscar();
					$("#modalEditaLeitor").dialog("close");
				},
				error: function(info){
					alert("erro");
					BIBLIOTECA.exibirAviso("Erro ao editar o leitor: "+info.status+" - "+info.statusText);
				}
			});
		}

		
	}
	
	BIBLIOTECA.leitor.validacaoEmail = function(field) {
		usuario = field.value.substring(0, field.value.indexOf("@"));
		dominio = field.value.substring(field.value.indexOf("@")+ 1, field.value.length);

		if ((usuario.length >=1) &&
		    (dominio.length >=3) &&
		    (usuario.search("@")==-1) &&
		    (dominio.search("@")==-1) &&
		    (usuario.search(" ")==-1) &&
		    (dominio.search(" ")==-1) &&
		    (dominio.search(".")!=-1) &&
		    (dominio.indexOf(".") >=1)&&
		    (dominio.lastIndexOf(".") < dominio.length - 1)) {
		}
		else{
			alert("E-mail invalido");
			document.getElementById('email').value=''; 
		}
		}
	
	BIBLIOTECA.leitor.buscar();
	
});
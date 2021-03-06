BIBLIOTECA.funcionario = new Object();


$(document).ready(function(){
	
	BIBLIOTECA.funcionario.cadastrar = function(){
		var funcionario = new Object();
		funcionario.nome = document.frmFuncionario.nomeFunc.value;
		funcionario.cpf = document.frmFuncionario.cpf.value;
		funcionario.rg = document.frmFuncionario.rg.value;
		funcionario.data_nasc = document.frmFuncionario.date.value;
		funcionario.telefone_principal = document.frmFuncionario.telefone.value;
		funcionario.telefone_sec = document.frmFuncionario.telefoneSec.value;
		funcionario.email = document.frmFuncionario.email.value;
		funcionario.cidade = document.frmFuncionario.cidade.value;
		funcionario.bairro = document.frmFuncionario.bairro.value;
		funcionario.rua = document.frmFuncionario.rua.value;
		funcionario.numero = document.frmFuncionario.numero.value;
		funcionario.cep = document.frmFuncionario.cep.value;
		funcionario.login = document.frmFuncionario.login.value;
		funcionario.senha = document.frmFuncionario.senha.value;
		funcionario.cargo = document.frmFuncionario.cargoId.value;

		
		console.log(funcionario);
		
		if(funcionario.nome=="" || funcionario.cpf=="" || funcionario.rg=="" || funcionario.data_nasc=="" || funcionario.telefone_principal==0 || funcionario.telefone_sec=="" || funcionario.email=="" || funcionario.cidade=="" || funcionario.bairro=="" || funcionario.rua=="" || funcionario.numero=="" || funcionario.cep=="" || funcionario.login=="" || funcionario.senha==""){
			alert("Preencha corretamente as informações do Funcionário")
		}else{			$.ajax({
				type: "POST",
				url: "/ProjetoIndividual/rest/funcionario/inserir",
				data: JSON.stringify(funcionario),
				success: function(msg) {
					BIBLIOTECA.exibirAviso(msg);
					BIBLIOTECA.funcionario.buscar();
					$("#frmFuncionario").trigger("reset");
				},
				error: function (info){
					alert("Erro não identificado");
				}	
			});
		}
		BIBLIOTECA.funcionario.buscar();
	};
	
	BIBLIOTECA.funcionario.buscar = function(){
		var valorBusca = $("#inputBuscaFunc").val();
		
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/funcionario/buscar",
			data: "valorBusca="+valorBusca,
			success: function(dados){
				dados = JSON.parse(dados);
				$("#listaFuncionario").html(BIBLIOTECA.funcionario.exibir(dados));
		
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao consultar os funcionario: "+info.status+" - "+info.statusText);
			}
		
		});
	};
	
	BIBLIOTECA.funcionario.exibir = function(listaFuncionario){
		
		var tabela = "<table>" +
		"<tr>" +
		"<th>Nome</th>" +
		"<th>Data de Nascimento</th>" +
		"<th>Id</th>" +
		"<th>Ações</th>" +
		"</tr>";
		
		if(listaFuncionario != undefined && listaFuncionario.length > 0){
			
			for (var i=0; i<listaFuncionario.length; i++){
				tabela += "<tr>" +
						"<td>"+listaFuncionario[i].nome+"</td>" +
						"<td>"+listaFuncionario[i].data_nasc+"</td>" +
						"<td>"+listaFuncionario[i].id+"</td>" +
							"<td>" +
								"<a onclick=\"BIBLIOTECA.funcionario.exibirEdicao('"+listaFuncionario[i].id+"')\"><img src='../../../imgs/edit.png'></a>" +
								"<a onclick=\"BIBLIOTECA.funcionario.confirmarPergunta('"+listaFuncionario[i].id+"')\"><img src='../../../imgs/delete.png'></a>" +
							"</td>" +
						"</tr>"
			}
			
		} else if(listaFuncionario == ""){
			tabela += "<tr><td colspan='8'>Nenhum registro encontrado</td></tr>"
		}
		tabela += "</table>";
		
		return tabela;
	};
	
	
	
	
	BIBLIOTECA.funcionario.excluir = function(id){
		$.ajax({
			type: "DELETE",
			url: "/ProjetoIndividual/rest/funcionario/excluir/"+id,
			success: function(msg){
				BIBLIOTECA.exibirAviso(msg);
				BIBLIOTECA.funcionario.buscar();
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao excluir funcionario: "+info.status+" - "+info.statusText);
			}
		});
	};
	
	BIBLIOTECA.funcionario.confirmarPergunta = function(id){
		var pergunta = "Deseja mesmo excluir esse funcionario?";
		var resposta = BIBLIOTECA.funcionario.exibirPergunta(pergunta, id);
	};	
	
	BIBLIOTECA.funcionario.exibirPergunta = function(pergunta, id){
		var modalPergunta = {
				title: "Confirma a Ação?",
				height: 250,
				width: 400,
				modal: true,
				buttons: {
					"Confirmar": function(){
						BIBLIOTECA.funcionario.excluir(id);
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
	
	
	BIBLIOTECA.funcionario.exibirEdicao = function(id){
		
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/funcionario/buscarPorId/",
			data: "id="+id,
			success: function(funcionario){
				console.log(funcionario);
				
				document.frmEditaFuncionario.idFunc.value = funcionario.id;
				document.frmEditaFuncionario.nomeEdicao.value = funcionario.nome;
				document.frmEditaFuncionario.cpfFunc.value = funcionario.cpf;
				document.frmEditaFuncionario.rgFunc.value = funcionario.rg;
				document.frmEditaFuncionario.nascFunc.value = funcionario.data_nasc;
				document.frmEditaFuncionario.fone1Func.value = funcionario.telefone_principal;
				document.frmEditaFuncionario.fone2Func.value = funcionario.telefone_sec;
				document.frmEditaFuncionario.emailFunc.value = funcionario.email;
				document.frmEditaFuncionario.cidadeFunc.value = funcionario.cidade;
				document.frmEditaFuncionario.bairroFunc.value = funcionario.bairro;
				document.frmEditaFuncionario.ruaFunc.value = funcionario.rua;
				document.frmEditaFuncionario.NumeroFunc.value = funcionario.numero;
				document.frmEditaFuncionario.cepFunc.value = funcionario.cep;
				document.frmEditaFuncionario.loginFunc.value = funcionario.login;
				document.frmEditaFuncionario.senhaFunc.value = funcionario.senha;
				document.frmEditaFuncionario.cargoFunc.value = funcionario.cargo;
				document.frmEditaFuncionario.idEndereco.value = funcionario.id_endereco;


								
				var modalEditaFuncionario = {
					title: "Editar Funcionário",
					height: 600,
					width: 450,
					modal: true,
					buttons:{
						"Salvar": function(){
							BIBLIOTECA.funcionario.editar();
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					close: function(){}
				};
				
				$("#modalEditaFuncionario").dialog(modalEditaFuncionario);
				
			},
			error: function(info){
				BIBLIOTECA.exibirAviso("Erro ao buscar o funcionario para edição: "+info.status+" - "+info.statusText);
			}
		});
	}
	
	BIBLIOTECA.funcionario.editar = function(){
		var funcionario = new Object();
		
		funcionario.id = document.frmEditaFuncionario.idFunc.value;
		funcionario.nome = document.frmEditaFuncionario.nomeEdicao.value;
		funcionario.cpf = document.frmEditaFuncionario.cpfFunc.value;
		funcionario.rg = document.frmEditaFuncionario.rgFunc.value;
		funcionario.data_nasc = document.frmEditaFuncionario.nascFunc.value;
		funcionario.telefone_principal = document.frmEditaFuncionario.fone1Func.value;
		funcionario.telefone_sec = document.frmEditaFuncionario.fone2Func.value;
		funcionario.email = document.frmEditaFuncionario.emailFunc.value;
		funcionario.cidade = document.frmEditaFuncionario.cidadeFunc.value;
		funcionario.bairro = document.frmEditaFuncionario.bairroFunc.value;
		funcionario.rua = document.frmEditaFuncionario.ruaFunc.value;
		funcionario.numero = document.frmEditaFuncionario.NumeroFunc.value;
		funcionario.cep = document.frmEditaFuncionario.cepFunc.value;
		funcionario.login = document.frmEditaFuncionario.loginFunc.value;
		funcionario.senha = document.frmEditaFuncionario.senhaFunc.value;
		funcionario.cargo = document.frmEditaFuncionario.cargoFunc.value;
		funcionario.id_endereco = document.frmEditaFuncionario.idEndereco.value;



		
		if(funcionario.nome=="" || funcionario.cpf=="" || funcionario.rg=="" || funcionario.data_nasc=="" || funcionario.telefone_principal==0 || funcionario.telefone_sec=="" || funcionario.email=="" || funcionario.cidade=="" || funcionario.bairro=="" || funcionario.rua=="" || funcionario.numero=="" || funcionario.cep=="" || funcionario.login=="" || funcionario.senha==""){
			alert("Preencha corretamente as informações do Funcionário")
		}else{
			
			$.ajax({
				
				type: "PUT",
				url: "/ProjetoIndividual/rest/funcionario/alterar",
				data: JSON.stringify(funcionario),
				success: function(msg){
					BIBLIOTECA.exibirAviso(msg);
					BIBLIOTECA.funcionario.buscar();
					$("#modalEditaFuncionario").dialog("close");
				},
				error: function(info){
					alert("erro");
					BIBLIOTECA.exibirAviso("Erro ao editar o funcionario: "+info.status+" - "+info.statusText);
				}
			});
		}

		
	}
	
	BIBLIOTECA.funcionario.validacaoEmail = function(field) {
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
	document.getElementById('inputEmail').value=''; 
	}
	}
	
	
	BIBLIOTECA.funcionario.buscar();
	
});
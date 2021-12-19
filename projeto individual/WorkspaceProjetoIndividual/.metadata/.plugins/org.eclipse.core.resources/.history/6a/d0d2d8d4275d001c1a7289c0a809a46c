package br.com.biblioteca.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.biblioteca.bd.Conexao;
import br.com.biblioteca.jdbc.JDBCCategoriaDAO;
import br.com.biblioteca.jdbc.JDBCFuncionarioDAO;
import br.com.biblioteca.jdbc.JDBCLivroDAO;
import br.com.biblioteca.modelo.Categoria;
import br.com.biblioteca.modelo.Funcionario;
import br.com.biblioteca.modelo.Livro;

@Path("funcionario")
public class FuncionarioRest extends UtilRest{

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String funcionarioParam) {
		
		try {
			Funcionario funcionario = null;
			
			funcionario = new Gson().fromJson(funcionarioParam, Funcionario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);
			
			int validador = jdbcFuncionario.validaCpf(funcionario);
			
			System.out.println("VALIDADOR REST");
			System.out.println(validador);
			
			
			String msg = "";
			
			if(validador==1) {
				msg = "Funcionario com este CPF já cadastrado!";
				conec.fecharConexao();
				return this.buildResponse(msg);
			}else if(validador!=1) {
				System.out.println("ENTREI CPF NÃO CADASTRADO");

				boolean retorno = jdbcFuncionario.inserirEndereco(funcionario);
				boolean retornoFunc = false;
				if(retorno) {
					int retornoIdEndereco = jdbcFuncionario.buscarEndereco(funcionario);
					retornoFunc = jdbcFuncionario.inserirFuncionario(funcionario, retornoIdEndereco);
				}else {
					msg = "Erro ao cadastrar o funcionario!";
				}
				
				if(retornoFunc) {
					msg = "Funcionario cadastrado com sucesso!";
				} else {
					msg = "Erro ao cadastrar o funcionario!";
				}
			}

			System.out.println(msg);

			conec.fecharConexao();
			return this.buildResponse(msg);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@QueryParam("valorBusca") String nome) {

		try {
			List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);
			listaFuncionario = jdbcFuncionario.buscar(nome);
			conec.fecharConexao();

			String json = new Gson().toJson(listaFuncionario);
			return this.buildResponse(json);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir (@PathParam("id") int id) {
		
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);
			
			boolean retorno = jdbcFuncionario.deletar(id);
			
			String msg="";
			
			if (retorno) {
				msg = "Funcionario excluído com sucesso!";
			}else {
				msg = "Erro ao excluir o Funcionario!";
			}
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {
		try {
			Funcionario funcionario = new Funcionario();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);
			
			funcionario = jdbcFuncionario.buscarPorId(id);
			
			conec.fecharConexao();
			return this.buildResponse(funcionario);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterar")
	@Consumes ("application/*")
	public Response alterar (String FuncionarioParam) {
		try {
			System.out.println(FuncionarioParam);
			Funcionario funcionario = new Gson().fromJson(FuncionarioParam, Funcionario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);
			String msg = "";
			
			int validador = jdbcFuncionario.validaCpf(funcionario);

			if(validador==1) {
				msg = "Funcionario com este CPF já cadastrado!";
				conec.fecharConexao();
				return this.buildResponse(msg);
			}else if(validador!=1) {
					boolean retorno = jdbcFuncionario.alterar(funcionario);
					if(retorno) {
						msg = "Funcionario alterado com sucesso!";
					}else {
						msg = "Erro ao alterar o funcionario!";
					}
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
}

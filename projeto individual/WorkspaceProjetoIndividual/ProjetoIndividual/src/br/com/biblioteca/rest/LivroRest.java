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
import br.com.biblioteca.jdbc.JDBCLivroDAO;
import br.com.biblioteca.modelo.Categoria;
import br.com.biblioteca.modelo.Livro;

@Path("livro")
public class LivroRest extends UtilRest {
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar() {
		try {
			List<Categoria> listaCategoria = new ArrayList<Categoria>();
		
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLivroDAO jdbcLivro = new JDBCLivroDAO(conexao);
			listaCategoria = jdbcLivro.buscar();
			conec.fecharConexao();	
			return this.buildResponse(listaCategoria);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String livroParam) {
		
		try {
			Livro livro = null;
			livro = new Gson().fromJson(livroParam, Livro.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			String msg = "";

			
			JDBCLivroDAO jdbcLivro = new JDBCLivroDAO(conexao);
			
			int validaCategoria = jdbcLivro.validaCategoria(livro);		
			
			if(validaCategoria==2) {
				msg="Categoria inexistente!";
				conec.fecharConexao();
				return this.buildResponse(msg);
			}
			
				boolean retorno = jdbcLivro.inserir(livro);
				
				int retornaId = jdbcLivro.buscarIdLivro(livro);

				if(retorno) {
					msg = "Livro cadastrado com sucesso!"
							+ ""
							+ "O Id do livro é:" + retornaId;
					
				} else {
					msg = "Erro ao cadastrar o Livro!";
				}


			conec.fecharConexao();
			return this.buildResponse(msg);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	
	@GET
	@Path("/buscarPorNome")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String nome) {

		try {
			List<JsonObject> listaLivros = new ArrayList<JsonObject>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLivroDAO jdbcLivro = new JDBCLivroDAO(conexao);
			listaLivros = jdbcLivro.buscarPorNome(nome);
			conec.fecharConexao();

			String json = new Gson().toJson(listaLivros);
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
			JDBCLivroDAO jdbcLivro = new JDBCLivroDAO(conexao);
			
			boolean retorno = jdbcLivro.deletar(id);
			
			String msg="";
			
			if (retorno) {
				msg = "Livro excluído com sucesso!";
			}else {
				msg = "Erro ao excluir o Livro!";
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
			Livro livro = new Livro();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLivroDAO jdbcLivro = new JDBCLivroDAO(conexao);
			
			livro = jdbcLivro.buscarPorId(id);
			
			conec.fecharConexao();
			return this.buildResponse(livro);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterar")
	@Consumes ("application/*")
	public Response alterar (String livroParam) {
		try {
			System.out.println(livroParam);
			Livro livro = new Gson().fromJson(livroParam, Livro.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLivroDAO jdbcLivro = new JDBCLivroDAO(conexao);
			String msg = "";
			
			int validaCategoria = jdbcLivro.validaCategoria(livro);
			
			if(validaCategoria==2) {
				msg="Categoria inexistente!";
				conec.fecharConexao();
				return this.buildResponse(msg);
			}else {
				boolean retorno = jdbcLivro.alterar(livro);
				if(retorno) {
					msg = "Livro alterado com sucesso!";
				}else {
					msg = "Erro ao alterar o Livro!";
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

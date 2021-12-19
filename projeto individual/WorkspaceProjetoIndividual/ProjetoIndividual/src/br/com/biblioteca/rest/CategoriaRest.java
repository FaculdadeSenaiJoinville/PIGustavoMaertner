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
import br.com.biblioteca.modelo.Categoria;

@Path("categoria")
public class CategoriaRest extends UtilRest{

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String categoriaParam) {
		
		try {
			Categoria categoria = null;
			
			categoria = new Gson().fromJson(categoriaParam, Categoria.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			
			int validador = jdbcCategoria.validaNome(categoria);
			
			
			String msg = "";
			if(validador==1) {
				msg="Categoria com nome já existente";
			}else if (validador==0) {
				boolean retorno = jdbcCategoria.inserir(categoria);

				if(retorno) {
					msg = "Categoria cadastrada com sucesso!";
				} else {
					msg = "Erro ao cadastrar a categoria!";
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
	@Path("/buscarPorNome")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String nome) {

		try {
			List<JsonObject> listaCategorias = new ArrayList<JsonObject>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcMarca = new JDBCCategoriaDAO(conexao);
			listaCategorias = jdbcMarca.buscarPorNome(nome);
			conec.fecharConexao();

			String json = new Gson().toJson(listaCategorias);
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
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			
			boolean retorno = jdbcCategoria.deletar(id);
			
			String msg="";
			
			if (retorno) {
				msg = "Categoria excluída com sucesso!";
			}else {
				msg = "Erro ao excluir a categoria!";
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
			Categoria categoria = new Categoria();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			
			categoria = jdbcCategoria.buscarPorId(id);
			
			conec.fecharConexao();
			return this.buildResponse(categoria);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterar")
	@Consumes ("application/*")
	public Response alterar (String categoriaParam) {
		try {
			System.out.println(categoriaParam);
			Categoria categoria = new Gson().fromJson(categoriaParam, Categoria.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			int validador = jdbcCategoria.validaNome(categoria);
			String msg = "";
				
				if(validador==1) {
					msg="Categoria com nome já existente";
				}else if(validador==0) {
					boolean retorno = jdbcCategoria.alterar(categoria);
					if(retorno) {
						msg = "Categoria alterada com sucesso!";
					}else {
						msg = "Erro ao alterar a categoria!";
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

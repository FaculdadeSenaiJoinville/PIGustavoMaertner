package br.com.biblioteca.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;

import br.com.biblioteca.bd.Conexao;
import br.com.biblioteca.jdbc.JDBCLeitorDAO;
import br.com.biblioteca.modelo.Leitor;

@Path("leitor")
public class LeitorRest extends UtilRest {
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String leitorParam) {

		try {
			Leitor leitor = null;

			leitor = new Gson().fromJson(leitorParam, Leitor.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);

			int validador = jdbcLeitor.validaCpf(leitor);

			String msg = "";

			
			if(validador==1) {
				System.out.println("ENTREI CPF JA CADASTRADO");
				msg = "Leitor com este CPF já cadastrado!";
			 	conec.fecharConexao();
			    return this.buildResponse(msg);
			 
			}else if(validador!=1) {
			 System.out.println("ENTREI CPF NÃO CADASTRADO");

			boolean retorno = jdbcLeitor.inserirEndereco(leitor);
			boolean retornoFunc = false;
			if (retorno) {
				int retornoIdEndereco = jdbcLeitor.buscarEndereco(leitor);
				retornoFunc = jdbcLeitor.inserirLeitor(leitor, retornoIdEndereco);
			} else {
				msg = "Erro ao cadastrar o Leitor!";
			}

			if (retornoFunc) {
				msg = "Leitor cadastrado com sucesso!";
			} else {
				msg = "Erro ao cadastrar o Leitor!";
			}
		 }

			System.out.println(msg);

			conec.fecharConexao();
			return this.buildResponse(msg);

		} catch (Exception e) {
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
			List<Leitor> listLeitor = new ArrayList<Leitor>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
			listLeitor = jdbcLeitor.buscar(nome);
			conec.fecharConexao();

			String json = new Gson().toJson(listLeitor);
			return this.buildResponse(json);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id) {

		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);

			boolean retorno = jdbcLeitor.deletar(id);

			String msg = "";

			if (retorno) {
				msg = "Leitor excluído com sucesso!";
			} else {
				msg = "Erro ao excluir o Leitor!";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		} catch (Exception e) {
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
			Leitor leitor = new Leitor();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);

			leitor = jdbcLeitor.buscarPorId(id);

			conec.fecharConexao();
			return this.buildResponse(leitor);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String LeitorParam) {
		try {
			System.out.println(LeitorParam);
			Leitor leitor = new Gson().fromJson(LeitorParam, Leitor.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
			String msg = "";

			boolean retorno = jdbcLeitor.alterar(leitor);
			if (retorno) {
				msg = "Leitor alterado com sucesso!";
			} else {
				msg = "Erro ao alterar o leitor!";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

}

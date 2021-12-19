package br.com.biblioteca.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.biblioteca.modelo.Categoria;
import br.com.biblioteca.modelo.Funcionario;
import br.com.biblioteca.modelo.Livro;
import br.com.biblioteca.jdbcinterface.LivroDAO;


public class JDBCLivroDAO implements LivroDAO{
	
	private Connection conexao;
	
	public JDBCLivroDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public List<Categoria> buscar(){
		
		String comando = "SELECT * FROM categoria";
		
		List<Categoria> listCategoria = new ArrayList<Categoria>();
		
		Categoria categoria = null;
		
		try {
			
			Statement stmt = conexao.createStatement();
			
			ResultSet rs = stmt.executeQuery(comando);
			
			while(rs.next()) {
								
				categoria = new Categoria();
				
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				
				categoria.setId(id);
				categoria.SetCategoria(nome);
				
				listCategoria.add(categoria);
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return listCategoria;
	}
	
	public boolean inserir(Livro livro) {
		String comando = "INSERT INTO livro (id, nome, ano, autor, revisao, status_fisico, categoria_id) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement c;
		
		try {
			
			c = this.conexao.prepareStatement(comando);
			
			c.setInt(1, livro.getId());
			c.setString(2, livro.getNome());
			c.setString(3, livro.getAno());
			c.setString(4, livro.getAutor());
			c.setInt(5, livro.getRevisao());
			c.setInt(6, livro.getStatusFisico());
			c.setInt(7, livro.getCategoriaId());
						
			c.execute();
			
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<JsonObject> buscarPorNome(String nome){
		
		String comando = "SELECT livro.id, livro.nome AS nomeLivro, ano, autor, revisao, status_fisico, categoria_id, categoria.nome AS nomeCategoria FROM livro INNER JOIN categoria ON livro.categoria_id=categoria.id ";
		
		if(!nome.equals("")) {
			comando += "WHERE livro.nome LIKE '%" + nome + "%' ";
		}
		
		comando += "ORDER BY nomeLivro ASC";
				
		List<JsonObject> listaLivro = new ArrayList<JsonObject>();
		JsonObject livro = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String nmLivro = rs.getString("nomeLivro");
				String anoLivro = rs.getString("ano");
				String autLivro = rs.getString("autor");
				int revLivro = rs.getInt("revisao");
				int catLivro = rs.getInt("categoria_id");
				int statusLivro = rs.getInt("status_fisico");
				String nomeCategoria = rs.getString("nomeCategoria");
				
				
				livro = new JsonObject();
				livro.addProperty("id", id);
				livro.addProperty("nome", nmLivro);
				livro.addProperty("ano", anoLivro);
				livro.addProperty("autor", autLivro);
				livro.addProperty("revisao", revLivro);
				livro.addProperty("categoria_id", catLivro);
				livro.addProperty("status_fisico", statusLivro);
				livro.addProperty("nomeCategoria", nomeCategoria);

				
				listaLivro.add(livro);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaLivro;
		
	}
	
	public boolean deletar (int id) {
		String comando = "DELETE FROM livro WHERE id = ?";
		PreparedStatement p;
		try {
			
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;

	}
	
	public Livro buscarPorId(int id) {
		
		String comando = "SELECT * FROM livro WHERE id = ?";
		Livro livro = new Livro();
		
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			
				while(rs.next()){
					
					String nome = rs.getString("nome"); 
					String ano = rs.getString("ano"); 
					String autor = rs.getString("autor"); 
					int revisao = rs.getInt("revisao"); 
					int status_fisico = rs.getInt("status_fisico"); 
					int categoria_id = rs.getInt("categoria_id"); 
					
					livro.setId(id);
					livro.setNome(nome);
					livro.SetAno(ano);
					livro.setAutor(autor);
					livro.setRevisao(revisao);
					livro.setStatusFisico(status_fisico);
					livro.setCategoriaId(categoria_id);
					
				}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return livro;
		
	}
	
	public boolean alterar(Livro livro) {
		String comando = "UPDATE livro "
				+ "SET nome=?, ano=?, autor=?, revisao=?, status_fisico=?, categoria_id=?"
				+ " WHERE id=?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			
			p.setString(1, livro.getNome());
			p.setString(2, livro.getAno());
			p.setString(3, livro.getAutor());
			p.setInt(4, livro.getRevisao());
			p.setInt(5, livro.getStatusFisico());
			p.setInt(6, livro.getCategoriaId());
			p.setInt(7, livro.getId());
			p.executeUpdate();
			
			System.out.println(comando);
			
			System.out.println(livro.getNome());
			System.out.println(livro.getAno());
			System.out.println(livro.getAutor());
			System.out.println(livro.getRevisao());
			System.out.println(livro.getStatusFisico());
			System.out.println(livro.getCategoriaId());
			System.out.println(livro.getId());
			
			
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int buscarIdLivro(Livro livro) {
		
		String nome = livro.getNome();
		int rev = livro.getRevisao();
		String autor = livro.getAutor();
		
		String comando = "SELECT id FROM livro WHERE nome like '"+nome+"' AND revisao = "+rev+" AND autor like '"+autor+"'";
		int idLivro = 0;
				
		try {
			
			System.out.println(comando);
						
			Statement stmt = conexao.createStatement();
			ResultSet rs =stmt.executeQuery(comando);
			while (rs.next()) {
				idLivro = rs.getInt("id");
			}
						
		}catch (SQLException e){
			e.printStackTrace();
			return idLivro;
		}
		return idLivro;
	}
	
	public int validaCategoria (Livro livro) {
				
		String comando = "SELECT * FROM categoria WHERE id="+livro.getCategoriaId();
		int valida=0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs =stmt.executeQuery(comando);
			
			System.out.println("COmando valida:");
			System.out.println(comando);
			
			while(rs.next()) {
				valida =1;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			valida=2;
		}
		System.out.println("VALIDA:");
		System.out.println(valida);
		return valida;
	}
	

}

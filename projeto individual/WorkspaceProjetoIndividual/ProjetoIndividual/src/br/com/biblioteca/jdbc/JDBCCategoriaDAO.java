package br.com.biblioteca.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.biblioteca.jdbcinterface.CategoriaDAO;
import br.com.biblioteca.modelo.Categoria;

public class JDBCCategoriaDAO implements CategoriaDAO {

	private Connection conexao;
	
	public JDBCCategoriaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean inserir(Categoria categoria) {
		String comando = "INSERT INTO categoria (id, nome) VALUES (?,?)";
		PreparedStatement c;
		
		try {
			
			c = this.conexao.prepareStatement(comando);
			
			c.setInt(1, categoria.getId());
			c.setString(2, categoria.getCategoria());
			
			c.execute();
			
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<JsonObject> buscarPorNome(String nome){
		
		String comando = "SELECT * FROM categoria ";
		
		if(!nome.equals("")) {
			comando += "WHERE nome LIKE '%" + nome + "%' ";
		}
		
		comando += "ORDER BY nome ASC";
		
		List<JsonObject> listaCategoria = new ArrayList<JsonObject>();
		JsonObject categoria = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				int id = rs.getInt("id");
				String nomeCat = rs.getString("nome");
				
				
				categoria = new JsonObject();
				categoria.addProperty("id", id);
				categoria.addProperty("nome", nomeCat);
				
				listaCategoria.add(categoria);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCategoria;
		
	}
	
	public boolean deletar (int id) {
		String comando = "DELETE FROM categoria WHERE id = ?";
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
	
	public Categoria buscarPorId(int id) {
		
		String comando = "SELECT * FROM categoria WHERE id = ?";
		Categoria categoria = new Categoria();
		
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			
				while(rs.next()){
					
					String nome = rs.getString("nome"); 
					
					categoria.setId(id);
					categoria.SetCategoria(nome);
					
				}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return categoria;
		
	}
	
	public boolean alterar(Categoria categoria) {
		String comando = "UPDATE categoria "
				+ "SET nome=?"
				+ "WHERE id=?";
		PreparedStatement p;
		System.out.println(categoria.getId());
		
		System.out.println(categoria.getCategoria());

		try {
			p = this.conexao.prepareStatement(comando);
			
			p.setString(1, categoria.getCategoria());
			p.setInt(2, categoria.getId());
			
			p.execute();
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int validaNome (Categoria categoria) {
		String comando ="SELECT * FROM categoria WHERE nome='"+categoria.getCategoria()+"'";
		int validador = 0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs =stmt.executeQuery(comando);
			
			while(rs.next()) {
				validador = 1;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			validador=2;
		}
		return validador;
	}
	
}

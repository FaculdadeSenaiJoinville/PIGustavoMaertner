package br.com.biblioteca.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.biblioteca.jdbcinterface.CategoriaDAO;
import br.com.biblioteca.jdbcinterface.LeitorDAO;
import br.com.biblioteca.modelo.Categoria;
import br.com.biblioteca.modelo.Funcionario;
import br.com.biblioteca.modelo.Leitor;
import br.com.biblioteca.modelo.Livro;

public class JDBCLeitorDAO implements LeitorDAO {

	private Connection conexao;
	
	public JDBCLeitorDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean inserirEndereco(Leitor leitor) {
		
		String comandoEnde = "INSERT INTO endereco (id, cidade, bairro, rua, numero, cep) VALUES (?,?,?,?,?,?)";
		PreparedStatement e;
		
		try {
			
			e = this.conexao.prepareStatement(comandoEnde);
			
			e.setInt(1, leitor.getId_endereco());
			e.setString(2, leitor.getCidade());
			e.setString(3, leitor.getBairro());
			e.setString(4, leitor.getRua());
			e.setInt(5, leitor.getNumero());
			e.setInt(6, leitor.getCep());

						
			e.execute();
			
			System.out.println(leitor.getId_endereco());
			System.out.println(leitor.getCidade());
			System.out.println(leitor.getBairro());
			System.out.println(leitor.getRua());
			System.out.println(leitor.getNumero());
			System.out.println(leitor.getCep());
			
			
		}catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int buscarEndereco(Leitor livro) {
		String comando = "SELECT id FROM endereco WHERE endereco.cep = "+livro.getCep();
		int idEndereco = 0;
				
		try {
						
			Statement stmt = conexao.createStatement();
			ResultSet rs =stmt.executeQuery(comando);
			while (rs.next()) {
				idEndereco = rs.getInt("id");
			}
						
		}catch (SQLException e){
			e.printStackTrace();
			return idEndereco;
		}
		return idEndereco;
	}
	
	public boolean inserirLeitor(Leitor leitor, int retornoIdEndereco) {
		String comandoFunc = "INSERT INTO leitor (id, nome, cpf, rg, telefone_principal, telefone_secundario, data_nasc, nome_mae, email, endereco_id) VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement c;
				
		String idEndereco = new Gson().toJson(retornoIdEndereco);
				
		try {
						
			
			c = this.conexao.prepareStatement(comandoFunc);
			
			c.setInt(1, leitor.getId());
			c.setString(2, leitor.getNome());
			c.setString(3, leitor.getCpf());
			c.setString(4, leitor.getRg());
			c.setString(5, leitor.getTelefone_principal());
			c.setString(6, leitor.getTelefone_sec());
			c.setString(7, leitor.getData_nasc());
			c.setString(8, leitor.getNomeMae());
			c.setString(9, leitor.getEmail());
			c.setString(10, idEndereco);

						
			c.execute();
			
			System.out.println(leitor.getId());
			System.out.println(leitor.getNome());
			System.out.println(leitor.getCpf());
			System.out.println(leitor.getRg());
			System.out.println(leitor.getTelefone_principal());
			System.out.println(leitor.getTelefone_sec());
			System.out.println(leitor.getData_nasc());
			System.out.println(leitor.getEmail());
			System.out.println(leitor.getId_endereco());

			
			
		}catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<Leitor> buscar(String nome){
		
		String comando = "SELECT * FROM leitor ";
		
		if(!nome.equals("")) {
			comando += "WHERE nome LIKE '%" + nome + "%' ";
		}

		
		List<Leitor> listLeitor = new ArrayList<Leitor>();
		
		Leitor leitor = null;
		
		try {
			
			Statement stmt = conexao.createStatement();
			
			ResultSet rs = stmt.executeQuery(comando);
			
			while(rs.next()) {
								
				leitor = new Leitor();
				
				int id = rs.getInt("id");
				String nomeFunc = rs.getString("nome");
				String cpf = rs.getString("cpf");
				String rg = rs.getString("rg");
				String telefone_principal = rs.getString("telefone_principal");
				String telefone_sec = rs.getString("telefone_secundario");
				String data_nasc = rs.getString("data_nasc");
				String email = rs.getString("email");
				int id_endereco = rs.getInt("endereco_id");
				String nomeMae = rs.getString("nome_mae");

				
				leitor.setId(id);
				leitor.setNome(nomeFunc);
				leitor.setCpf(cpf);
				leitor.setRg(rg);
				leitor.setTelefone_principal(telefone_principal);
				leitor.setTelefone_sec(telefone_sec);
				leitor.setData_nasc(data_nasc);
				leitor.setEmail(email);
				leitor.setNomeMae(nomeMae);
				leitor.setId_endereco(id_endereco);

				
				listLeitor.add(leitor);
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return listLeitor;
	}
	
	public boolean deletar (int id) {
		String comando = "DELETE FROM leitor WHERE id = ?";
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
	
	public Leitor buscarPorId(int id) {
		
		String comando = "SELECT * FROM leitor INNER JOIN endereco ON leitor.endereco_id = endereco.id WHERE leitor.id = ?";
		Leitor leitor = new Leitor();
		
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			
				while(rs.next()){
					
					String nome = rs.getString("nome"); 
					String cpf = rs.getString("cpf"); 
					String rg = rs.getString("rg"); 
					String telefone_principal = rs.getString("telefone_principal"); 
					String telefone_sec = rs.getString("telefone_secundario"); 
					String data_nasc = rs.getString("data_nasc"); 
					String email = rs.getString("email"); 
					int id_endereco = rs.getInt("endereco_id"); 
					String cidade = rs.getString("cidade"); 
					String bairro = rs.getString("bairro"); 
					String rua = rs.getString("rua"); 
					int numero = rs.getInt("numero"); 
					int cep = rs.getInt("cep"); 
					String nomeMae = rs.getString("nome_mae");

					
					leitor.setId(id);
					leitor.setNome(nome);
					leitor.setNomeMae(nomeMae);
					leitor.setCpf(cpf);
					leitor.setRg(rg);
					leitor.setTelefone_principal(telefone_principal);
					leitor.setTelefone_sec(telefone_sec);
					leitor.setData_nasc(data_nasc);
					leitor.setEmail(email);
					leitor.setId_endereco(id_endereco);
					leitor.setCidade(cidade);
					leitor.setBairro(bairro);
					leitor.setRua(rua);
					leitor.setNumero(numero);
					leitor.setCep(cep);

				}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return leitor;
		
	}
	
	public boolean alterar(Leitor leitor) {
		String comando = "UPDATE endereco "
				+ "SET cidade=?, bairro=?, rua=?, numero=?, cep=?"
				+ " WHERE id=?";
		PreparedStatement p;
		
		String comando2 = "UPDATE leitor "
				+ "SET nome=?, cpf=?, rg=?, telefone_principal=?, telefone_secundario=?, data_nasc=?, nome_mae=?, email=? "
				+ "WHERE id=?";
		PreparedStatement c;
		try {
			p = this.conexao.prepareStatement(comando);
			
			p.setString(1, leitor.getCidade());
			p.setString(2, leitor.getBairro());
			p.setString(3, leitor.getRua());
			p.setInt(4, leitor.getNumero());
			p.setInt(5, leitor.getCep());
			p.setInt(6, leitor.getId_endereco());
			
			System.out.println(leitor.getId_endereco());



			p.executeUpdate();
			
			c = this.conexao.prepareStatement(comando2);
			
			c.setString(1, leitor.getNome());
			c.setString(2, leitor.getCpf());
			c.setString(3, leitor.getRg());
			c.setString(4, leitor.getTelefone_principal());
			c.setString(5, leitor.getTelefone_sec());
			c.setString(6, leitor.getData_nasc());
			c.setString(7, leitor.getNomeMae());
			c.setString(8, leitor.getEmail());
			c.setInt(9, leitor.getId());

			
			c.executeUpdate();

			
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int validaCpf (Leitor leitor) {
		
		String comando = "SELECT * FROM leitor WHERE cpf="+leitor.getCpf();
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

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
import br.com.biblioteca.jdbcinterface.FuncionarioDAO;
import br.com.biblioteca.modelo.Categoria;
import br.com.biblioteca.modelo.Funcionario;
import br.com.biblioteca.modelo.Livro;

public class JDBCFuncionarioDAO implements FuncionarioDAO {

	private Connection conexao;
	
	public JDBCFuncionarioDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean inserirEndereco(Funcionario funcionario) {
		
		String comandoEnde = "INSERT INTO endereco (id, cidade, bairro, rua, numero, cep) VALUES (?,?,?,?,?,?)";
		PreparedStatement e;
		
		try {
			
			e = this.conexao.prepareStatement(comandoEnde);
			
			e.setInt(1, funcionario.getId_endereco());
			e.setString(2, funcionario.getCidade());
			e.setString(3, funcionario.getBairro());
			e.setString(4, funcionario.getRua());
			e.setInt(5, funcionario.getNumero());
			e.setInt(6, funcionario.getCep());

						
			e.execute();
			
			System.out.println(funcionario.getId_endereco());
			System.out.println(funcionario.getCidade());
			System.out.println(funcionario.getBairro());
			System.out.println(funcionario.getRua());
			System.out.println(funcionario.getNumero());
			System.out.println(funcionario.getCep());
			
			
		}catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int buscarEndereco(Funcionario funcionario) {
		String comando = "SELECT id FROM endereco WHERE endereco.cep = "+funcionario.getCep();
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
	
	public boolean inserirFuncionario(Funcionario funcionario, int retornoIdEndereco) {
		String comandoFunc = "INSERT INTO funcionario (id, login, senha, nome, cpf, rg, telefone_principal, telefone_secundario, data_nasc, email, cargo, endereco_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement c;
				
		String idEndereco = new Gson().toJson(retornoIdEndereco);
				
		try {
						
			
			c = this.conexao.prepareStatement(comandoFunc);
			
			c.setInt(1, funcionario.getId());
			c.setString(2, funcionario.getLogin());
			c.setString(3, funcionario.getSenha());
			c.setString(4, funcionario.getNome());
			c.setString(5, funcionario.getCpf());
			c.setString(6, funcionario.getRg());
			c.setString(7, funcionario.getTelefone_principal());
			c.setString(8, funcionario.getTelefone_sec());
			c.setString(9, funcionario.getData_nasc());
			c.setString(10, funcionario.getEmail());
			c.setInt(11, funcionario.getCargo());
			c.setString(12, idEndereco);

						
			c.execute();
			
			System.out.println(funcionario.getId());
			System.out.println(funcionario.getLogin());
			System.out.println(funcionario.getSenha());
			System.out.println(funcionario.getNome());
			System.out.println(funcionario.getCpf());
			System.out.println(funcionario.getRg());
			System.out.println(funcionario.getTelefone_principal());
			System.out.println(funcionario.getTelefone_sec());
			System.out.println(funcionario.getData_nasc());
			System.out.println(funcionario.getEmail());
			System.out.println(funcionario.getCargo());
			System.out.println(funcionario.getId_endereco());

			
			
		}catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<Funcionario> buscar(String nome){
		
		String comando = "SELECT * FROM funcionario ";
		
		if(!nome.equals("")) {
			comando += "WHERE nome LIKE '%" + nome + "%' ";
		}

		
		List<Funcionario> listFuncionario = new ArrayList<Funcionario>();
		
		Funcionario funcionario = null;
		
		try {
			
			Statement stmt = conexao.createStatement();
			
			ResultSet rs = stmt.executeQuery(comando);
			
			while(rs.next()) {
								
				funcionario = new Funcionario();
				
				int id = rs.getInt("id");
				String login = rs.getString("login");
				String senha = rs.getString("senha");
				String nomeFunc = rs.getString("nome");
				String cpf = rs.getString("cpf");
				String rg = rs.getString("rg");
				String telefone_principal = rs.getString("telefone_principal");
				String telefone_sec = rs.getString("telefone_secundario");
				String data_nasc = rs.getString("data_nasc");
				String email = rs.getString("email");
				int cargo = rs.getInt("cargo");
				int id_endereco = rs.getInt("endereco_id");

				
				funcionario.setId(id);
				funcionario.setLogin(login);;
				funcionario.setSenha(senha);;
				funcionario.setNome(nomeFunc);;
				funcionario.setCpf(cpf);;
				funcionario.setRg(rg);;
				funcionario.setTelefone_principal(telefone_principal);;
				funcionario.setTelefone_sec(telefone_sec);;
				funcionario.setData_nasc(data_nasc);;
				funcionario.setEmail(email);;
				funcionario.setCargo(cargo);;
				funcionario.setId_endereco(id_endereco);

				
				listFuncionario.add(funcionario);
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return listFuncionario;
	}
	
	
	public boolean deletar (int id) {
		String comando = "DELETE FROM funcionario WHERE id = ?";
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
	
	public Funcionario buscarPorId(int id) {
		
		String comando = "SELECT * FROM funcionario INNER JOIN endereco ON funcionario.endereco_id = endereco.id WHERE funcionario.id = ?";
		Funcionario funcionario = new Funcionario();
		
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			
				while(rs.next()){
					
					String login = rs.getString("login"); 
					String senha = rs.getString("senha"); 
					String nome = rs.getString("nome"); 
					String cpf = rs.getString("cpf"); 
					String rg = rs.getString("rg"); 
					String telefone_principal = rs.getString("telefone_principal"); 
					String telefone_sec = rs.getString("telefone_secundario"); 
					String data_nasc = rs.getString("data_nasc"); 
					String email = rs.getString("email"); 
					int cargo = rs.getInt("cargo"); 
					int id_endereco = rs.getInt("endereco_id"); 
					String cidade = rs.getString("cidade"); 
					String bairro = rs.getString("bairro"); 
					String rua = rs.getString("rua"); 
					int numero = rs.getInt("numero"); 
					int cep = rs.getInt("cep"); 


					
					funcionario.setId(id);
					funcionario.setLogin(login);
					funcionario.setSenha(senha);
					funcionario.setNome(nome);
					funcionario.setCpf(cpf);
					funcionario.setRg(rg);
					funcionario.setTelefone_principal(telefone_principal);
					funcionario.setTelefone_sec(telefone_sec);
					funcionario.setData_nasc(data_nasc);
					funcionario.setEmail(email);
					funcionario.setCargo(cargo);
					funcionario.setId_endereco(id_endereco);
					funcionario.setCidade(cidade);
					funcionario.setBairro(bairro);
					funcionario.setRua(rua);
					funcionario.setNumero(numero);
					funcionario.setCep(cep);

				}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return funcionario;
		
	}
	
	public boolean alterar(Funcionario funcionario) {
		String comando = "UPDATE endereco "
				+ "SET cidade=?, bairro=?, rua=?, numero=?, cep=?"
				+ " WHERE id=?";
		PreparedStatement p;
		
		String comando2 = "UPDATE funcionario "
				+ "SET login=?, senha=?, nome=?, cpf=?, rg=?, telefone_principal=?, telefone_secundario=?, data_nasc=?, email=?, cargo=? "
				+ "WHERE id=?";
		PreparedStatement c;
		try {
			p = this.conexao.prepareStatement(comando);
			
			p.setString(1, funcionario.getCidade());
			p.setString(2, funcionario.getBairro());
			p.setString(3, funcionario.getRua());
			p.setInt(4, funcionario.getNumero());
			p.setInt(5, funcionario.getCep());
			p.setInt(6, funcionario.getId_endereco());


			p.executeUpdate();
			
			c = this.conexao.prepareStatement(comando2);
			
			c.setString(1, funcionario.getLogin());
			c.setString(2, funcionario.getSenha());
			c.setString(3, funcionario.getNome());
			c.setString(4, funcionario.getCpf());
			c.setString(5, funcionario.getRg());
			c.setString(6, funcionario.getTelefone_principal());
			c.setString(7, funcionario.getTelefone_sec());
			c.setString(8, funcionario.getData_nasc());
			c.setString(9, funcionario.getEmail());
			c.setInt(10, funcionario.getCargo());
			c.setInt(11, funcionario.getId());

			
			c.executeUpdate();

			
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int validaCpf (Funcionario funcionario) {
		
		String comando = "SELECT * FROM funcionario WHERE cpf="+funcionario.getCpf();
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

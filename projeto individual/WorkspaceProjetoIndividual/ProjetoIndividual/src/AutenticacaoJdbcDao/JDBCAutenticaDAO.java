package AutenticacaoJdbcDao;

import java.sql.Connection;
import java.sql.ResultSet;

import ArmazenaDadosUsuario.ArmaDadosUsuario;

public class JDBCAutenticaDAO {
	
	private Connection conexao;
	
	public JDBCAutenticaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean Consultar(ArmaDadosUsuario dadosautentica) {
		try {

			String comando = "SELECT * FROM funcionario WHERE login = '"+dadosautentica.getloginUsu()+"' AND senha = '"+dadosautentica.getsenhaUse()+"'";
			System.out.println(comando);
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
						
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
			
		}catch (Exception e){
			return false;
		}
	}
	
}

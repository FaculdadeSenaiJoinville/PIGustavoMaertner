package AutenticacaoJdbcDao;

import java.sql.Connection;
import java.sql.ResultSet;

import ArmzanaDadosUsuario.ArmaDadosUsuario;

public class JDBCAutenticaDAO {
	
	private Connection conexao;
	
	public JDBCAutenticaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean Consultar(ArmaDadosUsuario dadosautentica) {
		try {

			String comando = "SELECT * FROM dadosusuario WHERE usuario = '"+dadosautentica.getUsuario()+"' AND senha = '"+dadosautentica.getSenha()+"'";
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

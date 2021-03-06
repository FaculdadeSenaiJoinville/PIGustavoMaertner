package LoginServlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ArmzanaDadosUsuario.ArmaDadosUsuario;
import AutenticacaoJdbcDao.JDBCAutenticaDAO;
import Conexao.Conexao;



public class AutenticacaoServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArmaDadosUsuario dadosautentica = new ArmaDadosUsuario();
		dadosautentica.setUsuario(request.getParameter("usuario"));
		
		String senmd5 = "";
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		BigInteger hash = new BigInteger(1, md.digest(request.getParameter("senha").getBytes()));
		
		senmd5 = hash.toString(16);
		
		dadosautentica.setSenha(senmd5);
		String textodeserializado = new String(Base64.getDecoder().decode(request.getParameter("senha")));
		System.out.println(textodeserializado);

		Conexao conec = new Conexao();
		Connection conexao = (Connection)conec.abrirConexao();		 
		 
		 JDBCAutenticaDAO jdbAutentica = new JDBCAutenticaDAO(conexao);
		 boolean retorno = jdbAutentica.Consultar(dadosautentica);
		 
		 if(retorno) {
			 
			 HttpSession sessao = request.getSession(); 
			 sessao.setAttribute("login", request.getParameter("usuario"));
			 response.sendRedirect("Acesso/principal.html");
			 
		 } else {
			 response.sendRedirect("erro.html"); 
		 }
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

}

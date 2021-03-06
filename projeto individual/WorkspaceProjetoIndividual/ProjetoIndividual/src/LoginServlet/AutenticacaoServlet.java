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

import ArmazenaDadosUsuario.ArmaDadosUsuario;
import AutenticacaoJdbcDao.JDBCAutenticaDAO;
import br.com.biblioteca.bd.Conexao;




public class AutenticacaoServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArmaDadosUsuario dadosautentica = new ArmaDadosUsuario();
		dadosautentica.setloginUsu(request.getParameter("loginUsu"));
		
		String senmd5 = "";
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		BigInteger hash = new BigInteger(1, md.digest(request.getParameter("senhaUse").getBytes()));
		
		senmd5 = hash.toString(32);
		
		dadosautentica.setsenhaUse(senmd5);
		String textodeserializado = new String(Base64.getDecoder().decode(request.getParameter("senhaUse")));
		System.out.println(textodeserializado);

		Conexao conec = new Conexao();
		Connection conexao = (Connection)conec.abrirConexao();		 
		 
		 JDBCAutenticaDAO jdbAutentica = new JDBCAutenticaDAO(conexao);
		 boolean retorno = jdbAutentica.Consultar(dadosautentica);
		 System.out.println(retorno);
		 
		 if(retorno) {
			 
			 HttpSession sessao = request.getSession(); 
			 sessao.setAttribute("login", request.getParameter("loginUsu"));
			 response.sendRedirect("../../ProjetoIndividual/pages/gerente/livro/index.html");
			 
		 } else {
			 response.sendRedirect("index.html"); 
		 }

		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

}

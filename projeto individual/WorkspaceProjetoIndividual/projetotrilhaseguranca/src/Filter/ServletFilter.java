package Filter;

import LoginServlet.AutenticacaoServlet;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletFilter implements Filter{

public void init(FilterConfig filterConfig) throws ServletException {
	
}

public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterChain)throws IOException, ServletException {

	String context = request.getServletContext().getContextPath();

try{

	
HttpSession session = ((HttpServletRequest)request).getSession();

String usuario = null;
// Se existe uma Session
if (session != null){
//Atribua o valor do login de quem se logou a variável usuario
usuario = (String)session.getAttribute("login");
}
//Verificando se usuário é nulo
if (usuario==null) {
	
	System.out.println("Usuario nulo");
//Se for redireciona para a apresentação da mensagem de

((HttpServletResponse)response).sendRedirect("http://localhost:8080/projetotrilhaseguranca/erro.html");

}else{

	filterChain.doFilter(request,response);

}

}catch (Exception e){

e.printStackTrace();
}
}

public void destroy() {
	
}
}//Fechando a classe
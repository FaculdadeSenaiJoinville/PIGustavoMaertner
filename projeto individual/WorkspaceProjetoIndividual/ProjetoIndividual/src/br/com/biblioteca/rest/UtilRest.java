package br.com.biblioteca.rest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.Gson;
public class UtilRest {

	/*
	 * Abaixo o método responsável por enviar a resposta ao cliente sobre a transação
	 * realizada (inclusão, consulta, edição, ou exclusão) caso ela seja realizada com suceeso.
	 * Repare que o método em questão aguarda que seja repassado um conteúdo que será
	 * referenciado por um objeto chamado result.
	 */
	public Response buildErrorResponse(String str) {
		//Abaixo o objeto rb recebe o status do erro.
		
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		
		//DEFINE A ENTEIDADE(OBEJTO), QUE NESSE CASO É UMA MENSAGEM QUE SERÁ RETORNADO PARA O CLIENTE
		
		rb = rb.entity(str);
		
		//define o tipo de retorno desta entidade(objeto), no caso é definido como texto simples
		rb = rb.type("text/plain");
		
		//retorna o objeto de resposta com status 500(erro), junto com a String contendo a mensagem de erro.
		return rb.build();
	}
	
	public Response buildResponse (Object result) {
		try {
			/*
			 * Retorna o objeto de resposta com status 200 (ok), tendo em seu corpo o objeto valorResposta
			 * (que consiste no objeto result convertido para JSON)
			 */
			String valorResposta = new Gson().toJson(result);

			return Response.ok(valorResposta).build();
		}catch (Exception ex) {
			ex.printStackTrace();
			//se algo der errado acima, cria response de erro

			return this.buildErrorResponse(ex.getMessage());

		}
	}
	
	
	
}

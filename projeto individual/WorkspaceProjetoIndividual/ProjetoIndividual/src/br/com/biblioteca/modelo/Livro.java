package br.com.biblioteca.modelo;

import java.io.Serializable;
import java.util.Date;


public class Livro implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public int id;
	private String nmLivro;
	private String anoLivro;
	private String autLivro;
	private int revLivro;
	private int catLivro;
	private int statusLivro;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public String getNome() {
		return nmLivro;
	}
	
	public void setNome(String nmLivro) {
		this.nmLivro=nmLivro;
	}
	
	public String getAno() {
		return anoLivro;
	}
	
	public void SetAno(String anoLivro) {
		this.anoLivro=anoLivro;
	}
	
	public String getAutor() {
		return autLivro;
	}
	
	public void setAutor(String autLivro) {
		this.autLivro=autLivro;
	}
	
	public int getRevisao() {
		return revLivro;
	}
	
	public void setRevisao(int revLivro) {
		this.revLivro=revLivro;
	}
	
	public int getStatusFisico() {
		return statusLivro;
	}
	
	public void setStatusFisico(int statusLivro) {
		this.statusLivro=statusLivro;
	}
	
	public int getCategoriaId(){
		return catLivro;
	}
	
	public void setCategoriaId(int catLivro) {
		this.catLivro=catLivro;
	}
	

}

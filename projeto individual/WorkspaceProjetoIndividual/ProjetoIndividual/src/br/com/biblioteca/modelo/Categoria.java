package br.com.biblioteca.modelo;

import java.io.Serializable;

public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nmCategoria;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
	
	public String getCategoria() {
		return nmCategoria;
	}
	public void SetCategoria(String nmCategoria) {
		this.nmCategoria=nmCategoria;
	}
	
}

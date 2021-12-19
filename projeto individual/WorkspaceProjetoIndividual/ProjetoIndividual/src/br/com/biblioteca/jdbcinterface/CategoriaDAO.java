package br.com.biblioteca.jdbcinterface;

import br.com.biblioteca.modelo.Categoria;

public interface CategoriaDAO {
	
	public boolean inserir(Categoria categoria);
	public boolean deletar(int id);
	public Categoria buscarPorId(int id);
	public boolean alterar(Categoria categoria);
	public int validaNome (Categoria categoria);
}


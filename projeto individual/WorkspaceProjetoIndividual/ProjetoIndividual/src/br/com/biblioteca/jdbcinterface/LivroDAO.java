package br.com.biblioteca.jdbcinterface;

import java.util.List;

import br.com.biblioteca.modelo.Categoria;
import br.com.biblioteca.modelo.Livro;

public interface LivroDAO {
	public List<Categoria> buscar();
	public boolean inserir(Livro livro);
	public boolean deletar(int id);
	public Livro buscarPorId(int id);
	public boolean alterar(Livro livro);
	public int buscarIdLivro(Livro livro);
	public int validaCategoria(Livro livro);

}

package br.com.biblioteca.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.biblioteca.modelo.Categoria;
import br.com.biblioteca.modelo.Funcionario;
import br.com.biblioteca.modelo.Leitor;
import br.com.biblioteca.modelo.Livro;

public interface LeitorDAO {
	
	public boolean inserirEndereco(Leitor leitor);
	public int buscarEndereco(Leitor leitor);
	public boolean inserirLeitor(Leitor leitor, int retornoIdEndereco);
	public List<Leitor> buscar(String nome);
	public boolean deletar(int id);
	public Leitor buscarPorId(int id);
	public boolean alterar(Leitor leitor);
	public int validaCpf(Leitor leitor);

}


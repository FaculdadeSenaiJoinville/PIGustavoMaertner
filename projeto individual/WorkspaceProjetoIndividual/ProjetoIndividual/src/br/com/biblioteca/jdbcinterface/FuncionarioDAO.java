package br.com.biblioteca.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.biblioteca.modelo.Categoria;
import br.com.biblioteca.modelo.Funcionario;
import br.com.biblioteca.modelo.Livro;

public interface FuncionarioDAO {
	
	public boolean inserirEndereco(Funcionario funcionario);
	public int buscarEndereco(Funcionario funcionario);
	public boolean inserirFuncionario(Funcionario funcionario, int retornoIdEndereco);
	public List<Funcionario> buscar(String nome);
	public boolean deletar(int id);
	public Funcionario buscarPorId(int id);
	public boolean alterar(Funcionario funcionario);
	public int validaCpf(Funcionario funcionario);

}


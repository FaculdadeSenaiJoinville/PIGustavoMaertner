package br.com.biblioteca.modelo;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.JsonObject;

public class Funcionario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String login;
	private String senha;
	private String nome;
	private String cpf;
	private String rg;
	private String telefone_principal;
	private String telefone_sec;
	private String data_nasc;
	private String email;
	private int cargo;
	private int id_endereco;
	private String cidade;
	private String bairro;
	private String rua;
	private int numero;
	private int cep;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getTelefone_principal() {
		return telefone_principal;
	}
	public void setTelefone_principal(String telefone_principal) {
		this.telefone_principal = telefone_principal;
	}
	public String getTelefone_sec() {
		return telefone_sec;
	}
	public void setTelefone_sec(String telefone_sec) {
		this.telefone_sec = telefone_sec;
	}
	public String getData_nasc() {
		return data_nasc;
	}
	public void setData_nasc(String data_nasc) {
		this.data_nasc = data_nasc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCargo() {
		return cargo;
	}
	public void setCargo(int cargo) {
		this.cargo = cargo;
	}
	public int getId_endereco() {
		return id_endereco;
	}
	public void setId_endereco(int id_endereco) {
		this.id_endereco = id_endereco;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public int getCep() {
		return cep;
	}
	public void setCep(int cep) {
		this.cep = cep;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
}

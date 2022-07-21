package com.progweb.DiarioEscolar.domain.dtos;

public class UserDTO{
    
    private String nome;
	private String senha;

	public String getNome() {
		return nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setNome(String novoNome) {
		this.nome = novoNome;
	}

	public void setSenha(String novaSenha) {
		this.senha = novaSenha;
	}
}


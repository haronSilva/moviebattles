package com.letscode.moviesbattle.to;

import java.io.Serializable;

public class JogoTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7512291092948354432L;
	
	private Long id;
	private Long jogadorId;
	private int tentativas;
	private boolean concluido;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getJogadorId() {
		return jogadorId;
	}
	public void setJogadorId(Long jogadorId) {
		this.jogadorId = jogadorId;
	}
	public int getTentativas() {
		return tentativas;
	}
	public void setTentativas(int tentativas) {
		this.tentativas = tentativas;
	}
	public boolean isConcluido() {
		return concluido;
	}
	public void setConcluido(boolean concluido) {
		this.concluido = concluido;
	}
	
	


}

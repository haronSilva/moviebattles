package com.letscode.moviesbattle.to;

import java.io.Serializable;

public class RankingTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 615775667004734003L;
	
	private String nomeJogador;
	private int pontuacao;
	public String getNomeJogador() {
		return nomeJogador;
	}
	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}
	public int getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	
}

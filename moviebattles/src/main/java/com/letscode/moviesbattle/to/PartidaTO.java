package com.letscode.moviesbattle.to;

import java.io.Serializable;
import java.util.List;

public class PartidaTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8647637437726566377L;
	private Long partidaId;
	private List<FilmeTO> listaFilmesPartida;
	private FilmeTO filmeEscolhido;
	private Long jogadorId;
	private Long jogoId;
	
	
	public Long getPartidaId() {
		return partidaId;
	}
	public void setPartidaId(Long partidaId) {
		this.partidaId = partidaId;
	}
	
	
	public List<FilmeTO> getListaFilmesPartida() {
		return listaFilmesPartida;
	}
	public void setListaFilmesPartida(List<FilmeTO> listaFilmesPartida) {
		this.listaFilmesPartida = listaFilmesPartida;
	}
	public FilmeTO getFilmeEscolhido() {
		return filmeEscolhido;
	}
	public void setFilmeEscolhido(FilmeTO filmeEscolhido) {
		this.filmeEscolhido = filmeEscolhido;
	}
	public Long getJogadorId() {
		return jogadorId;
	}
	public void setJogadorId(Long jogadorId) {
		this.jogadorId = jogadorId;
	}
	public Long getJogoId() {
		return jogoId;
	}
	public void setJogoId(Long jogoId) {
		this.jogoId = jogoId;
	}
	
	

}

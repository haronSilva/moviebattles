package com.letscode.moviesbattle.to;

import java.io.Serializable;

public class FilmeTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5109047799122512082L;
	private Long id;
	private String imagem;
	
	public FilmeTO() {
		
	}
	
	public FilmeTO(Long id, String imagem) {
		this.id=id;
		this.imagem = imagem;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
}

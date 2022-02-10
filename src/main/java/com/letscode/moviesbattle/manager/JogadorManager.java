package com.letscode.moviesbattle.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letscode.moviesbattle.model.Jogador;
import com.letscode.moviesbattle.repository.JogadorRepository;

@Service
public class JogadorManager {
	@Autowired
	private JogadorRepository jogadorRepository;
	
	public Jogador encontrarJogadorPorId(Long id) {
		return jogadorRepository.getById(id);
	}
}

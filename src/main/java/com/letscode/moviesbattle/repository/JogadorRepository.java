package com.letscode.moviesbattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letscode.moviesbattle.model.Jogador;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
	Jogador findByNome(String nome);
}

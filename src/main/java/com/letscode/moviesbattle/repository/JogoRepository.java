package com.letscode.moviesbattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.letscode.moviesbattle.model.Jogo;
import com.letscode.moviesbattle.model.Partida;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long>{
	@Query("select j from Jogo j where j.jogador.id=:jogadorId and j.concluido=false")
	Jogo encontrarJogoAtual(@Param("jogadorId") Long jogadorId);
}

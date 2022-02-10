package com.letscode.moviesbattle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.letscode.moviesbattle.model.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
	@Query("select p from Partida p where p.jogador.id=:jogadorId and p.concluido=false")
	Partida encontrarPartidaAtual(@Param("jogadorId") Long jogadorId);
	
	@Query("select p from Partida p where p.jogador.id=:jogadorId and p.concluido=true")
	List<Partida> encontrarPartidasConcluidasPorJogadorId(@Param("jogadorId")Long jogadorId);
	
	@Query("select p from Partida p where p.jogador.id=:jogadorId and p.jogo.id=:jogoId and p.concluido=false")
	List<Partida> encontrarPartidasNaoConcluidasPorJogoIdEJogadorId(@Param("jogadorId") Long jogadorId,
			@Param("jogoId") Long jogoId);
}

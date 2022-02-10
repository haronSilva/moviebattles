package com.letscode.moviesbattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.letscode.moviesbattle.model.Ranking;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long>{
	@Query("select r from Ranking r where r.jogador.id = ?1")
	public Ranking findByJogador(Long jogadorId);
}

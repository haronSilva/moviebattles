package com.letscode.moviesbattle.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.letscode.moviesbattle.model.Jogador;
import com.letscode.moviesbattle.model.Partida;
import com.letscode.moviesbattle.model.Ranking;
import com.letscode.moviesbattle.repository.JogadorRepository;
import com.letscode.moviesbattle.repository.PartidaRepository;
import com.letscode.moviesbattle.repository.RankingRepository;
import com.letscode.moviesbattle.to.RankingTO;

@Service
public class RankingManager {
	
	@Autowired
	private RankingRepository rankingRepository;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	public List<RankingTO> listarTodos(){
		List<RankingTO> rankingsTO = new ArrayList<RankingTO>();
		List<Ranking> rankings = rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "pontuacao")); 
		rankings.stream().forEach(ranking -> {
			RankingTO rankingTO = new RankingTO();
			rankingTO.setNomeJogador(ranking.getJogador().getNome());
			rankingTO.setPontuacao(ranking.getPontuacao());
			rankingsTO.add(rankingTO);
		});
		
		return rankingsTO; 
	}
	
	public void atualizarRanking(Long jogadorId) {
		Ranking ranking = rankingRepository.findByJogador(jogadorId);
		List<Partida> partidasJogador = partidaRepository.encontrarPartidasConcluidasPorJogadorId(jogadorId);  
 		
		int pontuacaoTotal = partidasJogador.stream()
 				.map(partida -> partida.getPontuacao())
 				.reduce(0, (a, b) -> a+b);
		if(ranking == null) {
			ranking = new Ranking();
			Jogador jogador = jogadorRepository.findById(jogadorId).get();
			ranking.setJogador(jogador);
		} 
		
		ranking.setPontuacao(pontuacaoTotal);
		
		rankingRepository.save(ranking);
	}
}

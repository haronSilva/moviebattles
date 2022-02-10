package com.letscode.moviesbattle.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import com.letscode.moviesbattle.model.Jogador;
import com.letscode.moviesbattle.model.Partida;
import com.letscode.moviesbattle.model.Ranking;
import com.letscode.moviesbattle.repository.JogadorRepository;
import com.letscode.moviesbattle.repository.PartidaRepository;
import com.letscode.moviesbattle.repository.RankingRepository;
import com.letscode.moviesbattle.to.RankingTO;



public class TesteRankingManager {
	
	@Mock
	private RankingRepository rankingRepository;
	
	@Mock
	private PartidaRepository partidaRepository;
	
	@Mock
	private JogadorRepository jogadorRepository;
	
	@InjectMocks
	private RankingManager rankingManager;
	
	@Captor
	private ArgumentCaptor<Ranking> rankingCaptor;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	
	@Test
	public void testarNaoHaRanking() {
		List<Ranking> rankings = new ArrayList<>();
		Mockito.when(rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "pontuacao"))).thenReturn(rankings);
		
		List<RankingTO> resultado = rankingManager.listarTodos();
		
		assertTrue(resultado.isEmpty());
	}
	
	@Test
	public void testarListarTodos() {
		List<Ranking> rankings = new ArrayList<>();
		Ranking ranking = new Ranking();
		Jogador jogador = new Jogador();
		jogador.setNome("teste");
		ranking.setPontuacao(4);
		ranking.setJogador(jogador);		
		rankings.add(ranking);
				
		Mockito.when(rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "pontuacao"))).thenReturn(rankings);
		
		List<RankingTO> resultado = rankingManager.listarTodos();
		
		assertFalse(resultado.isEmpty());
		assertEquals(ranking.getPontuacao(), resultado.get(0).getPontuacao());
		assertEquals(ranking.getJogador().getNome(), resultado.get(0).getNomeJogador());
		
		
	}
	
	@Test
	public void testarAtualizarRankingExistente() {
		Long jogadorId = 1L;
		Ranking ranking = new Ranking();
		ranking.setId(123L);
		ranking.setPontuacao(1);
		
		Jogador jogador = new Jogador();
		jogador.setId(jogadorId);
		jogador.setNome("teste");
		
		ranking.setJogador(jogador);
		
		Partida partida = new Partida();
		partida.setPontuacao(1);
		List<Partida> partidasJogador = new ArrayList();
		partidasJogador.add(partida);
		
		Mockito.when(rankingRepository.findByJogador(jogadorId)).thenReturn(ranking);
		Mockito.when(partidaRepository.encontrarPartidasConcluidasPorJogadorId(jogadorId)).thenReturn(partidasJogador);
		
		rankingManager.atualizarRanking(jogadorId);
		
		
		Mockito.verify(rankingRepository).save(rankingCaptor.capture());
		
		assertEquals(ranking.getPontuacao(), rankingCaptor.getValue().getPontuacao());
		assertEquals(ranking.getId(), rankingCaptor.getValue().getId());
	}
	
	@Test
	public void testarAtualizarRankingCriado() {
		Long jogadorId = 1L;
		Ranking ranking = new Ranking();
		ranking.setPontuacao(1);
		
		Jogador jogador = new Jogador();
		jogador.setId(jogadorId);
		jogador.setNome("teste");
		
		ranking.setJogador(jogador);
		
		Partida partida = new Partida();
		partida.setPontuacao(1);
		List<Partida> partidasJogador = new ArrayList();
		partidasJogador.add(partida);
		
		Mockito.when(jogadorRepository.findById(jogadorId)).thenReturn(Optional.of(jogador));
		Mockito.when(partidaRepository.encontrarPartidasConcluidasPorJogadorId(jogadorId)).thenReturn(partidasJogador);
		
		rankingManager.atualizarRanking(jogadorId);
		
		
		Mockito.verify(rankingRepository).save(rankingCaptor.capture());
		
		assertEquals(ranking.getPontuacao(), rankingCaptor.getValue().getPontuacao());
		assertNull(rankingCaptor.getValue().getId());
	}
	
	
}

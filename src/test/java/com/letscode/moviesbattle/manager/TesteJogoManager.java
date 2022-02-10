package com.letscode.moviesbattle.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.letscode.moviesbattle.exceptions.JogadorNaoEncontradoException;
import com.letscode.moviesbattle.exceptions.JogoNaoEncontradoException;
import com.letscode.moviesbattle.model.Jogador;
import com.letscode.moviesbattle.model.Jogo;
import com.letscode.moviesbattle.model.Partida;
import com.letscode.moviesbattle.repository.JogadorRepository;
import com.letscode.moviesbattle.repository.JogoRepository;
import com.letscode.moviesbattle.repository.PartidaRepository;
import com.letscode.moviesbattle.to.JogoTO;

public class TesteJogoManager {
	@Mock
	private JogoRepository jogoRepository;
	
	@Mock
	private JogadorRepository jogadorRepository;
	
	@Mock
	private PartidaRepository partidaRepository;
	
	@InjectMocks
	private JogoManager jogoManager;
	
	@Captor
	private ArgumentCaptor<Jogo> jogoCaptor;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testeIniciarJogo() throws JogadorNaoEncontradoException {
		Long jogadorId = 1L;
		Jogador jogador = new Jogador();
		jogador.setNome("teste");
		jogador.setId(jogadorId);
		
		Jogo jogo = new Jogo();
		jogo.setJogador(jogador);
		
		Mockito.when(jogadorRepository.findById(jogadorId)).thenReturn(Optional.of(jogador));
		Mockito.when(jogoRepository.save(Mockito.any(Jogo.class))).thenReturn(jogo);
		
		JogoTO jogoTO = jogoManager.iniciarJogo(jogadorId);
		
		assertEquals(jogadorId, jogoTO.getJogadorId());
	}
	
	@Test
	public void testeIniciarJogoJogadorNaoEncontrado() {
		try {
			jogoManager.iniciarJogo(0L);	
		} catch (JogadorNaoEncontradoException e) {
			assertEquals("Jogador 0 não encontrado", e.getMessage());
		}
		
	}
	
	@Test
	public void testeEncerrarJogo() throws JogoNaoEncontradoException {
		Long jogadorId = 1L;
		Long jogoId = 1L;
		Jogador jogador = new Jogador();
		jogador.setNome("teste");
		jogador.setId(jogadorId);
		
		Jogo jogo = new Jogo();
		jogo.setId(jogoId);
		jogo.setJogador(jogador);
		
		JogoTO jogoTO = new JogoTO();
		jogoTO.setId(jogoId);
		jogoTO.setJogadorId(jogadorId);
		
		List<Partida> partidas = new ArrayList<Partida>();
		Mockito.when(jogoRepository.encontrarJogoAtual(jogadorId)).thenReturn(jogo);
		Mockito.when(partidaRepository.encontrarPartidasNaoConcluidasPorJogoIdEJogadorId(jogadorId, jogoId)).thenReturn(partidas);
		
		jogoManager.encerrarJogo(jogoTO);
		
		Mockito.verify(jogoRepository).save(jogoCaptor.capture());
		
		assertEquals(jogoId, jogoCaptor.getValue().getId());
		
	}
	
	@Test
	public void testeEncerrarJogoNaoEncontrado() throws JogoNaoEncontradoException {
		JogoTO jogo = new JogoTO();
		jogo.setJogadorId(1L);
		try {
			jogoManager.encerrarJogo(jogo);
		}catch (JogoNaoEncontradoException e) {
			assertEquals("Jogador "+jogo.getJogadorId()+" não está registrado em um jogo", e.getMessage());
		}
		
		
		
	}
}

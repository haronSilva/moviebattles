package com.letscode.moviesbattle.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letscode.moviesbattle.exceptions.JogadorEmPartidaException;
import com.letscode.moviesbattle.exceptions.JogoNaoEncontradoException;
import com.letscode.moviesbattle.exceptions.PartidaNaoEncontradaException;
import com.letscode.moviesbattle.exceptions.TentativaNaoPermitaException;
import com.letscode.moviesbattle.model.Filme;
import com.letscode.moviesbattle.model.Jogador;
import com.letscode.moviesbattle.model.Jogo;
import com.letscode.moviesbattle.model.Partida;
import com.letscode.moviesbattle.repository.FilmeRepository;
import com.letscode.moviesbattle.repository.JogadorRepository;
import com.letscode.moviesbattle.repository.JogoRepository;
import com.letscode.moviesbattle.repository.PartidaRepository;
import com.letscode.moviesbattle.to.FilmeTO;
import com.letscode.moviesbattle.to.PartidaTO;

@Service
public class PartidaManager {
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private FilmeRepository filmeRepository;
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private JogoManager jogoManager;
	
	public void concluirPartida(PartidaTO partida) throws PartidaNaoEncontradaException, JogoNaoEncontradoException, TentativaNaoPermitaException{
		Partida entidade = partidaRepository.encontrarPartidaAtual(partida.getJogadorId());
		
		if(entidade == null) {
			String mensagemErro = String.format("Não foi possivel encontrar a partida atual para o jogadorId %s",
					partida.getJogadorId());
			throw new PartidaNaoEncontradaException(mensagemErro); 
		}
		List<Long> filmeIds = partida.getListaFilmesPartida().stream()
				.map(FilmeTO::getId)
				.collect(Collectors.toList());
		List<Filme> opcoesFilmePartida = filmeRepository.listarPorIds(filmeIds);
		Filme filmeEscolhidoPeloJogador = filmeRepository.getById(partida.getFilmeEscolhido().getId());
		int pontuacaoPartida = 0;
		
		entidade.setFilme(filmeEscolhidoPeloJogador);
		Filme opcaoCorretaPartida = opcoesFilmePartida.stream()
				.max(Comparator.comparing((Filme::getNota)))
				.get();
		
		if(opcaoCorretaPartida.getId().equals(filmeEscolhidoPeloJogador.getId())) {
			pontuacaoPartida+=1;
		} else {
			jogoManager.salvarTentativasJogo(partida.getJogadorId());
		}
		
		entidade.setPontuacao(pontuacaoPartida);
		entidade.setConcluido(true);
		partidaRepository.save(entidade);
		
	}
	
	public PartidaTO iniciarPartida(Long jogadorId) throws JogadorEmPartidaException, JogoNaoEncontradoException {
		Jogo jogo = jogoRepository.encontrarJogoAtual(jogadorId);
		
		if(jogo == null) {
			if(jogo == null) {
				String mensagem = String.format("Jogador %s não está registrado em um jogo ", jogadorId);
				throw new JogoNaoEncontradoException(mensagem);
			}
		}
		Partida partidaAtual = partidaRepository.encontrarPartidaAtual(jogadorId);
		
		if(partidaAtual != null) {
			String mensagemErro = String.format("Jogador %s se encontra em uma partida em aberto. PartidaId %s", 
					partidaAtual.getJogador().getNome(), partidaAtual.getId());
			throw new JogadorEmPartidaException(mensagemErro);
		}
		
		PartidaTO partida = new PartidaTO();
		
		Partida entidade = new Partida();
		Jogador jogador = jogadorRepository.getById(jogadorId);		
		entidade.setJogador(jogador);
		entidade.setJogo(jogo);
		entidade.setConcluido(false);
		
		entidade = partidaRepository.save(entidade);
		
		List<FilmeTO> filmesTO = selecionarFilmesPartida();
		
		partida.setJogadorId(jogadorId);
		partida.setListaFilmesPartida(filmesTO);
		partida.setPartidaId(entidade.getId());
		partida.setJogoId(entidade.getJogo().getId());
		
		
		return partida;
	}

	private List<FilmeTO> selecionarFilmesPartida() {
		List<Filme> selecaoFilmes = filmeRepository.findAll();
		Collections.shuffle(selecaoFilmes);
		List<Filme> filmesEscolhidos =  selecaoFilmes.subList(0, 2);
		List<FilmeTO> filmesTO = new ArrayList<FilmeTO>();
		filmesEscolhidos.forEach(filme -> {
			FilmeTO filmeTO = new FilmeTO(filme.getId(), filme.getImagem());
			filmesTO.add(filmeTO);
		});
		return filmesTO;
	}
}

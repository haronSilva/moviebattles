package com.letscode.moviesbattle.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letscode.moviesbattle.exceptions.JogadorNaoEncontradoException;
import com.letscode.moviesbattle.exceptions.JogoNaoEncontradoException;
import com.letscode.moviesbattle.exceptions.TentativaNaoPermitaException;
import com.letscode.moviesbattle.model.Jogador;
import com.letscode.moviesbattle.model.Jogo;
import com.letscode.moviesbattle.model.Partida;
import com.letscode.moviesbattle.repository.JogadorRepository;
import com.letscode.moviesbattle.repository.JogoRepository;
import com.letscode.moviesbattle.repository.PartidaRepository;
import com.letscode.moviesbattle.to.JogoTO;

@Service
public class JogoManager {
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	public JogoTO iniciarJogo(Long jogadorId) throws JogadorNaoEncontradoException {
		JogoTO jogoTO = new JogoTO();
		
		Optional<Jogador> jogadorOpcional = jogadorRepository.findById(jogadorId); 
		
		if(!jogadorOpcional.isPresent()) {
			String mensagem = String.format("Jogador %s não encontrado", jogadorId);
			throw new JogadorNaoEncontradoException(mensagem);
		}
		
		Jogo entidade = new Jogo();
		entidade.setConcluido(false);
		entidade.setJogador(jogadorOpcional.get());
		entidade.setTentativas(0);
		
		entidade = jogoRepository.save(entidade);
		
		jogoTO.setId(entidade.getId());
		jogoTO.setConcluido(entidade.isConcluido());
		jogoTO.setTentativas(entidade.getTentativas());
		jogoTO.setJogadorId(jogadorId);
		
		return jogoTO;
	}
	
	public void salvarTentativasJogo(Long jogadorId)throws JogoNaoEncontradoException, TentativaNaoPermitaException {
		Jogo jogo = jogoRepository.encontrarJogoAtual(jogadorId);
		if(jogo == null) {
			String mensagem = String.format("Jogador %s não está registrado em um jogo ", jogadorId);
			throw new JogoNaoEncontradoException(mensagem);
		}
		
		int tentativas = jogo.getTentativas() + 1;
		
		if(tentativas > 3) {
			String mensagem = String.format("Número máximo de tentativas permitadas para o jogador %s do jogo % alcançado. Por favor, encerre o jogo", jogadorId, jogo.getId());
			throw new TentativaNaoPermitaException(mensagem);
		}
		
		jogo.setTentativas(tentativas);
		
		jogoRepository.save(jogo);
	}
	
	public void encerrarJogo(JogoTO jogoTO) throws JogoNaoEncontradoException{
		Jogo jogo = jogoRepository.encontrarJogoAtual(jogoTO.getJogadorId());
		
		if(jogo == null) {
			String mensagem = String.format("Jogador %s não está registrado em um jogo", jogoTO.getJogadorId());
			throw new JogoNaoEncontradoException(mensagem);
		}
		
		List<Partida> partidasEmAndamento = partidaRepository.encontrarPartidasNaoConcluidasPorJogoIdEJogadorId(jogoTO.getJogadorId(), jogoTO.getId());
		
		if(!partidasEmAndamento.isEmpty()) {
			partidasEmAndamento.stream().forEach(partida -> partida.setConcluido(true));
			partidaRepository.saveAll(partidasEmAndamento);
		}
		
		jogo.setConcluido(true);
		jogoRepository.save(jogo);
	}
}

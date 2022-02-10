package com.letscode.moviesbattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.moviesbattle.manager.JogoManager;
import com.letscode.moviesbattle.to.JogoTO;
import com.letscode.moviesbattle.to.RespostaJogoTO;
import com.letscode.moviesbattle.to.RespostaPartidaTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/jogo")
public class JogoController {
	
	@Autowired
	private JogoManager jogoManager;
	
	@Operation(summary = "Metodo responsável por iniciar um jogo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Jogo iniciada com sucesso", 
				    content = { @Content(mediaType = "application/json", 
				      schema = @Schema(implementation = RespostaPartidaTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Exceção inesperada", 
		    content = { @Content(mediaType = "application/json", 
		      schema = @Schema(implementation = RespostaPartidaTO.class)) })})
	@PostMapping
	public ResponseEntity<RespostaJogoTO> iniciarJogo(@RequestBody JogoTO jogoTO){
		RespostaJogoTO resposta = new RespostaJogoTO();
		try {
			JogoTO jogo = jogoManager.iniciarJogo(jogoTO.getJogadorId()); 
			resposta.setMensagem("Jogo criado com sucesso!");
			resposta.setJogoTO(jogo);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		}catch (Exception e) {
			resposta.setMensagem(e.getMessage());
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Operation(summary = "Metodo responsável por encerrar um jogo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Jogo encerrado com sucesso", 
				    content = { @Content(mediaType = "application/json", 
				      schema = @Schema(implementation = RespostaPartidaTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Exceção inesperada", 
		    content = { @Content(mediaType = "application/json", 
		      schema = @Schema(implementation = RespostaPartidaTO.class)) })})
	@PutMapping
	public ResponseEntity<RespostaJogoTO> encerrarJogo(@RequestBody JogoTO jogoTO){
		RespostaJogoTO resposta = new RespostaJogoTO();
		try {
			jogoManager.encerrarJogo(jogoTO); 
			resposta.setMensagem("Jogo encerrado com sucesso!");
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		}catch (Exception e) {
			resposta.setMensagem(e.getMessage());
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

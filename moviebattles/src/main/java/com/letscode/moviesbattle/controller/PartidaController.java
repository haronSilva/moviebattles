package com.letscode.moviesbattle.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letscode.moviesbattle.manager.PartidaManager;
import com.letscode.moviesbattle.manager.RankingManager;
import com.letscode.moviesbattle.to.PartidaTO;
import com.letscode.moviesbattle.to.RespostaPartidaTO;
import com.letscode.moviesbattle.to.RespostaTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
@RequestMapping("/partidas")
public class PartidaController {
	@Autowired
	private PartidaManager partidaManager;
	
	@Autowired
	private RankingManager rankingManager;
	
	@Operation(summary = "Metodo responsável por iniciar uma partida")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Partida iniciada com sucesso", 
				    content = { @Content(mediaType = "application/json", 
				      schema = @Schema(implementation = RespostaPartidaTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Exceção inesperada", 
		    content = { @Content(mediaType = "application/json", 
		      schema = @Schema(implementation = RespostaPartidaTO.class)) })})
	@PostMapping	
	public ResponseEntity<RespostaPartidaTO> iniciarPartida(@RequestBody PartidaTO partida) {
		RespostaPartidaTO resposta = new RespostaPartidaTO();
		try {
			PartidaTO partidaCriada = partidaManager.iniciarPartida(partida.getJogadorId());
			resposta.setMensagem("Partida iniciada com sucesso!");
			resposta.setPartida(partidaCriada);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		}catch (Exception e) {
			resposta.setMensagem(e.getMessage());
			return new ResponseEntity<>(resposta,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@Operation(summary = "Metodo responsável por finalizar uma partida e atualizar o ranking de jogadores")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Partida finalizada com sucesso", 
				    content = { @Content(mediaType = "application/json", 
				      schema = @Schema(implementation = RespostaTO.class))}),
			@ApiResponse(responseCode = "500", description = "Exceção inesperada", 
		    content = { @Content(mediaType = "application/json", 
		      schema = @Schema(implementation = RespostaTO.class)) })
			})
	@PutMapping
	public ResponseEntity<RespostaTO> finalizarPartida(@RequestBody PartidaTO partida){
		RespostaTO resposta = new RespostaTO();
		try {
			partidaManager.concluirPartida(partida);
			rankingManager.atualizarRanking(partida.getJogadorId());
			resposta.setMensagem("Partida finalizada com sucesso!");
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		}catch (Exception e) {
			resposta.setMensagem(e.getMessage());
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

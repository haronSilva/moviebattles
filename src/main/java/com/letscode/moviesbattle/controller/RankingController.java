package com.letscode.moviesbattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.moviesbattle.manager.RankingManager;
import com.letscode.moviesbattle.model.Ranking;
import com.letscode.moviesbattle.to.RankingTO;
import com.letscode.moviesbattle.to.RespostaPartidaTO;
import com.letscode.moviesbattle.to.RespostaRankingTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/ranking")
public class RankingController {
	@Autowired
	private RankingManager rankingManager;
	
	@Operation(summary = "Metodo responsável por listar o ranking de jogadores")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ranking listado com sucesso", 
				    content = { @Content(mediaType = "application/json", 
				      schema = @Schema(implementation = RespostaRankingTO.class)) }),
			@ApiResponse(responseCode = "204", description = "Ranking não populado", 
		    content = { @Content(mediaType = "application/json", 
		      schema = @Schema(implementation = RespostaRankingTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Exceção inesperada", 
		    content = { @Content(mediaType = "application/json", 
		      schema = @Schema(implementation = RespostaRankingTO.class)) })})
	@GetMapping
	public ResponseEntity<RespostaRankingTO> listarRanking(){
		RespostaRankingTO resposta = new RespostaRankingTO();
		try {
			List<RankingTO> rankings = rankingManager.listarTodos();
			if(rankings.isEmpty()) {
				resposta.setMensagem("Ranking não populado!");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			resposta.setListaRanking(rankings);
			resposta.setMensagem("Ranking listado com sucesso!");
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		}catch (Exception e) {
			resposta.setMensagem(e.getMessage());
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

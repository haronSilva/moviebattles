package com.letscode.moviesbattle.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letscode.moviesbattle.model.Filme;
import com.letscode.moviesbattle.repository.FilmeRepository;

/**
 * 
 * @author Haron
 *
 */

@Service
public class FilmeManager {
	@Autowired
	private FilmeRepository filmeRepository;
	
	public List<Filme> listarTodos(){
		return filmeRepository.findAll();
	}
}

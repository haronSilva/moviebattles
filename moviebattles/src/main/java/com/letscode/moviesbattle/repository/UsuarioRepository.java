package com.letscode.moviesbattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letscode.moviesbattle.model.Usuario;

/**
 * 
 * @author Haron
 *
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
}

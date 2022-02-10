package com.letscode.moviesbattle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.letscode.moviesbattle.model.Filme;

/**
 * 
 * @author Haron
 *
 */
public interface FilmeRepository extends JpaRepository<Filme, Long> {
	
	@Query("select f from Filme f where f.id IN (:ids)")
	List<Filme> listarPorIds(@Param("ids") List<Long> ids);
	

}

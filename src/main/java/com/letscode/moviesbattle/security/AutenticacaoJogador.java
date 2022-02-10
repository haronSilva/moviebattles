package com.letscode.moviesbattle.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.letscode.moviesbattle.model.Jogador;
import com.letscode.moviesbattle.repository.JogadorRepository;

@Component
public class AutenticacaoJogador implements AuthenticationProvider{
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String nome = authentication.getName();
		String senha = authentication.getCredentials().toString();
		
		Jogador jogador = jogadorRepository.findByNome(nome);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(jogador.getPermissao()));
		return new UsernamePasswordAuthenticationToken(nome, senha, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

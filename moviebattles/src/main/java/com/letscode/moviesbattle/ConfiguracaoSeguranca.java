package com.letscode.moviesbattle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.letscode.moviesbattle.security.AutenticacaoJogador;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoJogador autenticacao;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    	auth
        .inMemoryAuthentication()
        .withUser("admin")
        .password("{noop}letscode")
        .roles("JOGADOR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        .formLogin().disable()
        .csrf().disable()
        .httpBasic()
        .and()
        .authorizeRequests().anyRequest().authenticated();
    }

}

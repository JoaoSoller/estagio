package br.com.mgk.Estagio;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityCliente extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSourcefuncionario;

	@Bean
	public BCryptPasswordEncoder passwordEncoderFuncionario() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSourcefuncionario)
				.usersByUsernameQuery(
						"select login as username, senha as password, 1 as enable from cliente where login=?")
				.authoritiesByUsernameQuery(
						"select login as username, 'cliente' as authority from cliente where login=?")
				.passwordEncoder(new BCryptPasswordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/finalizar/**").authorizeRequests().anyRequest().hasAnyAuthority("cliente").and().csrf()
				.disable().formLogin().loginPage("/cliente").permitAll().failureUrl("/cliente")
				.loginProcessingUrl("/finalizar/login").defaultSuccessUrl("/finalizar").usernameParameter("username")
				.passwordParameter("password").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/finalizar/logout")).logoutSuccessUrl("/").permitAll()
				.and().exceptionHandling().accessDeniedPage("/negadoCliente");
	}

}
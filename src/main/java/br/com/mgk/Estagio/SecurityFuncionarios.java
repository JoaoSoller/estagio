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
@Order(2)
public class SecurityFuncionarios extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select login as username, senha as password, 1 as enable from funcionario where login=?")
				.authoritiesByUsernameQuery(
						"select login as username, 'funcionario' as authority from funcionario where login=?")
				.passwordEncoder(new BCryptPasswordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/administrativo/cadastrar/**")
				.hasAnyAuthority("funcionario").antMatchers("/administrativo/**").authenticated().and().formLogin()
				.loginPage("/login").failureUrl("/login").loginProcessingUrl("/admin")
				.defaultSuccessUrl("/administrativo").usernameParameter("username").passwordParameter("password").and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/administrativo/logout"))
				.logoutSuccessUrl("/login").deleteCookies("JSESSIONID").and().exceptionHandling()
				.accessDeniedPage("/negadoFuncionario").and().csrf().disable();

	}

}
package br.com.alura.forum.config.security;

import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// ! Depois disso, por padrão, tudo é bloqueado até que
// ! Até que indique quais url's serão liberadas.

// ? Habilitar o Spring Security, feito na classe e não no main
@EnableWebSecurity
// ? Faz com que a classe seja configurável para o Spring
@Configuration
// ? Configuração de ambientes no Spring, utilizasse o conceitos de
// ? Profile, e esse anotação indica qual ambiente essa classe
// ? será utilizada pelo Spring
@Profile("dev")
public class DevSecurityConfigurations extends WebSecurityConfigurerAdapter {

    // ! Configurações de autorização, urls, perfil de acesso
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .and().csrf().disable();
    }

}

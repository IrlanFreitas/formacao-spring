package br.com.alura.forum.config.security;

import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// ! Depois disso, por padrão, tudo é bloqueado até que
// ! Até que indique quais url's serão liberadas.

// ? Habilitar o Spring Security, feito na classe e não no main
@EnableWebSecurity
// ? Faz com que a classe seja configurável para o Spring
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ! Configurada para ser um @Bean a ser gerenciado pelo Spring
    // ! por padrão não é assim
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // ! Configurações de autenticação, jwt
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());

    }

    // ! Configurações de autorização, urls, perfil de acesso
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // ? Mapeando quais urls serão liberadas para acesso sem autenticação
        // ? e sendo mais especifico ainda, com o método http.

        // ? Fora isso qualquer outra requisição deve ser autenticada.

        // ? formLogin, cria um página de login
        // ? com gerenciamento de sessão.

        // ? sessionCreationPolicy é uma das partes que indica que
        // ? não é para criar sessão, jsession ou algo do tipo
        // ? porque será utilizado token

        // ? .and().addFilterBefore() é pra adicionar o filtro de requisições
        // ? porém é necessário se atentar ao fato de que já há um filtro
        // ? que o Spring está utilizando, então é necessário utilizar
        // ? o addFilterBefore, antes de qualquer coisa ele deve
        // ? verificar se o usuário está autenticado e
        // ? depois continuar a fazer o que estava fazendo

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/topicos").permitAll()
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
                // .and().formLogin();

    }

    // ! Configurações de recursos estáticos, arquivos, js, css, images
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");

    }


    // ! Main necessário somente para gerar o hash da senha 123456
    //    public static void main(String[] args) {
    //        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    //    }
}

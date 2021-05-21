package br.com.alura.forum.config.security;


import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

// ! Herdando de uma classe que faz a interceptação
// ! dos requests e responses, uma vez
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;

    // ! Injetando via construtor já que nos filtros
    // ! não são gerenciados pelo Spring e foi
    // ! dado um new na mão na classe SecurityConfigurations
    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    // ! Fazendo sempre a autenticação do token a cada requisição
    // ! já que agora é stateless, não há mais sessão guardada
    // ! para usuários logados
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        
        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValido(token);

        if(valido) {
            autenticarCliente(token);
        }

        // ! Linha que indica que pode continuar a requisição ou response
        // ! Depois de ter aplicado a lógica necessária
        chain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {

        Long idUsuario = tokenService.getIdUsuario(token);

        Usuario usuario = usuarioRepository.findById(idUsuario).get();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        // ! Classe do Spring para autenticar o usuário.
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
        return null;

        // * Pegar somente o jwt
        return token.substring(7, token.length());
    }
}

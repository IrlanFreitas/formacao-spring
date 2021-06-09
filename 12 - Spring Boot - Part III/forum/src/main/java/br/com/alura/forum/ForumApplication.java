package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
// ! Habilita o suporte para o Spring pegar da requisição
// ! um objeto, Pageable por exemplo e mapear certinho
@EnableSpringDataWebSupport
// ! Necessário habilitar o uso de cache na aplicação
@EnableCaching
// ! Para habilitar o swagger no projeto
@EnableSwagger2

// ! o SpringBootServletInitializer junto com o pom
// ! modificado para o packaging ser o war e o tomcat
// ! desabilitadas no empacotamento
//public class ForumApplication extends SpringBootServletInitializer {
public class ForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }

    // ! Método que é necessário sobrescrever para que o empacotamento
    // ! como war, funcione.
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(ForumApplication.class);
//	}

}

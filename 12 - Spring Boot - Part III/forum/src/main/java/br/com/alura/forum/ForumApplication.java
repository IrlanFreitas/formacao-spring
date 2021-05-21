package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}

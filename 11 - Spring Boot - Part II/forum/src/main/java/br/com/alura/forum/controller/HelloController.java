package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// * Pesquisar exatamente o que é
@Controller

// * Pode ser usado na classe toda ou método
// * Para que o Spring não ache que o retorno é uma página
@ResponseBody
public class HelloController {

    @RequestMapping("/")
    public String hello() {
        return "Funcionando";
    }
}

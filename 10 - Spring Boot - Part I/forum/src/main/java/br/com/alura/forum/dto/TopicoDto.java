package br.com.alura.forum.dto;

import br.com.alura.forum.model.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoDto {

    private final Long id;
    private final String titulo;
    private final String mensagem;
    private final LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public static List<TopicoDto> convert(List<Topico> topicos) {
        return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

}

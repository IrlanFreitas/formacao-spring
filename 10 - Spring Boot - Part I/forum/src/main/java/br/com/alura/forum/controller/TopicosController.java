package br.com.alura.forum.controller;

import br.com.alura.forum.dto.DetalhesTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.AtualizacaoTopicoForm;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.Optional;

//@Controller
//@ResponseBody

// ! Para não precisar mais utilizar o @ResponseBody
@RestController
// ! Fica como prefix de todos os métodos do serviço
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // * Mapear o endpoint
    // * Foi melhorado
    // * @RequestMapping(value = "/topicos", method = RequestMethod.GET)
    // * Mapear o endpoint
    // ! @RequestMapping(method = RequestMethod.GET)
    // ? Modificado para GetMapping
    @GetMapping
    // * Recebendo parâmetro com query
    public List<TopicoDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            return TopicoDto.convert(topicoRepository.findAll());
        } else {
            return TopicoDto.convert(topicoRepository.findByCursoNome(nomeCurso));
        }
    }

    // * @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    // * Obtendo o dado do corpo da requisição

    // * Usando o ResponseEntity para devolver uma resposta
    // * no padrão correto de uma api rest de quando o recurso
    // * foi criado.

    // ? Avisar ao Spring para comitar quando finalizar o método
    // ? Salvar, alterar e excluir devem ter
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {

        //! Esse não é o melhor jeito de fazer validações
        //! o Bean Validation serve pra isso
//        if(form.getTitulo() == null) {
//            throw new RuntimeException("Está faltando o campo titulo");
//        }

        Topico topico = form.convert(cursoRepository);

        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) return ResponseEntity.ok(new DetalhesTopicoDto(topico.get()));

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    // ? Avisar ao Spring para comitar quando finalizar o método
    // ? Salvar, alterar e excluir devem ter
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,
                                               @RequestBody @Valid AtualizacaoTopicoForm form) {

        Optional<Topico> optional = topicoRepository.findById(id);

        if (optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    // ? Avisar ao Spring para comitar quando finalizar o método
    // ? Salvar, alterar e excluir devem ter
    @Transactional
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }

}

package br.com.alura.forum.repository;

import br.com.alura.forum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;

// ! Não precisa ?
//@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // ? Criando uma Derived Query
    List<Topico> findByCursoNome(String nomeCurso);

    // ! Colocar o underline indica que é um atributo
    // ! de um relacionamento como em Curso.nome
    // ! caso exista um atributo cursoNome esse
    // ! artefício resolveria
    // ! List<Topico> findByCurso_Nome(String nomeCurso);

    // ? Usando Query
    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
    List<Topico> carregarPorNomeCruso(@Param("nomeCurso") String nomeCurso);
}

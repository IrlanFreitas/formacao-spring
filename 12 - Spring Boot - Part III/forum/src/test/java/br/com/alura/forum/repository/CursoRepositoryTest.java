package br.com.alura.forum.repository;

import br.com.alura.forum.model.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;


// * Anotação específica para quando se vai testar
// * Repositories, vem com várias soluções prontos
// * Injeção de entityManagers, transactionais, etc...
// * É necessário subir toda a aplicação, o que pode ser muito
// * Custoso.

// ! Nesse teste é considerado o banco de dados de teste em memória
@DataJpaTest

// ! Para configurar um banco de testes diferente de um banco em memória
// ! o NONE indica que não é para que o banco configurado no pom.xml
// ! seja substituído por um banco em memória
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

// * Forçar o ambiente para ser o ativo nessa classe
// * Para que quando essa classe seja executada, seja sempre em test
@ActiveProfiles("test")
class CursoRepositoryTest {

    // ? Essa é a diferença de um teste, utilizando o Spring como apoio
    // ? se fosse um teste puramente, JUnit, não conseguia injetar
    // ? a dependência
    @Autowired
    private CursoRepository repository;

    // ? Preencher a base de dados em teste
    @Autowired
    private TestEntityManager em;

    @Test
    void deveCarregarUmCursoAoBuscarPeloNome() {
        String nome = "HTML 5";

        // * Preenchendo o banco antes de testar
        final Curso html5 = new Curso();
        html5.setNome("HTML 5");
        html5.setCategoria("Programação");
        em.persist(html5);

        final Curso curso = repository.findOneByNome(nome);

        Assertions.assertNotNull(curso);
        Assertions.assertEquals(curso.getNome(), nome);
    }

    @Test
    void naoDeveCarregarUmCursoAoBuscarPeloNome() {
        String nome = "JPA";

        final Curso curso = repository.findOneByNome(nome);

        Assertions.assertNull(curso);
    }
}
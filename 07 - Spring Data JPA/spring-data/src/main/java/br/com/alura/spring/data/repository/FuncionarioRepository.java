package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// ? Para que a paginação seja feita pelo Spring Data
// ? é necessária a mudança de Repository
// ? de CrudRepository para PagingAndSortingRepository

// ! Necessário extender a Specification para
// ! conseguir implementar as queries dinâmicas
@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario> {

    /*
     ! Conceito foda chamado Derived Query
     * Link para saber mais: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
     */
     List<Funcionario> findByNome(String nome);

    // ! Usado o Derived Query, porém ficou extenso
//    public List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome,
//                                                                               Double salario,
//                                                                               LocalDate data);

    // ! JPQL
    // ? O nome da tabela utilizado é baseado no nome da classe
    // ? O campos da tabela aqui utilizados são os nomes dos atributos da classe
    @Query("SELECT f FROM Funcionario f" +
            " WHERE f.nome = :nome" +
            " AND f.salario >= :salario" +
            " AND f.dataContratacao = :data")
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);

    // ! Natived Query
    // ? Usando queries nativas do SQL dentro do
    // ? Spring, é necessário dois paramêtros
    // ? value com a consulta e nativeQuery com true
    @Query(value = " SELECT * FROM funcionaios f " +
            " WHERE f.data_contratacao >= :data",
            nativeQuery = true)
    List<Funcionario> findDataContratacaoMaior(LocalDate data);


    @Query(value = " SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();

    // ! Fazendo JOINS com o Derived Query e o JPQL - @Query

    // ? Encontra o funcionario pela descrição do cargo
    List<Funcionario> findByCargoDescricao(String descricao);

    // ? A mesma coisa que feito acima só que
    // ? com o JPQL
    @Query("SELECT f FROM Funcionario f " +
            " JOIN f.cargo c " +
            " WHERE c.descricao = :descricao")
    List<Funcionario> findByCargoPelaDescricao(String descricao);

    /*
     * No entanto temos o problema que o nome da entidade
     * UnidadeTrabalho é composto de duas palavras.
     * Para separar claramente o nome da entidade do atributo
     * devemos usar o caracter _ (underline).
     */
    List<Funcionario> findByUnidadeTrabalhos_Descricao(String descricao);

    // ? Mesma coisa só que sem se preocupar com o Derived Query
    @Query("SELECT f FROM Funcionario f JOIN " +
            " f.unidadeTrabalhos u " +
            " WHERE u.descricao = :descricao")
    List<Funcionario> findByUnidadeTrabalhosDescricao(String descricao);
}

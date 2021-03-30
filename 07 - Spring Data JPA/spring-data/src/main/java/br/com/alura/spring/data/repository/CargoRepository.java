package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.orm.Cargo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Interface com todos os métodos de acesso ao
// dados implementados e facilitando o desenvolvimento

// É necessário dois paramêtros pro CrudRepository
// Funcionar, a classe que será usada para criar a tabela, Cargo
// E o tipo do id, nesse caso Integer
@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer> {
}

package br.com.alura.spring.data.orm;

// * Antigamente era chamado de DTO
// * na verdade é um recurso novo
// * DTO ainda existe e é utilizado

// ! Interface based Projection
public interface FuncionarioProjecao {

    // * Padrão utilizado para obter os valores dos
    // * campos nos retornos do Spring DATA

    Integer getId();
    String getNome();
    Double getSalario();

}

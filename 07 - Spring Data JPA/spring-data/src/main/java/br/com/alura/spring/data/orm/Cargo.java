package br.com.alura.spring.data.orm;

import javax.persistence.*;
import java.util.List;

// Sinalizamos pro Spring Data que essa classe é
// uma entidade relacionada ao banco de dados
// onde desejamos que seja criada uma entidade
// a partir dessa classe.
@Entity
// Mudar alguns comportamentos da tabela
@Table( name = "cargos")
public class Cargo {

    // Indicar ao spring data que esse é o atributo
    // id, onde tem o controle e identificação
    // dos registros.
    @Id
    // Para criar o id de formal sequencial e
    // automática
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;

    // Necessário pegar a manha nos relacionamentos
    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}

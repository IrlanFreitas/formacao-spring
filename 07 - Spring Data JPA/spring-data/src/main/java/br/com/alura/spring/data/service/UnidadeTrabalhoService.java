package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UnidadeTrabalhoService {

    private Boolean system = true;

    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public UnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println();
            System.out.println("Qual ação de Unidade de Trabalho deseja executar :");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");
            System.out.println();

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar();
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = !system;
                    break;
            }

        }
    }

    private void salvar(Scanner scanner) {
        System.out.println("Digite o nome da unidade");
        String nome = scanner.next();

        System.out.println("Digite o endereco");
        String endereco = scanner.next();

        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
        unidadeTrabalho.setDescricao(nome);
        unidadeTrabalho.setEndereco(endereco);

        unidadeTrabalhoRepository.save(unidadeTrabalho);
        System.out.println("Salvo.");
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Digite o id");
        Integer id = scanner.nextInt();

        System.out.println("Digite o nome da unidade");
        String nome = scanner.next();

        System.out.println("Digite o endereco");
        String endereco = scanner.next();

        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
        unidadeTrabalho.setId(id);
        unidadeTrabalho.setDescricao(nome);
        unidadeTrabalho.setEndereco(endereco);

        unidadeTrabalhoRepository.save(unidadeTrabalho);
        System.out.println("Alterado");

    }

    private void visualizar() {
        Iterable<UnidadeTrabalho> unidadeTrabalhos = unidadeTrabalhoRepository.findAll();

        unidadeTrabalhos.forEach(System.out::println);
    }

    private void deletar(Scanner scanner) {
        System.out.println("Id");

        int id = scanner.nextInt();
        scanner.nextLine();

        unidadeTrabalhoRepository.deleteById(id);
        System.out.println("\nDeletado");

    }
}

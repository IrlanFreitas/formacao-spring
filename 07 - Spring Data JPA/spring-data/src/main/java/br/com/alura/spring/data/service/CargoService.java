package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

// O que significa ser um @Service ?
@Service
public class CargoService {

    private Boolean system = true;

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println();
            System.out.println("Qual ação de cargo deseja executar:");
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
        System.out.println("Descrição do cargo");
        String descricao = scanner.next();
        Cargo cargo = new Cargo();
        cargo.setDescricao(descricao);

        cargoRepository.save(cargo);
        System.out.println("Salvo.");
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Id");

        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Nova descrição");
        String descricao = scanner.next();

        System.out.println(descricao);

        Cargo cargo = new Cargo();
        cargo.setId(id);
        cargo.setDescricao(descricao);

        // Atualiza o registro caso venha com o Id
        cargoRepository.save(cargo);
        System.out.println("Salvo.");

    }

    private void visualizar() {
        Iterable<Cargo> cargos = cargoRepository.findAll();

        cargos.forEach(System.out::println);
    }

    private void deletar(Scanner scanner) {
        System.out.println("Id");

        int id = scanner.nextInt();
        scanner.nextLine();

        cargoRepository.deleteById(id);
        System.out.println("\nDeletado");

    }
}

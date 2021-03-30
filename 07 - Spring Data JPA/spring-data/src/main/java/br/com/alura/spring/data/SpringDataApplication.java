package br.com.alura.spring.data;

import br.com.alura.spring.data.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/*
	A annotation @SpringBootApplication serve para que ao iniciar o framework spring
	ele percorra todas as anotações dentro da aplicação para executa-las.
*/
@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

    private Boolean system = true;

    private final CargoService cargoService;
    private final FuncionarioService funcionarioService;
    private final UnidadeTrabalhoService unidadeTrabalhoService;
    private final RelatoriosService relatoriosService;
    private final RelatorioFuncionarioDinamico relatorioFuncionarioDinamico;

    /*
     * Fazendo a injeção de dependência pelo construtor
     * que o Spring crie assim que o SpringData for iniciado
     * com ele conhece o tipo Repository, ele consegue
     * injetar isso.
     */


    public SpringDataApplication(CargoService cargoService,
                                 FuncionarioService funcionarioService,
                                 UnidadeTrabalhoService unidadeTrabalhoService,
                                 RelatoriosService relatoriosService,
                                 RelatorioFuncionarioDinamico relatorioFuncionarioDinamico) {
        this.cargoService = cargoService;
        this.funcionarioService = funcionarioService;
        this.unidadeTrabalhoService = unidadeTrabalhoService;
        this.relatoriosService = relatoriosService;
        this.relatorioFuncionarioDinamico = relatorioFuncionarioDinamico;
    }

    public static void main(String[] args) {
        // * Para iniciar o Framework Spring
        SpringApplication.run(SpringDataApplication.class, args);
    }

    // ? Método que é invocado logo após o main
    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (system) {
            System.out.println("\nQual ação você quer executar ?");
            System.out.println("0 - Sair");
            System.out.println("1 - Cargo");
            System.out.println("2 - Funcionário");
            System.out.println("3 - Unidade de Trabalho");
            System.out.println("4 - Relatórios");
            System.out.println("5 - Relatório dinâmico");

            int action = scanner.nextInt();
            scanner.nextLine();

            // * TODO tentar melhorar com algum padrão de projeto
            if (action == 1) {
                cargoService.inicial(scanner);
            } else if (action == 2) {
                funcionarioService.inicial(scanner);
            } else if (action == 3) {
                unidadeTrabalhoService.inicial(scanner);
            } else if (action == 4) {
                relatoriosService.inicial(scanner);
            }else if (action == 5) {
                relatorioFuncionarioDinamico.inicial(scanner);
            } else {
                system = !system;
            }
        }
    }

}

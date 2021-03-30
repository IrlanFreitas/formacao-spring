package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private Boolean system = true;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println();
            System.out.println("Qual ação de relatórios deseja executar:");
            System.out.println();
            System.out.println("0 - Sair");
            System.out.println("1 - Busca funcionario por nome ");
            System.out.println("2 - Busca funcionario por nome, data de contratação e salario maior ");
            System.out.println("3 - Busca funcionario por data de contratação ");
            System.out.println("4 - Pesquisa funcionário, id, nome e salário ");

            System.out.println();

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    buscaFuncionarioPorNome(scanner);
                    break;
                case 2:
                    buscaFuncionarioPorNomeDataContratacaoSalarioMaior(scanner);
                    break;
                case 3:
                    buscaFuncionarioDataContratacao(scanner);
                    break;
                case 4:
                    pesquisaFuncionarioSalario();
                    break;
                default:
                    system = !system;
                    break;
            }

        }
    }

    public void buscaFuncionarioPorNome(Scanner scanner) {
        System.out.println("Digite o nome do funcionario:");
        String nome = scanner.next();

        List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);

        funcionarios.forEach(System.out::println);
    }

    public void buscaFuncionarioPorNomeDataContratacaoSalarioMaior(Scanner scanner) {
        System.out.println("Digite o nome do funcionario:");
        String nome = scanner.next();

        System.out.println("Digite a data de contratação:");
        String data = scanner.next();

        System.out.println("Digite o salario:");
        Double salario = scanner.nextDouble();

        LocalDate dataFormatada = LocalDate.parse(data, formatter);

        List<Funcionario> funcionarios = funcionarioRepository
                .findNomeSalarioMaiorDataContratacao(nome, salario, dataFormatada);

        funcionarios.forEach(System.out::println);
    }

    private void buscaFuncionarioDataContratacao(Scanner scanner) {
        System.out.println("Digite a data de contratação:");
        String data = scanner.next();

        LocalDate dataFormatada = LocalDate.parse(data, formatter);

        List<Funcionario> funcionarios = funcionarioRepository
                .findDataContratacaoMaior(dataFormatada);

        funcionarios.forEach(System.out::println);
    }

    private void pesquisaFuncionarioSalario() {

        List<FuncionarioProjecao> funcionarios = funcionarioRepository.findFuncionarioSalario();

        funcionarios.forEach(funcionario -> {
            System.out.println("\nFuncionário: ");
            System.out.println(funcionario.getId());
            System.out.println(funcionario.getNome());
            System.out.println(funcionario.getSalario());
        });

    }
}

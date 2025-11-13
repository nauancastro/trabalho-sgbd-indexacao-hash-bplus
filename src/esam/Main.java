package esam;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("  ESAM - DEMONSTRAÇÃO");
        System.out.println("===========================================\n");

        System.out.print("Digite a capacidade por página (recomendado: 3-6): ");
        int capacidade;
        try {
            capacidade = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Entrada inválida. Usando capacidade padrão: 4");
            capacidade = 4;
        }

        ESAM esam = new ESAM(capacidade);
        System.out.println("\nESAM criada com capacidade " + capacidade + " por página.");

        while (true) {
            System.out.println("\n--- OPERAÇÕES ---");
            System.out.println("1. Inserir");
            System.out.println("2. Buscar");
            System.out.println("3. Remover");
            System.out.println("4. Exibir estrutura");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma operação: ");

            int op;
            try {
                op = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (op) {
                case 1:
                    System.out.print("Digite a chave (inteiro): ");
                    Integer chave;
                    try {
                        chave = Integer.parseInt(scanner.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Informe um inteiro.");
                        break;
                    }
                    System.out.print("Digite o valor (string): ");
                    String valor = scanner.nextLine();
                    boolean ok = esam.inserir(chave, valor);
                    if (ok) {
                        System.out.println("Inserido: (" + chave + ", " + valor + ")");
                    } else {
                        System.out.println("Falha na inserção (verifique capacidade/estado)");
                    }
                    break;

                case 2:
                    System.out.print("Digite a chave a buscar: ");
                    Integer cBusca;
                    try {
                        cBusca = Integer.parseInt(scanner.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Informe um inteiro.");
                        break;
                    }
                    String res = esam.buscar(cBusca);
                    if (res != null) {
                        System.out.println("Encontrado: " + res);
                    } else {
                        System.out.println("Chave não encontrada");
                    }
                    break;

                case 3:
                    System.out.print("Digite a chave a remover: ");
                    Integer cRem;
                    try {
                        cRem = Integer.parseInt(scanner.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Informe um inteiro.");
                        break;
                    }
                    boolean removed = esam.remover(cRem);
                    if (removed) {
                        System.out.println("Chave removida");
                    } else {
                        System.out.println("Chave não encontrada");
                    }
                    break;

                case 4:
                    esam.exibir();
                    break;

                case 0:
                    System.out.println("\nEncerrando...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
package src.bPlusTree;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===========================================");
        System.out.println("  ÁRVORE B+ - DEMONSTRAÇÃO");
        System.out.println("===========================================\n");
        
        // Menu principal
        while (true) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Demonstração Automática");
            System.out.println("2. Modo Interativo");
            System.out.println("3. Teste com Dados Aleatórios");
            System.out.println("4. Executar Suite de Testes");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");
            
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                continue;
            }
            
            switch (choice) {
                case 1:
                    demonstracaoAutomatica();
                    break;
                case 2:
                    modoInterativo(scanner);
                    break;
                case 3:
                    testeAleatorio(scanner);
                    break;
                case 4:
                    BPlusTreeTest.main(new String[]{});
                    break;
                case 0:
                    System.out.println("\nEncerrando o programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void demonstracaoAutomatica() {
        System.out.println("\n========== DEMONSTRAÇÃO AUTOMÁTICA ==========\n");
        
        // Cria árvore B+ com ordem 4
        BPlusTree tree = new BPlusTree(4);
        
        System.out.println("Criando Árvore B+ de ordem 4 (máximo 3 chaves por nó)\n");
        tree.display();
        
        // Sequência de inserções
        int[] keys = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        
        for (int key : keys) {
            System.out.println("\n>>> Inserindo chave: " + key + " com valor: 'Registro_" + key + "'");
            tree.insert(key, "Registro_" + key);
            tree.display();
            pausar();
        }
        
        // Demonstração de busca
        System.out.println("\n>>> TESTANDO BUSCAS <<<");
        for (int i = 10; i <= 100; i += 30) {
            String result = tree.search(i);
            System.out.println("Buscar chave " + i + ": " + result);
        }
        
        // Buscar chave inexistente
        System.out.println("Buscar chave 999 (inexistente): " + tree.search(999));
        
        // Demonstração de remoção
        System.out.println("\n>>> TESTANDO REMOÇÕES <<<");
        int[] removeKeys = {30, 70, 50};
        
        for (int key : removeKeys) {
            System.out.println("\n>>> Removendo chave: " + key);
            boolean removed = tree.delete(key);
            System.out.println("Resultado: " + (removed ? "Removido com sucesso" : "Chave não encontrada"));
            tree.display();
            pausar();
        }
        
        // Verifica se as chaves removidas realmente sumiram
        System.out.println("\n>>> Verificando chaves removidas <<<");
        for (int key : removeKeys) {
            String result = tree.search(key);
            System.out.println("Buscar chave " + key + ": " + (result == null ? "Não encontrada (OK)" : result));
        }
        
        // Exibir estatísticas finais
        tree.displayStats();
    }

    private static void modoInterativo(Scanner scanner) {
        System.out.println("\n========== MODO INTERATIVO ==========\n");
        System.out.print("Digite a ordem da árvore (recomendado: 3-10, mínimo 3): ");
        int ordem;
        try {
            ordem = Integer.parseInt(scanner.nextLine().trim());
            if (ordem < 3) {
                System.out.println("Ordem inválida. Usando ordem padrão: 4");
                ordem = 4;
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Usando ordem padrão: 4");
            ordem = 4;
        }
        
        BPlusTree tree = new BPlusTree(ordem);
        System.out.println("\nÁrvore B+ criada com ordem " + ordem + 
                          " (máximo " + tree.getMaxKeys() + " chaves por nó)");
        
        while (true) {
            System.out.println("\n--- OPERAÇÕES ---");
            System.out.println("1. Inserir");
            System.out.println("2. Buscar");
            System.out.println("3. Remover");
            System.out.println("4. Exibir estrutura");
            System.out.println("5. Exibir chaves em ordem");
            System.out.println("6. Estatísticas");
            System.out.println("0. Voltar ao menu principal");
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
                    Integer key;
                    try {
                        key = Integer.parseInt(scanner.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Informe um inteiro.");
                        break;
                    }
                    System.out.print("Digite o valor (string): ");
                    String value = scanner.nextLine();
                    tree.insert(key, value);
                    System.out.println("Inserido: (" + key + ", " + value + ")");
                    break;
                    
                case 2:
                    System.out.print("Digite a chave a buscar: ");
                    Integer searchKey;
                    try {
                        searchKey = Integer.parseInt(scanner.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Informe um inteiro.");
                        break;
                    }
                    String result = tree.search(searchKey);
                    if (result != null) {
                        System.out.println("Encontrado: " + result);
                    } else {
                        System.out.println("Chave não encontrada");
                    }
                    break;
                    
                case 3:
                    System.out.print("Digite a chave a remover: ");
                    Integer removeKey;
                    try {
                        removeKey = Integer.parseInt(scanner.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Informe um inteiro.");
                        break;
                    }
                    boolean removed = tree.delete(removeKey);
                    if (removed) {
                        System.out.println("Chave removida com sucesso");
                    } else {
                        System.out.println("Chave não encontrada");
                    }
                    break;
                    
                case 4:
                    tree.display();
                    break;
                    
                case 5:
                    tree.displayInOrder();
                    break;
                    
                case 6:
                    tree.displayStats();
                    break;
                    
                case 0:
                    return;
                    
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void testeAleatorio(Scanner scanner) {
        System.out.println("\n========== TESTE COM DADOS ALEATÓRIOS ==========\n");
        System.out.print("Digite a ordem da árvore (recomendado: 5-10): ");
        int ordem;
        try {
            ordem = Integer.parseInt(scanner.nextLine().trim());
            if (ordem < 3) ordem = 5;
        } catch (Exception e) {
            System.out.println("Entrada inválida. Usando ordem padrão: 5");
            ordem = 5;
        }
        
        System.out.print("Digite a quantidade de inserções: ");
        int numInsertions;
        try {
            numInsertions = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Entrada inválida. Usando 50 inserções.");
            numInsertions = 50;
        }
        
        BPlusTree tree = new BPlusTree(ordem);
        Random random = new Random();
        
        System.out.println("\nInserindo " + numInsertions + " valores aleatórios...\n");
        
        // Gera lista de chaves únicas
        List<Integer> keys = new ArrayList<>();
        for (int i = 1; i <= numInsertions; i++) {
            keys.add(i);
        }
        Collections.shuffle(keys, random);
        
        long startTime = System.nanoTime();
        
        for (int i = 0; i < keys.size(); i++) {
            int key = keys.get(i);
            tree.insert(key, "Valor_" + key);
            
            if ((i + 1) % 10 == 0) {
                System.out.println("Inseridos " + (i + 1) + " valores - Altura: " + tree.getHeight());
            }
        }
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("\nInserções concluídas em " + String.format("%.2f", duration) + " ms!");
        tree.display();
        tree.displayStats();
        
        // Teste de buscas
        System.out.println("\n>>> Testando 10 buscas aleatórias <<<");
        Collections.shuffle(keys, random);
        for (int i = 0; i < Math.min(10, keys.size()); i++) {
            int searchKey = keys.get(i);
            String result = tree.search(searchKey);
            System.out.println("Busca [" + searchKey + "]: " + 
                (result != null ? "Encontrado - " + result : "Não encontrado"));
        }
        
        // Teste de remoções
        System.out.print("\nDeseja testar remoções? (s/n): ");
        String response = scanner.nextLine().trim();
        
        if (response.equalsIgnoreCase("s")) {
            System.out.print("Quantas remoções realizar? ");
            int numRemovals;
            try {
                numRemovals = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                numRemovals = 5;
            }
            
            System.out.println("\nRealizando " + numRemovals + " remoções aleatórias...\n");
            Collections.shuffle(keys, random);
            
            for (int i = 0; i < Math.min(numRemovals, keys.size()); i++) {
                int removeKey = keys.get(i);
                boolean removed = tree.delete(removeKey);
                System.out.println("Remover [" + removeKey + "]: " + 
                    (removed ? "Removido" : "Não encontrado"));
            }
            
            System.out.println("\nRemoções concluídas!");
            tree.display();
            tree.displayStats();
        }
        
        // Verificação de integridade
        System.out.println("\n>>> Verificando integridade dos dados <<<");
        int erros = 0;
        for (int i = 1; i <= numInsertions; i++) {
            String val = tree.search(i);
            if (val == null) {
                erros++;
            }
        }
        
        if (erros == 0) {
            System.out.println("✓ Todos os dados estão íntegros!");
        } else {
            System.out.println("✗ Encontrados " + erros + " erros na estrutura");
        }
    }

    private static void pausar() {
        System.out.print("\n[Pressione Enter para continuar...]");
        try {
            System.in.read();
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // ignora exceções
        }
    }
}
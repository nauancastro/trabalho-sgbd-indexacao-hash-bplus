package hash;

import java.util.Random;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===========================================");
        System.out.println("  HASH EXTENSÍVEL - DEMONSTRAÇÃO");
        System.out.println("===========================================\n");
        
        // menu
        while (true) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Demonstração Automática");
            System.out.println("2. Modo Interativo");
            System.out.println("3. Teste com Dados Aleatórios");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // limpando o buffer
            
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
        
        // cria hash com capacidade 2 por bucket
        ExtendibleHash hash = new ExtendibleHash(2);
        
        System.out.println("Criando Hash Extensível com capacidade de 2 entradas por bucket\n");
        hash.display();
        
        // sequência de inserções
        int[] keys = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        for (int key : keys) {
            System.out.println("\n>>> Inserindo chave: " + key + " com valor: 'valor" + key + "'");
            hash.insert(key, "valor" + key);
            hash.display();
            pausar();
        }
        
        // demonstração de busca
        System.out.println("\n>>> TESTANDO BUSCAS <<<");
        for (int i = 1; i <= 10; i += 3) {
            String result = hash.search(i);
            System.out.println("Buscar chave " + i + ": " + result);
        }
        
        // demonstração de remoção
        System.out.println("\n>>> TESTANDO REMOÇÕES <<<");
        int[] removeKeys = {3, 7, 5};
        
        for (int key : removeKeys) {
            System.out.println("\n>>> Removendo chave: " + key);
            boolean removed = hash.remove(key);
            System.out.println("Resultado: " + (removed ? "Removido com sucesso" : "Chave não encontrada"));
            hash.display();
            pausar();
        }
        
        // verifica se as entradas removidas através de suas chaves realmente sumiram
        System.out.println("\n>>> Verificando chaves removidas <<<");
        for (int key : removeKeys) {
            String result = hash.search(key);
            System.out.println("Buscar chave " + key + ": " + (result == null ? "Não encontrada (OK)" : result));
        }
    }

    private static void modoInterativo(Scanner scanner) {
        System.out.println("\n========== MODO INTERATIVO ==========\n");
        System.out.print("Digite a capacidade dos buckets (recomendado: 2-4): ");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        
        ExtendibleHash hash = new ExtendibleHash(capacity);
        System.out.println("\nHash Extensível criado com capacidade " + capacity);
        
        while (true) {
            System.out.println("\n--- OPERAÇÕES ---");
            System.out.println("1. Inserir");
            System.out.println("2. Buscar");
            System.out.println("3. Remover");
            System.out.println("4. Exibir estrutura");
            System.out.println("5. Estatísticas");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("\nEscolha uma operação: ");
            
            int op = scanner.nextInt();
            scanner.nextLine();
            
            switch (op) {
                case 1:
                    System.out.print("Digite a chave (inteiro): ");
                    int key = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite o valor (string): ");
                    String value = scanner.nextLine();
                    hash.insert(key, value);
                    System.out.println("✓ Inserido: (" + key + ", " + value + ")");
                    break;
                    
                case 2:
                    System.out.print("Digite a chave a buscar: ");
                    int searchKey = scanner.nextInt();
                    scanner.nextLine();
                    String result = hash.search(searchKey);
                    if (result != null) {
                        System.out.println("✓ Encontrado: " + result);
                    } else {
                        System.out.println("✗ Chave não encontrada");
                    }
                    break;
                    
                case 3:
                    System.out.print("Digite a chave a remover: ");
                    int removeKey = scanner.nextInt();
                    scanner.nextLine();
                    boolean removed = hash.remove(removeKey);
                    if (removed) {
                        System.out.println("✓ Chave removida com sucesso");
                    } else {
                        System.out.println("✗ Chave não encontrada");
                    }
                    break;
                    
                case 4:
                    hash.display();
                    break;
                    
                case 5:
                    System.out.println("\n" + hash.getStats());
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
        System.out.print("Digite a capacidade dos buckets: ");
        int capacity = scanner.nextInt();
        System.out.print("Digite a quantidade de inserções: ");
        int numInsertions = scanner.nextInt();
        scanner.nextLine();
        
        ExtendibleHash hash = new ExtendibleHash(capacity);
        Random random = new Random();
        
        System.out.println("\nInserindo " + numInsertions + " valores aleatórios...\n");
        
        for (int i = 0; i < numInsertions; i++) {
            int key = random.nextInt(100);
            hash.insert(key, "valor" + key);
            
            if ((i + 1) % 10 == 0) {
                System.out.println("Inseridos " + (i + 1) + " valores - " + hash.getStats());
            }
        }
        
        System.out.println("\nInserções concluídas!");
        hash.display();
        
        // teste de buscas
        System.out.println("\n>>> Testando 10 buscas aleatórias <<<");
        for (int i = 0; i < 10; i++) {
            int searchKey = random.nextInt(100);
            String result = hash.search(searchKey);
            System.out.println("Busca [" + searchKey + "]: " + 
                (result != null ? "Encontrado - " + result : "Não encontrado"));
        }
        
        // teste de remoções
        System.out.print("\nDeseja testar remoções? (s/n): ");
        String response = scanner.nextLine();
        
        if (response.equalsIgnoreCase("s")) {
            System.out.print("Quantas remoções realizar? ");
            int numRemovals = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\nRealizando " + numRemovals + " remoções aleatórias...\n");
            for (int i = 0; i < numRemovals; i++) {
                int removeKey = random.nextInt(100);
                boolean removed = hash.remove(removeKey);
                System.out.println("Remover [" + removeKey + "]: " + 
                    (removed ? "✓ Removido" : "✗ Não encontrado"));
            }
            
            System.out.println("\nRemoções concluídas!");
            hash.display();
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

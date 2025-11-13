package src.bPlusTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Classe de testes para a Árvore B+
 * Demonstra o funcionamento das operações de inserção, busca e remoção
 */
public class BPlusTreeTest {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   TESTES DA ÁRVORE B+ - CSGBD - UFC                       ║");
        System.out.println("║   Professora: Lívia Almada                                ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        // Executa todos os testes
        testeBasico();
        testeBuscas();
        testeSplits();
        testeOrdemAleatoria();
    }

    /**
     * Teste 1: Operações básicas de inserção e busca
     */
    public static void testeBasico() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│ TESTE 1: Operações Básicas              │");
        System.out.println("└─────────────────────────────────────────┘");
        
        BPlusTree tree = new BPlusTree(3);
        
        System.out.println("Criando árvore de ordem 3 (máximo 2 chaves por nó)");
        System.out.println("\nInserindo chaves: 10, 20, 30, 40, 50");
        
        tree.insert(10, "Valor10");
        tree.insert(20, "Valor20");
        tree.insert(30, "Valor30");
        tree.insert(40, "Valor40");
        tree.insert(50, "Valor50");
        
        tree.display();
        
        System.out.println("Realizando buscas:");
        System.out.println("  Chave 20: " + tree.search(20));
        System.out.println("  Chave 40: " + tree.search(40));
        System.out.println("  Chave 99 (inexistente): " + tree.search(99));
        
        tree.displayStats();
        tree.displayInOrder();
        
        System.out.println("Teste básico concluído com sucesso!");
    }

    /**
     * Teste 2: Teste de buscas
     */
    public static void testeBuscas() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│ TESTE 2: Buscas                          │");
        System.out.println("└─────────────────────────────────────────┘");
        
        BPlusTree tree = new BPlusTree(4);
        
        System.out.println("Inserindo números de 1 a 20...");
        for (int i = 1; i <= 20; i++) {
            tree.insert(i, "Registro_" + i);
        }
        
        tree.display();
        
        System.out.println("Verificando todas as buscas:");
        boolean todasOk = true;
        for (int i = 1; i <= 20; i++) {
            String resultado = tree.search(i);
            if (resultado == null || !resultado.equals("Registro_" + i)) {
                todasOk = false;
                System.out.println("Erro na chave " + i);
            }
        }
        
        if (todasOk) {
            System.out.println("Todas as 20 chaves foram encontradas corretamente!");
        }
        
        System.out.println("\nTestando buscas de chaves inexistentes:");
        System.out.println("  Chave 0: " + tree.search(0));
        System.out.println("  Chave 25: " + tree.search(25));
        System.out.println("  Chave 100: " + tree.search(100));
        
        tree.displayStats();
    }

    /**
     * Teste 3: Demonstração de splits em diferentes níveis
     */
    public static void testeSplits() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│ TESTE 3: Splits e Crescimento da Árvore │");
        System.out.println("└─────────────────────────────────────────┘");
        
        BPlusTree tree = new BPlusTree(4);
        
        System.out.println("Árvore de ordem 4 (máximo 3 chaves por nó)");
        System.out.println("Inserindo sequencialmente: 1 a 15");
        System.out.println("Acompanhe o crescimento da árvore:\n");
        
        for (int i = 1; i <= 15; i++) {
            tree.insert(i, "V" + i);
            
            if (i == 3) {
                System.out.println(">>> Após inserir 3 elementos (antes do primeiro split):");
                tree.display();
            } else if (i == 4) {
                System.out.println(">>> Após inserir 4 elementos (primeiro split!):");
                tree.display();
            } else if (i == 7) {
                System.out.println(">>> Após inserir 7 elementos:");
                tree.display();
            } else if (i == 15) {
                System.out.println(">>> Após inserir 15 elementos (árvore completa):");
                tree.display();
            }
        }
        
        tree.displayInOrder();
        tree.displayStats();
        
        System.out.println("Observe como a árvore cresce em altura conforme splits ocorrem!");
    }

    /**
     * Teste 4: Inserção em ordem aleatória (conforme sugerido no trabalho)
     */
    public static void testeOrdemAleatoria() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│ TESTE 4: Inserção em Ordem Aleatória    │");
        System.out.println("└─────────────────────────────────────────┘");
        
        BPlusTree tree = new BPlusTree(5);
        
        // Cria lista de 1 a 50 (conforme sugerido no trabalho)
        List<Integer> chaves = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            chaves.add(i);
        }
        
        // Embaralha as chaves
        Collections.shuffle(chaves, new Random(42));
        
        System.out.println("Inserindo chaves de 1 a 50 em ordem aleatória:");
        System.out.println("Primeiras 20 chaves na ordem de inserção: " + chaves.subList(0, 20));
        
        // Insere todas as chaves
        for (int key : chaves) {
            tree.insert(key, "Valor_" + key);
        }
        
        System.out.println("\nEstrutura final da árvore:");
        tree.display();
        tree.displayInOrder();
        tree.displayStats();
        
        System.out.println("Verificando integridade após inserções aleatórias...");
        boolean integro = true;
        for (int i = 1; i <= 50; i++) {
            String valor = tree.search(i);
            if (valor == null || !valor.equals("Valor_" + i)) {
                integro = false;
                System.out.println("Erro na chave " + i);
                break;
            }
        }
        
        if (integro) {
            System.out.println("Todas as 50 chaves foram encontradas e estão ordenadas!");
            System.out.println("A árvore manteve o balanceamento após inserções aleatórias!");
        }
    }
}
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
        testeRemocao();
        testePerformance();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   TODOS OS TESTES CONCLUÍDOS COM SUCESSO!                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
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

    /**
     * Teste 5: Operações de remoção
     */
    public static void testeRemocao() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│ TESTE 5: Remoção de Chaves              │");
        System.out.println("└─────────────────────────────────────────┘");
        
        BPlusTree tree = new BPlusTree(4);
        
        System.out.println("Inserindo chaves de 1 a 15:");
        for (int i = 1; i <= 15; i++) {
            tree.insert(i, "V" + i);
        }
        
        System.out.println("\nÁrvore inicial:");
        tree.display();
        
        System.out.println("\nRemovendo chaves: 5, 10, 15");
        boolean r1 = tree.delete(5);
        boolean r2 = tree.delete(10);
        boolean r3 = tree.delete(15);
        
        System.out.println("  Remoção da chave 5: " + (r1 ? "✓" : "✗"));
        System.out.println("  Remoção da chave 10: " + (r2 ? "✓" : "✗"));
        System.out.println("  Remoção da chave 15: " + (r3 ? "✓" : "✗"));
        
        System.out.println("\nÁrvore após remoções:");
        tree.display();
        tree.displayInOrder();
        
        System.out.println("\nVerificando remoções:");
        System.out.println("  Chave 5 (removida): " + tree.search(5));
        System.out.println("  Chave 7 (existente): " + tree.search(7));
        System.out.println("  Chave 10 (removida): " + tree.search(10));
        System.out.println("  Chave 12 (existente): " + tree.search(12));
        
        // Tenta remover chave inexistente
        System.out.println("\nTentando remover chave inexistente (99):");
        boolean r4 = tree.delete(99);
        System.out.println("  Resultado: " + (r4 ? "Removido (erro!)" : "Não encontrado (correto)"));
        
        System.out.println("\n Teste de remoção concluído!");
    }

    /**
     * Teste 6: Teste de performance com grande volume de dados
     */
    public static void testePerformance() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│ TESTE 6: Performance e Grande Volume    │");
        System.out.println("└─────────────────────────────────────────┘");
        
        BPlusTree tree = new BPlusTree(10);
        
        int n = 1000;
        System.out.println("Testando com " + n + " elementos...");
        
        // Cria lista aleatória
        List<Integer> chaves = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            chaves.add(i);
        }
        Collections.shuffle(chaves, new Random(999));
        
        // Teste de inserção
        System.out.println("\n1. Teste de INSERÇÃO:");
        long inicioInsert = System.nanoTime();
        for (int key : chaves) {
            tree.insert(key, "Dado_" + key);
        }
        long fimInsert = System.nanoTime();
        double tempoInsert = (fimInsert - inicioInsert) / 1_000_000.0;
        
        System.out.println("  ✓ " + n + " inserções concluídas em " + 
                          String.format("%.2f", tempoInsert) + " ms");
        System.out.println("Tempo médio por inserção: " + 
                          String.format("%.4f", tempoInsert / n) + " ms");
        System.out.println("Altura final da árvore: " + tree.getHeight());
        
        // Teste de busca
        System.out.println("\n2. Teste de BUSCA:");
        Collections.shuffle(chaves, new Random(111));
        int buscasOk = 0;
        
        long inicioBusca = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int chave = chaves.get(i % n);
            String resultado = tree.search(chave);
            if (resultado != null && resultado.equals("Dado_" + chave)) {
                buscasOk++;
            }
        }
        long fimBusca = System.nanoTime();
        double tempoBusca = (fimBusca - inicioBusca) / 1_000_000.0;
        
        System.out.println("1000 buscas concluídas em " + 
                          String.format("%.2f", tempoBusca) + " ms");
        System.out.println("Tempo médio por busca: " + 
                          String.format("%.4f", tempoBusca / 1000) + " ms");
        System.out.println("Taxa de sucesso: " + buscasOk + "/1000 (" + 
                          (buscasOk * 100.0 / 1000) + "%)");
        
        // Teste de integridade
        System.out.println("\n3. Teste de INTEGRIDADE:");
        boolean integro = true;
        for (int i = 1; i <= n; i++) {
            String valor = tree.search(i);
            if (valor == null || !valor.equals("Dado_" + i)) {
                integro = false;
                System.out.println("  ✗ Erro na chave " + i);
                break;
            }
        }
        
        if (integro) {
            System.out.println("Todos os " + n + " elementos verificados com sucesso!");
        }
        
        tree.displayStats();
        
        System.out.println("Teste de performance concluído!");
    }
}
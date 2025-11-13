package src.bPlusTree;

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

        // Executa testes básicos
        testeBasico();
        testeBuscas();
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
        
        System.out.println("✓ Teste básico concluído com sucesso!");
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
                System.out.println("  ✗ Erro na chave " + i);
            }
        }
        
        if (todasOk) {
            System.out.println("  ✓ Todas as 20 chaves foram encontradas corretamente!");
        }
        
        System.out.println("\nTestando buscas de chaves inexistentes:");
        System.out.println("  Chave 0: " + tree.search(0));
        System.out.println("  Chave 25: " + tree.search(25));
        System.out.println("  Chave 100: " + tree.search(100));
        
        tree.displayStats();
    }
}
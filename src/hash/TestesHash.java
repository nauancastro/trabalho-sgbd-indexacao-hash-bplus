package hash;

/**
 * Classe com testes unitários simples para o Hash Extensível
 * Demonstra casos de teste específicos conforme requisitos do trabalho
 */
public class TestesHash {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   TESTES DO HASH EXTENSÍVEL               ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        teste1_InsercaoBasica();
        teste2_SplitSimples();
        teste3_SplitsMultiplos();
        teste4_Busca();
        teste5_Remocao();
        teste6_Atualizacao();
        teste7_SequenciaCompleta();
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   TODOS OS TESTES CONCLUÍDOS!             ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
    
    /**
     * Teste 1: Inserção básica sem splits
     */
    private static void teste1_InsercaoBasica() {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("TESTE 1: Inserção Básica (sem overflow)");
        System.out.println("─────────────────────────────────────────────");
        
        ExtendibleHash hash = new ExtendibleHash(3);
        
        hash.insert(1, "Alice");
        hash.insert(2, "Bob");
        hash.insert(3, "Carol");
        
        assert hash.search(1).equals("Alice") : "Falha ao buscar chave 1";
        assert hash.search(2).equals("Bob") : "Falha ao buscar chave 2";
        assert hash.search(3).equals("Carol") : "Falha ao buscar chave 3";
        
        System.out.println("✓ Inserções bem-sucedidas");
        System.out.println("✓ Buscas bem-sucedidas");
        System.out.println("✓ Profundidade Global: " + hash.getGlobalDepth());
        
        hash.display();
    }
    
    /**
     * Teste 2: Split simples (primeiro overflow)
     */
    private static void teste2_SplitSimples() {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("TESTE 2: Split Simples (primeiro overflow)");
        System.out.println("─────────────────────────────────────────────");
        
        ExtendibleHash hash = new ExtendibleHash(2);
        
        System.out.println("Inserindo chaves 1, 2, 3...");
        hash.insert(1, "um");
        hash.insert(2, "dois");
        hash.insert(3, "tres"); // Causa o primeiro split
        
        System.out.println("✓ Split executado");
        System.out.println("✓ Nova profundidade global: " + hash.getGlobalDepth());
        System.out.println("✓ Número de buckets: " + hash.getNumberOfBuckets());
        
        // Verifica que todas as chaves ainda são acessíveis
        assert hash.search(1).equals("um");
        assert hash.search(2).equals("dois");
        assert hash.search(3).equals("tres");
        
        System.out.println("✓ Todas as chaves acessíveis após split");
        
        hash.display();
    }
    
    /**
     * Teste 3: Múltiplos splits
     */
    private static void teste3_SplitsMultiplos() {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("TESTE 3: Múltiplos Splits");
        System.out.println("─────────────────────────────────────────────");
        
        ExtendibleHash hash = new ExtendibleHash(2);
        
        // Insere chaves que causarão múltiplos splits
        int[] chaves = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        System.out.println("Inserindo chaves: 1 a 10");
        for (int chave : chaves) {
            hash.insert(chave, "valor" + chave);
            System.out.println("  Inserido " + chave + " - Profundidade: " + 
                hash.getGlobalDepth() + ", Buckets: " + hash.getNumberOfBuckets());
        }
        
        System.out.println("\n✓ Profundidade final: " + hash.getGlobalDepth());
        System.out.println("✓ Buckets criados: " + hash.getNumberOfBuckets());
        
        // Verifica integridade
        int encontrados = 0;
        for (int chave : chaves) {
            if (hash.search(chave) != null) {
                encontrados++;
            }
        }
        
        assert encontrados == chaves.length : "Algumas chaves foram perdidas!";
        System.out.println("✓ Todas as " + encontrados + " chaves encontradas");
        
        hash.display();
    }
    
    /**
     * Teste 4: Busca (chaves existentes e não existentes)
     */
    private static void teste4_Busca() {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("TESTE 4: Busca (existentes e não existentes)");
        System.out.println("─────────────────────────────────────────────");
        
        ExtendibleHash hash = new ExtendibleHash(3);
        
        hash.insert(10, "dez");
        hash.insert(20, "vinte");
        hash.insert(30, "trinta");
        
        // Buscas bem-sucedidas
        assert hash.search(10).equals("dez");
        assert hash.search(20).equals("vinte");
        assert hash.search(30).equals("trinta");
        System.out.println("✓ Buscas de chaves existentes: OK");
        
        // Buscas sem sucesso
        assert hash.search(5) == null;
        assert hash.search(15) == null;
        assert hash.search(100) == null;
        System.out.println("✓ Buscas de chaves inexistentes: OK (retornam null)");
    }
    
    /**
     * Teste 5: Remoção
     */
    private static void teste5_Remocao() {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("TESTE 5: Remoção de Chaves");
        System.out.println("─────────────────────────────────────────────");
        
        ExtendibleHash hash = new ExtendibleHash(2);
        
        // Insere chaves
        for (int i = 1; i <= 8; i++) {
            hash.insert(i, "v" + i);
        }
        
        System.out.println("Estado inicial:");
        System.out.println("  Chaves: 1-8");
        System.out.println("  Profundidade: " + hash.getGlobalDepth());
        
        // Remove algumas chaves
        System.out.println("\nRemovendo chaves 3, 5, 7...");
        assert hash.remove(3) == true;
        assert hash.remove(5) == true;
        assert hash.remove(7) == true;
        
        // Verifica remoções
        assert hash.search(3) == null;
        assert hash.search(5) == null;
        assert hash.search(7) == null;
        System.out.println("✓ Chaves removidas com sucesso");
        
        // Verifica que outras chaves permanecem
        assert hash.search(1) != null;
        assert hash.search(2) != null;
        assert hash.search(4) != null;
        System.out.println("✓ Outras chaves permanecem intactas");
        
        // Tenta remover chave inexistente
        assert hash.remove(99) == false;
        System.out.println("✓ Remoção de chave inexistente retorna false");
        
        hash.display();
    }
    
    /**
     * Teste 6: Atualização de valores
     */
    private static void teste6_Atualizacao() {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("TESTE 6: Atualização de Valores");
        System.out.println("─────────────────────────────────────────────");
        
        ExtendibleHash hash = new ExtendibleHash(3);
        
        hash.insert(42, "valorOriginal");
        System.out.println("Valor inicial: " + hash.search(42));
        
        // Atualiza o valor (inserindo mesma chave)
        hash.insert(42, "valorAtualizado");
        System.out.println("Valor atualizado: " + hash.search(42));
        
        assert hash.search(42).equals("valorAtualizado");
        System.out.println("✓ Atualização bem-sucedida");
    }
    
    /**
     * Teste 7: Sequência completa (conforme especificação do trabalho)
     */
    private static void teste7_SequenciaCompleta() {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("TESTE 7: Sequência Completa (1 a 50 aleatório)");
        System.out.println("─────────────────────────────────────────────");
        
        ExtendibleHash hash = new ExtendibleHash(3);
        
        // Chaves de 1 a 50 em ordem "aleatória"
        int[] chavesAleatorias = {
            23, 45, 12, 7, 31, 19, 48, 3, 36, 15,
            41, 8, 27, 50, 14, 33, 6, 22, 39, 11,
            47, 2, 28, 13, 44, 18, 5, 34, 25, 9,
            42, 16, 30, 1, 37, 21, 49, 10, 26, 4,
            35, 17, 43, 20, 46, 29, 38, 24, 32, 40
        };
        
        System.out.println("Inserindo 50 chaves em ordem aleatória...");
        for (int chave : chavesAleatorias) {
            hash.insert(chave, "valor" + chave);
        }
        
        System.out.println("✓ 50 chaves inseridas");
        System.out.println("  Profundidade Global: " + hash.getGlobalDepth());
        System.out.println("  Número de Buckets: " + hash.getNumberOfBuckets());
        
        // Verifica todas as chaves
        int contador = 0;
        for (int i = 1; i <= 50; i++) {
            if (hash.search(i) != null) {
                contador++;
            }
        }
        
        assert contador == 50 : "Nem todas as chaves foram encontradas!";
        System.out.println("✓ Todas as 50 chaves encontradas");
        
        // Remove algumas chaves
        System.out.println("\nRemovendo 10 chaves aleatórias...");
        int[] remover = {5, 15, 25, 35, 45, 10, 20, 30, 40, 50};
        
        for (int chave : remover) {
            hash.remove(chave);
        }
        
        System.out.println("✓ 10 chaves removidas");
        System.out.println("  Profundidade após remoções: " + hash.getGlobalDepth());
        
        // Verifica que foram removidas
        for (int chave : remover) {
            assert hash.search(chave) == null;
        }
        System.out.println("✓ Chaves removidas não encontradas");
        
        // Verifica que as outras permanecem
        contador = 0;
        for (int i = 1; i <= 50; i++) {
            boolean foiRemovida = false;
            for (int rem : remover) {
                if (i == rem) {
                    foiRemovida = true;
                    break;
                }
            }
            if (!foiRemovida && hash.search(i) != null) {
                contador++;
            }
        }
        
        assert contador == 40;
        System.out.println("✓ 40 chaves restantes encontradas");
        
        hash.display();
    }
}

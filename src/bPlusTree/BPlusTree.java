package src.bPlusTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de Árvore B+ para indexação de dados
 * Estrutura de dados balanceada que mantém dados ordenados e permite
 * buscas, inserções e remoções em tempo logarítmico O(log n)
 */
public class BPlusTree {
    private Node root;
    private final int order;
    private final int minKeys;
    private final int maxKeys;

    /**
     * Construtor da Árvore B+
     * @param order Ordem da árvore (deve ser >= 3)
     * @throws IllegalArgumentException se order < 3
     */
    public BPlusTree(int order) {
        if (order < 3) {
            throw new IllegalArgumentException("A ordem da árvore deve ser no mínimo 3");
        }
        this.order = order;
        this.maxKeys = order - 1;
        this.minKeys = (int) Math.ceil(order / 2.0) - 1;
        this.root = new LeafNode(maxKeys);
    }

    /**
     * Insere uma chave e valor na árvore
     * @param key Chave a ser inserida
     * @param value Valor associado à chave
     */
    public void insert(int key, String value) {
        Node newRoot = root.insert(key, value);
        if (newRoot != null) {
            root = newRoot;
        }
    }

    /**
     * Busca um valor pela chave
     * @param key Chave a ser buscada
     * @return Valor associado à chave, ou null se não encontrado
     */
    public String search(int key) {
        return root.search(key);
    }

    /**
     * Remove uma chave da árvore
     * @param key Chave a ser removida
     * @return true se a remoção foi bem-sucedida, false caso contrário
     */
    public boolean delete(int key) {
        boolean result = root.delete(key);
        
        // Se a raiz é um nó interno e ficou vazio, promove o único filho
        if (root instanceof InternalNode && root.getKeyCount() == 0) {
            InternalNode internalRoot = (InternalNode) root;
            if (internalRoot.getChildCount() > 0) {
                root = internalRoot.getChildren().get(0);
            }
        }
        
        return result;
    }

    /**
     * Exibe a estrutura da árvore de forma hierárquica
     */
    public void display() {
        System.out.println("\n=== Estrutura da Árvore B+ (Ordem " + order + ") ===");
        root.display(0);
        System.out.println("==========================================\n");
    }

    /**
     * Exibe todas as chaves em ordem (percorre as folhas)
     */
    public void displayInOrder() {
        System.out.println("\n=== Chaves em Ordem ===");
        List<Integer> keys = new ArrayList<>();
        collectKeys(root, keys);
        System.out.println(keys);
        System.out.println("=======================\n");
    }

    /**
     * Coleta todas as chaves da árvore em ordem
     * @param node Nó atual
     * @param keys Lista para armazenar as chaves
     */
    private void collectKeys(Node node, List<Integer> keys) {
        if (node instanceof LeafNode) {
            keys.addAll(node.getKeys());
        } else if (node instanceof InternalNode) {
            InternalNode internal = (InternalNode) node;
            for (Node child : internal.getChildren()) {
                collectKeys(child, keys);
            }
        }
    }

    /**
     * Retorna a altura da árvore
     * @return Altura da árvore
     */
    public int getHeight() {
        return root.getHeight();
    }

    /**
     * Retorna a ordem da árvore
     * @return Ordem da árvore
     */
    public int getOrder() {
        return order;
    }

    /**
     * Retorna o número máximo de chaves por nó
     * @return Número máximo de chaves
     */
    public int getMaxKeys() {
        return maxKeys;
    }

    /**
     * Retorna o número mínimo de chaves por nó
     * @return Número mínimo de chaves
     */
    public int getMinKeys() {
        return minKeys;
    }

    /**
     * Verifica se a árvore está vazia
     * @return true se vazia, false caso contrário
     */
    public boolean isEmpty() {
        return root.getKeyCount() == 0;
    }

    /**
     * Retorna todas as chaves da árvore em ordem
     * @return Lista de chaves ordenadas
     */
    public List<Integer> getAllKeys() {
        List<Integer> keys = new ArrayList<>();
        collectKeys(root, keys);
        return keys;
    }

    /**
     * Exibe estatísticas da árvore
     */
    public void displayStats() {
        System.out.println("\n=== Estatísticas da Árvore B+ ===");
        System.out.println("Ordem: " + order);
        System.out.println("Altura: " + getHeight());
        System.out.println("Total de chaves: " + getAllKeys().size());
        System.out.println("Chaves mínimas por nó: " + minKeys);
        System.out.println("Chaves máximas por nó: " + maxKeys);
        System.out.println("==================================\n");
    }
}
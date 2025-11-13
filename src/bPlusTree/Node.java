package src.bPlusTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa um nó genérico da Árvore B+
 * Pode ser um nó interno (InternalNode) ou um nó folha (LeafNode)
 */
public abstract class Node {
    protected List<Integer> keys;

    /**
     * Construtor base para todos os nós
     */
    public Node() {
        this.keys = new ArrayList<>();
    }

    /**
     * Insere uma chave e valor no nó apropriado
     * @param key Chave a ser inserida
     * @param value Valor associado à chave
     * @return Novo nó caso ocorra split, null caso contrário
     */
    public abstract Node insert(int key, String value);

    /**
     * Busca uma chave no nó
     * @param key Chave a ser buscada
     * @return Valor associado à chave, ou null se não encontrado
     */
    public abstract String search(int key);

    /**
     * Remove uma chave do nó
     * @param key Chave a ser removida
     * @return true se a remoção foi bem-sucedida, false caso contrário
     */
    public abstract boolean delete(int key);

    /**
     * Exibe a estrutura do nó de forma hierárquica
     * @param level Nível de profundidade na árvore
     */
    public abstract void display(int level);

    /**
     * Retorna a altura da subárvore a partir deste nó
     * @return Altura da subárvore
     */
    public abstract int getHeight();

    /**
     * Verifica se o nó é uma folha
     * @return true se for folha, false se for nó interno
     */
    public abstract boolean isLeaf();

    /**
     * Retorna o número de chaves no nó
     * @return Quantidade de chaves
     */
    public int getKeyCount() {
        return keys.size();
    }

    /**
     * Retorna a lista de chaves (para testes e debug)
     * @return Lista de chaves
     */
    public List<Integer> getKeys() {
        return new ArrayList<>(keys);
    }
}
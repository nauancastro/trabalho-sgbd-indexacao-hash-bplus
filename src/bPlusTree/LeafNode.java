package src.bPlusTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Nó folha da Árvore B+
 * Armazena as chaves e valores efetivos dos dados
 * Mantém ponteiro para a próxima folha para percurso sequencial
 */
public class LeafNode extends Node {
    private List<String> values;
    private LeafNode next;
    private int maxKeys;

    /**
     * Construtor do nó folha
     * @param maxKeys Número máximo de chaves que o nó pode conter
     */
    public LeafNode(int maxKeys) {
        super();
        this.values = new ArrayList<>();
        this.next = null;
        this.maxKeys = maxKeys;
    }

    /**
     * Encontra a posição correta para inserir/buscar uma chave
     * Usa busca binária para eficiência
     * @param key Chave a ser localizada
     * @return Posição onde a chave está ou deveria estar
     */
    private int findPosition(int key) {
        int left = 0;
        int right = keys.size();
        
        while (left < right) {
            int mid = (left + right) / 2;
            if (keys.get(mid) < key) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    @Override
    public Node insert(int key, String value) {
        int pos = findPosition(key);
        
        // Se a chave já existe, atualiza o valor
        if (pos < keys.size() && keys.get(pos) == key) {
            values.set(pos, value);
            return null;
        }

        // Insere a chave e valor na posição correta
        keys.add(pos, key);
        values.add(pos, value);

        // Se não excedeu o limite, não precisa fazer split
        if (keys.size() <= maxKeys) {
            return null;
        }

        // Precisa fazer split do nó folha
        return split();
    }

    /**
     * Divide o nó folha em dois quando excede a capacidade
     * @return Novo nó interno que será a nova raiz
     */
    private Node split() {
        int mid = (maxKeys + 1) / 2;
        
        // Cria novo nó folha com metade das chaves
        LeafNode newLeaf = new LeafNode(maxKeys);
        newLeaf.keys.addAll(keys.subList(mid, keys.size()));
        newLeaf.values.addAll(values.subList(mid, values.size()));
        
        // Remove elementos movidos para o novo nó
        keys.subList(mid, keys.size()).clear();
        values.subList(mid, values.size()).clear();
        
        // Ajusta ponteiros para próxima folha
        newLeaf.next = this.next;
        this.next = newLeaf;

        // Cria novo nó interno como nova raiz
        InternalNode newRoot = new InternalNode(maxKeys);
        newRoot.addKey(newLeaf.keys.get(0));
        newRoot.addChild(this);
        newRoot.addChild(newLeaf);
        
        return newRoot;
    }

    @Override
    public String search(int key) {
        int pos = findPosition(key);
        if (pos < keys.size() && keys.get(pos) == key) {
            return values.get(pos);
        }
        return null;
    }

    @Override
    public boolean delete(int key) {
        int pos = findPosition(key);
        if (pos < keys.size() && keys.get(pos) == key) {
            keys.remove(pos);
            values.remove(pos);
            return true;
        }
        return false;
    }

    @Override
    public void display(int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + "FOLHA: " + keys + " -> " + values);
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    /**
     * Retorna o próximo nó folha (para percurso sequencial)
     * @return Próxima folha ou null se for a última
     */
    public LeafNode getNext() {
        return next;
    }

    /**
     * Define o próximo nó folha
     * @param next Próximo nó folha
     */
    public void setNext(LeafNode next) {
        this.next = next;
    }

    /**
     * Retorna os valores armazenados (para testes)
     * @return Lista de valores
     */
    public List<String> getValues() {
        return new ArrayList<>(values);
    }
}
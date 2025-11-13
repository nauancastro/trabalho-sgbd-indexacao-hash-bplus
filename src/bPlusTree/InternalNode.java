package src.bPlusTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Nó interno da Árvore B+
 * Não armazena valores, apenas chaves para guiar as buscas
 * Mantém ponteiros para nós filhos (podem ser internos ou folhas)
 */
public class InternalNode extends Node {
    private List<Node> children;
    private int maxKeys;

    /**
     * Construtor do nó interno
     * @param maxKeys Número máximo de chaves que o nó pode conter
     */
    public InternalNode(int maxKeys) {
        super();
        this.children = new ArrayList<>();
        this.maxKeys = maxKeys;
    }

    /**
     * Adiciona uma chave ao nó interno
     * @param key Chave a ser adicionada
     */
    public void addKey(int key) {
        keys.add(key);
    }

    /**
     * Adiciona um filho ao nó interno
     * @param child Nó filho a ser adicionado
     */
    public void addChild(Node child) {
        children.add(child);
    }

    /**
     * Encontra a posição do filho apropriado para uma chave
     * @param key Chave a ser localizada
     * @return Índice do filho onde a chave deve estar
     */
    private int findChildPosition(int key) {
        int pos = 0;
        while (pos < keys.size() && key >= keys.get(pos)) {
            pos++;
        }
        return pos;
    }

    @Override
    public Node insert(int key, String value) {
        int pos = findChildPosition(key);
        Node child = children.get(pos);
        Node newChild = child.insert(key, value);

        if (newChild == null) {
            return null; // Sem split no filho
        }

        // Houve split no filho, precisa inserir nova chave
        if (newChild instanceof InternalNode) {
            InternalNode splitNode = (InternalNode) newChild;
            keys.add(pos, splitNode.keys.get(0));
            children.set(pos, splitNode.children.get(0));
            children.add(pos + 1, splitNode.children.get(1));
        }

        // Se não excedeu o limite, não precisa fazer split
        if (keys.size() <= maxKeys) {
            return null;
        }

        // Precisa fazer split do nó interno
        return split();
    }

    /**
     * Divide o nó interno em dois quando excede a capacidade
     * @return Novo nó interno que será a nova raiz
     */
    private Node split() {
        int mid = (maxKeys + 1) / 2;
        
        // Cria novo nó interno com metade das chaves
        InternalNode newInternal = new InternalNode(maxKeys);
        newInternal.keys.addAll(keys.subList(mid, keys.size()));
        newInternal.children.addAll(children.subList(mid, children.size()));
        
        // Remove elementos movidos para o novo nó
        keys.subList(mid, keys.size()).clear();
        children.subList(mid, children.size()).clear();

        // Cria novo nó interno como nova raiz
        InternalNode newRoot = new InternalNode(maxKeys);
        newRoot.keys.add(newInternal.keys.get(0));
        newRoot.children.add(this);
        newRoot.children.add(newInternal);
        
        return newRoot;
    }

    @Override
    public String search(int key) {
        int pos = findChildPosition(key);
        return children.get(pos).search(key);
    }

    @Override
    public boolean delete(int key) {
        int pos = findChildPosition(key);
        return children.get(pos).delete(key);
    }

    @Override
    public void display(int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + "INTERNO: " + keys);
        for (Node child : children) {
            child.display(level + 1);
        }
    }

    @Override
    public int getHeight() {
        return 1 + children.get(0).getHeight();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    /**
     * Retorna a lista de filhos (para testes)
     * @return Lista de nós filhos
     */
    public List<Node> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Retorna o número de filhos
     * @return Quantidade de filhos
     */
    public int getChildCount() {
        return children.size();
    }
}
package src.bPlusTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Nó interno da Árvore B+
 */
public class InternalNode extends Node {
    List<Node> children;  // Agora package-private para LeafNode acessar
    private int maxKeys;

    public InternalNode(int maxKeys) {
        super();
        this.children = new ArrayList<>();
        this.maxKeys = maxKeys;
    }

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
        
        // Proteção contra índice fora do limite
        if (pos >= children.size()) {
            pos = children.size() - 1;
        }
        
        Node child = children.get(pos);
        Node newChild = child.insert(key, value);

        if (newChild == null) {
            return null;  // Sem split no filho
        }

        // Houve split no filho
        if (newChild instanceof InternalNode) {
            InternalNode splitNode = (InternalNode) newChild;
            int newKey = splitNode.keys.get(0);
            Node leftChild = splitNode.children.get(0);
            Node rightChild = splitNode.children.get(1);
            
            // Insere a nova chave e ajusta os filhos
            keys.add(pos, newKey);
            children.set(pos, leftChild);
            children.add(pos + 1, rightChild);
        }

        // Verifica se este nó precisa fazer split
        if (keys.size() <= maxKeys) {
            return null;
        }

        return split();
    }

    private Node split() {
        int mid = (keys.size() + 1) / 2;
        
        InternalNode newInternal = new InternalNode(maxKeys);
        newInternal.keys.addAll(keys.subList(mid, keys.size()));
        newInternal.children.addAll(children.subList(mid, children.size()));
        
        keys.subList(mid, keys.size()).clear();
        children.subList(mid, children.size()).clear();

        InternalNode newRoot = new InternalNode(maxKeys);
        newRoot.keys.add(newInternal.keys.get(0));
        newRoot.children.add(this);
        newRoot.children.add(newInternal);
        
        return newRoot;
    }

    @Override
    public String search(int key) {
        int pos = findChildPosition(key);
        if (pos >= children.size()) {
            pos = children.size() - 1;
        }
        return children.get(pos).search(key);
    }

    @Override
    public boolean delete(int key) {
        int pos = findChildPosition(key);
        if (pos >= children.size()) {
            pos = children.size() - 1;
        }
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

    public List<Node> getChildren() {
        return new ArrayList<>(children);
    }

    public int getChildCount() {
        return children.size();
    }
}
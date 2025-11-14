package src.bPlusTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Nó interno da Árvore B+
 */
public class InternalNode extends Node {
    List<Node> children;
    private int maxKeys;

    public InternalNode(int maxKeys) {
        super();
        this.children = new ArrayList<>();
        this.maxKeys = maxKeys;
    }

    private int findChildPosition(int key) {
        for (int i = 0; i < keys.size(); i++) {
            if (key < keys.get(i)) {
                return i;
            }
        }
        // Retorna o último filho, mas garante que não exceda o limite
        int pos = keys.size();
        if (pos >= children.size()) {
            pos = children.size() - 1;
        }
        return pos;
    }

    @Override
    public Node insert(int key, String value) {
        int pos = findChildPosition(key);
        Node child = children.get(pos);
        Node newChild = child.insert(key, value);

        if (newChild == null) {
            return null; // Sem split
        }

        InternalNode splitResult = (InternalNode) newChild;
        int promotedKey = splitResult.keys.get(0);
        Node leftChild = splitResult.children.get(0);
        Node rightChild = splitResult.children.get(1);

        // Insere a chave promovida na posição correta
        keys.add(pos, promotedKey);
        children.set(pos, leftChild);
        children.add(pos + 1, rightChild);

        // Verifica se este nó precisa split
        if (keys.size() <= maxKeys) {
            return null;
        }

        return split();
    }

    private Node split() {
        int mid = (keys.size() + 1) / 2;
        
        // Cria novo nó interno com a metade direita
        InternalNode newInternal = new InternalNode(maxKeys);
        
        // Move metade das chaves e filhos para o novo nó
        for (int i = mid; i < keys.size(); i++) {
            newInternal.keys.add(keys.get(i));
        }
        for (int i = mid; i < children.size(); i++) {
            newInternal.children.add(children.get(i));
        }
        
        // Remove as chaves e filhos movidos
        for (int i = keys.size() - 1; i >= mid; i--) {
            keys.remove(i);
        }
        for (int i = children.size() - 1; i >= mid; i--) {
            children.remove(i);
        }

        // Cria nó temporário para retornar (será a nova raiz)
        InternalNode newRoot = new InternalNode(maxKeys);
        newRoot.keys.add(newInternal.keys.get(0));
        newRoot.children.add(this);
        newRoot.children.add(newInternal);
        
        return newRoot;
    }

    @Override
    public String search(int key) {
        int pos = findChildPosition(key);
        // Proteção extra de segurança
        if (pos >= children.size()) {
            pos = children.size() - 1;
        }
        if (pos < 0 || children.isEmpty()) {
            return null;
        }
        return children.get(pos).search(key);
    }

    @Override
    public boolean delete(int key) {
        int pos = findChildPosition(key);
        // Proteção extra de segurança
        if (pos >= children.size()) {
            pos = children.size() - 1;
        }
        if (pos < 0 || children.isEmpty()) {
            return false;
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
        if (children.isEmpty()) return 1;
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
package src.bPlusTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Nó folha da Árvore B+
 */
public class LeafNode extends Node {
    private List<String> values;
    private LeafNode next;
    private int maxKeys;

    public LeafNode(int maxKeys) {
        super();
        this.values = new ArrayList<>();
        this.next = null;
        this.maxKeys = maxKeys;
    }

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
        
        if (pos < keys.size() && keys.get(pos) == key) {
            values.set(pos, value);
            return null;
        }

        keys.add(pos, key);
        values.add(pos, value);

        if (keys.size() <= maxKeys) {
            return null;
        }

        return split();
    }

    private Node split() {
        int mid = (keys.size() + 1) / 2;
        
        // Cria nova folha
        LeafNode newLeaf = new LeafNode(maxKeys);
        
        // Move metade para a nova folha
        for (int i = mid; i < keys.size(); i++) {
            newLeaf.keys.add(keys.get(i));
            newLeaf.values.add(values.get(i));
        }
        
        // Remove elementos movidos
        for (int i = keys.size() - 1; i >= mid; i--) {
            keys.remove(i);
            values.remove(i);
        }
        
        // Ajusta ponteiros
        newLeaf.next = this.next;
        this.next = newLeaf;

        // Cria nó temporário para promover a primeira chave da nova folha
        InternalNode newRoot = new InternalNode(maxKeys);
        newRoot.keys.add(newLeaf.keys.get(0));
        newRoot.children.add(this);
        newRoot.children.add(newLeaf);
        
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

    public LeafNode getNext() {
        return next;
    }

    public void setNext(LeafNode next) {
        this.next = next;
    }

    public List<String> getValues() {
        return new ArrayList<>(values);
    }
}
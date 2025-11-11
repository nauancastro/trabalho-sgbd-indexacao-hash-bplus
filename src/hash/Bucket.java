package hash;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private int localDepth;           // profundidade local do bucket
    private int capacity;              // capacidade máxima do bucket
    private List<Entry> entries;       // lista de entradas (chave-valor)

    public static class Entry {
        int key;
        String value;
        
        public Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
        
        public int getKey() {
            return key;
        }
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
        
        @Override
        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }
    
    // constructor
    public Bucket(int localDepth, int capacity) {
        this.localDepth = localDepth;
        this.capacity = capacity;
        this.entries = new ArrayList<>();
    }
    
    // insere uma nova entrada no bucket
    public boolean insert(int key, String value) {
        // verifica se a chave existe e atualiza o valor dela
        for (Entry entry : entries) {
            if (entry.getKey() == key) {
                entry.setValue(value);
                return true;
            }
        }
        
        // adiciona uma nova entrada se o bucket não tiver cheio
        if (!isFull()) {
            entries.add(new Entry(key, value));
            return true;
        }
        
        return false; // bucket cheio
    }
    
    // busca uma entrada no bucket utilizando a chave
    public String search(int key) {
        for (Entry entry : entries) {
            if (entry.getKey() == key) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    // remove uma entrada do bucket
    public boolean remove(int key) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getKey() == key) {
                entries.remove(i);
                return true;
            }
        }
        return false;
    }
    
    // verifica se o bucket ta cheio
    public boolean isFull() {
        return entries.size() >= capacity;
    }
    
    // retorna o número de entradas no bucket (o tamanho atual dele)
    public int size() {
        return entries.size();
    }
    
    // retorna a profundidade local
    public int getLocalDepth() {
        return localDepth;
    }
    
    // incrementa a profundidade local do bucket
    public void incrementDepth() {
        this.localDepth++;
    }
    
    // retorna a capacidade do bucket
    public int getCapacity() {
        return capacity;
    }
    
    // retorna todas as entradas do bucket
    public List<Entry> getEntries() {
        return new ArrayList<>(entries);
    }
    
    // limpa todas as entradas do bucket
    public void clear() {
        entries.clear();
    }
    
    @Override
    public String toString() {
        return "Bucket{depth=" + localDepth + ", entries=" + entries + "}";
    }
}

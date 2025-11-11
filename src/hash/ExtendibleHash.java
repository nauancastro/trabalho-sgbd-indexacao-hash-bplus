package hash;

import java.util.ArrayList;
import java.util.List;

public class ExtendibleHash {
    private int globalDepth;              // profundidade global do diretório
    private int bucketCapacity;           // capacidade de cada bucket
    private List<Bucket> directory;       // diretório de ponteiros para buckets
    
    // constructor
    public ExtendibleHash(int bucketCapacity) {
        this.globalDepth = 0;
        this.bucketCapacity = bucketCapacity;
        this.directory = new ArrayList<>();
        
        // Inicializa com um único bucket
        Bucket initialBucket = new Bucket(0, bucketCapacity);
        directory.add(initialBucket);
    }
    
    // função hash baseada na profundidade que retorna o índice do diretório
    private int hash(int key, int depth) {
        return key & ((1 << depth) - 1);
    }
    
    // insere uma nova chave-valor no hash
    public void insert(int key, String value) {
        int index = hash(key, globalDepth);
        Bucket bucket = directory.get(index);
        
        // tenta inserir no bucket
        if (bucket.insert(key, value)) {
            return; // inserção deu certo
        }
        
        // bucket cheio, necessário fazer um split
        split(index, key, value);
    }
    
    // realiza o split do bucket quando estiver cehio
    private void split(int bucketIndex, int key, String value) {
        Bucket bucket = directory.get(bucketIndex);
        
        // se a profundidade local é igual à global, precisa duplicar o diretório
        if (bucket.getLocalDepth() == globalDepth) {
            expandDirectory();
            bucketIndex = hash(key, globalDepth);
            bucket = directory.get(bucketIndex);
        }
        
        // incrementa a profundidade local
        bucket.incrementDepth();
        int localDepth = bucket.getLocalDepth();
        
        // cria um bucket novo com a mesma profundidade local
        Bucket newBucket = new Bucket(localDepth, bucketCapacity);
        
        // redistribui as entradas entre o bucket original e o novo
        List<Bucket.Entry> oldEntries = new ArrayList<>(bucket.getEntries());
        bucket.clear();
        
        // adiciona a nova chave às entradas antigas
        oldEntries.add(new Bucket.Entry(key, value));
        
        // redistribui todas as entradas
        for (Bucket.Entry entry : oldEntries) {
            int entryHash = hash(entry.getKey(), localDepth);
            int bucketHash = hash(bucketIndex, localDepth);
            
            if (entryHash == bucketHash) {
                bucket.insert(entry.getKey(), entry.getValue());
            } else {
                newBucket.insert(entry.getKey(), entry.getValue());
            }
        }
        
        // atualiza o diretório
        updateDirectory(bucketIndex, bucket, newBucket);
    }
    

    private void expandDirectory() {
        globalDepth++;
        int oldSize = directory.size();
        
        // duplica o diretório
        for (int i = 0; i < oldSize; i++) {
            directory.add(directory.get(i));
        }
    }

    private void updateDirectory(int oldIndex, Bucket oldBucket, Bucket newBucket) {
        int localDepth = oldBucket.getLocalDepth();
        
        for (int i = 0; i < directory.size(); i++) {
            if (directory.get(i) == oldBucket) {
                int hash = hash(i, localDepth);
                int oldHash = hash(oldIndex, localDepth);
                
                if (hash != oldHash) {
                    directory.set(i, newBucket);
                }
            }
        }
    }

    public String search(int key) {
        int index = hash(key, globalDepth);
        Bucket bucket = directory.get(index);
        return bucket.search(key);
    }

    public boolean remove(int key) {
        int index = hash(key, globalDepth);
        Bucket bucket = directory.get(index);
        boolean removed = bucket.remove(key);
        
        if (removed) {
            // verifica se dá pra o fazer merge
            tryMerge();
        }
        
        return removed;
    }

    private void tryMerge() {
        // verifica se todos os buckets tem profundidade local menor que global
        boolean canShrink = true;
        for (Bucket bucket : directory) {
            if (bucket.getLocalDepth() == globalDepth) {
                canShrink = false;
                break;
            }
        }
        
        // reduz a profundidade global se possível
        if (canShrink && globalDepth > 0) {
            globalDepth--;
            directory = new ArrayList<>(directory.subList(0, directory.size() / 2));
        }
    }

    // exibe a estrutura do hash
    public void display() {
        System.out.println("\n========== HASH EXTENSÍVEL ==========");
        System.out.println("Profundidade Global: " + globalDepth);
        System.out.println("Capacidade dos Buckets: " + bucketCapacity);
        System.out.println("Tamanho do Diretório: " + directory.size());
        System.out.println("\n--- DIRETÓRIO ---");
        
        for (int i = 0; i < directory.size(); i++) {
            Bucket bucket = directory.get(i);
            String binaryIndex;
            if (globalDepth == 0) {
                binaryIndex = "0";
            } else {
                binaryIndex = String.format("%" + globalDepth + "s", 
                    Integer.toBinaryString(i)).replace(' ', '0');
            }
            
            System.out.printf("[%s] (%d) -> Bucket (depth=%d, size=%d/%d) %s\n",
                binaryIndex, i, bucket.getLocalDepth(), 
                bucket.size(), bucket.getCapacity(),
                bucket.getEntries());
        }
        
        System.out.println("\n--- BUCKETS ÚNICOS ---");
        List<Bucket> uniqueBuckets = new ArrayList<>();
        for (Bucket bucket : directory) {
            if (!uniqueBuckets.contains(bucket)) {
                uniqueBuckets.add(bucket);
            }
        }
        
        for (int i = 0; i < uniqueBuckets.size(); i++) {
            Bucket bucket = uniqueBuckets.get(i);
            System.out.printf("Bucket %d: depth=%d, entries=%s\n", 
                i, bucket.getLocalDepth(), bucket.getEntries());
        }
        
        System.out.println("=====================================\n");
    }
    
    // retorna algumas estatísticas do hash
    public String getStats() {
        List<Bucket> uniqueBuckets = new ArrayList<>();
        for (Bucket bucket : directory) {
            if (!uniqueBuckets.contains(bucket)) {
                uniqueBuckets.add(bucket);
            }
        }
        
        int totalEntries = 0;
        for (Bucket bucket : uniqueBuckets) {
            totalEntries += bucket.size();
        }
        
        return String.format("Stats: GlobalDepth=%d, Directory=%d, Buckets=%d, Entries=%d",
            globalDepth, directory.size(), uniqueBuckets.size(), totalEntries);
    }

    // retorna a profundidade global
    public int getGlobalDepth() {
        return globalDepth;
    }
    
    // retorna o número de buckets únicos
    public int getNumberOfBuckets() {
        List<Bucket> uniqueBuckets = new ArrayList<>();
        for (Bucket bucket : directory) {
            if (!uniqueBuckets.contains(bucket)) {
                uniqueBuckets.add(bucket);
            }
        }
        return uniqueBuckets.size();
    }
}

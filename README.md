# Trabalho SGBD - Indexação: Hash Extensível e Árvore B+

## 📌 Descrição
Projeto da disciplina de **Construção de Sistemas de Gerência de Bancos de Dados (CSGBD)** da Universidade Federal do Ceará.

Implementação em Java de estruturas de indexação utilizadas em SGBDs:
- **Hash Extensível** ✅ (Implementado)
- **Árvore B+** 🚧 (Em desenvolvimento)

## 🎯 Objetivo
Compreender o funcionamento interno dessas estruturas de dados, incluindo:
- Operações de inserção, busca e remoção
- Gerenciamento de páginas/buckets
- Divisão (split) e fusão (merge) de blocos

---

## 🗂️ Estrutura do Projeto

```
trabalho-sgbd-indexacao-hash-bplus/
├── src/
│   ├── hash/
│   │   ├── Bucket.java          # Classe que representa um bucket
│   │   ├── ExtendibleHash.java  # Implementação do Hash Extensível
│   │   └── Main.java            # Programa de demonstração e testes
│   └── bplus/                   # (A ser implementado)
├── README.md
└── LICENSE
```

---

## 🔨 Hash Extensível

### Características da Implementação
- **Profundidade Global**: Controla o tamanho do diretório
- **Profundidade Local**: Controla a distribuição das chaves em cada bucket
- **Split Dinâmico**: Quando um bucket fica cheio, é dividido automaticamente
- **Merge**: Redução da estrutura quando possível após remoções

### Classes Principais

#### 1. `Bucket.java`
Representa um bucket que armazena pares chave-valor.
- Capacidade configurável
- Profundidade local
- Métodos: insert, search, remove, isFull, isEmpty

#### 2. `ExtendibleHash.java`
Estrutura principal do Hash Extensível.
- Métodos públicos:
  - `insert(int key, String value)` - Insere uma chave-valor
  - `search(int key)` - Busca uma chave e retorna o valor
  - `remove(int key)` - Remove uma chave
  - `display()` - Exibe toda a estrutura do hash
  - `getStats()` - Retorna estatísticas da estrutura

#### 3. `Main.java`
Programa de demonstração com três modos:
- **Demonstração Automática**: Executa uma sequência predefinida de operações
- **Modo Interativo**: Permite ao usuário executar operações manualmente
- **Teste Aleatório**: Gera inserções e remoções aleatórias

---

## 🚀 Como Compilar e Executar

### Pré-requisitos
- Java 17 ou superior
- JDK instalado e configurado no PATH

### Compilação

No diretório raiz do projeto:

```bash
# Windows (PowerShell)
javac -d bin src/hash/*.java

# Linux/Mac
javac -d bin src/hash/*.java
```

### Execução

```bash
# Windows (PowerShell)
java -cp bin hash.Main

# Linux/Mac
java -cp bin hash.Main
```

### Compilação e Execução Direta (sem diretório bin)

```bash
# Compilar
javac src/hash/*.java

# Executar (no diretório src)
cd src
java hash.Main
```

---

## 📖 Exemplo de Uso

### Uso Programático

```java
import hash.ExtendibleHash;

public class Exemplo {
    public static void main(String[] args) {
        // Cria hash com capacidade de 2 entradas por bucket
        ExtendibleHash hash = new ExtendibleHash(2);
        
        // Inserções
        hash.insert(1, "Alice");
        hash.insert(2, "Bob");
        hash.insert(3, "Carol");
        hash.insert(4, "Dave");
        
        // Busca
        String valor = hash.search(2);
        System.out.println("Chave 2: " + valor); // Output: Bob
        
        // Remoção
        hash.remove(3);
        
        // Exibir estrutura
        hash.display();
    }
}
```

### Demonstração Interativa

Execute o programa `Main.java` e escolha uma das opções do menu:

1. **Demonstração Automática**: Veja o hash em ação com uma sequência predefinida
2. **Modo Interativo**: Teste manualmente inserções, buscas e remoções
3. **Teste Aleatório**: Gere dados aleatórios para testar a estrutura

---

## 🧪 Testes Sugeridos

Conforme especificação do trabalho, recomenda-se testar com:
- Chaves inteiras de 1 a 50
- Inserções em ordem aleatória
- Remoções em ordem aleatória
- Observar comportamento de splits e merges

---

## 📊 Funcionamento do Hash Extensível

### Inserção
1. Calcula o hash da chave usando os últimos `globalDepth` bits
2. Localiza o bucket correspondente no diretório
3. Se o bucket tem espaço, insere a chave
4. Se o bucket está cheio:
   - Se `localDepth == globalDepth`: duplica o diretório
   - Incrementa a profundidade local do bucket
   - Divide o bucket em dois
   - Redistribui as chaves

### Busca
1. Calcula o hash da chave
2. Acessa diretamente o bucket correspondente
3. Busca a chave no bucket

### Remoção
1. Localiza e remove a chave do bucket
2. Tenta fazer merge se possível (reduz profundidade global)

---

## 👥 Equipe
- Nauan Castro

## 📅 Informações do Trabalho
- **Disciplina**: Construção de Sistemas de Gerência de Bancos de Dados
- **Professora**: Lívia Almada
- **Instituição**: Universidade Federal do Ceará
- **Prazo**: 12/11/2025

---

## 📝 Licença
Este projeto está sob a licença especificada no arquivo LICENSE.

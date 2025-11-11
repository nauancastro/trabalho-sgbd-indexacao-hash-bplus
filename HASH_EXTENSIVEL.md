# Documentação Técnica - Hash Extensível

## 📚 Conceitos Fundamentais

### O que é Hash Extensível?

O Hash Extensível é uma técnica de indexação dinâmica usada em bancos de dados para permitir que a estrutura de índice cresça e diminua conforme necessário, sem precisar reorganizar toda a estrutura.

### Componentes Principais

#### 1. Diretório
- Array de ponteiros para buckets
- Tamanho: 2^(profundidade global)
- Pode crescer dinamicamente

#### 2. Buckets
- Armazenam os pares chave-valor reais
- Capacidade fixa (definida na criação)
- Possuem profundidade local

#### 3. Profundidade Global
- Número de bits usados para indexar o diretório
- Controla o tamanho do diretório (2^d entradas)

#### 4. Profundidade Local
- Número de bits usados para distribuir chaves em um bucket
- Sempre: profundidade_local ≤ profundidade_global

---

## 🔧 Operações

### Inserção

```
1. Calcular hash(chave) usando os últimos 'd' bits (d = profundidade global)
2. Localizar o bucket correspondente no diretório
3. Se o bucket tem espaço:
   - Inserir a chave
   - FIM
4. Se o bucket está cheio (OVERFLOW):
   a. Se profundidade_local == profundidade_global:
      - Duplicar o diretório (dobrar o tamanho)
      - Incrementar profundidade_global
   b. Incrementar profundidade_local do bucket
   c. Criar novo bucket com mesma profundidade_local
   d. Redistribuir as chaves entre os dois buckets
   e. Atualizar ponteiros do diretório
   f. Tentar inserir novamente
```

#### Exemplo Visual de Inserção

**Estado Inicial:**
- Profundidade Global = 1
- Capacidade dos Buckets = 2

```
Diretório:          Buckets:
[0] ─────────────→  B0 [depth=1, entries: (0,v0), (4,v4)]
[1] ─────────────→  B1 [depth=1, entries: (1,v1)]
```

**Inserindo chave 8 (hash = 0 em 1 bit):**
- B0 está cheio!
- B0.depth == global_depth → Duplicar diretório

```
Diretório:          Buckets:
[00] ───────────→   B0 [depth=2, entries: (0,v0), (4,v4)]
[01] ───────────→   B1 [depth=1, entries: (1,v1)]
[10] ───────────→   B2 [depth=2, entries: (8,v8)]
[11] ───────────→   B1 [depth=1, entries: (1,v1)]
```

### Busca

```
1. Calcular hash(chave) usando 'd' bits
2. Acessar diretório[hash]
3. Buscar a chave no bucket
4. Retornar valor (se encontrado) ou null
```

**Complexidade:** O(1) para acessar o bucket + O(n) para buscar no bucket (n = capacidade)

### Remoção

```
1. Localizar o bucket usando hash(chave)
2. Remover a chave do bucket
3. Tentar MERGE:
   a. Verificar se todos buckets têm profundidade < global
   b. Se sim: reduzir profundidade_global e encolher diretório
```

---

## 🎯 Função Hash

Nossa implementação usa **hashing modular** com bits menos significativos:

```java
hash(key, depth) = key & ((1 << depth) - 1)
```

**Exemplos:**
- hash(5, 2) = 5 & 3 = 0101 & 0011 = 0001 = 1
- hash(13, 3) = 13 & 7 = 1101 & 0111 = 0101 = 5

**Por que usar os bits menos significativos?**
- Distribuição mais uniforme
- Funciona bem com números sequenciais
- Split local afeta apenas um bit

---

## 📊 Análise de Complexidade

| Operação | Caso Médio | Pior Caso |
|----------|-----------|-----------|
| Busca    | O(1)      | O(n)      |
| Inserção | O(1)      | O(n log n) com splits |
| Remoção  | O(1)      | O(n)      |

**Onde:**
- n = número de elementos em um bucket
- Na prática, n é pequeno (2-4 elementos)

---

## 💡 Vantagens

1. **Crescimento Dinâmico**: Não precisa redimensionar toda a estrutura
2. **Sem Reorganização Global**: Apenas buckets locais são afetados
3. **Busca Rápida**: Acesso direto via diretório
4. **Boa Utilização de Espaço**: Buckets compartilhados quando possível

---

## ⚠️ Desvantagens

1. **Overhead do Diretório**: Pode crescer exponencialmente (2^d)
2. **Splits Encadeados**: Inserção pode causar múltiplos splits
3. **Não Mantém Ordem**: Não permite range queries eficientes

---

## 🧪 Exemplo Passo a Passo

### Configuração
- Capacidade do Bucket: 2
- Profundidade Global Inicial: 0

### Sequência de Inserções

#### **Inserir 1**
```
Global Depth: 0
Directory: [0] → B0
B0: [depth=0, entries=(1,"v1")]
```

#### **Inserir 2**
```
Global Depth: 0
Directory: [0] → B0
B0: [depth=0, entries=(1,"v1"), (2,"v2")]  ← CHEIO
```

#### **Inserir 3** (causa split!)
```
B0 está cheio!
depth_local(0) == global_depth(0) → Expandir diretório

Global Depth: 1
Directory: 
  [0] → B0 [depth=1, entries=(2,"v2")]
  [1] → B1 [depth=1, entries=(1,"v1"), (3,"v3")]
```

#### **Inserir 4**
```
hash(4, 1) = 0 → B0

Global Depth: 1
Directory: 
  [0] → B0 [depth=1, entries=(2,"v2"), (4,"v4")]  ← CHEIO
  [1] → B1 [depth=1, entries=(1,"v1"), (3,"v3")]  ← CHEIO
```

#### **Inserir 5** (outro split!)
```
hash(5, 1) = 1 → B1 está cheio!
depth_local(1) == global_depth(1) → Expandir diretório

Global Depth: 2
Directory: 
  [00] → B0 [depth=1, entries=(2,"v2"), (4,"v4")]
  [01] → B2 [depth=2, entries=(1,"v1"), (5,"v5")]
  [10] → B0 [depth=1, entries=(2,"v2"), (4,"v4")]
  [11] → B3 [depth=2, entries=(3,"v3")]
```

---

## 🔍 Debugging e Visualização

A classe `ExtendibleHash` fornece o método `display()` que mostra:

1. **Profundidade Global**
2. **Diretório Completo**: Todos os índices e para onde apontam
3. **Buckets Únicos**: Lista de buckets reais (sem duplicatas)

Exemplo de saída:
```
========== HASH EXTENSÍVEL ==========
Profundidade Global: 2
Capacidade dos Buckets: 2
Tamanho do Diretório: 4

--- DIRETÓRIO ---
[00] (0) -> Bucket (depth=2, size=2/2) [(4, valor4), (8, valor8)]
[01] (1) -> Bucket (depth=2, size=1/2) [(1, valor1)]
[10] (2) -> Bucket (depth=2, size=2/2) [(4, valor4), (8, valor8)]
[11] (3) -> Bucket (depth=2, size=1/2) [(1, valor1)]

--- BUCKETS ÚNICOS ---
Bucket 0: depth=2, entries=[(4, valor4), (8, valor8)]
Bucket 1: depth=2, entries=[(1, valor1)]
=====================================
```

---

## 📖 Referências

1. **Database Management Systems** - Ramakrishnan & Gehrke
2. **Database System Concepts** - Silberschatz, Korth & Sudarshan
3. Material da disciplina CSGBD - Profa. Lívia Almada

---

## 💻 Estrutura do Código

### Arquivos

```
src/hash/
├── Bucket.java          # Classe que representa um bucket
├── ExtendibleHash.java  # Implementação principal
└── Main.java           # Demonstrações e testes
```

### Principais Métodos

**ExtendibleHash.java:**
- `insert(int key, String value)` - Insere uma chave
- `search(int key)` - Busca uma chave
- `remove(int key)` - Remove uma chave
- `display()` - Visualiza a estrutura
- `split(int index, int key, String value)` - Divide um bucket
- `expandDirectory()` - Duplica o diretório

**Bucket.java:**
- `insert(int key, String value)` - Adiciona entrada
- `search(int key)` - Busca no bucket
- `remove(int key)` - Remove do bucket
- `isFull()` - Verifica se está cheio
- `getLocalDepth()` - Retorna profundidade local

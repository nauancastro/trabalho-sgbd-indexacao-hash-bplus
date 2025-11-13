# Árvore B+ - Trabalho CSGBD

## Informações

**Disciplina:** Construção de Sistemas de Gerência de Bancos de Dados  
**Professora:** Lívia Almada  
**UFC - 2025**

---

## Como Executar
```bash
# Compilar
javac src/bPlusTree/*.java

# Executar testes
java src.bPlusTree.BPlusTreeTest
```

---

## Funcionalidades

- Inserção de chaves
- Busca de chaves
- Remoção de chaves
- Splits automáticos
- Testes com ordem aleatória (1-50)

---

## Arquivos

- `Node.java` - Classe abstrata base
- `LeafNode.java` - Nós folha
- `InternalNode.java` - Nós internos
- `BPlusTree.java` - Classe principal
- `BPlusTreeTest.java` - Testes completos
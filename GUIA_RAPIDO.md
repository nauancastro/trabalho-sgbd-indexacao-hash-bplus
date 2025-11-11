# Guia Rápido de Uso - Hash Extensível

## 🚀 Como Começar

### 1. Verificar Instalação do Java

Abra o PowerShell e execute:
```powershell
java -version
javac -version
```

Se aparecer um erro, você precisa instalar o Java JDK 17 ou superior.

### 2. Navegar até o Projeto

```powershell
cd "c:\Users\Rabelo\Desktop\TRABALHO_CSGBD\trabalho-sgbd-indexacao-hash-bplus"
```

### 3. Compilar

**Opção 1: Usando o script (recomendado)**
```powershell
.\compilar.bat
```

**Opção 2: Manualmente**
```powershell
javac -d bin src/hash/*.java
```

### 4. Executar

**Opção 1: Usando o script (recomendado)**
```powershell
.\executar.bat
```

**Opção 2: Manualmente**
```powershell
java -cp bin hash.Main
```

---

## 📋 Opções do Menu Principal

Quando você executar o programa, verá:

```
===========================================
  HASH EXTENSÍVEL - DEMONSTRAÇÃO
===========================================

--- MENU PRINCIPAL ---
1. Demonstração Automática
2. Modo Interativo
3. Teste com Dados Aleatórios
0. Sair

Escolha uma opção:
```

### Opção 1: Demonstração Automática
- Executa uma sequência predefinida de inserções e remoções
- Mostra passo a passo o comportamento do hash
- **Recomendado para primeira visualização**

### Opção 2: Modo Interativo
- Você controla manualmente cada operação
- Pode inserir, buscar, remover e visualizar
- **Ideal para experimentar e entender**

### Opção 3: Teste com Dados Aleatórios
- Gera inserções automáticas com números aleatórios
- Você define quantidade e capacidade
- **Bom para testes de stress**

---

## 💻 Executar Testes Unitários

Para executar os testes automatizados:

```powershell
# Compilar (se ainda não compilou)
javac -d bin src/hash/*.java

# Executar testes
java -cp bin hash.TestesHash
```

Os testes verificam:
- ✓ Inserções básicas
- ✓ Splits (divisão de buckets)
- ✓ Buscas
- ✓ Remoções
- ✓ Atualizações
- ✓ Sequência completa (1-50)

---

## 📖 Exemplo de Uso Programático

Se você quiser usar o hash em seu próprio código:

```java
import hash.ExtendibleHash;

public class MeuTeste {
    public static void main(String[] args) {
        // Criar hash com capacidade 2
        ExtendibleHash hash = new ExtendibleHash(2);
        
        // Inserir dados
        hash.insert(10, "Dez");
        hash.insert(20, "Vinte");
        hash.insert(30, "Trinta");
        
        // Buscar
        String valor = hash.search(20);
        System.out.println(valor); // Output: Vinte
        
        // Remover
        hash.remove(10);
        
        // Visualizar estrutura
        hash.display();
    }
}
```

Compilar e executar:
```powershell
javac -d bin src/hash/*.java MeuTeste.java
java -cp bin MeuTeste
```

---

## 🎯 Dicas para Apresentação

### Para demonstrar o funcionamento:

1. **Inicie com capacidade pequena (2)**
   - Facilita visualizar os splits
   - Mostra melhor o crescimento dinâmico

2. **Use a demonstração automática primeiro**
   - Mostra o comportamento padrão
   - Explique cada split que acontece

3. **Depois use modo interativo**
   - Insira números específicos
   - Mostre como a função hash funciona
   - Explique a profundidade global vs local

4. **Mostre os testes**
   - Execute `TestesHash.java`
   - Valida que tudo funciona corretamente

### Pontos importantes para explicar:

- **Profundidade Global**: Tamanho do diretório (2^d)
- **Profundidade Local**: Bits usados em cada bucket
- **Split**: O que acontece quando bucket fica cheio
- **Diretório**: Como múltiplos índices podem apontar para mesmo bucket

---

## 📊 Interpretando a Saída

Quando você executa `display()`, você vê:

```
========== HASH EXTENSÍVEL ==========
Profundidade Global: 2          ← Diretório tem 2^2 = 4 entradas
Capacidade dos Buckets: 2       ← Cada bucket guarda até 2 pares
Tamanho do Diretório: 4         ← Confirmação

--- DIRETÓRIO ---
[00] (0) -> Bucket (depth=2, size=2/2) [(4, valor4), (8, valor8)]
 ↑    ↑             ↑         ↑                    ↑
 │    │             │         │                    └─ Entradas
 │    │             │         └─ Tamanho atual/capacidade
 │    │             └─ Profundidade local deste bucket
 │    └─ Índice decimal
 └─ Índice binário (usando global_depth bits)

--- BUCKETS ÚNICOS ---
Bucket 0: depth=2, entries=[(4, valor4), (8, valor8)]
         ← Lista apenas buckets reais (sem duplicatas no diretório)
```

---

## ❓ Resolução de Problemas

### Erro: "javac is not recognized"
- **Problema**: Java não está no PATH
- **Solução**: 
  1. Instale Java JDK 17+
  2. Adicione ao PATH ou use caminho completo:
     ```powershell
     "C:\Program Files\Java\jdk-17\bin\javac.exe" -d bin src/hash/*.java
     ```

### Erro: "cannot find symbol"
- **Problema**: Arquivo não foi compilado
- **Solução**: Compile todos os arquivos:
  ```powershell
  javac -d bin src/hash/*.java
  ```

### Erro: "NoClassDefFoundError"
- **Problema**: Executando do diretório errado
- **Solução**: Execute com `-cp bin` da raiz do projeto

---

## 📦 Estrutura de Arquivos

```
trabalho-sgbd-indexacao-hash-bplus/
├── src/hash/
│   ├── Bucket.java          ← Estrutura do bucket
│   ├── ExtendibleHash.java  ← Implementação principal
│   ├── Main.java            ← Programa demo interativo
│   └── TestesHash.java      ← Testes automatizados
├── bin/                     ← Arquivos .class (gerados)
├── compilar.bat             ← Script de compilação
├── executar.bat             ← Script de execução
├── README.md                ← Documentação principal
├── HASH_EXTENSIVEL.md       ← Teoria e conceitos
└── GUIA_RAPIDO.md           ← Este arquivo
```

---

## 🎓 Para o Relatório

Não esqueça de incluir:

1. **Prints de Execução**
   - Demonstração automática
   - Exemplos de splits
   - Antes e depois de remoções

2. **Explicação Teórica**
   - Como funciona o hash extensível
   - Quando acontece split
   - Vantagens e desvantagens

3. **Análise de Complexidade**
   - Inserção: O(1) médio
   - Busca: O(1) médio
   - Remoção: O(1) médio

4. **Testes Realizados**
   - Mostrar resultados dos TestesHash
   - Explicar casos de teste

---

## 📞 Recursos Adicionais

- **Documentação Técnica**: `HASH_EXTENSIVEL.md`
- **README Completo**: `README.md`
- **Código-Fonte**: `src/hash/`

Boa sorte na apresentação! 🚀

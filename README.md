# Sistema de Biblioteca Digital

## 📚 Sobre o Projeto

Este projeto demonstra a implementação correta dos métodos `equals()` e `hashCode()` em Java através de um sistema de biblioteca digital. O exemplo mostra como gerenciar livros únicos baseados no ISBN, garantindo que diferentes edições do mesmo livro sejam tratadas adequadamente.

## 🎯 Objetivo

Demonstrar as melhores práticas para:
- Override correto do método `equals()`
- Implementação obrigatória do método `hashCode()`
- Uso adequado em coleções Java (HashSet, HashMap)
- Evitar armadilhas comuns que quebram o contrato equals-hashCode

## 🚀 Como Executar

### Pré-requisitos
- Java 8 ou superior
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

### Compilação e Execução
```bash
# Compilar
javac *.java

# Executar exemplo principal
java SistemaBiblioteca

# Executar demonstração de problemas
java -cp . ProblemasBiblioteca
```

## 🔧 Funcionalidades

### Classe `Livro`
- **Atributos**: ISBN, título, autor, preço, edição
- **Igualdade**: Baseada exclusivamente no ISBN
- **Hash Code**: Consistente com equals()
- **Thread-Safe**: Implementação segura para uso concorrente

### Recursos Demonstrados
- ✅ Verificação de igualdade por ISBN
- ✅ Funcionamento correto com HashSet
- ✅ Funcionamento correto com HashMap
- ✅ Busca eficiente por ISBN
- ❌ Problemas com implementação incorreta

## 📖 Conceitos Abordados

### Contrato do equals()
1. **Reflexivo**: `x.equals(x)` sempre retorna `true`
2. **Simétrico**: Se `x.equals(y)` é `true`, então `y.equals(x)` também é `true`
3. **Transitivo**: Se `x.equals(y)` e `y.equals(z)` são `true`, então `x.equals(z)` é `true`
4. **Consistente**: Múltiplas invocações retornam o mesmo resultado
5. **Null-safe**: `x.equals(null)` sempre retorna `false`

### Implementação Segura
```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;                    // Reflexividade
    if (obj == null) return false;                   // Null-safe
    if (getClass() != obj.getClass()) return false;  // Tipo correto
    
    Livro outro = (Livro) obj;
    return Objects.equals(isbn, outro.isbn);         // Comparação segura
}

@Override
public int hashCode() {
    return Objects.hash(isbn);                       // Consistente com equals
}
```

## ⚠️ Problemas Comuns

### ❌ Implementar apenas equals()
```java
// ERRO: Apenas equals() sem hashCode()
@Override
public boolean equals(Object obj) { ... }
// hashCode() não implementado - PROBLEMA!
```

**Consequências:**
- HashSet pode conter "duplicatas"
- HashMap pode não encontrar chaves existentes
- Performance degradada
- Comportamento inconsistente

### ❌ Hash codes diferentes para objetos iguais
```java
Livro livroA = new Livro("123-456", "Título", "Autor", 25.0, 1);
Livro livroB = new Livro("123-456", "Título", "Autor", 30.0, 2);

livroA.equals(livroB);    // true (mesmo ISBN)
livroA.hashCode();        // Ex: 12345
livroB.hashCode();        // Ex: 67890 - PROBLEMA!
```

## 🧪 Casos de Teste

### Teste de Igualdade
```java
Livro livro1 = new Livro("978-0321356680", "Effective Java", "Joshua Bloch", 45.99, 2);
Livro livro2 = new Livro("978-0321356680", "Effective Java", "Joshua Bloch", 52.50, 3);

assert livro1.equals(livro2);              // true - mesmo ISBN
assert livro1.hashCode() == livro2.hashCode(); // true - hash consistente
```

### Teste com Coleções
```java
Set<Livro> acervo = new HashSet<>();
acervo.add(livro1);
acervo.add(livro2);  // Não adiciona - é duplicata

assert acervo.size() == 1;  // Apenas um livro único
```

### Teste de Busca
```java
Map<Livro, Integer> estoque = new HashMap<>();
estoque.put(livro1, 10);
estoque.put(livro2, 5);  // Substitui o valor anterior

assert estoque.get(livro1) == 5;  // Valor atualizado
assert estoque.size() == 1;       // Uma única entrada
```

## 📊 Resultados dos Testes

### Implementação Correta
```
=== Testando Igualdade ===
livro1.equals(livro2): true
livro1.equals(livro3): false

=== Testando com HashSet ===
Total de livros únicos no acervo: 2

=== Testando com HashMap ===
Itens diferentes no estoque: 2
Quantidade do livro1: 5

=== Verificando HashCodes ===
HashCode livro1: 1234567
HashCode livro2: 1234567  // Mesmo hash!
HashCode livro3: 7654321  // Hash diferente
```

### Implementação Problemática
```
=== DEMONSTRAÇÃO DOS PROBLEMAS ===
livroA.equals(livroB): true
HashCode livroA: 1234567
HashCode livroB: 9876543  // PROBLEMA: Hash diferente!

Livros na biblioteca (deveria ser 1): 2  // DUPLICATA!
Biblioteca contém livroA: true
Biblioteca contém livroB: false          // BUSCA QUEBRADA!
```

## 📚 Referências

- [Oracle Java Documentation - Object.equals()](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-)
- [Oracle Java Documentation - Object.hashCode()](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode--)
- [Effective Java by Joshua Bloch](https://www.oreilly.com/library/view/effective-java/9780134686097/)
- [Java Collections Framework](https://docs.oracle.com/javase/8/docs/technotes/guides/collections/)

## 🏷️ Tags

`java` `equals` `hashcode` `collections` `hashset` `hashmap` `best-practices` `tutorial` `biblioteca` `isbn`

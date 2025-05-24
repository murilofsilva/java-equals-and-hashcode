import java.util.*;

public class Livro {
    private String isbn;
    private String titulo;
    private String autor;
    private double preco;
    private int edicao;

    public Livro(String isbn, String titulo, String autor, double preco, int edicao) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
        this.edicao = edicao;
    }

    // Implementação correta do equals()
    @Override
    public boolean equals(Object obj) {
        // Primeiro: verificar se é a mesma instância
        if (this == obj) {
            return true;
        }

        // Segundo: verificar se não é null
        if (obj == null) {
            return false;
        }

        // Terceiro: verificar se são da mesma classe
        if (getClass() != obj.getClass()) {
            return false;
        }

        // Quarto: fazer casting e comparar atributos relevantes
        Livro outroLivro = (Livro) obj;

        // Para livros, consideramos iguais se têm o mesmo ISBN
        return Objects.equals(isbn, outroLivro.isbn);
    }

    // Implementação obrigatória do hashCode()
    @Override
    public int hashCode() {
        // Usar os mesmos campos que utilizamos no equals()
        return Objects.hash(isbn);
    }

    // ToString para visualização melhor
    @Override
    public String toString() {
        return "Livro{isbn='" + isbn + "', titulo='" + titulo + "', autor='" + autor +
                "', preco=" + preco + ", edicao=" + edicao + "}";
    }

    // Métodos getters
    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public double getPreco() { return preco; }
    public int getEdicao() { return edicao; }
}

// Classe para testar o funcionamento
class SistemaBiblioteca {
    public static void main(String[] args) {
        // Criando diferentes edições do mesmo livro
        Livro livro1 = new Livro("978-0321356680", "Effective Java", "Joshua Bloch", 45.99, 2);
        Livro livro2 = new Livro("978-0321356680", "Effective Java", "Joshua Bloch", 52.50, 3); // Mesmo ISBN, edição diferente
        Livro livro3 = new Livro("978-0134685991", "Effective Java", "Joshua Bloch", 48.00, 3); // ISBN diferente

        System.out.println("=== Testando Igualdade ===");
        System.out.println("livro1.equals(livro2): " + livro1.equals(livro2)); // true - mesmo ISBN
        System.out.println("livro1.equals(livro3): " + livro1.equals(livro3)); // false - ISBNs diferentes

        System.out.println("\n=== Testando com HashSet ===");
        Set<Livro> acervo = new HashSet<>();
        acervo.add(livro1);
        acervo.add(livro2); // Não será adicionado - considerado duplicata
        acervo.add(livro3);

        System.out.println("Total de livros únicos no acervo: " + acervo.size()); // 2, não 3
        System.out.println("Livros no acervo:");
        for (Livro livro : acervo) {
            System.out.println("  " + livro);
        }

        System.out.println("\n=== Testando com HashMap ===");
        Map<Livro, Integer> estoque = new HashMap<>();
        estoque.put(livro1, 10);
        estoque.put(livro2, 5); // Substitui a quantidade do livro1 (mesmo ISBN)
        estoque.put(livro3, 8);

        System.out.println("Itens diferentes no estoque: " + estoque.size()); // 2, não 3
        System.out.println("Quantidade do livro1: " + estoque.get(livro1)); // 5 (foi substituído)

        System.out.println("\n=== Verificando HashCodes ===");
        System.out.println("HashCode livro1: " + livro1.hashCode());
        System.out.println("HashCode livro2: " + livro2.hashCode()); // Igual ao livro1
        System.out.println("HashCode livro3: " + livro3.hashCode()); // Diferente

        // Demonstrando busca eficiente
        System.out.println("\n=== Busca no Acervo ===");
        Livro livroBusca = new Livro("978-0321356680", "", "", 0.0, 0);
        boolean encontrado = acervo.contains(livroBusca);
        System.out.println("Livro encontrado apenas pelo ISBN: " + encontrado); // true
    }
}

// Exemplo de implementação problemática
class LivroProblematico {
    private String isbn;
    private String titulo;

    public LivroProblematico(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
    }

    // ERRO: Implementa equals() mas esquece do hashCode()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LivroProblematico that = (LivroProblematico) obj;
        return Objects.equals(isbn, that.isbn);
    }

    // hashCode() não foi implementado - GRANDE ERRO!

    @Override
    public String toString() {
        return "LivroProblematico{isbn='" + isbn + "', titulo='" + titulo + "'}";
    }
}

// Demonstração dos problemas causados
class ProblemasBiblioteca {
    public static void mostrarProblemas() {
        System.out.println("\n=== DEMONSTRAÇÃO DOS PROBLEMAS ===");

        LivroProblematico livroA = new LivroProblematico("123-456", "Livro A");
        LivroProblematico livroB = new LivroProblematico("123-456", "Livro A Revisado");

        System.out.println("livroA.equals(livroB): " + livroA.equals(livroB)); // true
        System.out.println("HashCode livroA: " + livroA.hashCode());
        System.out.println("HashCode livroB: " + livroB.hashCode()); // Provavelmente diferente!

        // Problema com coleções
        Set<LivroProblematico> biblioteca = new HashSet<>();
        biblioteca.add(livroA);
        biblioteca.add(livroB);

        System.out.println("Livros na biblioteca (deveria ser 1): " + biblioteca.size()); // Pode ser 2!
        System.out.println("Biblioteca contém livroA: " + biblioteca.contains(livroA)); // true
        System.out.println("Biblioteca contém livroB: " + biblioteca.contains(livroB)); // Pode ser false!

        // Problema com HashMap
        Map<LivroProblematico, String> localizacao = new HashMap<>();
        localizacao.put(livroA, "Estante A");
        localizacao.put(livroB, "Estante B"); // Pode não substituir como esperado

        System.out.println("Localização do livroA: " + localizacao.get(livroA));
        System.out.println("Localização do livroB: " + localizacao.get(livroB));
        System.out.println("Tamanho do mapa: " + localizacao.size());
    }
}
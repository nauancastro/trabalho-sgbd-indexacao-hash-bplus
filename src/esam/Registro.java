package esam;

/**
 * Representa um registro (par chave-valor) armazenado na ESAM.
 * Chaves inteiras e valores em String para manter compatibilidade
 * com os exemplos existentes no projeto.
 */
public class Registro implements Comparable<Registro> {
    private final int chave;           // chave primária
    private String valor;              // valor associado

    public Registro(int chave, String valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public int getChave() {
        return chave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String novoValor) {
        this.valor = novoValor;
    }

    @Override
    public int compareTo(Registro o) {
        return Integer.compare(this.chave, o.chave);
    }

    @Override
    public String toString() {
        return "(" + chave + ", " + valor + ")";
    }
}
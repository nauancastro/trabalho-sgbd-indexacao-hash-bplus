package esam;

import java.util.ArrayList;
import java.util.List;

/**
 * Índice esparso de 1 nível.
 * Cada entrada referencia a menor chave de uma página e a própria página.
 * Fornece localização rápida da página candidata para uma chave.
 */
public class IndiceEsparso {
    /** Entrada de índice: menor chave da página e referência para a página. */
    public static class Entrada {
        public final int chaveMin;
        public final Pagina pagina;

        public Entrada(int chaveMin, Pagina pagina) {
            this.chaveMin = chaveMin;
            this.pagina = pagina;
        }

        @Override
        public String toString() {
            return "[" + chaveMin + ":pag=" + pagina.tamanho() + "]";
        }
    }

    private List<Entrada> entradas;   // ordenadas por chaveMin

    public IndiceEsparso() {
        this.entradas = new ArrayList<>();
    }

    /**
     * Reconstrói o índice a partir da lista de páginas, em ordem de chave mínima.
     */
    public void construir(List<Pagina> paginas) {
        List<Entrada> novas = new ArrayList<>();
        for (Pagina p : paginas) {
            Integer min = p.chaveMinima();
            if (min != null) {
                novas.add(new Entrada(min, p));
            }
        }
        // ordena por chave mínima
        novas.sort((a, b) -> Integer.compare(a.chaveMin, b.chaveMin));
        this.entradas = novas;
    }

    /**
     * Localiza a página candidata para uma chave.
     * Regra: última entrada com chaveMin <= chave.
     * Se nenhuma atender, retorna null.
     */
    public Pagina localizarPagina(int chave) {
        if (entradas.isEmpty()) return null;
        int pos = -1;
        for (int i = 0; i < entradas.size(); i++) {
            if (entradas.get(i).chaveMin <= chave) {
                pos = i;
            } else {
                break;
            }
        }
        if (pos >= 0) {
            return entradas.get(pos).pagina;
        }
        return null;
    }

    /**
     * Retorna cópia das entradas para diagnóstico.
     */
    public List<Entrada> getEntradas() {
        return new ArrayList<>(entradas);
    }
}
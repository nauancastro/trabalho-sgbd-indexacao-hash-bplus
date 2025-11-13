package esam;

import java.util.ArrayList;
import java.util.List;

/**
 * ESAM (Estrutura Sequencial com Acesso por Índice Esparso)
 * Objetivo: armazenar registros ordenados em páginas e acelerar buscas via índice esparso.
 * Nesta primeira parte: inserção sem split de página; índice reconstruído quando necessário.
 */
public class ESAM {
    private final int capacidadePagina;        // capacidade máxima de cada página
    private final List<Pagina> paginas;        // páginas de dados em ordem por chave
    private final IndiceEsparso indice;        // índice esparso de 1 nível

    public ESAM(int capacidadePagina) {
        this.capacidadePagina = capacidadePagina;
        this.paginas = new ArrayList<>();
        this.indice = new IndiceEsparso();
        // cria primeira página vazia para iniciar
        this.paginas.add(new Pagina(capacidadePagina));
        atualizarIndice();
    }

    /**
     * Insere um par chave-valor.
     * Etapa 1: escolhe página candidata e tenta inserir; não realiza split ainda.
     * Retorna true se inseriu/atualizou; false se a página estava cheia.
     */
    public boolean inserir(int chave, String valor) {
        Pagina pagina = indice.localizarPagina(chave);
        if (pagina == null) {
            pagina = paginas.get(0);
        }
        boolean ok = pagina.inserir(chave, valor);
        if (ok) {
            atualizarIndice();
            return true;
        }

        // página cheia: realiza split e tenta novamente
        splitPagina(pagina);

        Pagina destino = indice.localizarPagina(chave);
        if (destino == null) destino = paginas.get(0);
        boolean ok2 = destino.inserir(chave, valor);
        if (ok2) {
            atualizarIndice();
        }
        return ok2;
    }

    /**
     * Busca o valor pela chave.
     */
    public String buscar(int chave) {
        Pagina pagina = indice.localizarPagina(chave);
        if (pagina == null) return null;
        return pagina.buscar(chave);
    }

    /**
     * Remove um registro pela chave.
     * Nesta etapa remove apenas dentro da página; sem merge.
     */
    public boolean remover(int chave) {
        Pagina pagina = indice.localizarPagina(chave);
        if (pagina == null) return false;
        boolean ok = pagina.remover(chave);
        if (ok) {
            atualizarIndice();
        }
        return ok;
    }

    /**
     * Atualiza o índice esparso em função das páginas atuais.
     */
    private void atualizarIndice() {
        indice.construir(paginas);
    }

    /**
     * Divide a página cheia em duas novas páginas e atualiza a lista e o índice.
     * Estratégia: particiona os registros ao meio mantendo ordenação.
     */
    private void splitPagina(Pagina paginaCheia) {
        int pos = paginas.indexOf(paginaCheia);
        if (pos < 0) return; // página não encontrada (defensivo)

        List<Registro> regs = paginaCheia.getRegistros();
        int mid = regs.size() / 2;

        Pagina pEsq = new Pagina(capacidadePagina);
        Pagina pDir = new Pagina(capacidadePagina);

        for (int i = 0; i < mid; i++) {
            Registro r = regs.get(i);
            pEsq.inserir(r.getChave(), r.getValor());
        }
        for (int i = mid; i < regs.size(); i++) {
            Registro r = regs.get(i);
            pDir.inserir(r.getChave(), r.getValor());
        }

        paginas.remove(pos);
        paginas.add(pos, pEsq);
        paginas.add(pos + 1, pDir);
        atualizarIndice();
    }

    /**
     * Exibe uma visão resumida da estrutura para diagnóstico.
     */
    public void exibir() {
        System.out.println("\n========== ESAM ==========");
        System.out.println("Capacidade por página: " + capacidadePagina);
        System.out.println("Páginas: " + paginas.size());
        for (int i = 0; i < paginas.size(); i++) {
            Pagina p = paginas.get(i);
            System.out.printf("Pagina %d: tam=%d, min=%s, max=%s, registros=%s\n",
                    i,
                    p.tamanho(),
                    String.valueOf(p.chaveMinima()),
                    String.valueOf(p.chaveMaxima()),
                    p.getRegistros());
        }
        System.out.println("Índice: " + indice.getEntradas());
        System.out.println("==========================\n");
    }
}
package esam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Página de dados da ESAM.
 * Mantém registros ordenados por chave para acesso sequencial eficiente.
 * A capacidade limita o número de registros antes de exigir split (implementado em etapas futuras).
 */
public class Pagina {
    private final int capacidade;                 // número máximo de registros
    private final List<Registro> registros;       // registros ordenados por chave

    public Pagina(int capacidade) {
        this.capacidade = capacidade;
        this.registros = new ArrayList<>();
    }

    /**
     * Insere ou atualiza um registro mantendo a ordenação.
     * Retorna true se inseriu/atualizou; false se a página está cheia e não inseriu.
     */
    public boolean inserir(int chave, String valor) {
        int idx = localizarPosicao(chave);

        if (idx >= 0) {
            // chave já existe: atualiza valor
            registros.get(idx).setValor(valor);
            return true;
        }

        // posição de inserção (método retorna -(inserção) - 1)
        int insertPos = -idx - 1;

        if (isCheia()) {
            return false; // sem split nesta etapa
        }

        registros.add(insertPos, new Registro(chave, valor));
        return true;
    }

    /**
     * Busca pelo valor de uma chave nesta página.
     * Retorna o valor ou null se não encontrado.
     */
    public String buscar(int chave) {
        int idx = localizarPosicao(chave);
        if (idx >= 0) {
            return registros.get(idx).getValor();
        }
        return null;
    }

    /**
     * Remove um registro pela chave. Retorna true se removeu; false caso não exista.
     */
    public boolean remover(int chave) {
        int idx = localizarPosicao(chave);
        if (idx >= 0) {
            registros.remove(idx);
            return true;
        }
        return false;
    }

    /**
     * Retorna a menor chave contida (para uso no índice esparso).
     */
    public Integer chaveMinima() {
        return registros.isEmpty() ? null : registros.get(0).getChave();
    }

    /**
     * Retorna a maior chave contida (auxiliar para diagnósticos).
     */
    public Integer chaveMaxima() {
        return registros.isEmpty() ? null : registros.get(registros.size() - 1).getChave();
    }

    /**
     * Informa se a página atingiu a capacidade.
     */
    public boolean isCheia() {
        return registros.size() >= capacidade;
    }

    /**
     * Retorna quantidade de registros atuais.
     */
    public int tamanho() {
        return registros.size();
    }

    /**
     * Retorna uma cópia dos registros para inspeção.
     */
    public List<Registro> getRegistros() {
        return new ArrayList<>(registros);
    }

    /**
     * Localiza a posição da chave via busca binária.
     * Se encontrada, retorna índice >=0; se não, retorna -(ponto de inserção) - 1.
     */
    private int localizarPosicao(int chave) {
        // usa Collections.binarySearch com lista ordenada
        return Collections.binarySearch(registros, new Registro(chave, ""));
    }

    @Override
    public String toString() {
        return "Pagina{cap=" + capacidade + ", reg=" + registros + "}";
    }
}
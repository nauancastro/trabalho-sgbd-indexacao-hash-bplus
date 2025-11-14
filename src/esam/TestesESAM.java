package esam;

import java.util.*;

public class TestesESAM {
    public static void main(String[] args) {
        System.out.println("=== Testes ESAM ===");
        boolean ok1 = testeBasico();
        boolean ok2 = testeSplit();
        boolean ok3 = testeRemocao();
        boolean ok4 = testeAleatorio();

        System.out.println("Resultados:");
        System.out.println("Basico: " + (ok1 ? "OK" : "FALHA"));
        System.out.println("Split: " + (ok2 ? "OK" : "FALHA"));
        System.out.println("Remocao: " + (ok3 ? "OK" : "FALHA"));
        System.out.println("Aleatorio: " + (ok4 ? "OK" : "FALHA"));
    }

    private static boolean testeBasico() {
        ESAM esam = new ESAM(4);
        int[] keys = {23, 5, 10, 2, 7, 12};
        String[] vals = {"cccc", "aaa", "bbb", "zz", "dd", "ee"};
        for (int i = 0; i < keys.length; i++) esam.inserir(keys[i], vals[i]);

        System.out.println("Inserções: " + Arrays.toString(keys));
        esam.exibir();

        int encontrados = 0;
        for (int i = 0; i < keys.length; i++) {
            String v = esam.buscar(keys[i]);
            if (vals[i].equals(v)) encontrados++;
        }
        String n = esam.buscar(99);
        boolean ok = encontrados == keys.length && n == null;
        System.out.println("Estatísticas: encontrados=" + encontrados + "/" + keys.length + ", inexistente=null:" + (n == null));
        System.out.println();
        return ok;
    }

    private static boolean testeSplit() {
        ESAM esam = new ESAM(3);
        int[] keys = {9, 1, 5, 3, 7, 2, 8, 4, 6};
        for (int k : keys) esam.inserir(k, "v" + k);

        System.out.println("Inserções: " + Arrays.toString(keys));
        esam.exibir();

        int okCount = 0;
        for (int k : keys) {
            String v = esam.buscar(k);
            if (("v" + k).equals(v)) okCount++;
        }
        System.out.println("Estatísticas: encontrados=" + okCount + "/" + keys.length);
        System.out.println();
        return okCount == keys.length;
    }

    private static boolean testeRemocao() {
        ESAM esam = new ESAM(4);
        int[] keys = {10, 20, 30, 40, 50, 60};
        for (int k : keys) esam.inserir(k, "v" + k);

        System.out.println("Inserções: " + Arrays.toString(keys));
        esam.exibir();

        int[] remov = {20, 40, 60};
        int removidos = 0;
        for (int r : remov) if (esam.remover(r)) removidos++;

        System.out.println("Remoções: " + Arrays.toString(remov));
        esam.exibir();

        int faltantes = 0;
        for (int r : remov) if (esam.buscar(r) == null) faltantes++;

        int presentes = 0;
        for (int k : keys) {
            if (Arrays.stream(remov).noneMatch(x -> x == k)) {
                String v = esam.buscar(k);
                if (("v" + k).equals(v)) presentes++;
            }
        }
        System.out.println("Estatísticas: removidos=" + removidos + "/" + remov.length + ", faltantes=" + faltantes + "/" + remov.length + ", presentes=" + presentes + "/" + (keys.length - remov.length));
        System.out.println();
        return removidos == remov.length && faltantes == remov.length && presentes == (keys.length - remov.length);
    }

    private static boolean testeAleatorio() {
        ESAM esam = new ESAM(4);
        Random rand = new Random(123);
        Set<Integer> set = new HashSet<>();
        List<Integer> keys = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();

        while (keys.size() < 50) {
            int k = rand.nextInt(1000);
            if (set.add(k)) {
                String v = randomString(rand, 5);
                keys.add(k);
                map.put(k, v);
                esam.inserir(k, v);
            }
        }

        System.out.println("Inserções: " + keys.size() + " chaves");
        esam.exibir();

        int encontrados = 0;
        for (int k : keys) {
            String v = esam.buscar(k);
            if (map.get(k).equals(v)) encontrados++;
        }

        Collections.shuffle(keys, rand);
        List<Integer> remov = keys.subList(0, 10);
        int remOk = 0;
        for (int r : remov) if (esam.remover(r)) remOk++;

        int remNull = 0;
        for (int r : remov) if (esam.buscar(r) == null) remNull++;

        int restantesOk = 0;
        for (int i = 10; i < keys.size(); i++) {
            int k = keys.get(i);
            String v = esam.buscar(k);
            if (map.get(k).equals(v)) restantesOk++;
        }

        System.out.println("Remoções: " + remov.size() + " chaves");
        esam.exibir();

        System.out.println("Estatísticas: inseridos=50, encontrados=" + encontrados + "/50, removidos=" + remOk + "/10, removidos-null=" + remNull + "/10, restantes=" + restantesOk + "/40");
        System.out.println();
        return encontrados == 50 && remOk == 10 && remNull == 10 && restantesOk == 40;
    }

    private static String randomString(Random r, int n) {
        char[] chars = new char[n];
        for (int i = 0; i < n; i++) {
            chars[i] = (char) ('a' + r.nextInt(26));
        }
        return new String(chars);
    }
}
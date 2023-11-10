import java.util.ArrayList;
import java.util.Queue;

public class Principal {
    public static void main(String[] args) {
        ArrayList<Integer> Q = new ArrayList<>();
        ArrayList<Integer> S = new ArrayList<>();

        String[][] matriz = new String[][]{
                {"0", "2", "-1", "-1", "10",},
                {"-1", "0", "3", "-1", "7"},
                {"-1", "-1", "0", "4", "-1"},
                {"-1", "-1", "-1", "0", "-1"},
                {"-1", "-1", "8", "5", "0"}
        };

        String[][] dij = initializeSingleSource(new String[2][matriz.length], 3);

        percorre(dij);


    }

    private static String[][] initializeSingleSource(String[][] g, int verticeInic) {
        verticeInic--;
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                if (i == 0) {
                    if (j == verticeInic) {
                        g[i][j] = "0";
                    } else {
                        g[i][j] = "inf";
                    }


                } else {
                    g[i][j] = "nil";
                }
            }
        }

        return g;

    }

    private static String[][] relax(int u, int v, int w) {
        return null;
    }

    private static void percorre(String[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }


    public class Aresta {

        private int custo;
        private Vertice origem;
        private Vertice destino;

        public Aresta(int custo, Vertice origem, Vertice destino) {
            this.custo = custo;
            this.origem = origem;
            this.destino = destino;
        }

        public int getCusto() {
            return custo;
        }

        public void setCusto(int custo) {
            this.custo = custo;
        }

        public Vertice getOrigem() {
            return origem;
        }

        public void setOrigem(Vertice origem) {
            this.origem = origem;
        }

        public Vertice getDestino() {
            return destino;
        }

        public void setDestino(Vertice destino) {
            this.destino = destino;
        }
    }

    public class Vertice {
        private String id;
        private ArrayList<Aresta> arestaAdj = new ArrayList<>();

        public Vertice(String id, ArrayList<Aresta> arestaAdj) {
            this.id = id;
            this.arestaAdj = arestaAdj;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ArrayList<Aresta> getArestaAdj() {
            return arestaAdj;
        }

        public void setArestaAdj(ArrayList<Aresta> arestaAdj) {
            this.arestaAdj = arestaAdj;
        }
    }
}
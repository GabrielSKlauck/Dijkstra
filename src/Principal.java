import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Principal {
    public static void main(String[] args) {
        ArrayList<Integer> Q = new ArrayList<>();
        ArrayList<Integer> S = new ArrayList<>();
        List<Aresta> listaArestas = null;
        Grafo grafo = new Grafo();

        /*
        * INICIALIZAÇAO DOS VERTICES JA TENDO O PAI COMO NULO E A DISTANCIA COMO
        * INFINITA COMO SE FOSSE A IMPLEMENTAÇÃO DO METODO INITIALIZE-SINGLE-SOURCE
        *  POREM DIRETO NA INSTANCIAÇÃO DO VERTICE
        * */
        Vertice v1 = new Vertice("V1", null, Double.POSITIVE_INFINITY);

        Vertice v2 = new Vertice("V2", null, Double.POSITIVE_INFINITY);
        Vertice v3 = new Vertice("V3", null, Double.POSITIVE_INFINITY);
        v1.addAresta(new Aresta(9.0, v1, v2));
        v1.addAresta(new Aresta(3.0, v1, v3));
        v2.addAresta(new Aresta(1.0, v2, v3));

        grafo.addVertice(v1);
        grafo.addVertice(v2);
        grafo.addVertice(v3);





        /*String[][] matriz = new String[][]{
                {"0", "2", "-1", "-1", "10",},
                {"-1", "0", "3", "-1", "7"},
                {"-1", "-1", "0", "4", "-1"},
                {"-1", "-1", "-1", "0", "-1"},
                {"-1", "-1", "8", "5", "0"}
        };

        String[][] dij = initializeSingleSource(new String[2][matriz.length], 3);

        percorre(dij);*/


    }



    private static ArrayList<String> leArquivo(){
        String texto;
        ArrayList<String> lista = new ArrayList<>();
        try {
            texto = Files.readString(Path.of("Arquivo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        texto = texto.replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("\r", "");

        texto = texto.replaceAll("\n", " ");
        String[] valores = texto.split(" ");

        for(int i =0; i < valores.length; i++){
            lista.add(valores[i]);
        }
        return lista;

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

    public static class Grafo{
        private ArrayList<Vertice> vertices;

        public void addVertice(Vertice v){
            vertices.add(v);
        }

        public ArrayList<Vertice> getVertices() {
            return vertices;
        }

        public void setVertices(ArrayList<Vertice> vertices) {
            this.vertices = vertices;
        }
    }

    public static class Aresta {

        private Double custo;
        private Vertice origem;
        private Vertice destino;

        public Aresta(Double custo, Vertice origem, Vertice destino) {
            this.custo = custo;
            this.origem = origem;
            this.destino = destino;
        }

        public Double getCusto() {
            return custo;
        }

        public void setCusto(Double custo) {
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

    public static class Vertice {
        private String id;

        private Vertice pai;

        private Double custo;
        private ArrayList<Aresta> arestaAdj = new ArrayList<>();

        public Vertice(String id, Vertice pai, Double w) {
            this.id = id;
            this.pai = pai;
            this.custo = custo;
        }

        public void addAresta(Aresta aresta){
            this.arestaAdj.add(aresta);
        }

        public void adicionaAresta(List<Aresta> arestas){
            for(Aresta item : arestas){
                this.arestaAdj.add(item);
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Vertice getPai() {
            return pai;
        }

        public void setPai(Vertice pai) {
            this.pai = pai;
        }

        public Double getCusto() {
            return custo;
        }

        public void setCusto(Double custo) {
            this.custo = custo;
        }

        public ArrayList<Aresta> getArestaAdj() {
            return arestaAdj;
        }

        public void setArestaAdj(ArrayList<Aresta> arestaAdj) {
            this.arestaAdj = arestaAdj;
        }
    }
}
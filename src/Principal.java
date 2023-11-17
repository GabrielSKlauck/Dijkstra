import com.sun.jdi.connect.Connector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Principal {
    public static void main(String[] args) {
        List<Aresta> listaArestas = null;
        Grafo grafo = iniciaGrafo();

        System.out.println(grafo.percorre());





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



    /*
    * Faz a leitura da matriz do arquivo.txt e cria os vertices e arestas respectivas
    * do grafo
    * */
    private static Grafo iniciaGrafo(){
        String texto;
        Grafo grafo = new Grafo();
        char[] alfa = new char[]{ 'A','B','C','D','E','F','G','H','I','J','K','L',};

        try {
            texto = Files.readString(Path.of("Arquivo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Nao existe arquivo");

        }

        //Organiza o texto do arquivo
        texto = texto.replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("\r", "")
                .replaceAll("\n", " ; ");

        String textoItems = texto.replaceAll(" ", "");

        int qtdVertices = Integer.parseInt(textoItems.charAt(0) + "");
        textoItems = textoItems.replace(textoItems.substring(0,textoItems.indexOf(";") + 1), "");

        //Cria os todos os vertices
        for(int i = 0; i < qtdVertices - 1; i++){

            //Inicializa os vertices ja tendo o pai com nullo e o custo para chegar nele como infinito
            //igual ao metodo INITIALIZE-SINGLE-SOURCE porem feito na instanciaçao dos objetos de vertice
            Vertice v = new Vertice(alfa[i] + "", null, Double.POSITIVE_INFINITY);
            grafo.addVertice(v);
        }

        String[][] matrizCustos = new String[textoItems.indexOf(";")][textoItems.indexOf(";")];

        for(int i = 0; i < matrizCustos.length; i++){
            for(int j = 0; j < matrizCustos[i].length; j++){
                if(!(textoItems.charAt(j) == ';')){

                }
            }
        }



        for(int i = 0; i < textoItems.length(); i++){
            if(!textoItems.equals("I") && !textoItems.equals(("0"))){
                grafo.getVertices().get(i);
            }
        }
        return grafo;

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
        private ArrayList<Vertice> vertices = new ArrayList<>();

        public void addVertice(Vertice v){
            this.vertices.add(v);
        }

        public String percorre(){
            String vertice = "";
            for(Vertice v : this.vertices){
                vertice += v.id + "\n" + "";
            }
            return vertice;
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
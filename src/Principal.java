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

        grafo.percorreArestas();



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
                .replaceAll("\n", ";");

        String[] linhaArquivo = texto.split(";");
        int qtdVertices = Integer.parseInt(linhaArquivo[0].substring(0,1));

        String verticeInicial = linhaArquivo[1].replaceAll(" ", "").substring(0,1);

        String verticeOrigem = linhaArquivo[1].replaceAll(" ", "").substring(1,2);

        String[][] matrizCusto = new String[linhaArquivo[2].replace(" ", "").length()]
                                           [linhaArquivo[2].replace(" ", "").length()];

        //Cria e povoa vetor, colocando cada linha da matriz de custo em uma posiçao do vetor
        String[] itensLinha = new String[linhaArquivo.length - 2];
        for(int i = 0; i < itensLinha.length; i++){
            itensLinha[i] = linhaArquivo[i + 2].replace(" ", "");
        }

        //Cria matriz de custo
        for(int i = 0; i < matrizCusto.length; i++){
            String valoresDaLinha = itensLinha[i];
            for(int j = 0; j < matrizCusto[i].length; j++){
                matrizCusto[i][j] = valoresDaLinha.charAt(j) + "";
            }
        }

        percorre(matrizCusto);

        //Cria os todos os vertices
        for(int i = 0; i < qtdVertices; i++){

            //Inicializa os vertices ja tendo o pai com nullo e o custo para chegar nele como infinito
            //igual ao metodo INITIALIZE-SINGLE-SOURCE porem feito na instanciaçao dos objetos de vertice
            Vertice v = new Vertice(alfa[i] + "", null, Double.POSITIVE_INFINITY);
            grafo.addVertice(v);
        }

        grafo.percorre();

        //Cria arestas
        for(int i = 0; i < matrizCusto.length; i++){
            for(int j = 0; j < matrizCusto[i].length; j++){
                if (!matrizCusto[i][j].equals("0") && !matrizCusto[i][j].equals("I")) {
                    Aresta a = new Aresta(Double.parseDouble(matrizCusto[i][j] + ""), grafo.getById(getAlfa(i + 1))
                                                                                        , grafo.getById(getAlfa(j + 1)));
                    grafo.getById(getAlfa(i)).addAresta(a);
                }
            }
        }

        return grafo;

    }

    private static String getAlfa(int num){
        switch (num){
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            case 5:
                return "E";
            case 6:
                return "F";
            case 7:
                return "G";
            default:
                return null;
        }
    }

    private static Vertice getVerticeById(Grafo grafo, String id){
        Vertice v;
        ArrayList<Vertice> lista = grafo.getVertices();
        for(int i = 0; i < lista.size(); i++){
            v = lista.get(i);
            if(v.getId().equals(id)){
                return v;
            }
        }
        return null;
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

        public void percorreArestas(){
            for(Vertice v : this.vertices){
                for(Aresta a : v.getArestaAdj()){
                    System.out.println("Origem: " + a.getOrigem().getId() +
                            " Destino: " + a.getDestino().getId() +
                            " Custo: " + a.custo);
                }
            }
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

        public Vertice getById(String id){
            for(int i = 0; i < this.getVertices().size(); i++){
                if(this.getVertices().get(i).getId().equals(id)){
                    return this.getVertices().get(i);
                }
            }
            return null;
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
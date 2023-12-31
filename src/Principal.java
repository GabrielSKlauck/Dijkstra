// gabriel klauck e gustavo celso bozzano
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        List<Aresta> listaArestas = null;
        Grafo grafo = iniciaGrafo();
        System.out.println(dijkstra(grafo));
    }

    public static String dijkstra(Grafo grafo) {
        Vertice origem = grafo.getById(getOrigem());
        Vertice atual;
        Vertice destino = grafo.getById(getDestino());

        // lista de prioridade de vertice
        Queue<Vertice> q = new LinkedList<>();

        q.add(origem);
        ArrayList<Vertice> verticesMod; // lista com todos os vertices modificados pelo relax, porem ainda nao
                                        // alterados no gradfo principal
        String aux; // variavel que recebera a identificaçao do vertice percorrido do grafo
        Vertice menor = null; // vertice que contera o menor valor
        Vertice menorAnterior = null; // vertice que contera o segundo menor valor
        while (!q.isEmpty()) {
            atual = q.remove();
            if (atual == null) {
                continue;
            }

            verticesMod = relax(atual, origem);
            if (!verticesMod.isEmpty()) {
                // PERCORRE ARESTAS ADJ DO VERTICE PIVO RELAXADAS
                for (int i = 0; i < verticesMod.size(); i++) {
                    for (int j = 0; j < grafo.getVertices().size(); j++) {
                        aux = grafo.getVertices().get(j).getId();
                        if (aux.equals(verticesMod.get(i).getId())) {
                            grafo.getVertices().get(j).setPai(verticesMod.get(i).getPai());
                            grafo.getVertices().get(j).setCusto(verticesMod.get(i).getCusto());

                            if (menor == null) {
                                menor = verticesMod.get(i);
                            } else {
                                if (verticesMod.get(i).getCusto() < menor.getCusto()) {
                                    menorAnterior = menor;
                                    menor = verticesMod.get(i);
                                }
                            }

                        }
                    }
                }

                q.add(menor);
                q.add(menorAnterior);
                menor = null;
                menorAnterior = null;
            }
        }

        String formatado = "";
        Double custoTotal = 0.0;

        Vertice[] lista = new Vertice[grafo.getVertices().size()];
        int counter = 1;
        lista[0] = destino;
        atual = destino.getPai();
        while (true) {
            if (atual.getPai() == null) {
                break;
            }
            lista[counter] = atual;
            counter++;
            custoTotal += atual.getCusto();
            atual = atual.getPai();
        }

        for (int k = 0; k < counter; k++) {
            atual = lista[k];
            if (atual == null) {
                break;
            }
            formatado = lista[k].getId() + "->" + formatado;
        }
        formatado = origem.getId() + "->"+ formatado;
        formatado = formatado.substring(0,formatado.length()-2);
        custoTotal -= 3;
        formatado += "\nCusto = " + custoTotal;
        return formatado;
    }

    // RETORNA A LISTA DE VERTICES RELAXADOS
    private static ArrayList<Vertice> relax(Vertice atual, Vertice origem) {
        ArrayList<Vertice> verticeAlt = new ArrayList<>();
        ArrayList<Aresta> listaAdj = atual.getArestaAdj();
        double custoTotal;
        double custoAresta;
        double custoPai;
        double custoVertice;

        for (int i = 0; i < listaAdj.size(); i++) {
            custoAresta = listaAdj.get(i).custo;
            if (listaAdj.get(i).getOrigem().id.equals(origem.getId())) {
                custoPai = 0;
            } else {
                custoPai = listaAdj.get(i).getOrigem().getCusto();
            }

            if (custoPai != Double.POSITIVE_INFINITY) {
                custoTotal = custoAresta + custoPai;
            } else {
                custoTotal = custoAresta + 0;
            }

            custoVertice = listaAdj.get(i).getDestino().getCusto();
            if (custoTotal < custoVertice) {
                listaAdj.get(i).destino.custo = custoTotal;
                listaAdj.get(i).destino.pai = listaAdj.get(i).getOrigem();
                verticeAlt.add(listaAdj.get(i).getDestino());
            }
        }
        atual.setArestaAdj(listaAdj);
        return verticeAlt;
    }

    public Vertice extractMin(Vertice v) {
        ArrayList<Aresta> listaAres = v.getArestaAdj();
        listaAres.sort(null);
        return listaAres.get(0).getDestino();
    }

    /*
     * Faz a leitura da matriz do arquivo.txt e cria os vertices e arestas
     * respectivas
     * do grafo
     */
    private static Grafo iniciaGrafo() {
        String texto;
        Grafo grafo = new Grafo();
        char[] alfa = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', };

        try {
            texto = Files.readString(Path.of("Arquivo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Nao existe arquivo");

        }

        // Organiza o texto do arquivo
        texto = texto.replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("\r", "")
                .replaceAll("\n", ";");

        String[] linhaArquivo = texto.split(";");
        int qtdVertices = Integer.parseInt(linhaArquivo[0].substring(0, 1));

        String verticeInicial = linhaArquivo[1].replaceAll(" ", "").substring(0, 1);

        String verticeOrigem = linhaArquivo[1].replaceAll(" ", "").substring(1, 2);

        String[][] matrizCusto = new String[linhaArquivo[2].replace(" ", "").length()][linhaArquivo[2].replace(" ", "")
                .length()];

        // Cria e povoa vetor, colocando cada linha da matriz de custo em uma posiçao do
        // vetor
        String[] itensLinha = new String[linhaArquivo.length - 2];
        for (int i = 0; i < itensLinha.length; i++) {
            itensLinha[i] = linhaArquivo[i + 2].replace(" ", "");
        }

        // Cria matriz de custo
        for (int i = 0; i < matrizCusto.length; i++) {
            String valoresDaLinha = itensLinha[i];
            for (int j = 0; j < matrizCusto[i].length; j++) {
                matrizCusto[i][j] = valoresDaLinha.charAt(j) + "";
            }
        }

        // Cria os todos os vertices
        for (int i = 0; i < qtdVertices; i++) {

            // Inicializa os vertices ja tendo o pai com nullo e o custo para chegar nele
            // como infinito
            // igual ao metodo INITIALIZE-SINGLE-SOURCE porem feito na instanciaçao dos
            // objetos de vertice
            Vertice v = new Vertice(alfa[i] + "", null, Double.POSITIVE_INFINITY);
            grafo.addVertice(v);
        }

        // Cria arestas
        for (int i = 0; i < matrizCusto.length; i++) {
            for (int j = 0; j < matrizCusto[i].length; j++) {
                if (!matrizCusto[i][j].equals("0") && !matrizCusto[i][j].equals("I")) {
                    Aresta a = new Aresta(Double.parseDouble(matrizCusto[i][j] + ""), grafo.getById(getAlfa(i + 1)),
                            grafo.getById(getAlfa(j + 1)));
                    grafo.getById(getAlfa(i + 1)).addAresta(a);
                }
            }
        }

        return grafo;

    }

    private static String getAlfa(int num) {
        switch (num) {
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

    public static String getOrigem() {
        String texto;
        Grafo grafo = new Grafo();
        char[] alfa = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', };

        try {
            texto = Files.readString(Path.of("Arquivo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Nao existe arquivo");

        }

        // Organiza o texto do arquivo
        texto = texto.replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("\r", "")
                .replaceAll("\n", ";");

        String[] linhaArquivo = texto.split(";");

        return linhaArquivo[1].replaceAll(" ", "").substring(0, 1);
    }

    public static String getDestino() {
        String texto;
        Grafo grafo = new Grafo();
        char[] alfa = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', };

        try {
            texto = Files.readString(Path.of("Arquivo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Nao existe arquivo");

        }

        // Organiza o texto do arquivo
        texto = texto.replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("\r", "")
                .replaceAll("\n", ";");

        String[] linhaArquivo = texto.split(";");

        return linhaArquivo[1].replaceAll(" ", "").substring(1, 2);
    }

    private static void percorre(String[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static class Grafo {
        private ArrayList<Vertice> vertices = new ArrayList<>();

        public void addVertice(Vertice v) {
            this.vertices.add(v);
        }

        public void percorreArestas() {
            for (Vertice v : this.vertices) {
                for (Aresta a : v.getArestaAdj()) {
                    System.out.println("Origem: " + a.getOrigem().getId() +
                            " Destino: " + a.getDestino().getId() +
                            " Custo: " + a.custo);
                }
            }
        }

        public String percorreVertice() {
            String vertice = "";
            for (Vertice v : this.vertices) {
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

        public Vertice getById(String id) {
            for (Vertice v : this.vertices) {
                if (v.getId().equals(id)) {
                    return v;
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

        public Vertice(String id, Vertice pai, Double custo) {
            this.id = id;
            this.pai = pai;
            this.custo = custo;
        }

        public void addAresta(Aresta aresta) {
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
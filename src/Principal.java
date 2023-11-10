public class Principal {
    public static void main(String[] args) {
        String[][] matriz = new String[][] {
                { "0",  "2", "-1", "-1",  "10",},
                {"-1",  "0",  "3", "-1",  "7"},
                {"-1", "-1",  "0",  "4", "-1"},
                {"-1", "-1", "-1",  "0", "-1"},
                {"-1", "-1",  "8",  "5",  "0"}
        };

        String[][] dij = initializeSingleSource(new String[2][matriz.length], 3);

        percorre(dij);



    }

    private static String[][] initializeSingleSource(String[][] g, int verticeInic){
        verticeInic--;
        for (int i = 0; i < g.length; i++){
            for (int j = 0; j < g[i].length; j++){
                if(i == 0){
                    if(j == verticeInic){
                        g[i][j] = "0";
                    }else{
                        g[i][j] = "inf";
                    }


                }else{
                    g[i][j] = "nil";
                }
            }
        }

        return g;

    }

    private static String[][] relax(){
        String[][] matriz = new String[2][2];
        return matriz;
    }

    private static void percorre(String[][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}
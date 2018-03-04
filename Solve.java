

abstract class Solve{

    int Solvability(
    Node source, Node objective, int height, int width){
        if(width%2 == 0){return -1;} //nao se pode concluir nada (os slides da professora so explicam caso seja par)
        else{
            boolean a = (inversion(source)%2 == 0) == blankRowSolve(source,height);
            boolean b = (inversion(objective)%2 == 0) == blankRowSolve(objective,height);
            if(a == b) return 1;
            else return 0;
        }
    }


    private static int inversion(int[] source){
        int inv_count = 0;
        for(int i=0;i<source.length;i++){//corremos o vetor source e comparamos todos os pares
            for(int j=i+1;j<source.length;j++){
                if(source[i] > source[j] && source[j]!=0){
                    inv_count++;
                }
            }
        }
        return inv_count;
    }


    private static boolean blankRowSolve(Node alfa, int height){
        if(height%2 == 0){
        //a paridade mantem-se se enumerarmos as linhas começando em 1 de baixo para cima
            if(alfa.zero_height%2 == 0){return false;} //queremos que blankRow % 2 == 1 e nao e o caso
            else return true;
        }
        else{
        //a paridade troca se enumerarmos as linhas começando em 1 de baixo para cima
            if(alfa.zero_height%2 == 0){return true;}
            else return false;
        }
    }
}



abstract class Solve{
    abstract boolean Solvability(
    Node source, Node objective, int height, int width);

    public static int inversion(int[] source){
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

    
}

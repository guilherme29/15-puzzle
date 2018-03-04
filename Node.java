import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math.*;

class Node implements Comparable<Node>{
    int[][] matrix;
    Node parent;
    int zero_height, zero_width;
    int depth;
    int score;

    public Node(int height, int width){
        matrix = new int[height][width];
        parent = null;
        depth = 0;
    }


    public static Node scan(
    int height, int width, Node alfa, Scanner stdin, int depth){
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                alfa.matrix[j][i] = stdin.nextInt();
                if(alfa.matrix[j][i] == 0){
                    alfa.zero_height=j;
                    alfa.zero_width=i;
                }
            }
        }
        return alfa;
    }


    public static Node clone(int height, int width, Node original){
        Node clone = new Node(height,width);
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                clone.matrix[j][i] = original.matrix[j][i];
            }
        }
        clone.zero_height=original.zero_height;
        clone.zero_width=original.zero_width;
        clone.parent=original.parent;
        clone.depth=original.depth;
        return clone;
    }


    public static Node son(int height, int width, Node parent){//cria um no de configuracao igual mas de profundidade +1 e cujo pai e o dado no input
        Node son = clone(height,width,parent);
        son.depth = son.depth + 1;
        son.parent = parent;
        return son;
    }


    public static void print(Node node, int height, int width){ //da print a configuracao dum node
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                System.out.print(node.matrix[j][i] + " ");
            }
            System.out.println();
        }
    }


    public static void print0(Node node){                   //da print da localizacao do 0
        System.out.println("(" + node.zero_height + "," + node.zero_width + ")");
    }


    public static void path_print(Node objective, int height, int width){
        if(objective.parent!=null){
            path_print(objective.parent,height,width);
            print(objective,height,width);
            System.out.println("---");
        }
    }

    public static boolean compare(Node node1, Node node2, int height, int width){   //self-explanatory, compara a configuracao de dois nodes
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                if(node1.matrix[j][i]!=node2.matrix[j][i]){ return false; }
            }
        }
        return true;
    }


    public int compareTo(Node beta){
        if(this.score < beta.score){return -1;}
        else if(this.score == beta.score){return 0;}
        else return 1;
    }


    public static void score(Node alfa, Node objective, int height, int width){
        for(int j=0;j<height;j++){//percorremos a node alfa
            for(int i=0;i<width;i++){
                boolean flag = true;
                int aux1 = 0,aux2 = 0;
                for(int m=0;m<height&&flag;m++){
                    for(int n=0;n<width;n++){
                        if(alfa.matrix[j][i] == objective.matrix[m][n]){//quando encontramos a posicao objetivo guardamos e saimos do loop
                            flag = false;
                            aux1 = m;
                            aux2 = n;
                            break;}
                    }
                }
                alfa.score = alfa.score + Math.abs(j-aux1) + Math.abs(i-aux2);//atribuicao da pontuacao
            }
        }
    }


    public static int[] createArray(Node alfa, int height, int width){
        int[] array = new int[height*width];
        int k = 0;
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                array[k] = alfa.matrix[j][i];
                k++;
            }
        }
        return array;
    }
}

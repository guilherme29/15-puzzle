import java.util.Scanner;
import java.util.LinkedList;

class Puzzle{
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);

        System.out.print("altura do jogo:");
        int height = stdin.nextInt();
        System.out.print("largura do jogo:");
        int width = stdin.nextInt();

        System.out.println("Estado atual do jogo:");
        Node alfa = new Node(height,width);
        Node.scan(height,width,alfa,stdin,0);
        System.out.println("Solucao do jogo:");
        Node beta = new Node(height,width);
        Node.scan(height,width,beta,stdin,0);


        Algorithms.greedy(alfa,beta,height,width);
        //Node.score(alfa,beta,height,width);
    }
}
/*
1 2 3 4 5 6 8 12 13 9 0 7 14 11 10 15
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0

*/

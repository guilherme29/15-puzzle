import java.util.Scanner;
import java.util.LinkedList;

class Puzzle{

  public static void main(String[] args){
    Scanner stdin = new Scanner(System.in);
    int maxdepth = 0, modes = 0;
    String modestring = "Selecione o modo de busca:\n"
                      + "1) Busca por profundidade\n"
                      + "2) Busca por largura\n"
                      + "3) Busca por profundidade limitada\n"
                      + "4) Busca Iterativa por Profundidade\n"
                      + "5) Busca A*\n"
                      + "6) Busca Gulosa com Heuristica\n"
                      + "7) Sair\n";

      System.out.println(modestring);
      modes = stdin.nextInt();
    do{
      //System.out.print("\033[H\033[2J");                //limpa a terminal

      if(modes < 1 || modes > 7){
        System.out.println("Input Invalido");
        System.exit(0);
      }

      //System.out.print("\033[H\033[2J");                //limpa a terminal

      System.out.print("Altura do jogo:");
      int height = stdin.nextInt();
      System.out.print("Largura do jogo:");
      int width = stdin.nextInt();
      System.out.println("Solucao do jogo:");
      Node alfa = new Node(height,width);
      alfa = Node.scan(height, width, alfa, stdin, 0);
      System.out.println("Estado atual do jogo:");
      Node beta = new Node(height,width);
      beta = Node.scan(height, width, beta, stdin, 0);

      if(modes == 3){
        System.out.println("Profundidade Maxima:");
        maxdepth = stdin.nextInt();
      }
      //System.out.print("\033[H\033[2J");

      switch(modes){
        case 1: Solve.hasSolution(beta, alfa, height, width);
                if(Solve.Solvability(beta, alfa, height, width)!= -1 && Solve.Solvability(beta, alfa, height, width) != 0){
                  System.out.println("Resposta passo a passo:");
                  Algorithms.dfs(beta, alfa, height, width);
                }
                break;
        case 2: Solve.hasSolution(beta, alfa, height, width);
                if(Solve.Solvability(beta, alfa, height, width)!= -1 && Solve.Solvability(beta, alfa, height, width) != 0){
                  System.out.println("Resposta passo a passo:");
                  Algorithms.bfs(beta, alfa, height, width);
                }
                break;
        case 3: Solve.hasSolution(beta, alfa, height, width);
                if(Solve.Solvability(beta, alfa, height, width)!= -1 && Solve.Solvability(beta, alfa, height, width) != 0){
                  System.out.println("Tem Solucao");
                  System.out.println("Resposta passo a passo:");
                  Algorithms.ldfs_print(beta, alfa, height, width, maxdepth);
                }
                break;
        case 4: Solve.hasSolution(beta, alfa, height, width);
                if(Solve.Solvability(beta, alfa, height, width)!= -1 && Solve.Solvability(beta, alfa, height, width) != 0){
                  System.out.println("Resposta passo a passo:");
                  Algorithms.idfs(beta, alfa, height, width);
                }
                break;
        case 5: Solve.hasSolution(beta, alfa, height, width);
                if(Solve.Solvability(beta, alfa, height, width)!= -1 && Solve.Solvability(beta, alfa, height, width) != 0){
                  System.out.println("Resposta passo a passo:");
                  Algorithms.a_star(beta, alfa, height, width);
                }
                break;
        case 6: Solve.hasSolution(beta, alfa, height, width);
                if(Solve.Solvability(beta, alfa, height, width)!= -1 && Solve.Solvability(beta, alfa, height, width) != 0){
                  System.out.println("Resposta passo a passo:");
                  Algorithms.greedy(beta, alfa, height, width);
                }
                break;
        case 7: System.exit(0);
        }

        System.out.println("\n" + modestring);
        modes = stdin.nextInt();

    }while(modes != 7);
  }
}

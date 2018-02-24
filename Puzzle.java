import java.util.Scanner;
import java.util.LinkedList;

class Puzzle{
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);

        System.out.print("altura do jogo:");
        int height = stdin.nextInt();
        System.out.print("largura do jogo:");
        int width = stdin.nextInt();


        System.out.println("Solucao do jogo:");
        Node alfa = new Node(height,width);
        System.out.println("Estado atual do jogo:");
        Node beta = new Node(height,width);
        System.out.println("Resposta passo a passo:");
        Algorithms.dfs(alfa,beta,height,width);

    }
}

class Node{
    int[][] matrix;
    Node parent;
    int zero_height, zero_width;


    public Node(int height, int width){                     //constroi um node com base no input dado pelo utilizador
        Scanner stdin = new Scanner(System.in);
        matrix = new int[height][width];
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                matrix[j][i]=stdin.nextInt();
                if(matrix[j][i]==0){
                    zero_height=j;
                    zero_width=i;
                }
            }
        }
        parent=null;
    }

    public Node(int height, int width, Node parent){        //constroi um node igual a um outro e considera-o seu pai
        this.matrix = new int[height][width];
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                this.matrix[j][i]=parent.matrix[j][i];
            }
        }
        this.zero_height=parent.zero_height;
        this.zero_width=parent.zero_width;
        this.parent=parent;
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

    public static boolean compare(Node node1, Node node2, int height, int width){   //self-explanatory, compara a configuracao de dois nodes
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                if(node1.matrix[j][i]!=node2.matrix[j][i]){ return false; }
            }
        }
        return true;
    }
}


class Algorithms{
    public static void bfs(Node source, Node objective, int height, int width){
        LinkedList<Node> queue = new LinkedList<Node>();    //fila
        LinkedList<Node> visited = new LinkedList<Node>();  //lista de nos ja visitados
        if(Node.compare(source,objective,height,width)){    //se os nodes forem iguais nao e preciso fazer nada
            Node.print(source,height,width);
            return;
        }
        queue.addLast(source);
        Node alfa;  //vai servir para examinar cada node
        do {
            alfa=queue.removeFirst();                       //tira-se o primeiro node da fila
            if(Node.compare(alfa,objective,height,width)){ break; }      //se for o node que procuramos paramos
            queue.addAll(sons(alfa,height,width,visited)); //adiciona-se os filhos ao final da fila e marcam-se como visitados
        } while(!queue.isEmpty());

        path_print(alfa,height,width);
    }

    public static LinkedList<Node> sons(Node parent, int height, int width, LinkedList<Node> visited){
        //funcao para gerar uma lista com os filhos dum dado node
        LinkedList<Node> sons = new LinkedList<Node>();
        int[][] movements = {{0,-1},{0,1},{-1,0},{1,0}};    //lista dos movimentos possiveis
        for(int k=0;k<4;k++){
            int vertical = parent.zero_height + movements[k][0];                   //indices depois dum movimento, tanto vertical como horizontal
            int horizontal = parent.zero_width + movements[k][1];

            if(vertical>=0 && vertical<height && horizontal>=0 && horizontal<width){    //os movimentos nao podem sair do quadro
                Node son = new Node(height,width,parent);
                //atualizacao da configuracao da matriz do filho que ao inicio era igual a do pai
                son.matrix[son.zero_height][son.zero_width] = son.matrix[vertical][horizontal];
                son.matrix[vertical][horizontal]=0;
                son.zero_height=vertical;                   //atualizacao da nova posicao do 0
                son.zero_width=horizontal;

                boolean flag = true;
                for(Node test : visited){                   //vemos se o no e igual a algum ja visitado
                    if(Node.compare(son,test,height,width)){
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    visited.addLast(son);                   //o no e marcado como visitado
                    sons.addLast(son);                      //e adicionado a fila de filhos
                }
            }
        }
        return sons;
    }

    public static void dfs(Node source, Node objective, int height, int width){
        LinkedList<Node> queue = new LinkedList<Node>();    //fila
        LinkedList<Node> visited = new LinkedList<Node>();  //lista de nos ja visitados
        if(Node.compare(source,objective,height,width)){    //se os nodes forem iguais nao e preciso fazer nada
            Node.print(source,height,width);
            return;
        }
        queue.addLast(source);
        Node alfa;  //vai servir para examinar cada node
        do {
            alfa=queue.removeFirst();                       //tira-se o primeiro node da fila
            if(Node.compare(alfa,objective,height,width)){ break; }      //se for o node que procuramos paramos
            queue.addAll(0,sons(alfa,height,width,visited)); //adiciona-se os filhos ao inicio da fila e marcam-se como visitados
        } while(!queue.isEmpty());

        path_print(alfa,height,width);
    }

    public static void path_print(Node objective, int height, int width){
        Node aux = objective;
        do{
            Node.print(aux,height,width);
            //Node.print0(aux);
            System.out.println("---");
            aux = aux.parent;
        } while(aux!=null);
    }

}

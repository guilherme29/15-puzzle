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
        //Algorithms.bfs(alfa,beta,height,width);
        Algorithms.bfs(beta,alfa,height,width);
    }
}

class Node{
    int[][] matrix;
    Node parent;
    int zero_height, zero_width;
    int depth;


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
        depth=0;
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
        this.depth=parent.depth+1;
    }


    public static Node clone(int height, int width, Node original){     //cria um node igual a outro dado
        Node clone = new Node(height,width,original);
        clone.zero_height=original.zero_height;
        clone.zero_width=original.zero_width;
        clone.parent=original.parent;
        clone.depth=original.depth;
        return original;
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
    private static LinkedList<Node> sons(Node parent, int height, int width, LinkedList<Node> visited){
        //funcao para gerar uma lista com os filhos dum dado node
        LinkedList<Node> sons = new LinkedList<Node>();
        int[][] movements = {{0,-1},{0,1},{-1,0},{1,0}};    //lista dos movimentos possiveis: CIMA,BAIXO,DIREITA,ESQUERDA
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

                boolean flag = true;                        //muda para falso se o no ja tiver sido visitado
                for(Node test : visited){                   //vemos se o no e igual a algum ja visitado
                    if(Node.compare(son,test,height,width)){
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    visited.addFirst(son);                  //o no e marcado como visitado, adicionado ao inicio para ser mais rapido, pois e mais provavel encontrar um no mais recentemente do que ha mais tempo
                    sons.addFirst(son);                     //e adicionado a fila de filhos, adicionado ao inicio para ser mais rapido (aqui a diferenca e pouca pois ha no maximo 4 filhos)
                }
            }
        }
        return sons;
    }

    public static void bfs(Node source, Node objective, int height, int width){
        LinkedList<Node> queue = new LinkedList<Node>();    //fila
        LinkedList<Node> visited = new LinkedList<Node>();  //lista de nos ja visitados

        queue.addLast(source);
        Node alfa;  //vai servir para examinar cada node
        do {
            alfa=queue.removeFirst();                       //tira-se o primeiro node da fila
            if(Node.compare(alfa,objective,height,width)){ break; }      //se for o node que procuramos paramos
            queue.addAll(sons(alfa,height,width,visited));  //adiciona-se os filhos ao final da fila e marcam-se como visitados
        } while(!queue.isEmpty());

        path_print(alfa,height,width);
    }


    public static void dfs(Node source, Node objective, int height, int width){
        LinkedList<Node> queue = new LinkedList<Node>();    //pilha
        LinkedList<Node> visited = new LinkedList<Node>();  //lista de nos ja visitados

        queue.addLast(source);
        Node alfa;                                          //vai servir para examinar cada node
        do{
            alfa=queue.removeFirst();                       //tira-se o primeiro node da fila
            if(Node.compare(alfa,objective,height,width)){ break; }//se for o node que procuramos paramos
            queue.addAll(0,sons(alfa,height,width,visited));//adiciona-se os filhos ao inicio da fila e marcam-se como visitados
        } while(!queue.isEmpty());

        path_print(alfa,height,width);
    }


    public static void ldfs(Node source, Node objective, int height, int width, int depth){//busca limitada em profundidade
        LinkedList<Node> queue = new LinkedList<Node>();
        LinkedList<Node> visited = new LinkedList<Node>();
        queue.addLast(source);
        visited.addLast(source);
        Node alfa;
        boolean flag = false;                               //vai-nos dizer se encontrou uma solucao
        do{
            alfa=queue.removeFirst();
            if(Node.compare(alfa,objective,height,width)){
                flag = true;
                break;
            } //se for o node que procuramos paramos
            if(alfa.depth<depth){ queue.addAll(0,sons(alfa,height,width,visited)); }//so adiciona os filhos se ainda nao tiver chegado a profundidade objetiva

        } while(!queue.isEmpty());

        if(!flag){ System.out.println("Solucao nao encontrada."); }//se houver solucao da print
        else{ path_print(alfa,height,width); }              //se nao houver solucao diz que nao ha
    }


    public static void idfs(Node source, Node objective, int height, int width){//busca iterativa em profundidade
        LinkedList<Node> visited = new LinkedList<Node>();
        for(int i=0;;i++){
            visited=ldfs2(source,objective,height,width,i); //faz-se ldfs para uma depth cada vez maior
            if(visited!=null){ break; }                     //se nao retornar null entao e porque encontrou o node
        }
        Node alfa = visited.removeFirst();                  //o node objective vai estar na primeira posicao pois a lista visited funciona por addFirst por ser mais rapida assim
        path_print(alfa,height,width);
    }


    private static LinkedList<Node> ldfs2(Node source, Node objective, int height, int width, int depth){
        //a diferenca desta para a outra idfs e que esta e auxiliar, retornando uma LinkedList que tem a cabeca o no objectivo. se o no nao for encontrado retorna null
        LinkedList<Node> queue = new LinkedList<Node>();
        LinkedList<Node> visited = new LinkedList<Node>();
        queue.addLast(source);
        visited.addLast(source);
        Node alfa;
        boolean flag = false;
        do{
            alfa=queue.removeFirst();
            if(Node.compare(alfa,objective,height,width)){
                flag = true;
                break;                                      //se for o node que procuramos paramos
            }
            if(alfa.depth<depth){ queue.addAll(0,sons(alfa,height,width,visited)); }//so adiciona os filhos se ainda nao tiver chegado a profundidade objetiva

        } while(!queue.isEmpty());

        if(!flag){ visited=null; }                          //passa a visited a null para a outra funcao saber que nao foi encontrado o node
        else{
            Node novo_alfa = Node.clone(height,width,alfa); //alfa era um apontador e nao um node, por isso temos de criar outro com as mesmas caracteristicas
            visited.addFirst(novo_alfa);                    //adiciona-se o alfa de novo a fila dos visitados para ficar a cabeca e nao aumentar a ordem (depois removemos)                                           //retorna a LinkedList dos visitados que de certeza tem o caminho e tem o node objetivo a cabeca
        }
        return visited;
    }


    public static void path_print(Node objective, int height, int width){
        Node aux = objective;
        do{
            Node.print(aux,height,width);
            System.out.println("---");
            aux = aux.parent;
        } while(aux!=null);
    }
}

//1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0
//1 2 3 4 5 6 8 12 13 9 0 7 14 11 10 15

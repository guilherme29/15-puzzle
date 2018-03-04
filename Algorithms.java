import java.util.LinkedList;
import java.util.PriorityQueue;

class Algorithms {

    private static LinkedList<Node> sons(
    Node parent, int height, int width){//funcao para gerar filhos dum node, nao verifica se ja foram visitados
        LinkedList<Node> sons = new LinkedList<>();
        int[][] movements = {{0,-1},{0,1},{-1,0},{1,0}};//lista dos movimentos possiveis: CIMA,BAIXO,DIREITA,ESQUERDA
        for(int k=0;k<4;k++){
            int vertical = parent.zero_height + movements[k][0];
            int horizontal = parent.zero_width + movements[k][1];
            if(vertical>=0 && horizontal>=0 && vertical<height && horizontal<width){
                Node son = Node.son(height,width,parent);
                //modificacao a matriz do filho
                son.matrix[son.zero_height][son.zero_width] = son.matrix[vertical][horizontal];
                son.matrix[vertical][horizontal]=0;
                son.zero_height=vertical;                   //atualizacao da nova posicao do 0
                son.zero_width=horizontal;
                sons.addFirst(son);
            }
        }
        return sons;
    }


    private static LinkedList<Node> sons(
    Node parent, int height, int width, LinkedList<Node> visited){
        //overload da sons mas faz verificaco de visitados antes de devolver a linkedlist
        LinkedList<Node> sons = new LinkedList<Node>();
        int[][] movements = {{0,-1},{0,1},{-1,0},{1,0}};//lista dos movimentos possiveis: CIMA,BAIXO,DIREITA,ESQUERDA
        for(int k=0;k<4;k++){
            int vertical = parent.zero_height + movements[k][0];//indices depois dum movimento, tanto vertical como horizontal
            int horizontal = parent.zero_width + movements[k][1];

            if(vertical>=0 && vertical<height && horizontal>=0 && horizontal<width){//os movimentos nao podem sair do quadro
                Node son = Node.son(height,width,parent);
                //atualizacao da configuracao da matriz do filho que ao inicio era igual a do pai
                son.matrix[son.zero_height][son.zero_width] = son.matrix[vertical][horizontal];
                son.matrix[vertical][horizontal]=0;
                son.zero_height=vertical;//atualizacao da nova posicao do 0
                son.zero_width=horizontal;

                boolean flag = true;//muda para falso se o no ja tiver sido visitado
                for(Node test : visited){//vemos se o no e igual a algum ja visitado
                    if(Node.compare(son,test,height,width)){
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    visited.addFirst(son);//o no e marcado como visitado, adicionado ao inicio para ser mais rapido, pois e mais provavel encontrar um no mais recentemente do que ha mais tempo
                    sons.addFirst(son);//e adicionado a fila de filhos, adicionado ao inicio para ser mais rapido (aqui a diferenca e pouca pois ha no maximo 4 filhos)
                }
            }
        }
        return sons;
    }


    public static void bfs(
    Node source, Node objective, int height, int width){
        LinkedList<Node> queue = new LinkedList<>();//fila
        LinkedList<Node> visited = new LinkedList<>();//lista de nos ja visitados
        queue.addLast(source);
        Node alfa;
        do{
            alfa=queue.removeFirst();
            if(Node.compare(alfa,objective,height,width)){break; }
            queue.addAll(sons(alfa,height,width,visited));//adiciona ao final da queue
        } while(!queue.isEmpty());
        Node.path_print(alfa,height,width);
    }


    public static void dfs(
    Node source, Node objective, int height, int width){
        LinkedList<Node> queue = new LinkedList<>();//fila
        LinkedList<Node> visited = new LinkedList<>();//lista de nos ja visitados
        queue.addLast(source);
        Node alfa;
        do{
            alfa=queue.removeFirst();
            if(Node.compare(alfa,objective,height,width)){break; }
            queue.addAll(0,sons(alfa,height,width,visited));//adiciona ao inicio da queue
        } while(!queue.isEmpty());
        Node.path_print(alfa,height,width);
    }


    public static void idfs(
    Node source, Node objective, int height, int width){
        LinkedList<Node> visited = new LinkedList<>();
        for(int i=0;;i++){
            visited=ldfs(source,objective,height,width,i);//ldfs que vai incrementando a depth
            if(visited!=null){break; }//quer dizer que ja encontrou o caminho
        }
        Node alfa = visited.removeFirst();//a outra funcao deixa a cabeca o objetivo
        Node.path_print(alfa,height,width);
    }


    private static LinkedList<Node> ldfs(
    Node source, Node objective, int height, int width, int maxdepth){
        //print diz se quero que imprima o caminho nesta funcao ou nao
        LinkedList<Node> queue = new LinkedList<>();
        LinkedList<Node> visited = new LinkedList<>();
        queue.addLast(source);
        visited.addLast(source);
        Node alfa;
        boolean flag = false;
        do{
            alfa = queue.removeFirst();
            if(Node.compare(alfa,objective,height,width)){
                flag = true;
                break;
            }
            if(alfa.depth<maxdepth){queue.addAll(0,sons(alfa,height,width));}
            //nao preciso de verificar se ja foram visitados pois estaria a eliminar solucoes e como ha um limite de profundidade mesmo que entre em ciclo eventualmente para
        } while(!queue.isEmpty());
        if(flag){
            Node aux = Node.clone(height,width,alfa);
            visited.addFirst(aux);//adiciona-se o alfa de novo a fila dos visitados para ficar a cabeca e nao aumentar a ordem (depois removemos)                                           //retorna a LinkedList dos visitados que de certeza tem o caminho e tem o node objetivo a cabeca
        }
        else{ visited=null; }//passa a visited a null para o idfs saber que nao foi encontrado o node
        return visited;
    }


    public static void ldfs_print(
    Node source, Node objective, int height, int width, int maxdepth){
        //print diz se quero que imprima o caminho nesta funcao ou nao
        LinkedList<Node> queue = new LinkedList<>();
        LinkedList<Node> visited = new LinkedList<>();
        queue.addLast(source);
        visited.addLast(source);
        Node alfa;
        boolean flag = false;
        do{
            alfa = queue.removeFirst();
            if(Node.compare(alfa,objective,height,width)){
                flag = true;
                break;
            }
            if(alfa.depth<maxdepth){queue.addAll(0,sons(alfa,height,width));}
            //nao preciso de verificar se ja foram visitados pois estaria a eliminar solucoes e como ha um limite de profundidade mesmo que entre em ciclo eventualmente para
        } while(!queue.isEmpty());
        if(flag){Node.path_print(alfa,height,width);}
        else{System.out.println("Solucao nao encontrada.");}
    }


    public static void greedy(
    Node source, Node objective, int height, int width){
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(source);
        Node alfa;
        do{
            alfa = queue.poll();
            if(Node.compare(alfa,objective,height,width)){break; }
            queue.addAll(sons(alfa,height,width));
        } while(!queue.isEmpty());
        Node.path_print(alfa,height,width);
    }

}

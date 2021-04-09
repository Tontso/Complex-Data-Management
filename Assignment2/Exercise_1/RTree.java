package Exercise_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RTree {

    private static final AtomicInteger count = new AtomicInteger(-1); 
    List<Node> tree = new ArrayList<Node>();
    Node currentNode;
    private int M;
    private int minSize; 

    public class Node{
        private int isnonleaf;
        private int level;
        private int id;
        private double[] mbr = new double [2];
        private List<Polygon> data = new ArrayList<Polygon>();

        public Node(int isnonleaf, int level){
            this.isnonleaf = isnonleaf;
            this.level = level;
            id = count.incrementAndGet();
        }
    }

    public RTree(int M){
        this.M =M;
        minSize = (int)(0.4*M);
        currentNode = new Node(0,0);
        tree.add(currentNode);
    }
    
    public void insert(Polygon polygon){
        if(currentNode.data.size() == M){
            currentNode = new Node(0,0);
            tree.add(currentNode);
        }
        currentNode.data.add(polygon); // THELEI DIORTHORSH !!
    }


    public void checkForLimits() {
        if(tree.get(tree.size()-1).data.size() < minSize){
            while(tree.get(tree.size()-1).data.size() < minSize){
                tree.get(tree.size()-1).data.add(tree.get(tree.size()-2).data.remove(tree.get(tree.size()-2).data.size()-1));
            }
        }
        /* for (Node node : tree) {
            System.out.println("Node "+node.id +":  Size:"+node.data.size());
        }
        System.out.println(tree.size());
        System.out.println("MinSize: "+minSize); */
    }

       
}

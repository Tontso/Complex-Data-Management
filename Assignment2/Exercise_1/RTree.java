package Exercise_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RTree {

    private static final AtomicInteger count = new AtomicInteger(-1); 
    HashMap<Integer, List<Node>> tree = new HashMap<Integer, List<Node>>();
    //List<Node> tree = new ArrayList<Node>();
    Node currentNode;
    private int M;
    private int minSize; 

    public class Node{
        private int isnonleaf;
        private int level;
        private int id;
        private double[] mbr = new double [2];
        private List<Polygon> data = new ArrayList<Polygon>();
        private List<Node> nodeData = new ArrayList<Node>();

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
        tree.put(0,new ArrayList<Node>());
        tree.get(0).add(currentNode);
    }
    
    public void insert(Polygon polygon){
        if(currentNode.data.size() == M){
            currentNode = new Node(0,0);
            tree.get(0).add(currentNode);
        }
        currentNode.data.add(polygon); // THELEI DIORTHORSH !!
    }


    public void checkForLimitsLeaf(int level) {
        if(tree.get(level).get(tree.get(level).size()-1).data.size() < minSize){
            while(tree.get(level).get(tree.get(level).size()-1).data.size() < minSize){
                tree.get(level).get(tree.get(level).size()-1).data.add(tree.get(level).get(tree.get(level).size()-2).data.remove(tree.get(level).get(tree.get(level).size()-2).data.size()-1));
            }
        }
    }
    public void checkForLimits(int level) {
        if(tree.get(level).get(tree.get(level).size()-1).nodeData.size() < minSize){
            while(tree.get(level).get(tree.get(level).size()-1).nodeData.size() < minSize){
                tree.get(level).get(tree.get(level).size()-1).nodeData.add(tree.get(level).get(tree.get(level).size()-2).nodeData.remove(tree.get(level).get(tree.get(level).size()-2).nodeData.size()-1));
            }
        }
    }

    public void cunstructRTree(int key) {
        tree.put(key+1,new ArrayList<Node>());
        currentNode = new Node(1,key+1);
        for (Node node : tree.get(key)) {
            currentNode.nodeData.add(node);
            if(currentNode.nodeData.size() == M){
                tree.get(key+1).add(currentNode);
                currentNode = new Node(1,key+1);
            }
        }
        if(currentNode.nodeData.size() != 0){
            tree.get(key+1).add(currentNode);
        }
        if(tree.get(key+1).size() == 1){
            return;
        }else{
            this.checkForLimits(key+1);
            this.cunstructRTree(key+1);
        }
    }

    public void printTree(){
        /* for (Integer key : tree.keySet()) {
            System.out.println("Level: "+key);
            for (Node node : tree.get(key)) {
                System.out.println("Node "+node.id +":  Size:"+node.data.size());
            }
        } */
        
        System.out.println("MinSize: "+minSize);
        for (Integer key : tree.keySet()) {
            System.out.println("Level: "+key+" has size: "+tree.get(key).size());
        }
    }

       
}

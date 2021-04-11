package Exercise_1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.atomic.AtomicInteger;

public class RTree {

    private static final AtomicInteger count = new AtomicInteger(-1); 
    Map<Integer, List<Node>> tree = new LinkedHashMap<Integer, List<Node>>();
    Node currentNode;
    private int M;
    private int minSize; 

    public class Node{
        private int isnonleaf;
        private int id;
        private Map<Integer,Double[]> data = new LinkedHashMap<Integer,Double[]>();

        public Node(int isnonleaf){
            this.isnonleaf = isnonleaf;
            id = count.incrementAndGet();
            if(id == 525)
                System.out.println("ID: "+id );
        }

        public Double[] findNodeMBR(int key){
            Double[] nodeMbr = new Double[4];
            for ( Map.Entry<Integer, Double[]> mapElement : data.entrySet()) {             
                nodeMbr[0] = mapElement.getValue()[0];
                nodeMbr[1] = mapElement.getValue()[1];
                nodeMbr[2] = mapElement.getValue()[2];
                nodeMbr[3] = mapElement.getValue()[3];
                break;             
            }

            for ( Map.Entry<Integer, Double[]> mapElement : data.entrySet()) {             
                // x-low 
                if(mapElement.getValue()[0] < nodeMbr[0]){
                    nodeMbr[0] = mapElement.getValue()[0];
                }
                // x-high
                if(mapElement.getValue()[1] > nodeMbr[1]){
                    nodeMbr[1] = mapElement.getValue()[1];
                }
            
                // y-low 
                if(mapElement.getValue()[2]< nodeMbr[2]){
                    nodeMbr[2] = mapElement.getValue()[2];
                }

                // y-high
                if(mapElement.getValue()[3] > nodeMbr[3]){
                    nodeMbr[3] = mapElement.getValue()[3];
                }                   
            }
            return nodeMbr;
        }
    }

    public RTree(int M){
        this.M = M;
        minSize = (int)(0.4*M);
        currentNode = new Node(0);
        tree.put(0,new ArrayList<Node>());
        tree.get(0).add(currentNode);
    }
    
    public void insert(Polygon polygon){
        if(currentNode.data.size() == M){
            currentNode = new Node(0);
            tree.get(0).add(currentNode);
        }
        currentNode.data.put(polygon.getId(), polygon.getMbr()); 
    }


    public void checkForLimits(int level) {
        
        while(tree.get(level).get(tree.get(level).size()-1).data.size() < minSize){
            int count = 1;
            for(Map.Entry<Integer, Double[]> element : tree.get(level).get(tree.get(level).size()-2).data.entrySet()){
                if(count == tree.get(level).get(tree.get(level).size()-2).data.size()){
                    tree.get(level).get(tree.get(level).size()-1).data.put(element.getKey(), element.getValue());
                    tree.get(level).get(tree.get(level).size()-2).data.remove(element.getKey());
                    continue;
                }
                count++;
            }
        }
        
    }

    public void cunstructRTree(int key) {
        this.checkForLimits(key);
        tree.put(key+1,new ArrayList<Node>());
        currentNode = new Node(1);
        for (Node node : tree.get(key)) {
            if(currentNode == null)
                currentNode = new Node(1);
            currentNode.data.put(node.id, node.findNodeMBR(key));
            if(currentNode.data.size() == M){
                tree.get(key+1).add(currentNode);
                currentNode = null;
            }
        }
        if(currentNode != null)
            if(currentNode.data.size() != 0)
                tree.get(key+1).add(currentNode);
        
        if(tree.get(key+1).size() == 1)
            return;
        else
            this.cunstructRTree(key+1);
        
    }

    public void printTree(){
        for (Integer key : tree.keySet()) {
            System.out.println(tree.get(key).size() +" nodes at level "+key);
        }
    }


    public void writeTreeToFile(FileWriter writer) throws IOException {
        String line = "";
        for (Integer key : tree.keySet()) {
            for (Node node : tree.get(key)) {
                writer.write("["+node.isnonleaf+" ,"+node.id+", [");
                for ( Map.Entry<Integer, Double[]> mapElement : node.data.entrySet()) {
                    line = line +"["+mapElement.getKey()+" ,["+mapElement.getValue()[0]+" ,"+mapElement.getValue()[1]+" ,"+mapElement.getValue()[2]+" ,"+mapElement.getValue()[3]+"]], ";                  
                }
                if(line.endsWith(", "))
                    line = line.substring(0, line.length()-2);
                line = line+"]] \n";
                writer.write(line);
                line = "";             
            }
        }
    }

   

       
}

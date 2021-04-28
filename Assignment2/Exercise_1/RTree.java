// Tontso Tontsev
// AM 3168

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class RTree {

    Map<Integer, List<Node>> tree;
    Map<Integer, Node> listTree;
    Node currentNode;
    private int M;
    private int minSize;
    private static List<Integer> okQuery = new ArrayList<>();
    

    class CustomComparatorNeightbor implements Comparator<Double[]>{

        @Override
        public int compare(Double[] o1, Double[] o2) {
            if(o1[0] > o2[0])
                return 1;
            else if(o1[0] < o2[0])
                return -1;
            else
                return 0;
        }
    }
    

    public RTree(int M){
        this.M = M;
        minSize = (int)(0.4*M);
        currentNode = new Node(0);
        tree = new LinkedHashMap<Integer, List<Node>>();
        tree.put(0,new ArrayList<Node>());
        tree.get(0).add(currentNode);
    }

    public RTree(){
        listTree = new LinkedHashMap<Integer, Node>();
    }


    public Map<Integer,Node> getListTree(){
        return listTree;
    }

    
    public void insert(Polygon polygon){
        if(currentNode.getData().size() == M){
            currentNode = new Node(0);
            tree.get(0).add(currentNode);
        }
        currentNode.getData().put(polygon.getId(), polygon.getMbr()); 
    }


    private void checkForLimits(int level) {
        
        while(tree.get(level).get(tree.get(level).size()-1).getData().size() < minSize){
            int count = 1;
            for(Map.Entry<Integer, Double[]> element : tree.get(level).get(tree.get(level).size()-2).getData().entrySet()){
                if(count == tree.get(level).get(tree.get(level).size()-2).getData().size()){
                    tree.get(level).get(tree.get(level).size()-1).getData().put(element.getKey(), element.getValue());
                    tree.get(level).get(tree.get(level).size()-2).getData().remove(element.getKey());
                    continue;
                }
                count++;
            }
        }
        
    }

    
    public void constructRTree(int level) {
        this.checkForLimits(level);
        tree.put(level+1,new ArrayList<Node>());
        currentNode = new Node(1);
        for (Node node : tree.get(level)) {
            if(currentNode == null)
                currentNode = new Node(1);
            currentNode.getData().put(node.getId(), node.findNodeMBR());
            if(currentNode.getData().size() == M){
                tree.get(level+1).add(currentNode);
                currentNode = null;
            }
        }
        if(currentNode != null)
            if(currentNode.getData().size() != 0)
                tree.get(level+1).add(currentNode);
        
        if(tree.get(level+1).size() == 1)
            return;
        else
            this.constructRTree(level+1); 
    }

    public void printTree(){
        for (Integer key : tree.keySet()) {
            System.out.println(tree.get(key).size() +" nodes at level "+key);
            //############################# TEST ###########################################
           /*  for (Node node : tree.get(key)) {
                System.out.print(node.getData().size()+", ");
            }
            System.out.println("\n"); */
            //##############################################################################
        }
    }


    public void writeTreeToFile(FileWriter writer) throws IOException {
        String line = "";
        for (Integer key : tree.keySet()) {
            for (Node node : tree.get(key)) {
                writer.write("["+node.getIsnonleaf()+" ,"+node.getId()+", [");
                for ( Map.Entry<Integer, Double[]> mapElement : node.getData().entrySet()) {
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



// #################################################### PART-2 ####################################################

    public void constructRTreeFromFile(String[] line){
        currentNode = new Node(line);
        listTree.put(currentNode.getId(),currentNode);
    }

    public Node getRoot(){
        int count =1;
        for(Map.Entry<Integer,Node> n : listTree.entrySet()){
            if(count == listTree.size())
                return n.getValue();
            count++;
        }
        //Just for compiler
        return null;
    }


    public static List<Integer> checkQuery(Double[] query,RTree tree,Node node) {
        
        if(node.getIsnonleaf() == 1){
            for(Integer key : node.getData().keySet()){
                if(checkX(node.getData().get(key), query)  && checkY(node.getData().get(key), query))
                    checkQuery(query, tree, tree.getListTree().get(key));               
            }
        }else{
            for(Integer key : node.getData().keySet()){
                if(checkX(node.getData().get(key), query)  && checkY(node.getData().get(key), query))
                    okQuery.add(key);               
            }
        }
        return okQuery;
    }


    public void newListQuery(){
        okQuery.clear();
    }


    private static boolean checkX(Double[] treeCorr, Double[] queryCorr) {
        // (xq-low < xt-low) and (xq-high >= xt-low)
        if((queryCorr[0] <= treeCorr[0]) && queryCorr[2] > treeCorr[0])
               return true;

        // xquery > xtree (inside) 
        if((queryCorr[0] > treeCorr[0]) && (queryCorr[0] <= treeCorr[1])) 
            return true;
        
        return false;
    }


    private static boolean checkY(Double[] treeCorr, Double[] queryCorr) {
        // (yq-low < yt-low) and (yq-high >= yt-low)
        if((queryCorr[1] <= treeCorr[2]) && queryCorr[3] >= treeCorr[2])
               return true;

        // (yq-low > yt-low) and (yq-low <= yt-ght)
        if((queryCorr[1] > treeCorr[2]) && (queryCorr[1] <= treeCorr[3])) 
            return true;
        
        return false;
    }



    // #################################################### PART-3 ####################################################
    
    public String bestFirstknn(Double[] query, RTree tree, Node root, int k) {
        PriorityQueue<Double[]> pq = new PriorityQueue<>(new CustomComparatorNeightbor());
        Node currentNode;
        double distance;
        int count = 0;
        String returnString = "";
        Double[] mbr = new Double[4];

 
        for(Integer key : root.getData().keySet()){
            currentNode = tree.getListTree().get(key);
            mbr = root.getData().get(key);
            distance = distanceBoxParticle2D(query[0], query[1], mbr[0],mbr[2], mbr[1], mbr[3]);
            pq.add(new Double[]{distance, (double)key, (double)currentNode.getIsnonleaf(), mbr[0],mbr[2], mbr[1], mbr[3]});          
        }
        
        while(!pq.isEmpty() && count != k){
            Double[] head = pq.poll();
            if(head[2] == 1.0){ //Has NODES
                currentNode = tree.getListTree().get(head[1].intValue());
                for(Integer key : currentNode.getData().keySet()){
                    mbr = currentNode.getData().get(key);
                    distance = distanceBoxParticle2D(query[0], query[1], mbr[0],mbr[2], mbr[1], mbr[3]);
                    pq.add(new Double[]{distance, (double)key, (double)tree.getListTree().get(key).getIsnonleaf(), mbr[0],mbr[2], mbr[1], mbr[3]});         
                }
            }else if(head[2] == 0.0){
                currentNode = tree.getListTree().get(head[1].intValue());
                for(Integer key : currentNode.getData().keySet()){
                    mbr = currentNode.getData().get(key);
                    distance = distanceBoxParticle2D(query[0], query[1], mbr[0],mbr[2], mbr[1], mbr[3]);
                    pq.add(new Double[]{distance, (double)key, 2.0, mbr[0],mbr[2], mbr[1], mbr[3]});  // Is polygon  
                }
            }else{
                count++;
                returnString += head[1].intValue()+","; 

            }
        }
        return returnString.substring(0, returnString.length()-1);
    }

    
    public static double distanceBoxParticle2D(double x, double y, double x_min, double y_min, double x_max, double y_max){
        if (x < x_min) {
            if (y <  y_min) return HYPOT(x_min-x, y_min-y);
            if (y <= y_max) return x_min - x;
                            return HYPOT(x_min-x, y_max-y);
        } else if (x <= x_max) {
            if (y <  y_min) return y_min - y;
            if (y <= y_max) return 0;
                            return y - y_max;
        } else {
            if (y <  y_min) return HYPOT(x_max-x, y_min-y);
            if (y <= y_max) return x - x_max;
                            return HYPOT(x_max-x, y_max-y);
        }
    }


    private static double HYPOT(double x, double y) {
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }
}



import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class RTree {

    Map<Integer, List<Node>> tree;
    List<Node> listTree;
    Node currentNode;
    private int M;
    private int minSize;
    private static List<Integer> okQuery = new ArrayList<>();
    
    class CustomComparator implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            if(o1.getDistance() > o2.getDistance())
                return 1;
            else if(o1.getDistance() < o2.getDistance())
                return -1;
            else
                return 0;
        }
       
    }

    class CustomComparatorNeightbor implements Comparator<Double[]>{

        @Override
        public int compare(Double[] o1, Double[] o2) {
            if(o1[0] < o2[0])
                return 1;
            else if(o1[0] > o2[0])
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
        listTree = new ArrayList<>();
    }


    public List<Node> getListTree(){
        return listTree;
    }

    
    public void insert(Polygon polygon){
        if(currentNode.getData().size() == M){
            currentNode = new Node(0);
            tree.get(0).add(currentNode);
        }
        currentNode.getData().put(polygon.getId(), polygon.getMbr()); 
    }


    public void checkForLimits(int level) {
        
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

    
    public void cunstructRTree(int key) {
        this.checkForLimits(key);
        tree.put(key+1,new ArrayList<Node>());
        currentNode = new Node(1);
        for (Node node : tree.get(key)) {
            if(currentNode == null)
                currentNode = new Node(1);
            currentNode.getData().put(node.getId(), node.findNodeMBR(key));
            if(currentNode.getData().size() == M){
                tree.get(key+1).add(currentNode);
                currentNode = null;
            }
        }
        if(currentNode != null)
            if(currentNode.getData().size() != 0)
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

    public void cunstructRTreeFromFile(String[] line){
        currentNode = new Node(line);
        listTree.add(currentNode);
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
        if((queryCorr[0] > treeCorr[0]) && (queryCorr[0] <= treeCorr[1])) // DEN THELEI ELEGXOS X-HIGHT
            return true;
        
        return false;
    }


    private static boolean checkY(Double[] treeCorr, Double[] queryCorr) {
        // (yq-low < yt-low) and (yq-high >= yt-low)
        if((queryCorr[1] <= treeCorr[2]) && queryCorr[3] >= treeCorr[2])
               return true;

        // (yq-low > yt-low) and (yq-low <= yt-ght)
        if((queryCorr[1] > treeCorr[2]) && (queryCorr[1] <= treeCorr[3])) // DEN THELEI ELEGXOS X-HIGHT
            return true;
        
        return false;
    }



    // #################################################### PART-3 ####################################################
    
    public String bestFirstknn(Double[] query, RTree tree, Node root, int k) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new CustomComparator());
        PriorityQueue<Double[]>kNeighbor = new PriorityQueue<>(new CustomComparatorNeightbor());
        List<Double> dis = new ArrayList<>();
        Node currentNode;
        Node child; 
        double minDistance = Double.POSITIVE_INFINITY;

 
        for(Integer key : root.getData().keySet()){
            child = tree.getListTree().get(key);
            child.setDistance(root.getData().get(key), query);
            pq.add(tree.getListTree().get(key));          
        }
        
        while(!pq.isEmpty() && pq.peek().getDistance() < minDistance){
            currentNode = pq.poll();
            if(currentNode.getIsnonleaf() == 1){
                for(Integer key : currentNode.getData().keySet()){
                    tree.getListTree().get(key).setDistance(currentNode.getData().get(key), query); //Calculate distance
                    if(tree.getListTree().get(key).getDistance() < minDistance)
                        pq.add(tree.getListTree().get(key));          
                }
            }else{
                for(Integer key : currentNode.getData().keySet()){
                    currentNode.setDistance(currentNode.getData().get(key), query);
                    if(currentNode.getDistance() < minDistance)
                            minDistance = currentNode.getDistance();
                    dis.add(currentNode.getDistance());         // TEST
                    minDistance = currentNode.getDistance();
                    kNeighbor.add(new Double[]{minDistance,(double)key});
                    if(kNeighbor.size() == (k+1))
                        kNeighbor.poll();    
                }
            }
        }
        Collections.sort(dis,(a,b) -> a.compareTo(b));

        String str ="";
        while(!kNeighbor.isEmpty())
            str = ","+kNeighbor.poll()[1].intValue()+ str;
        
        str = str.substring(1,str.length());
        return str;

    }
       
}

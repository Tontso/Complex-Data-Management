

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Node{

    private static final AtomicInteger count = new AtomicInteger(-1);
    private int isnonleaf;
    private int id;
    private Map<Integer,Double[]> data = new LinkedHashMap<Integer,Double[]>();
    


    public Node(int isnonleaf){
        this.isnonleaf = isnonleaf;
        id = count.incrementAndGet();  
    }


    public Node(String[] line) {
        isnonleaf = Integer.parseInt(line[0]);
        id = Integer.parseInt(line[1]);
        Double[] tmp;
        for (int i = 2; i < line.length; i+=5) {
            tmp = new Double[4];
            tmp[0] = Double.parseDouble(line[i+1]);
            tmp[1] = Double.parseDouble(line[i+2]);
            tmp[2] = Double.parseDouble(line[i+3]);
            tmp[3] = Double.parseDouble(line[i+4]);
            
            data.put(Integer.parseInt(line[i]),tmp);
        }
    }


    public Map<Integer, Double[]> getData() {
        return data;
    }


    public int getId(){
        return id;
    }

    public int getIsnonleaf() {
        return isnonleaf;
    }

    
    public Double[] findNodeMBR(){
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

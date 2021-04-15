package Exercise_1;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Node{

    private static final AtomicInteger count = new AtomicInteger(-1);
    private int isnonleaf;
    private int id;
    private Map<Integer,Double[]> data = new LinkedHashMap<Integer,Double[]>();
    private double distance = 1000;
    


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


    public double getDistance() {
        return distance;
    }

    public void setDistance(Double[] mbr, Double[] point) {
        this.distance = distanceBoxParticle2D(point[0], point[1], mbr[0],mbr[2], mbr[1], mbr[3]);
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

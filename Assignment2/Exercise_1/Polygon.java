package Exercise_1;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private int id;
    private List<Double[]> coordinates;
    private double[] mbr = new double[4]; // [x-low, x-high, y-low, y-high]
    private double[] center = new double[2];


    public Polygon(int id, List<Double[]> coordinates){
        this.id = id;
        this.coordinates = new ArrayList<>(coordinates);
    }


    public int getId(){
        return id;
    }
    
    public List<Double[]> getCoordinates(){
        return coordinates;
    }


    public void findMBR() {
        mbr[0] = coordinates.get(0)[0];
        mbr[1] = coordinates.get(0)[0];
        mbr[2] = coordinates.get(0)[1];
        mbr[3] = coordinates.get(0)[1];
        for (Double[] array : coordinates) {
            //System.out.println("x:"+array[0] +"  y:"+array[1]);
            // x-low -- x-high
            if(array[0]<mbr[0]){
                mbr[0] = array[0];
            }else if(array[0] > mbr[1]){
                mbr[1] = array[0];
            }
            
            // y-low -- y-high
            if(array[1]< mbr[2]){
                mbr[2] = array[1];
            }else if(array[1] > mbr[3]){
                mbr[3] = array[1];
            }
        }
        /* for (int i = 0; i < mbr.length; i++) {
            System.out.println(mbr[i]);
        } */
    }


    public void findCenter() {
        center[0] = (mbr[0]+mbr[1])/2; // x
        center[1] = (mbr[2]+mbr[3])/2; // y
    }
}

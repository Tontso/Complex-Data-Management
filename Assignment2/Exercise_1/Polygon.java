package Exercise_1;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private int id;
    private List<Double[]> coordinates;
    private Double[] mbr = new Double[4]; // [x-low, x-high, y-low, y-high]
    private double[] center = new double[2];
    private String zOrderCode;


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


    public String getzOrderCode() {
        return zOrderCode;
    }

    public Double[] getMbr() {
        return mbr;
    }

    public double[] getCenter(){
        return center;
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


    public void findzOrderCode() {
        double tmp, x, y;
        double lat = center[0];
        double lng = center[1];
        String morton_code = "";
        int digit = 0;
        List<Double> divisors = new ArrayList<>();

        for (int i = 0; i < 32; i++) {
            tmp = 180/Math.pow(2, i);
            divisors.add(tmp);
        }
        
        if (lng > 180)
            x = (lng % 180) + 180.0;
        else if (lng < -180)
            x = (-((-lng) % 180)) + 180.0;
        else
            x = lng + 180.0;
        if (lat > 90)
            y = (lat % 90) + 90.0;
        else if (lat < -90)
            y = (-((-lat) % 90)) + 90.0;
        else
            y = lat + 90.0;

        for (double dx : divisors){
            digit = 0;
            if (y >= dx){
                digit |= 2;
                y -= dx;
            }
            if (x >= dx){
                digit |= 1;
                x -= dx;
            }
            morton_code +=""+digit;
        }
        zOrderCode = morton_code;
    }


    
}

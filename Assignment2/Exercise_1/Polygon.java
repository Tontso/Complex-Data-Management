package Exercise_1;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private int id;
    private List<Double[]> coordinates;
    private int mbr;


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
}

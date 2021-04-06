package Exercise_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


class Main{
    private static Scanner fileScannerCoord;
    private static Scanner fileScannerOffset;
    private static List<Polygon> allPolygons = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        fileScannerOffset = new Scanner(new File("Exercise_1\\offsets.txt"));
        fileScannerCoord = new Scanner(new File("Exercise_1\\coords.txt"));
        String[] words;
        List<Double[]> tmpCoord = new ArrayList<>(); 
        while(fileScannerOffset.hasNext()){
            words = fileScannerOffset.nextLine().split(",");
            double dist = Double.parseDouble(words[2]) - Double.parseDouble(words[1]);
            for (int i = 0; i <= dist; i++) {
                tmpCoord.add(Stream.of(fileScannerCoord.nextLine().split(",")).map(Double::valueOf).toArray(Double[]::new));
            }
            allPolygons.add(new Polygon(Integer.parseInt(words[0]), tmpCoord));
            tmpCoord.clear();
        }

        for (Polygon pol: allPolygons){
            if(pol.getId() == 1){
                for (Double[] xy : pol.getCoordinates()) {
                    System.out.println("x,y is:"+xy[0]+" : "+xy[1]);
                }
            }
                
        }
    }
}
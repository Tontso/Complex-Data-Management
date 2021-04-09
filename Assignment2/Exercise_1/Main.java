package Exercise_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


class Main{
    private static Scanner fileScannerCoord;
    private static Scanner fileScannerOffset;
    private static List<Polygon> allPolygons = new ArrayList<>();
    private static List<Polygon> testPolygons = new ArrayList<>();
    private static int M = 20;

    public static void main(String[] args) throws FileNotFoundException {
        fileScannerOffset = new Scanner(new File("Exercise_1\\offsets.txt"));
        fileScannerCoord = new Scanner(new File("Exercise_1\\coords.txt"));
        loadDataFromFile(); //load Data

        // Find MBR ,CENTER ,z-orderCode
        for (Polygon item : allPolygons){
            item.findMBR();
            item.findCenter();
            item.findzOrderCode();
        }
        //sort
        Collections.sort(allPolygons,(a,b) -> a.getzOrderCode().compareTo(b.getzOrderCode()));

        RTree myTree = new RTree(M);     //make R-Tree

        for (int i = 0; i < 10000; i++) {
            testPolygons.add(allPolygons.get(0));
        }
        //Insert data into R-Tree
        for (Polygon pol : testPolygons) {
            myTree.insert(pol);
        }
        myTree.checkForLimits();
        
        //myTree.PrintNodeSize();
        /* int mod;
        int div;
        mod = 10007%20;
        div = 10007/20;
        System.out.println("Mod: "+mod);
        System.out.println("Div: "+div); */

       
    }


    private static void loadDataFromFile(){
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
    }
}
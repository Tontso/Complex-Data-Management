// Tontso Tontsev
// AM 3168

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class Exercise1{
    private static Scanner fileScannerCoord;
    private static Scanner fileScannerOffset;
    private static List<Polygon> allPolygons = new ArrayList<>();
    private static RTree myTree;
    private static int M = 20;

    public static void main(String[] args) throws IOException {
        fileScannerOffset = new Scanner(new File("offsets.txt"));
        fileScannerCoord = new Scanner(new File("coords.txt"));
        FileWriter writer = new FileWriter(new File("Rtree.txt")); 
        
        // Load data from file
        loadDataFromFile(); 
      
        // Find MBR ,CENTER ,z-orderCode for every polygon
        for (Polygon item : allPolygons){
            item.findMBR();
            item.findCenter();
            item.findzOrderCode();
        }
        
        //sort all polygons
        Collections.sort(allPolygons,(a,b) -> a.getzOrderCode().compareTo(b.getzOrderCode()));

        //############################# TEST ###########################################
        /* for (int i = 0; i < 10001; i++) {
            testPolygons.add(new Polygon(i, allPolygons.get(0).getCoordinates()));
        }
        for (Polygon item : testPolygons){
            item.findMBR();
            item.findCenter();
            item.findzOrderCode();
        } */
        //##############################################################################

        
        if(allPolygons.size() >= (int)(M*0.4)){
            // load Data to R- Tree (leaf data)
            loadDataToRtree();

            // Construct R-Tree
            myTree.constructRTree(0);

            // Print and Write
            myTree.printTree();
            myTree.writeTreeToFile(writer);
            writer.close();
        }else{
            System.out.println("Too few elements: "+allPolygons.size()+" the limit is: "+(int)(M*0.4));
        }
    }


    private static void loadDataToRtree() {
        myTree = new RTree(M);     //make R-Tree
        //Insert data into R-Tree
        for (Polygon pol : allPolygons){
            myTree.insert(pol);
        } 
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
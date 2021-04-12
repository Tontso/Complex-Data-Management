package Exercise_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;

import Exercise_1.RTree;

public class Exercise2 {


    private static Scanner fileRqueries;
    private static Scanner fileRtree;

    public static void main(String[] args) throws FileNotFoundException {
        fileRqueries = new Scanner(new File("Exercise_2\\Rqueries.txt"));
        fileRtree = new Scanner(new File("Rtree.txt"));
        Double[] query ;
        RTree tree = new RTree();

        loadRtreeFromFile(fileRtree,tree);

        while(fileRqueries.hasNextLine()){
            query = Stream.of(fileRqueries.nextLine().split(" ")).map(Double::valueOf).toArray(Double[]::new);
            tree.checkQuery(query);
            break;
        }
    }

    private static void loadRtreeFromFile(Scanner fileRtree, RTree tree) {
        
        while(fileRtree.hasNextLine()){
            String[] line = fileRtree.nextLine().replace("[", "").replace("]", "").replace(" ", "").split(",");
            tree.cunstructRTreeFromFile(line);
        }
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Exercise3 {

    private static Scanner fileRtree;
    private static Scanner fileNNqueries;

    
    public static void main(String[] args) throws FileNotFoundException{
        fileRtree = new Scanner(new File("Rtree.txt"));
        fileNNqueries = new Scanner(new File("Exercise_1\\NNqueries.txt"));
        RTree tree = new RTree();
        int k = 10;
        Double[] query;
        int queryCount = 0;

        loadRtreeFromFile(fileRtree,tree);
        
        while(fileNNqueries.hasNext()){
            query = Stream.of(fileNNqueries.nextLine().split(" ")).map(Double::valueOf).toArray(Double[]::new);
            System.out.println((queryCount+": "+tree.bestFirstknn(query,tree, tree.listTree.get(tree.getListTree().size()-1), k)));
            queryCount++;
        }  
    }


    private static void loadRtreeFromFile(Scanner fileRtree, RTree tree) {
        
        while(fileRtree.hasNextLine()){
            String[] line = fileRtree.nextLine().replace("[", "").replace("]", "").replace(" ", "").split(",");
            tree.cunstructRTreeFromFile(line);
        }
    }
}

package Exercise_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Exercise3 {

    private static Scanner fileRtree;
    private static Scanner fileNNqueries;
    
    public static void main(String[] args) throws FileNotFoundException {
        
        fileRtree = new Scanner(new File("Rtree.txt"));
        fileNNqueries = new Scanner(new File("Exercise_1\\NNqueries.txt"));
        RTree tree = new RTree();
        int k = 10;
        Double[] query = {-86.7883, 32.3534};

        loadRtreeFromFile(fileRtree,tree);

        //distanceBoxParticle2D(2, 1, 1, 2, 4, 4);
        tree.bestFirstknn(query,tree, tree.listTree.get(tree.getListTree().size()-1), k);
        
        //System.out.println(distanceBoxParticle2D(0, 0, 1, 2, 4, 4)); ///(x, y, x_min, y_min, x_max, y_max)
        

    }


    private static void loadRtreeFromFile(Scanner fileRtree, RTree tree) {
        
        while(fileRtree.hasNextLine()){
            String[] line = fileRtree.nextLine().replace("[", "").replace("]", "").replace(" ", "").split(",");
            tree.cunstructRTreeFromFile(line);
        }
    }
}

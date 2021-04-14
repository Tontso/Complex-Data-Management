package Exercise_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Exercise3 {

    private static Scanner fileRtree;
    private static Scanner fileNNqueries;
    
    public static void main(String[] args) throws FileNotFoundException {
        
        fileRtree = new Scanner(new File("Rtree.txt"));
        fileNNqueries = new Scanner(new File("NNqueries.txt"));
        RTree tree = new RTree();

        loadRtreeFromFile(fileRtree,tree);

        distanceBoxParticle2D(2, 1, 1, 2, 4, 4);
        
        System.out.println(distanceBoxParticle2D(0, 0, 1, 2, 4, 4)); ///(x, y, x_min, y_min, x_max, y_max)
        

    }


    private static void loadRtreeFromFile(Scanner fileRtree, RTree tree) {
        
        while(fileRtree.hasNextLine()){
            String[] line = fileRtree.nextLine().replace("[", "").replace("]", "").replace(" ", "").split(",");
            tree.cunstructRTreeFromFile(line);
        }
    }

    private static double distanceBoxParticle2D(double x, double y, double x_min, double y_min, double x_max, double y_max){
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

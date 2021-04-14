package Exercise_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class Exercise2 {


    private static Scanner fileRqueries;
    private static Scanner fileRtree;

    public static void main(String[] args) throws FileNotFoundException {
        fileRqueries = new Scanner(new File("Exercise_1\\Rqueries.txt"));
        fileRtree = new Scanner(new File("Rtree.txt"));
        Double[] query ;
        RTree tree = new RTree();
        int queryCount = 0;

        loadRtreeFromFile(fileRtree,tree);

        while(fileRqueries.hasNextLine()){
            query = Stream.of(fileRqueries.nextLine().split(" ")).map(Double::valueOf).toArray(Double[]::new);
            printQueryResult(queryCount, RTree.checkQuery(query, tree, tree.listTree.get(tree.getListTree().size()-1)));
            queryCount++;
            tree.newListQuery();
        }
    }

    private static void printQueryResult(int queryCount,List<Integer> checkQuery) {
        if(!checkQuery.isEmpty()){
            System.out.print(queryCount+" ("+checkQuery.size()+"): ");
            for (int i = 0; i < checkQuery.size()-1; i++) {
                System.out.print(checkQuery.get(i)+",");
            }
            System.out.print(checkQuery.get(checkQuery.size()-1)+"\n");
        }else{
            System.out.println(queryCount+" (0):");
        }
    }

    private static void loadRtreeFromFile(Scanner fileRtree, RTree tree) {
        
        while(fileRtree.hasNextLine()){
            String[] line = fileRtree.nextLine().replace("[", "").replace("]", "").replace(" ", "").split(",");
            tree.cunstructRTreeFromFile(line);
        }
    }
}

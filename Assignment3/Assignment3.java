import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

class Assignment3{

    private static HashMap<Integer,ArrayList<Double>> data = new LinkedHashMap<>();

    static class CustomComparator implements Comparator<Double[]>{

        @Override
        public int compare(Double[] o1, Double[] o2) {
            if(o1[1] > o2[1])
                return 1;
            else if(o1[1] < o2[1])
                return -1;
            else
                return 0;
        }
    }

    static class AStarCustomComparator implements Comparator<Double[]>{

        @Override
        public int compare(Double[] o1, Double[] o2) {
            if(o1[2] > o2[2])
                return 1;
            else if(o1[2] < o2[2])
                return -1;
            else
                return 0;
        }
    }
    
    public static void main(String[] args) throws IOException {
        loadDataFromNodes();
        loadDataFromEdges();
        writeDataToFile();
        printPath(dijkstraAlgorithm(6754, 12482));
        printPath(AStarAlgorithm(6754, 12482));        
        
    }

    private static ArrayList<Integer> AStarAlgorithm(int start, int finish) {
        PriorityQueue<Double[]> pq = new PriorityQueue<>(new CustomComparator());
        HashMap<Integer,Double> sorthestPathDistance = new HashMap<>();
        HashMap<Integer,Integer> path = new HashMap<>();
        HashMap<Integer,Boolean> isVisited = new HashMap<>();
        HashMap<Integer, Double> totalDistance = new HashMap<>();
        double estimateDistance;
        int count = 0;
        ArrayList<Double[]> neighbors = new ArrayList<>();
        
        for (Map.Entry<Integer, ArrayList<Double>> entry : data.entrySet()){
            sorthestPathDistance.put(entry.getKey(), Double.POSITIVE_INFINITY);
            isVisited.put(entry.getKey(), false);
        }
        sorthestPathDistance.put(start, 0.0);
        pq.add(new Double[]{Double.valueOf(start), 0.0});
        
        Double[] tmp; // [0] -> node_id , [1]-> distance
        while(!pq.isEmpty()){
            tmp = pq.poll();
            count++; 
            isVisited.put((tmp[0]).intValue(), true);

            if(tmp[0].intValue() == finish){
                System.out.println("------------------------- A* Algorithm -------------------------");
                System.out.println("From:"+start+" to "+finish+" has distance:"+sorthestPathDistance.get(finish)+ " with "+count +" loops and the path is:");
                return showPath(path, start, finish);
            } 
            neighbors.clear();
            for (int i = 2; i < data.get(tmp[0].intValue()).size(); i+=2) {
                neighbors.add(new Double[]{Double.valueOf(data.get(tmp[0].intValue()).get(i)), data.get(tmp[0].intValue()).get(i+1)});
            }

            for (Double[] neighbor : neighbors) {
                boolean visited = isVisited.get(neighbor[0].intValue());
                if(!visited){
                    double spdsu = sorthestPathDistance.get(neighbor[0].intValue());
                    double spdsv = sorthestPathDistance.get(tmp[0].intValue());
                    double weight = neighbor[1];
                    double prev = spdsv + weight;
                    if(spdsu > prev){
                        spdsu = prev;
                        estimateDistance = prev + euclideanDistance(neighbor[0].intValue(), finish);
                        totalDistance.put(neighbor[0].intValue(), estimateDistance);
                        sorthestPathDistance.put(neighbor[0].intValue(), spdsu);
                        path.put(neighbor[0].intValue(), tmp[0].intValue());
                        updatePriorityQueue(pq, neighbor[0].intValue() , estimateDistance);
                    }
                }
            }
        }       
        return null;
    }


    private static double euclideanDistance(int from, int finish) {
        Double x1 = data.get(from).get(0);
        Double y1 = data.get(from).get(1);
        Double x2 = data.get(finish).get(0);
        Double y2 = data.get(finish).get(1);

        double res = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        //System.out.println(res);
        return res;
    }


    private static void printPath(ArrayList<Integer> path) {
        for (int i = 0; i < path.size()-1; i++) {
            System.out.print(path.get(path.size()-1-i)+" -> ");
        }
        System.out.println(path.get(0));
    }


    private static ArrayList<Integer> dijkstraAlgorithm(int start, int finish){
        PriorityQueue<Double[]> pq = new PriorityQueue<>(new CustomComparator());
        HashMap<Integer,Double> sorthestPathDistance = new HashMap<>();
        HashMap<Integer,Integer> path = new HashMap<>();
        HashMap<Integer,Boolean> isVisited = new HashMap<>();
        int count = 0;
        ArrayList<Double[]> neighbors = new ArrayList<>();
        
        for (Map.Entry<Integer, ArrayList<Double>> entry : data.entrySet()){
            sorthestPathDistance.put(entry.getKey(), Double.POSITIVE_INFINITY);
            isVisited.put(entry.getKey(), false);
        }
        sorthestPathDistance.put(start, 0.0);
        pq.add(new Double[]{Double.valueOf(start), 0.0});
        
        Double[] tmp; // [0] -> node_id , [1]-> distance
        while(!pq.isEmpty()){
            tmp = pq.poll();
            count++; 
            isVisited.put((tmp[0]).intValue(), true);

            if(tmp[0].intValue() == finish){
                System.out.println("From:"+start+" to "+finish+" has distance:"+sorthestPathDistance.get(finish)+ " with "+count +" loops and the path is:");
                return showPath(path, start, finish);
            } 
            neighbors.clear();
            for (int i = 2; i < data.get(tmp[0].intValue()).size(); i+=2) {
                neighbors.add(new Double[]{Double.valueOf(data.get(tmp[0].intValue()).get(i)), data.get(tmp[0].intValue()).get(i+1)});
            }

            for (Double[] neighbor : neighbors) {
                boolean visited = isVisited.get(neighbor[0].intValue());
                if(!visited){
                    double spdsu = sorthestPathDistance.get(neighbor[0].intValue());
                    double spdsv = tmp[1];
                    double weight = neighbor[1];
                    if(spdsu > spdsv +  weight){
                        spdsu = spdsv + weight;
                        sorthestPathDistance.put(neighbor[0].intValue(), spdsu);
                        path.put(neighbor[0].intValue(), tmp[0].intValue());
                        updatePriorityQueue(pq, neighbor[0].intValue() , spdsu);
                    }
                }
            }
        }       
        return null;
    }


    private static ArrayList<Integer> showPath(HashMap<Integer, Integer> path, int start, int finish) {
        ArrayList<Integer> returnedPath = new ArrayList<>();
        int currentNode = finish;
        returnedPath.add(finish);
        while(currentNode != start){
            returnedPath.add(path.get(currentNode));
            currentNode = path.get(currentNode);
        }
        return returnedPath;
    }


    private static void updatePriorityQueue(PriorityQueue<Double[]> pq, int nodeId, Double distance) {
        for (Double[] el : pq) {
            if(el[0].intValue() == nodeId){ 
                updatePQvalue(pq, nodeId, distance);
                return;
            }
        }
        pq.add(new Double[]{Double.valueOf(nodeId), distance});
    }


    private static void updatePQvalue(PriorityQueue<Double[]> pq, int nodeId, Double distance) {
        ArrayList<Double[]> tempArray = new ArrayList<>();
        Double[] tmp;
        while(!pq.isEmpty()){
            tmp = pq.poll();
            if(tmp[0].intValue() == nodeId){
                pq.add(new Double[]{Double.valueOf(tmp[0]), distance});
                break;
            }else{
                tempArray.add(tmp);
            }
        }
        for (Double[] element : tempArray) {
            pq.add(element);
        }
    }


    private static void writeDataToFile() throws IOException {
        FileWriter myWriter = new FileWriter("output.txt");
        for (Map.Entry<Integer, ArrayList<Double>> entry : data.entrySet()){
            myWriter.write(entry.getKey()+"");
            myWriter.write(" "+entry.getValue().get(0));
            myWriter.write(" "+entry.getValue().get(1));
            for (int i = 2; i < entry.getValue().size(); i++) {
                if(i%2 == 0)
                    myWriter.write(" "+entry.getValue().get(i).intValue());
                else
                    myWriter.write(" "+entry.getValue().get(i));
            }
            myWriter.write("\n");
        }
        myWriter.close();
                             
    }
    

    private static void loadDataFromNodes() throws FileNotFoundException{
        Scanner myReaderNodes = new Scanner(new File("nodes"));

        while(myReaderNodes.hasNextLine()){
            String[] line = myReaderNodes.nextLine().split(" ");
            data.put(Integer.parseInt(line[0]), new ArrayList<>());
            for (int i = 1; i < line.length; i++) {
                data.get(Integer.parseInt(line[0])).add(Double.parseDouble(line[i]));
            }
        }
    }


    private static void loadDataFromEdges() throws FileNotFoundException{
        Scanner myReaderNodes = new Scanner(new File("edges"));

        while(myReaderNodes.hasNextLine()){
            String[] line = myReaderNodes.nextLine().split(" ");
            // 0 -> 1
            data.get(Integer.parseInt(line[1])).add(Double.parseDouble(line[2]));
            data.get(Integer.parseInt(line[1])).add(Double.parseDouble(line[3]));
            
            // 1 -> 0
            data.get(Integer.parseInt(line[2])).add(Double.parseDouble(line[1]));
            data.get(Integer.parseInt(line[2])).add(Double.parseDouble(line[3]));
        }
    }
}

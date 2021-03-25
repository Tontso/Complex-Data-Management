package Exercise_5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws IOException{


        List<String[]> arrayList = new ArrayList<String[]>();
        Scanner fileReader = new Scanner(new File("R.tsv"));
        FileWriter myWriter = new FileWriter("Rgroupby.tsv");

        while(fileReader.hasNext()){
            arrayList.add(fileReader.nextLine().split("\\s+"));
        }

        //List<Integer> mySortedList = sorted(numbers);
        sort(arrayList);
        for (String[] line : arrayList){
            myWriter.write(line[0]+"\t"+line[1]+"\n");
        }
        myWriter.close();
        
    }


    public static void sort(List<String[]> list) {
        if (list.size() < 2) {
          return;
        }
        int mid = list.size()/2;
        List<String[]> left = new ArrayList<String[]>(list.subList(0, mid));
        List<String[]> right = new ArrayList<String[]>(list.subList(mid, list.size()));
    
        sort(left);
        sort(right);
        merge(left, right, list);
    }
    

    private static void merge(List<String[]> left, List<String[]> right, List<String[]> list) {
        int same = 0;
        int leftIndex = 0;
        int rightIndex = 0;
        int listIndex = 0;
        
        while(list.size() > right.size() + left.size()) {
            list.remove(list.size()-1);
        }
        
        // have done delete
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex)[0].equals(right.get(rightIndex)[0])){
                left.get(leftIndex)[1] = (Integer.parseInt(left.get(leftIndex)[1]) + Integer.parseInt(right.get(rightIndex)[1]))+"";;
                list.set(listIndex++, left.get(leftIndex++));
                rightIndex++;
                same++;
            }else if (left.get(leftIndex)[0].compareTo(right.get(rightIndex)[0]) < 0) {
                list.set(listIndex++, left.get(leftIndex++));
            }else {
                list.set(listIndex++, right.get(rightIndex++));
            }
        }
        for (int i = 0; i < same; i++) {
            list.remove(list.size()-1);
        }

        while (leftIndex < left.size()) {
          list.set(listIndex++, left.get(leftIndex++));
        }
        while (rightIndex < right.size()) {
          list.set(listIndex++, right.get(rightIndex++));
        }
    }

}
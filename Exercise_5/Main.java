package Exercise_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    //private static ArrayList<Integer> same = new ArrayList<>();
    //private static int same = 0;

    public static void sort(List<Integer> list) {
        if (list.size() < 2) {
          return;
        }
        int mid = list.size()/2;
        List<Integer> left = new ArrayList<Integer>(list.subList(0, mid));
        List<Integer> right = new ArrayList<Integer>(list.subList(mid, list.size()));
    
        sort(left);
        sort(right);
        merge(left, right, list, mid);
    }
    

    private static void merge(List<Integer> left, List<Integer> right, List<Integer> list, int mid) {
        int same = 0;
        int leftIndex = 0;
        int rightIndex = 0;
        int listIndex = 0;
        
        while(list.size() > right.size() + left.size()){
            list.remove(list.size()-1);
        }
        
        // have done delete
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) == right.get(rightIndex)) { 
                list.set(listIndex++, left.get(leftIndex++));
                rightIndex++;
                same++;
            }else if (left.get(leftIndex) < right.get(rightIndex)) {
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



    /* 
    public static List<Integer> sorted(List<Integer> list) {
        if (list.size() < 2) {
          return list;
        }
        int mid = list.size()/2;
        return merged(
        sorted(list.subList(0, mid)), 
        sorted(list.subList(mid, list.size())));
    }
    
    private static List<Integer> merged(List<Integer> left, List<Integer> right) {
        int leftIndex = 0;
        int rightIndex = 0;
        List<Integer> merged = new ArrayList<Integer>();
    
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) == right.get(rightIndex)) {
                leftIndex++;
                //merged.remove(left.get(leftIndex));
                continue;
            }  
            if (left.get(leftIndex) < right.get(rightIndex)) {
                merged.add(left.get(leftIndex++));
            }else {
                merged.add(right.get(rightIndex++));
            } 
        }
        merged.addAll(left.subList(leftIndex, left.size()));
        merged.addAll(right.subList(rightIndex, right.size()));
        return merged;
    } */
  


    public static void main(String args[])
    {
        //int arr[] = { 12, 5, 11, 13, 5, 6, 7, 5 };
        List<Integer> numbers = new ArrayList<Integer>(Arrays.asList(2,1,1,4,11,10,3,1,2,3,4,12,12,14));
 
        //List<Integer> mySortedList = sorted(numbers);
        sort(numbers);
        for (Integer integer : numbers) {
            System.out.println(integer);
        }
        
    }
}
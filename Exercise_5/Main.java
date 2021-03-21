package Exercise_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

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
                merged.remove(left.get(leftIndex++));
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
    }


    public static void main(String args[])
    {
        //int arr[] = { 12, 5, 11, 13, 5, 6, 7, 5 };
        List<Integer> numbers = new ArrayList<Integer>(Arrays.asList(12, 11, 13, 5, 6, 7));
 
        List<Integer> mySortedList = sorted(numbers);
        for (Integer integer : mySortedList) {
            System.out.println(integer);
        }

        
    }
}
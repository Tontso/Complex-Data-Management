package Exercise_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    private static ArrayList<String[]> bufferR = new ArrayList<String[]>();
    private static ArrayList<String[]> bufferS = new ArrayList<String[]>();
    private static Scanner fileReaderR;
    private static Scanner fileReaderS;

    public static void main(String[] args) throws IOException{
        fileReaderR = new Scanner(new File("R_sorted.tsv"));
        fileReaderS = new Scanner(new File("S_sorted.tsv"));
        FileWriter myWriter = new FileWriter("TestFile.txt");


        while(fileReaderR.hasNext()){
            bufferR.add(fileReaderR.nextLine().split("\\s+"));
        }
        while(fileReaderS.hasNext()){
            bufferS.add(fileReaderS.nextLine().split("\\s+"));
        }
        for (String[] string : bufferR) {
            for (String[] string2 : bufferS) {
                if(string[0].equals(string2[0])){
                    myWriter.write(string[0]+" : "+string[1]+" : "+string2[1]+"\n");
                }
            }
        }
       myWriter.close();
    }
}

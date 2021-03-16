package Exercise_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestTheFiles {
    
    public static void main(String[] args) throws FileNotFoundException{
       Scanner fileReaderR = new Scanner(new File("TestFile.txt"));
       Scanner fileReaderS = new Scanner(new File("myFile.txt"));

        String linetest1;
        String linetest2;
       while(fileReaderR.hasNext()){
           linetest1 = fileReaderR.nextLine();
           linetest2 = fileReaderS.nextLine();
           if(!linetest1.equals(linetest2)){
               System.out.println("There is problem.");
               System.exit(0);
           }
       }
       System.out.println("Test pass successful !");
    }
}

package Exercise_3;
// Tontso Tontsev
// AM 3168

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    private static Scanner fileReaderR;
    private static Scanner fileReaderS;
    private static FileWriter myWriter;
    private static String[] wordsR = {"null","null"};
    private static String[] wordsS = {"null","null"};
    private static String[] prevR = {"null","null"};
    private static String[] prevS = {"null","null"};

    public static void main(String[] args) throws IOException{
        String readFrom;

        fileReaderR = new Scanner(new File("R_sorted.tsv"));
        fileReaderS = new Scanner(new File("S_sorted.tsv"));
        myWriter = new FileWriter("RintersectionS.tsv");

        if(!fileReaderR.hasNextLine() || !fileReaderS.hasNextLine()){
            System.out.print("One file is empty!");
            myWriter.close();
            System.exit(0);
        }

        wordsR = fileReaderR.nextLine().split("\\s+");
        wordsS = fileReaderS.nextLine().split("\\s+");

        prevR = wordsR;
        prevS = wordsS;
        while(true){
            
            readFrom =  compareTwoStrings(wordsR,wordsS);
            if(readFrom.equals("readFromS")){
                //System.out.println("Read from S.");
                wordsS = checkForSameLinesInS();
            }else if(readFrom.equals("readFromR")){
                //System.out.println("Read from R.");
                wordsR = checkForSameLinesInR();
            }else{
                //System.out.println("Read from S and R.");
                wordsS = checkForSameLinesInS();
                wordsR = checkForSameLinesInR();
            }

        }
    }

    
    private static String[] checkForSameLinesInS() throws IOException {
        while(fileReaderS.hasNextLine()){
            wordsS = fileReaderS.nextLine().split("\\s+");
            if(!(prevS[0].equals(wordsS[0]) && prevS[1].equals(wordsS[1]))){
                prevS = wordsS;
                return wordsS;
            }
        }
        myWriter.close();
        System.exit(0);

        // This return will never execute, it is just for the compiler
        return wordsS;
    }


    private static String[] checkForSameLinesInR() throws IOException {
        while(fileReaderR.hasNextLine()){
            wordsR = fileReaderR.nextLine().split("\\s+");
            if(!(prevR[0].equals(wordsR[0]) && prevR[1].equals(wordsR[1]))){
                prevR = wordsR;
                return wordsR;
            }
        }
        myWriter.close();
        System.exit(0);
        // This return will never execute, it is just for the compiler
        return wordsR;
    }


    private static String compareTwoStrings(String[] wordsR, String[] wordsS) throws IOException {

        // aa -- aa
        if(wordsR[0].equals(wordsS[0])) {
            if(wordsR[1].equals(wordsS[1])){      
                myWriter.write(wordsR[0] +"\t"+ wordsR[1]+"\n");
                return "readFromBoth";
            }else if(Integer.parseInt(wordsR[1]) < Integer.parseInt(wordsS[1])){
                return "readFromR";
            }else{
                return "readFromS";
            }
        // aa --  ab    
        }else if(wordsR[0].compareTo(wordsS[0]) < 0){
            return "readFromR";
        // ab -- aa    
        }else{
            return "readFromS";
        }
    }
}

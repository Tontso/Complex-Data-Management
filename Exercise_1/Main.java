package Exercise_1;
// Tontso Tontsev
// AM 3168
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main{

    private static ArrayList<String> buffer = new ArrayList<String>();
    private static Scanner fileReaderR;
    private static Scanner fileReaderS;
    private static FileWriter myWriter;
    private static String[] wordsR = {"",""};
    private static String[] wordsS = {"",""};
    private static String[] prevR = {"null","null"};
    private static int maxBufferSize = -1;
    
    public static void main(String[] args) throws IOException{
        String readFrom= "";

        fileReaderR = new Scanner(new File("Exercise_1\\TestFile1.txt"));
        fileReaderS = new Scanner(new File("Exercise_1\\TestFile2.txt"));
        myWriter = new FileWriter("Ex1.txt");

        if(!( fileReaderR.hasNextLine() || fileReaderS.hasNextLine())){
            System.out.println("One of the files is empty.");
            myWriter.close();
            System.exit(0);
        }

        wordsR = fileReaderR.nextLine().split("\\s+");
        wordsS = fileReaderS.nextLine().split("\\s+");

        prevR = wordsR;
        while(true){

            readFrom =  compareTwoStrings(wordsR,wordsS);
            if(readFrom.equals("readFromS")){
                if(fileReaderS.hasNextLine()){
                    wordsS = fileReaderS.nextLine().split("\\s+");
                }else{
                    readOnlyFromR();
                }
            }else if(readFrom.equals("readFromR")){
                wordsR = checkForSameLinesInR();
            }else{
                if(fileReaderS.hasNextLine()){
                    wordsS = fileReaderS.nextLine().split("\\s+");
                }else{
                    readOnlyFromR();
                }
                wordsR = checkForSameLinesInR();
            }
        }       
    }


    private static String compareTwoStrings(String[] wordsR, String[] wordsS) throws IOException {

        // aa -- aa
        if(wordsR[0].equals(wordsS[0])) {    
            buffer.add(wordsS[1]);      
            return "readFromS";
            
        // aa --  ab    
        }else if(wordsR[0].compareTo(wordsS[0]) < 0){
            merge(wordsR);
            return "readFromR";

        // ab -- aa    
        }else{
            return "readFromS";
        }
    }


    private static String[] checkForSameLinesInR() throws IOException {
        while(fileReaderR.hasNextLine()){
            wordsR = fileReaderR.nextLine().split("\\s+");
            if(!prevR[0].equals(wordsR[0])){
                prevR = wordsR;
                keepMaxBufferSize();
                return wordsR;
            }
            merge(wordsR);
        }
        System.out.println("Max Buffer: "+maxBufferSize);
        myWriter.close();
        System.exit(0);

        // This return will never execute, it is just for the compiler
        return wordsR;
    }


    private static void keepMaxBufferSize() {
        if(buffer.size() > maxBufferSize){
            maxBufferSize = buffer.size();
        }
        buffer.clear();
    }


    private static void readOnlyFromR() throws IOException {
        String[] prev = wordsR;
        merge(wordsR);
        while(fileReaderR.hasNextLine()){
            wordsR = fileReaderR.nextLine().split("\\s+");
            if(prev[0].equals(wordsR[0])){
                prev = wordsR;
                merge(wordsR);
            }else{
                break;
            }
        }
        System.out.println("Max Buffer: "+maxBufferSize);
        myWriter.close();
        System.exit(0);
    }


    private static void merge(String[] words) throws IOException {
        if(!buffer.isEmpty()){
            for (String string : buffer) {
                myWriter.write(words[0] + "\t" +words[1] + "\t" +string + "\n");      
            }
        }
    }
}
package Exercise_2;


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

        fileReaderR = new Scanner(new File("Exercise_2\\testFileEx2.txt"));
        fileReaderS = new Scanner(new File("Exercise_2\\testFile2Ex2.txt"));
        myWriter = new FileWriter("myFileExercise2.txt");
        
        // Read first Line
        if(!fileReaderR.hasNextLine() && !fileReaderS.hasNextLine()){
            System.out.print("Both files are empty!");
            System.exit(0);
        }

        if(fileReaderR.hasNextLine()){
            if(!fileReaderS.hasNextLine()){
                System.out.println("Second file is empty!");
                wordsR = fileReaderR.nextLine().split("\\s+");
                readOnlyFrom(fileReaderR, wordsR);
                System.exit(0);
            }
            wordsR = fileReaderR.nextLine().split("\\s+");
        }
        if(fileReaderS.hasNextLine()){
            if(!fileReaderR.hasNextLine()){
                System.out.println("First file is empty!");
                wordsS = fileReaderS.nextLine().split("\\s+");
                readOnlyFrom(fileReaderS, wordsS);
                System.exit(0);
            }
            wordsS = fileReaderS.nextLine().split("\\s+");
        }
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
        readOnlyFrom(fileReaderR,wordsR);
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
        readOnlyFrom(fileReaderS,wordsS);
        myWriter.close();
        System.exit(0);
        // This return will never execute, it is just for the compiler
        return wordsR;
    }



    private static void readOnlyFrom(Scanner fileReader, String[] words) {
        String[] prev = words;
        if(!(prev[0].equals(wordsR[0]) && prev[0].equals(wordsS[0]) && prev[1].equals(wordsR[1]) && prev[1].equals(wordsS[1]) )){
            System.out.println(prev[0] +" : " + prev[1]);
        }
        while(fileReader.hasNextLine()){
            words = fileReader.nextLine().split("\\s+");
            if(!(prev[0].equals(words[0]) && prev[1].equals(words[1]))){
                prev = words;
                System.out.println(words[0] +" : " + words[1]);
            }
        }
    }


    private static String compareTwoStrings(String[] wordsR, String[] wordsS) {

        // aa -- aa
        if(wordsR[0].equals(wordsS[0])) {
            if(Integer.parseInt(wordsR[1]) < Integer.parseInt(wordsS[1])){
                System.out.println(wordsR[0] +" : "+ wordsR[1]);
                return "readFromR";
            }else if(Integer.parseInt(wordsR[1]) > Integer.parseInt(wordsS[1])){
                System.out.println(wordsS[0] +" : "+ wordsS[1]);
                return "readFromS";
            }else{
                System.out.println(wordsR[0] +" : "+ wordsR[1]);
                return "readFromBoth";
            }
        // aa --  ab    
        }else if(wordsR[0].compareTo(wordsS[0]) < 0){
            System.out.println(wordsR[0] +" : "+ wordsR[1]);
            return "readFromR";
        // ab -- aa    
        }else{
            System.out.println(wordsS[0] +" : "+ wordsS[1]);
            return "readFromS";
        }
    }
}

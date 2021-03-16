package Exercise_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class Hello{

    private static ArrayList<String> buffer = new ArrayList<String>();
    private static Scanner fileReaderR;
    private static Scanner fileReaderS;
    private static FileWriter myWriter;

    public static void main(String[] args) throws IOException{
        
        fileReaderR = new Scanner(new File("R_sorted.tsv"));
        fileReaderS = new Scanner(new File("S_sorted.tsv"));
        myWriter = new FileWriter("myFile.txt");

        String[] wordsR = {"",""};;
        String[] wordsS = {"",""};;
        String[] prevR = {"null","null"};
        Boolean readFromR = true;
        Boolean readFromS = true;

        while(fileReaderR.hasNextLine() || fileReaderS.hasNextLine()){

            // File R
            if(fileReaderR.hasNext() && readFromR){
                prevR[0] = wordsR[0];
                prevR[1] = wordsR[1];
                wordsR = fileReaderR.nextLine().split("\\s+");
                while(prevR[0].equals(wordsR[0]) && fileReaderR.hasNext()){
                    merge(wordsR);
                    wordsR = fileReaderR.nextLine().split("\\s+");
                }
                if(!fileReaderR.hasNext()){
                    lastwordOfR(wordsR,wordsS,fileReaderS);
                    break;
                }
                buffer.clear();
                readFromR = false;
            }

            // File S
            if(readFromS){
                if(fileReaderS.hasNext()){
                    wordsS = fileReaderS.nextLine().split("\\s+");
                    readFromS = false;

                // last word from S
                }else{
                    lastwordOfS(wordsR, wordsS, fileReaderR);
                    break;
                }
            }

            if(wordsR[0].equals(wordsS[0])){
                buffer.add(wordsS[1]);
                readFromS = true;
            }else if (wordsR[0].compareTo(wordsS[0]) > 0){
                readFromR = false;
                readFromS = true;
            }else{
                merge(wordsR);
                readFromR = true;
                
            } 
            
        }
        myWriter.close();         
    }



    private static void lastwordOfS(String[] wordsR, String[] wordsS, Scanner fileReaderR2) {
        if(wordsR[0].equals(wordsS[0])){
            merge(wordsR);
        }
        String[] prev = wordsR;
        while(fileReaderR2.hasNext()){
            wordsR = fileReaderR2.nextLine().split("\\s+");
            if(prev[0].equals(wordsR[0])){              
                merge(wordsR);
            }else{
                break;
            }
        }
    }



    private static void lastwordOfR(String[] wordsR, String[] wordsS, Scanner fileReaderS2) {
        if(wordsR[0].equals(wordsS[0])){
            buffer.add(wordsS[1]);
        }
        while(fileReaderS2.hasNext()){
            wordsS = fileReaderS.nextLine().split("\\s+");
            if(wordsR[0].equals(wordsS[0])){
                buffer.add(wordsS[1]);
            }
        }
        merge(wordsR);
    }


    private static void merge(String[] words) {
        if(!buffer.isEmpty()){
            for (String string : buffer) {
                
                try {
                    myWriter.write(words[0] + " : " +words[1] + " : " +string + "\n");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
                
                //System.out.println(words[0] + " : " +words[1] + " : " +string + "\n");          
            }
             
        }
    }
}
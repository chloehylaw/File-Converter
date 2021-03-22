import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ConvertFiles {
    static Scanner kb = new Scanner(System.in);
    public static void main(String[] args) {
        String input1;
        String input2;
        String output1;
        String output2;
        String userOutput;

        Scanner sc = null;
        PrintWriter pt = null;
        BufferedReader br = null;

        // Check first input file
        System.out.println("Please enter the name of the first input file you wish to open.");
        input1 = kb.next();
        try{
            sc = new Scanner(new FileInputStream(input1));
        }
        catch(FileNotFoundException e){
            System.out.println("Could not open input file " + input1 + " for reading. \n" +
                    "Check if file exists! Program will terminate after closing any opened files.");
            e.printStackTrace();
            assert false;
            sc.close();
            System.exit(0);
        }
        System.out.println("The input file " + input1 + " is opened for reading.");

        // Check second input file
        System.out.println("Please enter the name of the second input file you wish to open.");
        input2 = kb.next();
        try{
            sc = new Scanner(new FileInputStream(input2));
        }
        catch(FileNotFoundException e){
            System.out.println("Could not open input file " + input2 + " for reading. \n" +
                    "Check if file exists! Program will terminate after closing any opened files.");
            e.printStackTrace();
            assert false;
            sc.close();
            System.exit(0);
        }
        System.out.println("The input file " + input2 + "is opened for reading.");

        // Check first output file
        System.out.println("Please enter the name of the first output file you wish to open.");
        output1 = kb.next();
        try{
            pt = new PrintWriter(new FileOutputStream(output1));
        } catch (FileNotFoundException e) {
            System.out.println("Could not open output file " + output1 +" for reading. \n" +
                    "Check if file exists! Program will terminate after closing any opened files.");
            e.printStackTrace();
            System.exit(0);
            try {
                Files.delete(Paths.get(output1));
                System.out.println(output1 +" has been deleted.");
            }
            catch(IOException ex){
                ex.printStackTrace();
                System.out.println(output1 + "has not be deleted.");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("The output file " + output1 + " is opened for writing.");


        // Check second output file
        System.out.println("Please enter the name of the second output file you wish to open.");
        output2 = kb.next();
        try{
            pt = new PrintWriter(new FileOutputStream(output2));
        } catch (FileNotFoundException e) {
            System.out.println("Could not open output file " + output2 + " for reading. \n" +
                    "Check if file exists! Program will terminate after closing any opened files.");
            e.printStackTrace();
            System.exit(0);
            try {
                Files.delete(Paths.get(output2));
                System.out.println(output2 +" has been deleted.");
            }
            catch(IOException ex){
                ex.printStackTrace();
                System.out.println(output2 + "has not be deleted.");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("The output file " + output2 + " is opened for writing.");


        // Ask user to enter the name of one of the created output files to display
        for(int i = 0; i < 2; i++) {
            System.out.println("Please enter the name of the output file you wish to open.");
            userOutput = kb.next();
            if(!(userOutput.equals(output1) || userOutput.equals(output2))){
                System.out.println("You have one last attempt to enter the name of the output file you wish to open.");
            }else{
                try{
                    displayFileContents(userOutput);
                }
                catch(Exception e){
                    System.out.println("Could not open output file " + userOutput +" for reading. \n" +
                            "Check if file exists! Program will terminate after closing any opened files.");
                    e.printStackTrace();
                    System.exit(0);
                }
                break;
            }
        }
    }

    private static void displayFileContents(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName));){
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("The output of the file");
                System.out.println(line);
            }
            br.close();
        }
        catch(IOException e){
            System.out.println("Could not open output file " + fileName +" for reading. \n" +
                    "Check if file exists! Program will terminate after closing any opened files.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void processFilesForValidation(String inputFile, String outputFile) throws CSVFileInvalidException {

    }

}

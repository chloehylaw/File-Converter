import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ConvertFiles {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter the number of files to convert to JSON: ");
        int numFiles = Integer.parseInt(kb.nextLine());
        String[] fileName = new String[numFiles];
        Scanner[] csv = new Scanner[numFiles];
        PrintWriter[] json = new PrintWriter[numFiles];

        for (int i = 0; i < numFiles; i++) {
            System.out.print("Enter the name of file " + (i + 1) + ": ");
            fileName[i] = kb.nextLine();
            try {
                csv[i] = new Scanner(new FileInputStream(fileName[i]));
            } catch (Exception e) {
                System.out.println("Could not open the file " + fileName[i] + " for reading.\n" +
                        "Please check if the file exists.\n" +
                        "The program will terminate after closing all opened files.\n");
                for (int j = i - 1; j >= 0; j--) {
                    csv[j].close();
                }
                System.exit(0);
            }
        }
        for (int i = 0; i < numFiles; i++) {
            String s = fileName[i].substring(0, fileName[i].length() - 3) + "json";
            try {
                json[i] = new PrintWriter(new FileOutputStream(s));
            } catch (FileNotFoundException e) {
                System.out.println("Could not create the file " + s + " for writing.\n" +
                        "The program will terminate after deleting all the files that were created.\n" +
                        "All opened files will be closed.\n");
                for (int j = i-1; j >= 0; j--)
                {
                    json[j].close();
                    try{
                        Files.deleteIfExists(Path.of(fileName[j].substring(0, fileName[j].length() - 3) + "json"));
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                    for (int k = i - 1; k >= 0; k--) {
                        csv[k].close();
                    }
                    System.exit(0);
                }
            }
        }
        for(int i = 0; i < numFiles; i++) {
            try {
                PrintWriter log = new PrintWriter(new FileOutputStream("log.txt", true));
                System.out.println("\nProcessing " + fileName[i]);
                processFilesForValidation(csv[i], json[i], log, fileName[i]);
                csv[i].close();
                json[i].close();
                log.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.deleteIfExists(Path.of("Car Rental Record no DrivLic.json"));
            Files.deleteIfExists(Path.of("Car Rental Record no Plate.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("These are the files available to open: ");
        for(int i = 0; i < numFiles; i ++){
            if(Files.exists(Path.of(fileName[i].substring(0, fileName[i].length()-3) + "json"))){
                System.out.println(fileName[i].substring(0, fileName[i].length()-3) + "json");
            }
        }
        displayFileContents();

    }

    private static void processFilesForValidation(Scanner csv, PrintWriter json, PrintWriter log, String inputFile){
        String line = csv.nextLine();
        File one = new File(line);
        File two = null;
        int countField = 0;
        int countLine = 0;
        int countInfo = 0;

        try{
            for(int i = 0; i < one.split.length; i++){
                if(one.split[i] == null){
                    countField++;
                }
            }
            if(countField > 0){
                throw new CSVFileInvalidException();
            }else{
                json.println("[");
                while(csv.hasNextLine()){
                    two = new File(csv.nextLine());
                    try{
                        for(int i = 0; i < two.split.length; i++){
                            if(two.split[i] == null){
                                countInfo++;
                                countLine = i;
                            }
                        }
                        if(countInfo > 0){
                            throw new CSVDataMissingException();
                        }else {
                            writeJson(one, two, json);
                        }
                    }catch(CSVDataMissingException e){
                        System.out.println("File " + inputFile + " is invalid: Line " + countLine + " contains Missing Data." );
                        System.out.println("File is not converted to JSON.");
                        System.out.println("If file was created, it has been deleted.");
                        writeLog(one, two, log, inputFile, countLine, 2);
                        countInfo = 0;
                    }
                }
                json.println("]");
            }
        }catch(CSVFileInvalidException e){
            System.out.println("File " + inputFile + " is invalid: Field is missing.");
            System.out.println("File is not converted to JSON.");
            System.out.println("If file was created, it has been deleted.");
            writeLog(one, two, log, inputFile, countField, 1);
        }
    }

    private static void displayFileContents(){
        String file;
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br2;

        for(int i = 0; i < 1; i++){
            try{
                System.out.print("\nEnter the name of the file created you want to display: ");
                file = br.readLine();
                try{
                    br2 = new BufferedReader((new FileReader(file)));
                }catch(FileNotFoundException e){
                    System.out.println("You have one last attempt to enter the name of the output file you wish to open.");
                    file = br.readLine();
                    br2 = new BufferedReader((new FileReader(file)));
                }
                line = br2.readLine();
                while(line != null){
                    System.out.println(line);
                    line = br2.readLine();
                }
                System.out.println("Everything in the file was displayed.\n");
            } catch(Exception e){
                System.out.println("Could not open the requested file for display.\n" +
                        "Check if file exists. The program will terminate after closing any opened files");
                System.exit(0);
            }
        }
    }

    private static void writeJson(File one, File two, PrintWriter json){
        json.println(" {");
        for(int i = 0; i < one.split.length; i++){
            json.println(" \"" + one.split[i] + "\": \"" + two.split[i] + "\",");
        }
        json.println(" },");
    }

    private static void writeLog(File one, File two, PrintWriter log, String inputFile, int count, int errorCode){
        if(errorCode == 1){
            log.println("File " + inputFile + " is invalid.");
            log.println("Missing field: " + (one.split.length - count) + " detected, " + count + " missing.");

            for(int i = 0; i < one.split.length - 1; i++) {
                if (one.split[i] != null) {
                    log.print(one.split[i] + ", ");
                } else {
                    log.print("***, ");
                }
            }
            if(one.split[one.split.length - 1] != null){
                log.println(one.split[one.split.length - 1]);
            }else{
                log.println("***");
            }
            System.out.println();
        }else{
            int line = 0;
            log.println("In file " + inputFile + " line " + count);
            for(int i = 0; i < two.split.length; i++){
                if(two.split[i] == null){
                    log.print("*** ");
                    line = i;
                }else{
                    log.print(two.split[i] + " ");
                }
            }
            log.println("\nMissing: " + one.split[line]);
        }
        log.println("____________________________________________\n");
    }
}

class File{
    String[] split;
    File(String line){
        split = adjustLine(line.split(","));
    }
    private String[] adjustLine(String[] info){
        String content;
        int countTokens = 0;
        int countFinalData = 0;

        for(int i = 0; i< info.length; i++){
            if(info[i] != ""){
                if(info[i].charAt(0) == '\"'){
                    for(int j = i + 1; j < info.length; j++){
                        if(info[j].charAt(info[j].length() - 1) == '\"'){
                            countTokens ++;
                            break;
                        }else{
                            countTokens ++;
                        }
                    }
                }
            }
        }
        String[] finalData = new String[info.length - countTokens];

        for (int i = 0; i<info.length; i++){
            if (info[i]!=""){
                if (info[i].charAt(0)=='\"') {
                    content = info[i].substring(1);
                    for (int j = i + 1; j < info.length; j++) {
                        if (info[j].charAt(info[j].length() - 1) == '\"') {
                            content = content + "," + info[j].substring(0, info[j].length() - 1);
                            i = j;
                            break;
                        } else {
                            content = content + "," + info[j];
                        }
                    }
                    finalData[countFinalData] = content;
                }
                else{
                    finalData[countFinalData] = info[i];
                }
            }
            countFinalData++;
        }
        return finalData;
    }

}

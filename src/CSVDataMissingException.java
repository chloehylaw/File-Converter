public class CSVDataMissingException extends InvalidException{
    public CSVDataMissingException(){
        super("Error: Cannot be parsed due to missing information.");
    }
    public CSVDataMissingException(String message){
        super(message);
    }
}

/**
 * CSVDataMissing Exception
 */
public class CSVDataMissingException extends InvalidException{
    /**
     * Default CSV data missing exception
     */
    public CSVDataMissingException(){
        super("Error: Cannot be parsed due to missing information.");
    }
    /**
     * Parameterized CSV data missing exception
     * @param message error message
     */
    public CSVDataMissingException(String message){
        super(message);
    }
}

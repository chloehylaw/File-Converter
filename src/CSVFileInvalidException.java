/**
 * CSVFileInvalidException
 */
public class CSVFileInvalidException extends InvalidException {
    /**
     * Default CSV file invalid exception
     */
    public CSVFileInvalidException(){
        super(" ");
    }
    /**
     * Parameterized CSV file invalid exception
     * @param message error message
     */
    public CSVFileInvalidException(String message){
        super(message);

    }
}

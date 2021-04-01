/**
 * InvalidException
 */
public class InvalidException extends Exception{
    /**
     * Default invalid exception
     */
    public InvalidException(){
        super(" ");
    }
    /**
     * Parameterized invalid exeception
     * @param message error message
     */
    public InvalidException(String message){
        super(message);
        System.out.println("Error: Input row cannot be parsed due to missing information.");
    }
}

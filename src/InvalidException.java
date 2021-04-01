public class InvalidException extends Exception{
    public InvalidException(){
        super(" ");
    }
    public InvalidException(String message){
        super(message);
        System.out.println("Error: Input row cannot be parsed due to missing information.");
    }
}

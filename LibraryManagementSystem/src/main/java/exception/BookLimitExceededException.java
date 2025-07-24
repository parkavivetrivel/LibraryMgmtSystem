package exception;

public class BookLimitExceededException extends Exception  {

	public BookLimitExceededException(String message) {
        super(message);
    }

}

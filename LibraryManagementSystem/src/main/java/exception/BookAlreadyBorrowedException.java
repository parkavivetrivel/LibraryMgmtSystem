package exception;

public class BookAlreadyBorrowedException extends Exception  {

	public BookAlreadyBorrowedException(String message) {
        super(message);
    }

}

package model;

public class Book {
	 private int bookId;
	    private String title;
	    private String author;
	    private String publisher;
	    private String isbn;
	    private int totalCopies;
	    private int availableCopies;

	public int getBookId() {
			return bookId;
		}

		public void setBookId(int bookId) {
			this.bookId = bookId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getPublisher() {
			return publisher;
		}

		public void setPublisher(String publisher) {
			this.publisher = publisher;
		}

		public String getIsbn() {
			return isbn;
		}

		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}

		public int getTotalCopies() {
			return totalCopies;
		}

		public void setTotalCopies(int totalCopies) {
			this.totalCopies = totalCopies;
		}

		public int getAvailableCopies() {
			return availableCopies;
		}

		public void setAvailableCopies(int availableCopies) {
			this.availableCopies = availableCopies;
		}
		 @Override
		    public String toString() {
		        return "Book {" +
		               "bookId=" + bookId +
		               ", title='" + title + '\'' +
		               ", author='" + author + '\'' +
		               ", publisher='" + publisher + '\'' +
		               ", isbn='" + isbn + '\'' +
		               ", totalCopies=" + totalCopies +
		               ", availableCopies=" + availableCopies +
		               '}';
		    }

	public Book() {
		// TODO Auto-generated constructor stub
	}

}

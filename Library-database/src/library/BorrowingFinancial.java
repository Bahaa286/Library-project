package library;

public class BorrowingFinancial {
	
	private String id;
    private String bookName;
    private String publisherName;
    private String userId;
    private String addingDate;
    private String financial;
    
    // constructor
	public BorrowingFinancial(String id, String bookName, String publisherName, String userId, String addingDate,
			String financial) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.publisherName = publisherName;
		this.userId = userId;
		this.addingDate = addingDate;
		this.financial = financial;
	}

	//////////////////////////////////////////////////
	// getters and setters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddingDate() {
		return addingDate;
	}

	public void setAddingDate(String addingDate) {
		this.addingDate = addingDate;
	}

	public String getFinancial() {
		return financial;
	}

	public void setFinancial(String financial) {
		this.financial = financial;
	}
	
	////////////////////////////////////////////////// 
}

package library;

import java.util.ArrayList;

public class Borrows {

	// class fields
	private String id; // borrows id
	private String name; // book name
	private String numberOfCopy; // number of copies
	private String price; // price
	private String borrowingPricePerDay; // borrowing price / day
	private String description; // book description
	private String dateOfPublish; // date of publish
	private String publisherName; // publisher name
	private ArrayList<String> authorName; // authors name
	private String employeeId; // employee id
	private String dateOfBorrow; // date of borrow
	private String endDateOfBorrow; // end date of borrow
	private String returnDate; // return date
	private String lateDays; // late days

	public Borrows() {
		authorName = new ArrayList<String>();
	}
	
	// constructor
	public Borrows(String id, String name, String numberOfCopy, String price, String borrowingPricePerDay, String description,
			String dateOfPublish, String publisherName, ArrayList<String> authorName, String employeeId,
			String dateOfBorrow, String endDateOfBorrow, String returnDate, String lateDays) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfCopy = numberOfCopy;
		this.price = price;
		this.borrowingPricePerDay = borrowingPricePerDay;
		this.description = description;
		this.dateOfPublish = dateOfPublish;
		this.publisherName = publisherName;
		this.authorName = authorName;
		this.employeeId = employeeId;
		this.dateOfBorrow = dateOfBorrow;
		this.endDateOfBorrow = endDateOfBorrow;
		this.returnDate = returnDate;
		this.lateDays = lateDays;
	} // end constructor

	// constructor
	public Borrows(String id, String name, String numberOfCopy, String price, String borrowingPricePerDay, String description,
			String dateOfPublish, String publisherName, String authorName, String employeeId, String dateOfBorrow,
			String endDateOfBorrow, String returnDate, String lateDays) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfCopy = numberOfCopy;
		this.price = price;
		this.borrowingPricePerDay = borrowingPricePerDay;
		this.description = description;
		this.dateOfPublish = dateOfPublish;
		this.publisherName = publisherName;

		this.authorName = new ArrayList<String>();
		this.authorName.add(authorName);

		this.employeeId = employeeId;
		this.dateOfBorrow = dateOfBorrow;
		this.endDateOfBorrow = endDateOfBorrow;
		this.returnDate = returnDate;
		this.lateDays = lateDays;
	} // end constructor

	// constructor
	public Borrows(String name, String numberOfCopy, String price, String borrowingPricePerDay, String description,
			String dateOfPublish, String publisherName, ArrayList<String> authorName, String employeeId,
			String dateOfBorrow, String endDateOfBorrow, String returnDate, String lateDays) {
		super();
		this.id = "0";
		this.name = name;
		this.numberOfCopy = numberOfCopy;
		this.price = price;
		this.borrowingPricePerDay = borrowingPricePerDay;
		this.description = description;
		this.dateOfPublish = dateOfPublish;
		this.publisherName = publisherName;
		this.authorName = authorName;
		this.employeeId = employeeId;
		this.dateOfBorrow = dateOfBorrow;
		this.endDateOfBorrow = endDateOfBorrow;
		this.returnDate = returnDate;
		this.lateDays = lateDays;
	} // end constructor

	// constructor
	public Borrows(String name, String numberOfCopy, String price, String borrowingPricePerDay, String description,
			String dateOfPublish, String publisherName, String authorName, String employeeId, String dateOfBorrow,
			String endDateOfBorrow, String returnDate, String lateDays) {
		super();
		this.id = "0";
		this.name = name;
		this.numberOfCopy = numberOfCopy;
		this.price = price;
		this.borrowingPricePerDay = borrowingPricePerDay;
		this.description = description;
		this.dateOfPublish = dateOfPublish;
		this.publisherName = publisherName;

		this.authorName = new ArrayList<String>();
		this.authorName.add(authorName);

		this.employeeId = employeeId;
		this.dateOfBorrow = dateOfBorrow;
		this.endDateOfBorrow = endDateOfBorrow;
		this.returnDate = returnDate;
		this.lateDays = lateDays;
	} // end constructor
	// ----------------------------------------------------------------//
	// generate setters and getters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumberOfCopy() {
		return numberOfCopy;
	}

	public void setNumberOfCopy(String numberOfCopy) {
		this.numberOfCopy = numberOfCopy;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBorrowingPricePerDay() {
		return borrowingPricePerDay;
	}

	public void setBorrowingPricePerDay(String borrowingPricePerDay) {
		this.borrowingPricePerDay = borrowingPricePerDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateOfPublish() {
		return dateOfPublish;
	}

	public void setDateOfPublish(String dateOfPublish) {
		this.dateOfPublish = dateOfPublish;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public ArrayList<String> getAuthorName() {
		return authorName;
	}

	public void setAuthorName(ArrayList<String> authorName) {
		this.authorName = authorName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getDateOfBorrow() {
		return dateOfBorrow;
	}

	public void setDateOfBorrow(String dateOfBorrow) {
		this.dateOfBorrow = dateOfBorrow;
	}

	public String getEndDateOfBorrow() {
		return endDateOfBorrow;
	}

	public void setEndDateOfBorrow(String endDateOfBorrow) {
		this.endDateOfBorrow = endDateOfBorrow;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getLateDays() {
		return lateDays;
	}

	public void setLateDays(String lateDays) {
		this.lateDays = lateDays;
	}

	// end getters and setters generating
	// ---------------------------------------------------------------------//

	// add author
	public void addauthorName(String authorName) {
		this.authorName.add(authorName);
	}

	// copy
	public void copy(Borrows borrow) {

		this.id = borrow.id;
		this.name = borrow.name;
		this.numberOfCopy = borrow.numberOfCopy;
		this.price = borrow.price;
		this.borrowingPricePerDay = borrow.borrowingPricePerDay;
		this.description = borrow.description;
		this.dateOfPublish = borrow.dateOfPublish;
		this.publisherName = borrow.publisherName;
		this.authorName = borrow.authorName;
		this.employeeId = borrow.employeeId;
		this.dateOfBorrow = borrow.dateOfBorrow;
		this.endDateOfBorrow = borrow.endDateOfBorrow;
		this.returnDate = borrow.returnDate;
		this.lateDays = borrow.lateDays;

	}

} // end Borrows class

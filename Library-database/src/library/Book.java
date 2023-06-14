package library;

import java.util.ArrayList;

// Book class
public class Book {

	// class fields
	private String name; // book name
	private String numberOfCopy; // number of copies
	private String price; // price
	private String borrowingPricePerDay; // borrowing price / day
	private String description; // book description
	private String dateOfPublish; // date of publish
	private String publisherName; // publisher name
	private ArrayList<String> authorName; // authors name

	// constructor
	public Book(String name, String numberOfCopy, String price, String borrowingPricePerDay, String description,
			String dateOfPublish, String publisherName, String authorName) {
		super();
		this.name = name;
		this.numberOfCopy = numberOfCopy;
		this.price = price;
		this.borrowingPricePerDay = borrowingPricePerDay;
		this.description = description;
		this.dateOfPublish = dateOfPublish;
		this.publisherName = publisherName;

		this.authorName = new ArrayList<String>();
		this.authorName.add(authorName);
	}

	// constructor
	public Book(String name, String numberOfCopy, String price, String borrowingPricePerDay, String description,
			String dateOfPublish, String publisherName, ArrayList<String> authorName) {
		super();
		this.name = name;
		this.numberOfCopy = numberOfCopy;
		this.price = price;
		this.borrowingPricePerDay = borrowingPricePerDay;
		this.description = description;
		this.dateOfPublish = dateOfPublish;
		this.publisherName = publisherName;
		this.authorName = authorName;
	}

	///////////////////////////////////////////////////////////////////
	// generate setters and getters

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
		return  authorName;
	}

	/////////////////////////////////////////////////////////////////////
	
	// copy
	public void copy(Borrows borrow) {

		this.name = borrow.getName();
		this.numberOfCopy = borrow.getNumberOfCopy();
		this.price = borrow.getPrice();
		this.borrowingPricePerDay = borrow.getBorrowingPricePerDay();
		this.description = borrow.getDescription();
		this.dateOfPublish = borrow.getDateOfPublish();
		this.publisherName = borrow.getPublisherName();
		this.authorName = borrow.getAuthorName();
	}
	
	// add author name Stringo list
	public void addAuthorName(String authorName) {
		this.authorName.add(authorName);
	}
	
	// delete author name
	public String deleteAuthorName(String authorName) {
		
		// remove
		this.authorName.remove(authorName);
		
		return authorName;
	} // end deleteAuthorName method

	
	@Override
	public String toString() {
		return "name: " + name + ", numberOfCopy: " + numberOfCopy + ", price: " + price + ", borrowingPricePerDay: "
				+ borrowingPricePerDay + ", description: " + description + ", dateOfPublish: " + dateOfPublish
				+ ", publisherName: " + publisherName + ", authorName: { " + this.authorsNames() + " }";
	} // end toString method
	
	// get authors String
	private String authorsNames() {
		
		// arrayList size
		int size = this.authorName.size();
		
		// author String
		String authors = "";
		
		// for each author
		for(int i = 0 ; i < size ; i++) {
			authors += this.authorName.get(i) + ", ";
		}// end for
		
		return authors.substring(0, authors.length() - 2);
		
	} // end authorsNames methods	
}// end Book class

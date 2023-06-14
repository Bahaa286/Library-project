package library;

public class Member extends User {
	
	private String fees;

	// constructor
	public Member(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
			String fees, String phone1, String password) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password);
		this.fees = fees;
		super.phone.add("");
		
	}
	
	// constructor
	public Member(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
			String fees, String phone1, String phone2, String password) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, phone2, password);
		this.fees = fees;
	}

	//-------------------------------------------------//
	// getters and setters
	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}
	//-------------------------------------------------//

	@Override
	public String toString() {
		return "Member [fees=" + fees + ", id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", birthDate=" + birthDate + ", finantialPalance=" + finantialPalance
				+ ", phone=" + phone + ", password=" + password + "]";
	}
}

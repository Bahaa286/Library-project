package library;

import java.util.ArrayList;

public class MonthlyEmployee extends Employee {

	private String vacationDays;
	private String allowedVacationDays;
	private String deduction;

	// 1 rule
	// constructor with 2 phone numbers
	public MonthlyEmployee(String id, String firstName, String lastName, char gender, String birthDate,
			String finantialPalance, String phone1, String phone2, String password, String salary,
			String employmentDate, String vacationDays, String allowedVacationDays, String deduction, Rule rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, phone2, password, employmentDate,
				salary, rules);

		this.vacationDays = vacationDays;
		this.allowedVacationDays = allowedVacationDays;
		this.deduction = deduction;
	}// allowed

	// constructor with 1 phone number
	public MonthlyEmployee(String id, String firstName, String lastName, char gender, String birthDate,
			String finantialPalance, String phone1, String password, String salary, String employmentDate,
			String vacationDays, String allowedVacationDays, String deduction, Rule rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password, employmentDate, salary,
				rules);

		this.vacationDays = vacationDays;
		this.allowedVacationDays = allowedVacationDays;
		this.deduction = deduction;
	}

	// list of rules
	// constructor with 2 phone numbers
	public MonthlyEmployee(String id, String firstName, String lastName, char gender, String birthDate,
			String finantialPalance, String phone1, String phone2, String password, String salary,
			String employmentDate, String vacationDays, String allowedVacationDays, String deduction,
			ArrayList<Rule> rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, phone2, password, employmentDate,
				salary, rules);

		this.vacationDays = vacationDays;
		this.allowedVacationDays = allowedVacationDays;
		this.deduction = deduction;
	}// allowed

	// constructor with 1 phone number
	public MonthlyEmployee(String id, String firstName, String lastName, char gender, String birthDate,
			String finantialPalance, String phone1, String password, String salary, String employmentDate,
			String vacationDays, String allowedVacationDays, String deduction, ArrayList<Rule> rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password, employmentDate, salary,
				rules);

		this.vacationDays = vacationDays;
		this.allowedVacationDays = allowedVacationDays;
		this.deduction = deduction;
	}

	/////////////////////////////////////////////////////////////
	// getters and setters
	public String getVacationDays() {
		return vacationDays;
	}

	public void setVacationDays(String vacationDays) {
		this.vacationDays = vacationDays;
	}

	public String getAllowedVacationDays() {
		return allowedVacationDays;
	}

	public void setAllowedVacationDays(String allowdVactionDays) {
		this.allowedVacationDays = allowdVactionDays;
	}

	public String getDeduction() {
		return deduction;
	}

	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}
	/////////////////////////////////////////////////////////////

	// return the employee's salary after deduction
	private String getSalaryAfterDeduction(String vactionDays, String allowdVactionDays, String deduction,
			String salary) {

		// chick if there is a deduction or not
		if (Integer.parseInt(allowdVactionDays) >= Integer.parseInt(vactionDays)) { // no deduction
			return salary;
		} else { // deduction
			return (Double.parseDouble(salary)
					* this.deductionRate(Integer.parseInt(vactionDays) - Integer.parseInt(allowdVactionDays),
							Integer.parseInt(allowdVactionDays)))
					+ "";
		}

	}

	// calculate the deduction's rate depending on the over vaction's days
	private int deductionRate(int day, int allowedVacationDays) {
		return day / (30 - allowedVacationDays);
	}
}

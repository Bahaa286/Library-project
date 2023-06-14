package library;

public class Admin extends User{

	private String employmentDate; // employment date
	private String salary; // salary
	private Rule rule; // rule
	
	public Admin(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
			String phone1, String password, String employmentDate, String salary, String ruleName, String ruleDescription) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password);
		this.employmentDate = employmentDate;
		this.salary = salary;
		this.rule = new Rule(ruleName, ruleDescription);

	}
	
	public Admin(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
			String phone1, String phone2, String password, String employmentDate, String salary, String ruleName, String ruleDescription) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, phone2, password);
		this.employmentDate = employmentDate;
		this.salary = salary;
		this.rule = new Rule(ruleName, ruleDescription);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// getters and setters
	public String getEmploymentDate() {
		return employmentDate;
	}

	public void setEmploymentDate(String employmentDate) {
		this.employmentDate = employmentDate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	public String getRuleName() {
		return rule.getName();
	}
	
	public void setRuleName(String ruleName) {
		this.rule.setName(ruleName);
	}
	
	public String getRuleDescription() {
		return rule.getDescription();
	}
	
	public void setRuleDescription(String ruleDescription) {
		this.rule.setDescription(ruleDescription);
	}
	
	// end getters and setters
	///////////////////////////////////////////////////////////////////////////////////////////	

} // end Administrator class

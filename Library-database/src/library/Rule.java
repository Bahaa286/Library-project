package library;

public class Rule {
	
	private String name; // rule name 
	private String description; // rule description
	
	// constructor
	public Rule(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return name + ": " + description;
	}
	
	@Override
	public boolean equals(Object rule) {
		return this.name.equals(((Rule)rule).name) && this.description.equals(((Rule)rule).description);
	}	
}

package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Seller implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer ID;
	private String name;
	private String email;
	private Date birthdate;
	private Double baseSalary;
	
	private Department dp;
	
	public Seller() {};

	public Seller(Integer iD, String name, String email, Date birthdate, Double baseSalary, Department dp) {
		this.ID = iD;
		this.name = name;
		this.email = email;
		this.birthdate = birthdate;
		this.baseSalary = baseSalary;
		this.dp = dp;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Department getDp() {
		return dp;
	}

	public void setDp(Department dp) {
		this.dp = dp;
	}
	//Implemented by Source
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}
	//Implemented by Source
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Seller: " + this.ID + " - " + this.dp + " - " + this.name + " - " + this.birthdate + " (" + this.email + " - " + this.baseSalary + ")";
	}
}

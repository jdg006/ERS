package com.revature.ers.model;

public class Company {
	
	private int id;
	private String name;
	private String hqAddress;
	
	public Company(int id, String name, String hqAddress) {
		super();
		this.id = id;
		this.name = name;
		this.hqAddress = hqAddress;
	}

	public Company(String name, String hqAddress) {
		super();
		this.name = name;
		this.hqAddress = hqAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHqAddress() {
		return hqAddress;
	}

	public void setHqAddress(String hqAddress) {
		this.hqAddress = hqAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hqAddress == null) ? 0 : hqAddress.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (hqAddress == null) {
			if (other.hqAddress != null)
				return false;
		} else if (!hqAddress.equals(other.hqAddress))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", hqAddress=" + hqAddress + "]";
	}

}

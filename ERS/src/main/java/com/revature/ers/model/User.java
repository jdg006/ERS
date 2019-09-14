package com.revature.ers.model;

public class User {
	
	private int id;
	private String email;
	private String password;
	private int permissionLevel;
	private int superiorId;
	private int companyId;
	private boolean approved;
	
	public User(int id, String email, String password, int permissionLevel, int superiorId, int companyId,
			boolean approved) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.permissionLevel = permissionLevel;
		this.superiorId = superiorId;
		this.companyId = companyId;
		this.approved = approved;
	}

	public User(String email, String password, int permissionLevel, int superiorId, int companyId, boolean approved) {
		super();
		this.email = email;
		this.password = password;
		this.permissionLevel = permissionLevel;
		this.superiorId = superiorId;
		this.companyId = companyId;
		this.approved = approved;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public int getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(int superiorId) {
		this.superiorId = superiorId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + companyId;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + permissionLevel;
		result = prime * result + superiorId;
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
		User other = (User) obj;
		if (approved != other.approved)
			return false;
		if (companyId != other.companyId)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (permissionLevel != other.permissionLevel)
			return false;
		if (superiorId != other.superiorId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", permissionLevel=" + permissionLevel
				+ ", superiorId=" + superiorId + ", companyId=" + companyId + ", approved=" + approved + "]";
	}
	
}

package com.revature.ers.model;

import java.time.LocalDate;

public class Reimbursement {
	
	private int id;
	private int empId;
	private int manId;
	private float amt;
	private String reason;
	private LocalDate date;
	private boolean denied;
	private boolean approved;
	
	
	
	public Reimbursement() {
		super();
	}
	
	public Reimbursement(int id, int empId, int manId, float amt, String reason, LocalDate date, boolean denied,
			boolean approved) {
		super();
		this.id = id;
		this.empId = empId;
		this.manId = manId;
		this.amt = amt;
		this.reason = reason;
		this.date = date;
		this.denied = denied;
		this.approved = approved;
	}

	public Reimbursement(int empId, int manId, float amt, String reason, LocalDate date, boolean denied, boolean approved) {
		super();
		this.empId = empId;
		this.manId = manId;
		this.amt = amt;
		this.reason = reason;
		this.date = date;
		this.denied = denied;
		this.approved = approved;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getManId() {
		return manId;
	}
	public void setManId(int manId) {
		this.manId = manId;
	}
	public float getAmt() {
		return amt;
	}
	public void setAmt(float amt) {
		this.amt = amt;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public boolean isDenied() {
		return denied;
	}
	public void setDenied(boolean denied) {
		this.denied = denied;
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
		result = prime * result + Float.floatToIntBits(amt);
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (denied ? 1231 : 1237);
		result = prime * result + empId;
		result = prime * result + id;
		result = prime * result + manId;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
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
		Reimbursement other = (Reimbursement) obj;
		if (Float.floatToIntBits(amt) != Float.floatToIntBits(other.amt))
			return false;
		if (approved != other.approved)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (denied != other.denied)
			return false;
		if (empId != other.empId)
			return false;
		if (id != other.id)
			return false;
		if (manId != other.manId)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", empId=" + empId + ", manId=" + manId + ", amt=" + amt + ", reason="
				+ reason + ", date=" + date + ", denied=" + denied + ", approved=" + approved + "]";
	}
	
}

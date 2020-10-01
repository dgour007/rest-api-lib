/**
 * 
 */
package com.omantel.restapi.bean;

/**
 * @author Dhiraj Gour
 * @date 01 Jan 2019
 *
 */
public class Ldap {
	
	private String fullName = null;
	private String eMailAddress = null;
	private String initials = null;
	private String department = null;
	private String mobile = null;
	private String manager = null;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String geteMailAddress() {
		return eMailAddress;
	}
	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
}

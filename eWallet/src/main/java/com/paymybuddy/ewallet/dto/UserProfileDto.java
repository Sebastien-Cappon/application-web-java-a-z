package com.paymybuddy.ewallet.dto;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>UserProfileDto</code>. It contains getters and setters, as well as an
 * override <code>toSring()</code> method for display in the console.
 * 
 * @frontCall Refers to <code>ProfileValue</code> model class.
 * 
 * @singularity <code>UserProfileDto</code> contains the firstname, lastname,
 *              e-mail address and non-encrypted password of the active
 *              <code>User</code> whose profile we wish to update.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class UserProfileDto {

	private String firstname;
	private String lastname;
	private String email;
	private String password;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	/**
	 * An override method for user-friendly display of <code>UserProfileDto</code>
	 * attributes in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>UserProfileDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + firstname + "]" + "[" + lastname + "]" + "[" + email + "]" + "[" + password + "]");
	}
}
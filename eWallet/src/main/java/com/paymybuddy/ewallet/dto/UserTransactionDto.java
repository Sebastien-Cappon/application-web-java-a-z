package com.paymybuddy.ewallet.dto;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>UserTransactionDto</code>. It contains getters and setters, as well as
 * an override <code>toSring()</code> method for display in the console.
 * 
 * @frontCall None.
 * 
 * @singularity <code>UserTransactionDto</code> is the only DTO which is not
 *              linked to a front-end model. <code>UserTransactionDto</code>
 *              contains the user id, firstname, lastname, e-mail address and
 *              non-encrypted password of the receiver of
 *              <code>TransactionAddDto</code>.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class UserTransactionDto {

	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * An override method for user-friendly display of
	 * <code>UserTransactionDto</code> attributes in the console. Not necessary,
	 * except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>UserTransactionDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + firstname + "]" + "[" + lastname + "]" + "[" + email + "]" + "[" + active + "]");
	}
}
package com.paymybuddy.ewallet.dto;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>BuddyAddDto</code>. It contains getters and setters, as well as an
 * override <code>toSring()</code> method for display in the console.
 * 
 * @frontCall Refers to <code>ContactValue</code> model class. 
 * 
 * @singularity <code>BuddyAddDto</code> contains the active <code>User</code>
 *              id and the e-mail address of the <code>User</code> we want to
 *              add to his friendlist.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class BuddyAddDto {

	private int userId;
	private String newBuddyEmail;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNewBuddyEmail() {
		return newBuddyEmail;
	}

	public void setNewBuddyEmail(String newBuddyEmail) {
		this.newBuddyEmail = newBuddyEmail;
	}

	/**
	 * An override method for user-friendly display of <code>BuddyAddDto</code>
	 * attributes in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>BuddyAddDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + userId + "]" + "[" + newBuddyEmail + "]");
	}
}
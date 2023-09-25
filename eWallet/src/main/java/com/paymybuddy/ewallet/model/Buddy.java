package com.paymybuddy.ewallet.model;

/**
 * A model class which creates the POJO (Plain Old Java Object) <code>Buddy</code>.
 * It contains getter and setter, as well as an override
 * <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>Buddy</code> is linked to the <code>user_user</code> join table of
 *              the database. Its only attribute is the composite key <code>BuddyKey</code>.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_user")
public class Buddy {

	@EmbeddedId
	private BuddyKey id;

	public BuddyKey getId() {
		return id;
	}

	public void setId(BuddyKey id) {
		this.id = id;
	}

	/**
	 * An override method for user-friendly display of <code>Buddy</code> attributes
	 * in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>Buddy</code>.
	 */
	@Override
	public String toString() {
		return ("[" + id + "]");
	}
}
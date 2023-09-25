package com.paymybuddy.ewallet.views;

/**
 * A class that contains some interfaces that inherit from each other.These are
 * used to filter the attributes to be returned in response to requests sent to
 * the API.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
public class UserView {
	public interface IdView {}
	public interface FirstnameView {}
	public interface LastnameView {}
	public interface EmailView {}
	public interface AmountView {}
	public interface ActiveView {}
	
	public interface LoginView extends IdView, FirstnameView, LastnameView, AmountView {}
	public interface ProfileView extends FirstnameView, LastnameView, EmailView {}
	public interface UserAmountView extends IdView, AmountView {}
	public interface BuddyView extends IdView, FirstnameView, LastnameView, EmailView, ActiveView {}
	
	public interface TransactionUserView extends IdView, FirstnameView, LastnameView {}
}
package com.paymybuddy.ewallet.views;

public class UserView {
	public interface IdView {}
	public interface FirstnameView {}
	public interface LastnameView {}
	public interface EmailView {}
	public interface SocialView {}
	public interface PasswordView {}
	public interface AmountView {}
	public interface ActiveView {}
	
	public interface LoginView extends IdView, FirstnameView, LastnameView, AmountView {}
	public interface ProfileView extends FirstnameView, LastnameView, EmailView {}
	public interface UserAmountView extends IdView, AmountView {}
	public interface BuddyView extends IdView, FirstnameView, LastnameView, EmailView, ActiveView {}
}

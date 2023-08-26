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
	
	public interface LoginView extends IdView, FirstnameView, LastnameView {}
	public interface ProfileView extends FirstnameView, LastnameView, EmailView {}
}

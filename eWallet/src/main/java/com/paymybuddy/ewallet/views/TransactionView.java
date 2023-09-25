package com.paymybuddy.ewallet.views;

/**
 * A class that contains some interfaces that inherit from each other.These are
 * used to filter the attributes to be returned in response to requests sent to
 * the API.
 * 
 * @singularity <code>SenderView</code> and <code>ReceiverView</code> extends an
 *              other <code>views</code> class
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class TransactionView {
	public interface DateView {}
	public interface SenderView extends UserView.TransactionUserView {}
	public interface ReceiverView extends UserView.TransactionUserView {}
	public interface AmountView {}
	public interface FeeView {}
	public interface DescriptionView {}

	public interface TransactionSimpleView extends DateView, SenderView, ReceiverView, AmountView, FeeView, DescriptionView {}
}
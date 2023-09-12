package com.paymybuddy.ewallet.views;

public class TransactionView {	
	public interface IdView {}
	public interface DateView {}
	public interface SenderView extends UserView.IdView {}
	public interface ReceiverView extends UserView.IdView {}
	public interface AmountView {}
	public interface FeeView {}
	public interface DescriptionView {}
	
	public interface AddTransactionView extends DateView, SenderView, ReceiverView, AmountView, FeeView, DescriptionView {}
}

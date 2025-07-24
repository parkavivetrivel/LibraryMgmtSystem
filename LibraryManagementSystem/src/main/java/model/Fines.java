package model;

public class Fines {
	private  int fine_id;
    private int loan_id;
    private double amount;
    private boolean paid;
   

	public int getFine_id() {
		return fine_id;
	}


	public void setFine_id(int fine_id) {
		this.fine_id = fine_id;
	}


	public int getLoan_id() {
		return loan_id;
	}


	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public boolean isPaid() {
		return paid;
	}


	public void setPaid(boolean paid) {
		this.paid = paid;
	}


	public Fines() {
		// TODO Auto-generated constructor stub
	}

}

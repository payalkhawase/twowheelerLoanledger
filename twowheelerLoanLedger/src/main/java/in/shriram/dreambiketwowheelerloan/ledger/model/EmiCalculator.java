package in.shriram.dreambiketwowheelerloan.ledger.model;

public class EmiCalculator {

	private double emi;
	private double principalAmount;
	private double rateOfInterest;
	private int loanTenureInMonth;
	
	private static final EmiCalculator em=new EmiCalculator();
	
	private EmiCalculator() {
		
	}
	
	public static double emiCalculte(double principalAmount,double rateOfInterest, int loanTenureInMonth) {
		
		double emi = (principalAmount * rateOfInterest * Math.pow(1 + rateOfInterest, loanTenureInMonth))
               / (Math.pow(1 + rateOfInterest, loanTenureInMonth) - 1);
		return emi;
	}
	
	public static EmiCalculator getEm() {
		
		return em;
		
		
	}
	
}

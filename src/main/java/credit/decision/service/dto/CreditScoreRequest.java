package credit.decision.service.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Sachin
 *
 */
@Valid
public class CreditScoreRequest {
	
	@NotNull(message = "SSN Number should not be empty")
	private String ssnNumber;

	@NotEmpty(message = "Loan Amount should not be empty")
	private long loanAmount;
	

	@NotEmpty(message = "Current Annual income not be empty")
	private long currentAnnualIncome;
	/**
	 * @return the ssnNumber
	 */
	public String getSsnNumber() {
		return ssnNumber;
	}
	/**
	 * @param ssnNumber the ssnNumber to set
	 */
	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}
	/**
	 * @return the loanAmount
	 */
	public long getLoanAmount() {
		return loanAmount;
	}
	/**
	 * @param loanAmount the loanAmount to set
	 */
	public void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}
	/**
	 * @return the currentAnnualIncome
	 */
	public long getCurrentAnnualIncome() {
		return currentAnnualIncome;
	}
	/**
	 * @param currentAnnualIncome the currentAnnualIncome to set
	 */
	public void setCurrentAnnualIncome(long currentAnnualIncome) {
		this.currentAnnualIncome = currentAnnualIncome;
	}

}

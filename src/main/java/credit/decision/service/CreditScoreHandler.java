package credit.decision.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sachin
 *
 */
@Service
public class CreditScoreHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreditScoreHandler.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Map<String, java.time.Instant> ssnNumberHistoryMap=new HashMap<>();
	
	

	/**
	 * @param ssnNumber
	 */
	public Optional<String> getCreditScore(String ssnNumber) {
		LOGGER.warn("getCreditScore Method execution - SSN Number {}",ssnNumber); 
		if(null!=ssnNumber) {
			ssnNumberHistoryMap.put(ssnNumber, Instant.now());
			ResponseEntity<String> resp = 
			          restTemplate.getForEntity("http://localhost:8084/ssnnumber/" + ssnNumber, String.class);
			LOGGER.warn("getCreditScore Method execution - response {}",resp.getBody()); 
			return Optional.ofNullable(resp.getBody());
		}
		return Optional.empty();
	}



	/**
	 * @param string
	 * @param currentAnnualIncome
	 */
	public Optional<Long> calculateLoanAmount(int creditScore, long currentAnnualIncome) {
		LOGGER.warn("calculateLoanAmount Method execution - Credit score {} , current Annual income {}",creditScore,currentAnnualIncome);
		if(creditScore>700 && currentAnnualIncome>0) {
			return Optional.ofNullable(currentAnnualIncome/2);
		}else {
			return Optional.empty();
		}
	}
	
	
	public boolean validateSSNNumberHistory(String ssnNumber) {
		Instant fromInstant = ssnNumberHistoryMap.get(ssnNumber);
		Instant toInstant = Instant.now();
		LOGGER.warn("validateSSNNumberHistory Method execution - SSN Number : {} fromDate {} toDate {}",ssnNumber,fromInstant,toInstant);
		if(null==fromInstant) {
			return true;
		}
		Duration duration = Duration.between(fromInstant, toInstant);
		final Duration thirtyDays = Duration.ofDays(30);
		if(duration.compareTo(thirtyDays) < 0) {
		    //Duration is less than thirty days
			return false;
		} else if(duration.compareTo(thirtyDays) > 0) {
		    //Duration is more than thirty days
			return true;
		} else {
		    //Duration is exactly thirty days.... somehow....
			return false;

		}
			
	}
	

}

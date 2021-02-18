package credit.decision.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sachin
 *
 */
@RestController
public class CreditScoreController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreditScoreController.class);

	
	 @GetMapping("/ssnnumber/{snnValue}")
	 public int calculateSanctionLoanAmount(@PathVariable final String snnValue) {
		LOGGER.warn("Inside CreditScoreController -- request received -START"); 
		LOGGER.warn("Inside CreditScoreController -- SSN Number : {}",snnValue); 
		return 800;
	  }

}

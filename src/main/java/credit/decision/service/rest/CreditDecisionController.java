package credit.decision.service.rest;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import credit.decision.service.CreditScoreHandler;
import credit.decision.service.dto.CreditScoreRequest;

/**
 * @author Sachin
 *
 */
@RestController
public class CreditDecisionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreditDecisionController.class);
	
	@Autowired
	private CreditScoreHandler creditScoreHandler;
	
	
	 @PostMapping(value="/sanctionamount",consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	 public ResponseEntity<String> calculateSanctionLoanAmount(@Valid @RequestBody CreditScoreRequest creditScoreRequest) {
		 LOGGER.warn("Inside CreditDecisionController -- request received -START");
		 LOGGER.warn("Request received -- SSN Number : {}, loanAmount : {}, currentAnnualIncome :{}",
				 creditScoreRequest.getSsnNumber(),creditScoreRequest.getLoanAmount(),creditScoreRequest.getCurrentAnnualIncome());
		 if(!StringUtils.hasText(creditScoreRequest.getSsnNumber())) {
			 return new ResponseEntity<>("falied - missing parameter ", HttpStatus.BAD_REQUEST); 
		 }
		 Optional<Long> response=null;
		 if(!creditScoreHandler.validateSSNNumberHistory(creditScoreRequest.getSsnNumber())) {
			 return new ResponseEntity<>("falied - already requested ", HttpStatus.ALREADY_REPORTED);
		 }
		 Optional<String> creditScoreOptional=creditScoreHandler.getCreditScore(creditScoreRequest.getSsnNumber());
		 if(creditScoreOptional.isPresent()) {
			 LOGGER.warn("Credit score : {}",creditScoreOptional.get());
			 response=creditScoreHandler.calculateLoanAmount(Integer.parseInt(creditScoreOptional.get()),creditScoreRequest.getCurrentAnnualIncome());
			 if(response.isPresent()) {
				 	LOGGER.warn("Laon Amount calculated : {}",response.get());
					return new ResponseEntity<>(response.get().toString(), HttpStatus.OK);
				}else {
					return new ResponseEntity<>("falied ", HttpStatus.NOT_FOUND);
				}
		 }
		return  new ResponseEntity<>("falied ", HttpStatus.INTERNAL_SERVER_ERROR);
		 
	  }

}

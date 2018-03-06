package com.altimetrik.wallet.walrew.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.wallet.walrew.api.service.util.CardNetworkType;
import com.altimetrik.wallet.walrew.api.service.util.CardValidation;
import com.altimetrik.wallet.walrew.api.service.util.MessageType;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.exception.WalletException;
import com.altimetrik.wallet.walrew.exception.WalletException.WalletError;
import com.altimetrik.wallet.walrew.model.Audit;
import com.altimetrik.wallet.walrew.model.Card;
import com.altimetrik.wallet.walrew.model.Product;
import com.altimetrik.wallet.walrew.model.User;

@RestController
@RequestMapping(value = "/api")
public class CardController extends BaseController {

	@Value("${spring.card.createdby}")
	private String createdBy;

	@Value("${spring.card.updatedby}")
	private String updatedBy;

	/**
	 * 
	 * @param card
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/card/process", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Card> addCard(@RequestBody Card card) throws Exception {

		// Save the request to database
		Audit requestLog = saveAudit(card.toString());

		// Check the card type, network and apply product
		String cardNo = card.getCardnumber();
		CardValidation cardValid = new CardValidation();
		CardNetworkType networkType = cardValid.detectCardType(cardNo);
		if (networkType == null || (networkType != null && networkType.name() == "UNKNOWN")) {
			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60196);
			throw new Exception(we.getErrorMessage());

		}
		card.setNetworktype(networkType.name());
		if (!cardValid.isValid(cardNo)) {
			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60197);
			throw new Exception(we.getErrorMessage());
		}

		Date expiry = card.getExpirydate();
		if (expiry.before(new Date(System.currentTimeMillis()))) {
			//
			System.out.println("Expiry date cannot be back dated");
		}

		// Set up the product for the card, it is hardcoded to 1 for testing
		// purpose
		Product product = productservice.findByIdProduct(1);
		card.setProduct(product);

		// Identify the user with wallet --> mobile no
		String mobileNo = card.getWallet().getUser().getMobileno();
		User userDetails = userService.findByMobileno(mobileNo);
		card.setWallet(walletService.findbyUserId(userDetails));

		requestLog.setUpdatedby(updatedBy);
		requestLog.setUpdationtimestamp(new Date(System.currentTimeMillis()));
		requestLog.setRequest(card.getCardnumber());
		auditService.updateAudit(requestLog);
		return new ResponseEntity<Card>(cardservice.addCard(card), HttpStatus.OK);

	}

	/**
	 * 
	 * @param card
	 * @return
	 * @throws NotFoundException
	 */
	private Audit saveAudit(String card) throws NotFoundException {

		Audit requestMsg = new Audit();
		requestMsg.setCreatedby(createdBy);
		requestMsg.setCreationtimestamp(new Date(System.currentTimeMillis()));
		requestMsg.setRequest(MessageType.CARD + ":" + card);
		auditService.addAudit(requestMsg);

		return requestMsg;
	}
}

package com.altimetrik.wallet.walrew.api.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.wallet.walrew.api.service.util.MessageType;
import com.altimetrik.wallet.walrew.api.service.util.WalletConstants;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.exception.WalletException;
import com.altimetrik.wallet.walrew.exception.WalletException.WalletError;
import com.altimetrik.wallet.walrew.model.Audit;
import com.altimetrik.wallet.walrew.model.Smscodes;
import com.altimetrik.wallet.walrew.model.User;

@RestController
@RequestMapping(value = "/api")
public class OTPController extends BaseController {

	@Value("${spring.sms.url}")
	private String smsurl;
	@Value("${spring.otp.url}")
	private String otpserver;
	@Value("${spring.sms.createdby}")
	private String createdBy;
	@Value("${spring.sms.updatedby}")
	private String updatedBy;

	/**
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/publishOTP", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Smscodes> generateOTP(@RequestBody String mobile) throws Exception {

		Audit requestMsg = saveAudit(mobile);

		User userDetails = userService.findByMobileno(mobile);
		if (userDetails == null) {

			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60194);
			throw new Exception(we.getErrorMessage());
		}

		Integer otp = generateOTP();
		if (otp == null) {
			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60195);
			throw new Exception(we.getErrorMessage());
		}

		Smscodes otpMessage = saveOTP(userDetails, otp.intValue());

		// Uncommented this line for publishing the OTP to mobile.
		//publishOTP(mobile, otp.intValue());

		requestMsg.setRequest(requestMsg.getRequest() + " Published the OTP Successfully & OTP :" + otp.intValue());
		requestMsg.setUpdatedby(updatedBy);
		requestMsg.setUpdationtimestamp(new Date(System.currentTimeMillis()));
		auditService.updateAudit(requestMsg);

		return new ResponseEntity<Smscodes>(otpMessage, HttpStatus.OK);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/generateDummyOTP", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Integer> getDummyOTP() throws Exception {
		Integer otp = generateOTP();
		if (otp == null) {
			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60195);
			throw new Exception(we.getErrorMessage());
		}
		return new ResponseEntity<Integer>(new Integer(otp.intValue()), HttpStatus.OK);
	}

	/**
	 * 
	 * @param smscode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateOTP", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Boolean> validateOTP(@RequestBody Smscodes smscode) throws Exception {

		boolean valid = isValidOTP(smscode);
		if (valid) {
			smscode.setStatus(String.valueOf(WalletConstants.Walletenum.INACTIVE.ordinal()));
			smsService.updateSmscodes(smscode);
		}
		return new ResponseEntity<Boolean>(new Boolean(valid), HttpStatus.OK);
	}

	/**
	 * 
	 * @param user
	 * @param otp
	 * @return
	 * @throws NotFoundException
	 */
	private Smscodes saveOTP(User user, int otp) throws NotFoundException {

		Smscodes otpmessage = new Smscodes();
		otpmessage.setUser(user);
		otpmessage.setCode(String.valueOf(otp));
		otpmessage.setStatus(String.valueOf(WalletConstants.Walletenum.ACTIVE.ordinal()));
		otpmessage.setCreationtimestamp(System.currentTimeMillis());
		smsService.addSmscodes(otpmessage);
		return otpmessage;

	}

	private Integer generateOTP() throws NoSuchAlgorithmException, InvalidKeyException {

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = headers();
		HttpEntity<?> request = new HttpEntity<>("", headers);

		ResponseEntity<Integer> response = restTemplate.exchange(otpserver, HttpMethod.POST, request, Integer.class);

		return response.getBody();
	}

	/**
	 * 
	 * @param mobile
	 * @return
	 * @throws NotFoundException
	 */
	private Audit saveAudit(String mobile) throws NotFoundException {

		Audit requestMsg = new Audit();
		requestMsg.setCreatedby(createdBy);
		requestMsg.setCreationtimestamp(new Date(System.currentTimeMillis()));
		requestMsg.setRequest(MessageType.OTP + ":" + mobile);
		auditService.addAudit(requestMsg);

		return requestMsg;
	}

	/**
	 * 
	 * @param mobileno
	 * @param otp
	 * @return
	 */
	private ResponseEntity<String> publishOTP(String mobileno, int otp) {

		String formattedURL = smsurl;
		RestTemplate restTemplate = new RestTemplate();
		formattedURL = String.format(formattedURL, mobileno, ":" + otp);
		ResponseEntity<String> response = restTemplate.exchange(formattedURL, HttpMethod.GET, null, String.class);

		return response;
	}
}

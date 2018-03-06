package com.altimetrik.wallet.walrew.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.wallet.walrew.api.service.AuditApiService;
import com.altimetrik.wallet.walrew.api.service.CardApiService;
import com.altimetrik.wallet.walrew.api.service.ProductApiService;
import com.altimetrik.wallet.walrew.api.service.RewardpointApiService;
import com.altimetrik.wallet.walrew.api.service.SmscodesApiService;
import com.altimetrik.wallet.walrew.api.service.TransactionApiService;
import com.altimetrik.wallet.walrew.api.service.UserApiService;
import com.altimetrik.wallet.walrew.api.service.WalletApiService;
import com.altimetrik.wallet.walrew.api.service.util.WalletConstants;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Smscodes;

@RestController
public class BaseController {

	@Autowired
	CardApiService cardservice;
	@Autowired
	SmscodesApiService smsService;
	@Autowired
	WalletApiService walletService;
	@Autowired
	AuditApiService auditService;
	@Autowired
	TransactionApiService txService;
	@Autowired
	RewardpointApiService rewardsService;
	@Autowired
	UserApiService userService;
	@Autowired
	ProductApiService productservice;
	@Value("${spring.order.url}")
	String orderurl;
	@Value("${spring.catalog.url}")
	String catalogurl;
	@Value("${spring.netbankaccount.url}")
	String netbankaccount;
	@Value("${spring.netbanklogin.url}")
	String netbanklogin;
	@Value("${spring.netbankbalanceupdate.url}")
	String updateaccount;
	@Value("${spring.catalogfilter.url}")
	String catalogfilter;

	long lagTime = 2 * 60 * 1000;

	/**
	 * 
	 * @return
	 */
	public MultiValueMap<String, String> headers() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "application/json");
		headers.setAll(map);
		return headers;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public MultiValueMap<String, String> addBasicAuth(String username, String password) {

		MultiValueMap<String, String> headers = headers();
		Map<String, String> map = new HashMap<String, String>();
		String auth = username + ":" + password;
		String encodedAuth = new String(Base64.encodeBase64(auth.getBytes()));
		String authHeader = "Basic " + new String(encodedAuth);
		map.put("Authorization", authHeader);
		headers.setAll(map);
		return headers;
	}

	/**
	 * 
	 * @param smscode
	 * @return
	 * @throws NotFoundException
	 */
	public boolean isValidOTP(Smscodes smscode) throws NotFoundException {

		Smscodes otpCodeDB = smsService.findByIdSmscodes(smscode.getId());
		long diff = (System.currentTimeMillis() - otpCodeDB.getCreationtimestamp());
		if (diff > lagTime) {
			// changed to true if it is for testing purpose
			return false;
		} else if ((otpCodeDB == null) || !(otpCodeDB.getCode().equalsIgnoreCase(smscode.getCode()))) {
			return false;
		} else if (WalletConstants.Walletenum.INACTIVE.ordinal() == Integer.parseInt(otpCodeDB.getStatus())) {
			return false;
		}
		return true;
	}
}

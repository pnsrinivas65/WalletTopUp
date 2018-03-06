package com.altimetrik.wallet.walrew.api.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
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
import com.altimetrik.wallet.walrew.api.service.util.TransactionType;
import com.altimetrik.wallet.walrew.api.service.util.WalletConstants;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.exception.WalletException;
import com.altimetrik.wallet.walrew.exception.WalletException.WalletError;
import com.altimetrik.wallet.walrew.model.Audit;
import com.altimetrik.wallet.walrew.model.Rewardpoint;
import com.altimetrik.wallet.walrew.model.Smscodes;
import com.altimetrik.wallet.walrew.model.Transaction;
import com.altimetrik.wallet.walrew.model.User;
import com.altimetrik.wallet.walrew.model.Wallet;

@RestController
@RequestMapping(value = "/api")
public class WalletController extends BaseController {

	@Value("${spring.wallet.createdby}")
	private String createdBy;
	@Value("${spring.wallet.updatedby}")
	private String updatedBy;

	@RequestMapping(value = "/wallet/cbs/auth", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)

	public ResponseEntity<String> cbsAuth(@RequestBody String user) throws JSONException {

		JSONObject userDetails = new JSONObject(user);
		String username = userDetails.optString("username");
		String password = userDetails.optString("password");

		return new ResponseEntity<String>(authNetBanking(username, password), HttpStatus.OK);

	}

	/**
	 * 
	 * @param wallet
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wallet/topup", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<Wallet> walletTopUp(@RequestBody String walletTopDetails) throws Exception {

		JSONObject jsonwallet = new JSONObject(walletTopDetails);

		String walletName = jsonwallet.optString("walletname");
		Double topUpValue = jsonwallet.optDouble("topUp");
		String account = jsonwallet.optString("accountnumber");
		String cbsusername = jsonwallet.optString("username");
		int otpid = jsonwallet.optInt("id");
		String otpcode = jsonwallet.optString("code");

		Wallet wallet = walletService.findbyWalletName(walletName);
		Audit requestLog = saveAudit(wallet);

		// Smscodes otp = smsService.findByIdSmscodes(new Integer(otpid));
		// System.out.println("otp -->" + otp.getCode());
		// System.out.println("otpcode -->" + otpcode);
		// otp.setCode(otpcode);
		// otp.setId(new Integer(otpid));
		boolean validotp = isValidOTPForTopup(otpid, otpcode);

		System.out.println("validotp -->" + validotp);
		if (validotp) {
			System.out.println("validotp inside-->" + validotp);

			User userDetails = wallet.getUser();
			String netBankingUserDetails = getAccountDetails(cbsusername, account);// ,
			if (netBankingUserDetails == null) {
				WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60200);
				throw new Exception(we.getErrorMessage());

			}

			Wallet walletDetails = walletService.findbyWalletName(wallet.getWalletname());
			if (walletDetails == null) {
				WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60201);
				throw new Exception(we.getErrorMessage());

			}

			JSONObject netBank = new JSONObject(netBankingUserDetails);
			Long balance = netBank.optLong("balance");
			String accountno = netBank.optString("accountnumber");
			if (wallet.getTopUp().floatValue() > balance.floatValue()) {
				WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60202);
				throw new Exception(we.getErrorMessage());
			}

			// Subtract the top up value from net banking account
			@SuppressWarnings("unused")
			boolean isNteBankingCommitted = debitNetBankAccount(netBank, new BigDecimal(topUpValue));

			// Product
			String productValues = "100";
			// card.getProduct().getProductvalues();
			Double points = ((topUpValue.floatValue()) * Double.valueOf(productValues)) / 100;

			// add top up to existing top up amount
			BigDecimal totalTopUp = walletDetails.getTopUp().add(new BigDecimal(topUpValue));
			walletDetails.setTopUp(totalTopUp);

			// transactions credit leg
			Transaction topupTx = new Transaction();
			topupTx.setCard(accountno);
			topupTx.setTransaction(wallet.getTopUp().toString());
			topupTx.setTransactiontype(TransactionType.CR.toString());
			topupTx.setPoints(points);
			txService.addTransaction(topupTx);

			// transactions debit leg
			Transaction carddebitTx = new Transaction();
			carddebitTx.setCard(accountno);
			carddebitTx.setTransaction(wallet.getTopUp().toString());
			carddebitTx.setTransactiontype(TransactionType.DR.toString());
			carddebitTx.setPoints(points);
			txService.addTransaction(carddebitTx);

			// Added few rewards for top up as well
			Rewardpoint rewards = rewardsService.findByUser(userDetails);
			if (rewards == null) {
				rewards = new Rewardpoint();
				rewards.setValue(0.00);
			}
			rewards.setUser(userDetails);
			rewards.setRewardname(TransactionType.CR.toString());
			rewards.setRewardexpiry(new Date(System.currentTimeMillis() + (12 * 30 * 24 * 60 * 60 * 1000)));
			rewards.setValue(rewards.getValue() + topupTx.getPoints());
			rewards.setRewardtype(MessageType.REWARD.toString());
			rewardsService.addRewardpoint(rewards);

			// update the audit table with wallet request
			requestLog.setRequest(walletDetails.getWalletname());
			requestLog.setUpdatedby(updatedBy);
			requestLog.setUpdationtimestamp(new Date(System.currentTimeMillis()));
			auditService.updateAudit(requestLog);

			return new ResponseEntity<Wallet>(walletService.addWallet(walletDetails), HttpStatus.OK);
		}
		return null;

	}

	/**
	 * 
	 * @param wallet
	 * @return
	 * @throws NotFoundException
	 */
	private Audit saveAudit(Wallet wallet) throws NotFoundException {

		Audit requestMsg = new Audit();
		requestMsg.setCreatedby(createdBy);
		requestMsg.setCreationtimestamp(new Date(System.currentTimeMillis()));
		requestMsg.setRequest(MessageType.WALLET_TOPUP + ":" + wallet.getWalletname());
		auditService.addAudit(requestMsg);

		return requestMsg;
	}

	/**
	 * 
	 * @param netBankAccount
	 * @param topUpValue
	 * @return
	 * @throws JSONException
	 */
	private boolean debitNetBankAccount(JSONObject netBankAccount, BigDecimal topUpValue) throws JSONException {

		Long balance = netBankAccount.optLong("balance");
		Long netBalance = balance - topUpValue.longValue();

		netBankAccount.put("balance", netBalance);

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = headers();
		HttpEntity<?> request = new HttpEntity<>(netBankAccount.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(updateaccount, HttpMethod.POST, request, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return true;
		} else
			return false;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws JSONException
	 */
	private String authNetBanking(String username, String password) throws JSONException {

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = headers();

		JSONObject userdetails = new JSONObject();
		userdetails.put("username", username);
		userdetails.put("password", password);
		HttpEntity<?> request = new HttpEntity<>(userdetails.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(netbanklogin, HttpMethod.POST, request, String.class);

		return response.getBody();
	}

	private String getAccountDetails(String username, String account) throws JSONException {

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = headers();

		JSONObject userdetails = new JSONObject();
		userdetails.put("username", username);
		userdetails.put("accountnumber", account);
		HttpEntity<?> request = new HttpEntity<>(userdetails.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(netbankaccount, HttpMethod.POST, request, String.class);

		return response.getBody();
	}

	private boolean isValidOTPForTopup(Integer id, String code) throws NotFoundException {

		Smscodes otpCodeDB = smsService.findByIdSmscodes(id);
		long diff = (System.currentTimeMillis() - otpCodeDB.getCreationtimestamp());
		if (diff > lagTime) {
			// changed to true if it is for testing purpose
			return false;
		} else if ((otpCodeDB == null) || !(otpCodeDB.getCode().equalsIgnoreCase(code))) {
			return false;
		} else if (WalletConstants.Walletenum.INACTIVE.ordinal() == Integer.parseInt(otpCodeDB.getStatus())) {
			return false;
		}
		otpCodeDB.setStatus(String.valueOf(WalletConstants.Walletenum.INACTIVE.ordinal()));
		smsService.updateSmscodes(otpCodeDB);
		return true;
	}

}
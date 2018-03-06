package com.altimetrik.wallet.walrew.api.controller;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.wallet.walrew.api.service.util.MessageType;
import com.altimetrik.wallet.walrew.api.service.util.PasswordEncryption;
import com.altimetrik.wallet.walrew.api.service.util.TransactionType;
import com.altimetrik.wallet.walrew.api.service.util.WalletConstants;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.exception.WalletException;
import com.altimetrik.wallet.walrew.exception.WalletException.WalletError;
import com.altimetrik.wallet.walrew.model.Audit;
import com.altimetrik.wallet.walrew.model.Rewardpoint;
import com.altimetrik.wallet.walrew.model.User;
import com.altimetrik.wallet.walrew.model.Wallet;

@RestController
@RequestMapping(value = "/api")
public class UserController extends BaseController {

	@Value("${spring.user.createdby}")
	private String createdBy;
	@Value("${spring.user.updatedby}")
	private String updatedBy;
	@Value("${spring.ouath.url}")
	private String oauthURL;
	@Value("${spring.acstoken.url}")
	private String accesstokenurl;
	@Value("${spring.acstokenchek.url}")
	private String accesstokenchek;
	@Value("${spring.refreshtoken.url}")
	private String refreshtoken;
	@Value("${spring.oauth.basicusn}")
	private String ouathbasicusn;
	@Value("${spring.oauth.basicpwd}")
	private String oauthbasicpwd;

	@RequestMapping(value = "/user/all", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<User>> findAllUser()
			throws NotFoundException, NoSuchAlgorithmException, InvalidKeyException {

		return new ResponseEntity<List<User>>(userService.findAllUser(), HttpStatus.OK);

	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/updateuser", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {

		// Save the request to Audit Table
		Audit requestLog = saveAudit(user);

		// Get details of user
		User userDetails = userService.findByMobileno(user.getMobileno());

		// Throw exception, unable to find user details
		if (userDetails == null) {
			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60200);
			throw new Exception(we.getErrorMessage());
		}

		// user validations are completed and user need to registered with
		// OAUTH2

		// encrypt the password
		PasswordEncryption encrypt = new PasswordEncryption();
		String encrypted = encrypt.encrypt(user.getPassword());
		userDetails.setPassword(encrypted);
		userDetails.setStatus("" + WalletConstants.Walletenum.ACTIVE.ordinal());

		// Add default wallet and top up with 0.0
		Wallet defaultWallet = new Wallet();
		defaultWallet.setWalletname(userDetails.getUsername());
		defaultWallet.setTopUp(new BigDecimal(0.0));
		defaultWallet.setUser(userDetails);
		walletService.updateWallet(defaultWallet);

		// update the audit table
		requestLog.setRequest(requestLog.getRequest() + " Updated Password Sucessfully");
		requestLog.setUpdatedby(updatedBy);
		requestLog.setUpdationtimestamp(new Date(System.currentTimeMillis()));
		auditService.updateAudit(requestLog);

		userService.updateUser(userDetails);
		userDetails.setPassword(encrypt.decrypt(encrypted));

		return new ResponseEntity<User>(userDetails, HttpStatus.OK);

	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/adduser", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {

		Audit requestLog = saveAudit(user);

		// Check user already exists or not
		User existUser = userService.findByUsername(user.getLastname() + " " + user.getFirstname());
		if (existUser != null) {
			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60198);
			throw new Exception(we.getErrorMessage());

		}
		existUser = userService.findByMobileno(user.getMobileno());
		if (existUser != null) {
			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60199);
			throw new Exception(we.getErrorMessage());

		}

		// set user name as First name + last name
		user.setUsername(user.getLastname() + " " + user.getFirstname());
		String username = user.getUsername();
		
		PasswordEncryption password = new PasswordEncryption();
		String tobeencrypted = user.getPassword() != null ? user.getPassword() : username;
		String encryptedPassword = password.encrypt(tobeencrypted);

		boolean success = true;
		// un comment this line for integration with ADFS or Sales Force login or social login
				//OAUTH2Registration("", user.getLastname() + " " + user.getFirstname(), tobeencrypted,
				//user.getMobileno(), user.getMobileno());
		if (success) {
			user.setPassword(encryptedPassword);
			user.setApikey(encryptedPassword);
			user.setCreatedby(createdBy);
			user.setStatus("" + WalletConstants.Walletenum.ACTIVE.ordinal());
			user.setCreationtimestamp(new Date(System.currentTimeMillis()));
			User newUser = userService.addUser(user);

			// update audit table
			requestLog.setUpdatedby(updatedBy);
			requestLog.setUpdationtimestamp(new Date(System.currentTimeMillis()));
			auditService.updateAudit(requestLog);

			newUser.setPassword(password.decrypt(newUser.getPassword()));

			// Add default wallet and top up with 0.0
			Wallet defaultWallet = new Wallet();
			defaultWallet.setWalletname(newUser.getUsername());
			defaultWallet.setTopUp(new BigDecimal(0.0));
			defaultWallet.setUser(newUser);
			walletService.addWallet(defaultWallet);

			// Add default rewards points with 0.0
			Rewardpoint rewards = new Rewardpoint();
			rewards.setUser(newUser);
			rewards.setRewardname(TransactionType.CR.toString());
			rewards.setRewardexpiry(new Date(System.currentTimeMillis() + (12 * 30 * 24 * 60 * 60 * 1000)));
			rewards.setValue(new Double(0.0));
			rewards.setRewardtype(MessageType.REWARD.toString());
			rewardsService.addRewardpoint(rewards);

			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		}

		try {

		} catch (NullPointerException | ArithmeticException ex) {

		}

		return new ResponseEntity<User>(new User(), HttpStatus.UNAUTHORIZED);

	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws NotFoundException
	 */
	private Audit saveAudit(User user) throws NotFoundException {

		Audit requestMsg = new Audit();
		requestMsg.setCreatedby(createdBy);
		requestMsg.setCreationtimestamp(new Date(System.currentTimeMillis()));
		requestMsg.setRequest(MessageType.USER + ":" + user.getUsername());
		auditService.addAudit(requestMsg);

		return requestMsg;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws NotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	@RequestMapping(value = "/user/auth", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<String> isUserAuthenticated(@RequestBody User user)
			throws NotFoundException, NoSuchAlgorithmException, NoSuchProviderException {

		User localdb = userService.findByMobileno(user.getMobileno());

		// captures password at local DB is optional.
		boolean valid = isAuthenticated(localdb.getPassword(), user.getPassword());

		if (valid) {

			String acstokenurl = String.format(accesstokenurl, localdb.getMobileno(), user.getPassword());

			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> headers = addBasicAuth(ouathbasicusn, oauthbasicpwd);
			HttpEntity<?> request = new HttpEntity<>("", headers);
			System.out.println("acstokenurl -->" + acstokenurl);
			ResponseEntity<String> response = restTemplate.exchange(acstokenurl, HttpMethod.POST, request,
					String.class);

			return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 
	 * @param access_token
	 * @return
	 * @throws NotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	@RequestMapping(value = "/user/accesstoken", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<String> isValidAccessToken(@RequestBody String access_token)
			throws NotFoundException, NoSuchAlgorithmException, NoSuchProviderException {

		String acstokenchk = String.format(accesstokenchek, access_token);
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = addBasicAuth(ouathbasicusn, oauthbasicpwd);
		HttpEntity<?> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.exchange(acstokenchk, HttpMethod.GET, request, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(response.getBody(), HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * 
	 * @param refresh_token
	 * @return
	 * @throws NotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	@RequestMapping(value = "/user/refreshtoken", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<String> getTokenfrmRefreshToken(@RequestBody String refresh_token)
			throws NotFoundException, NoSuchAlgorithmException, NoSuchProviderException {

		String acstokenchk = String.format(refreshtoken, refresh_token);

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = addBasicAuth(ouathbasicusn, oauthbasicpwd);
		HttpEntity<?> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.exchange(acstokenchk, HttpMethod.POST, request, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(response.getBody(), HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	@RequestMapping(value = "/wallet/get/{mobileno}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Wallet> getWallet(@PathVariable("mobileno") String mobileno) throws NotFoundException {
		User userDetails = userService.findByMobileno(mobileno);
		return new ResponseEntity<Wallet>(walletService.findbyUserId(userDetails), HttpStatus.OK);

	}

	@RequestMapping(value = "/rewards/get/{mobileno}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Rewardpoint> getRewards(@PathVariable("mobileno") String mobileno) throws NotFoundException {
		User userDetails = userService.findByMobileno(mobileno);
		return new ResponseEntity<Rewardpoint>(rewardsService.findByUser(userDetails), HttpStatus.OK);

	}

	/**
	 * 
	 * @param dbPassword
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	private boolean isAuthenticated(String dbPassword, String password)
			throws NoSuchAlgorithmException, NoSuchProviderException {

		PasswordEncryption decrypted = new PasswordEncryption();
		String decryptedpwd = decrypted.decrypt(dbPassword);
		if (decryptedpwd == null || password == null || !(password.equalsIgnoreCase(decryptedpwd))) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param email
	 * @param name
	 * @param password
	 * @param phone
	 * @param username
	 * @return
	 * @throws JSONException
	 */
	private boolean OAUTH2Registration(String email, String name, String password, String phone, String username)
			throws JSONException {

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> headers = headers();
		String oauthReq = prepareOAUTHRequest(email, name, password, phone, username);
		HttpEntity<?> request = new HttpEntity<>(oauthReq, headers);

		ResponseEntity<String> response = restTemplate.exchange(oauthURL, HttpMethod.POST, request, String.class);
		response.getBody();

		return true;
	}

	/**
	 * 
	 * @param email
	 * @param name
	 * @param password
	 * @param phone
	 * @param username
	 * @return
	 * @throws JSONException
	 */
	private String prepareOAUTHRequest(String email, String name, String password, String phone, String username)
			throws JSONException {

		JSONObject oauthReq = new JSONObject();
		oauthReq.put("email", email);
		oauthReq.put("name", name);
		oauthReq.put("password", password);
		oauthReq.put("phone", phone);
		oauthReq.put("userName", username);
		return oauthReq.toString();
	}
}

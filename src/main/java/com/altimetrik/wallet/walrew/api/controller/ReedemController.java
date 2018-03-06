package com.altimetrik.wallet.walrew.api.controller;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.wallet.walrew.exception.WalletException;
import com.altimetrik.wallet.walrew.exception.WalletException.WalletError;
import com.altimetrik.wallet.walrew.model.Rewardpoint;
import com.altimetrik.wallet.walrew.model.User;

@RestController
@RequestMapping(value = "/api")
public class ReedemController extends BaseController {

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productcatalog", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public String getProductCatalog() {

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = headers();
		HttpEntity<?> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.exchange(catalogurl, HttpMethod.GET, request, String.class);
		return response.getBody();
	}

	/**
	 * 
	 * @param reedem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reedemrewards", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public String reedemRewards(@RequestBody String reedem) throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> headers = headers();
		JSONObject redeemDetails = new JSONObject(reedem);
		JSONObject product = redeemDetails.optJSONObject("productid");

		// This should be avoided here, as user can tampers cost from front-end
		Double costofProduct = product.optDouble("productcost");
		String mobile = redeemDetails.optString("mobileno");
		User user = userService.findByMobileno(mobile);
		// Wallet wallet = walletService.findbyUserId(user);
		Rewardpoint rewards = rewardsService.findByUser(user);

		// Card card = cardService.findByWallet(wallet);
		// card.getAvailableAmount();
		// String cardnostring = new String(card.getCardnumber());
		// int cardno = Integer.parseInt(cardnostring);

		JSONObject order = new JSONObject();
		order.put("transactionid", user.getId());
		order.put("productid", redeemDetails.optJSONObject("productid"));
		order.put("orderdate", System.currentTimeMillis());

		// List<Rewardpoint> rewardsPoints = rewardsService.findByCardId(card);
		// Double rewardsvalue = new Double(0.0);
		// for (Rewardpoint rw : rewardsPoints) {
		// rewardsvalue = rewardsvalue + rw.getValue();
		// }
		// rewardsvalue = rewardsvalue + wallet.getTopUp().doubleValue();

		if (rewards.getValue() < costofProduct.doubleValue()) {

			WalletException we = new WalletException(WalletError.INVALID_INPUT_FORMAT_60203);
			throw new Exception(we.getErrorMessage());

		}

		HttpEntity<?> request = new HttpEntity<>(order.toString(), headers);
		ResponseEntity<String> response = restTemplate.postForEntity(orderurl, request, String.class);

		rewards.setValue(rewards.getValue() - costofProduct.doubleValue());
		rewardsService.updateRewardpoint(rewards);
		return response.getBody();
	}

	@RequestMapping(value = "/reedemrewards/filter", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public String getFilterProductCatalog(@RequestBody String filters) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = headers();
		HttpEntity<?> request = new HttpEntity<>(filters, headers);
		ResponseEntity<String> response = restTemplate.exchange(catalogfilter, HttpMethod.POST, request, String.class);
		return response.getBody();
	}
}

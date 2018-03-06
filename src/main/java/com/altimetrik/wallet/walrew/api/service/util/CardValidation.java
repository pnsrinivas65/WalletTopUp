package com.altimetrik.wallet.walrew.api.service.util;

public class CardValidation {

	/*public static void main(String s[]) {
		CardValidation cvalidation = new CardValidation();
		CardNetworkType cn = cvalidation.detectCardType("5105105105105100");
		System.out.println(cn.name());
	}*/
	public CardNetworkType detectCardType(String cardnumber) {

		return CardNetworkType.detect(cardnumber);
	}

	public boolean isValid(String cardNumber) {

		int sum = 0;
		boolean alternate = false;

		for (int i = cardNumber.length() - 1; i >= 0; i--) {

			int n = Integer.parseInt(cardNumber.substring(i, i + 1));

			if (alternate) {
				n *= 2;

				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}
}

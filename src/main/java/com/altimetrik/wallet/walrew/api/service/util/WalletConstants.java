package com.altimetrik.wallet.walrew.api.service.util;

public class WalletConstants {

	public enum Walletenum {
		INACTIVE(0), ACTIVE(1);

		public int status;

		private Walletenum(int status) {
			this.status = status;
		}

	}
}

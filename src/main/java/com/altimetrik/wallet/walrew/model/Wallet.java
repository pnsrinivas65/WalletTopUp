package com.altimetrik.wallet.walrew.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;

	private String walletname = null;

	@OneToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "id", insertable = false, updatable = false)
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user = null;

	@Column(columnDefinition = "bigint")
	private BigDecimal topup;

	/*
	 * @Column(columnDefinition = "bigint") private BigDecimal rewardspoints;
	 */
	/**
	 * {"primaryKey":true}
	 **/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * {}
	 **/
	public String getWalletname() {
		return walletname;
	}

	public void setWalletname(String walletname) {
		this.walletname = walletname;
	}

	/**
	 * {"foreignKeyColumn":"id","relation":"OneToOne"}
	 **/
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getTopUp() {
		return topup;
	}

	public void setTopUp(BigDecimal topup) {
		this.topup = topup;
	}

	/*
	 * public BigDecimal getRewardspoints() { return rewardspoints; }
	 * 
	 * public void setRewardspoints(BigDecimal rewardspoints) {
	 * this.rewardspoints = rewardspoints; }
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Wallet wallet = (Wallet) o;
		return Objects.equals(id, wallet.id) && Objects.equals(walletname, wallet.walletname)
				&& Objects.equals(user, wallet.user) && Objects.equals(topup, wallet.topup);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, walletname, user, topup);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Wallet {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  walletname: ").append(walletname).append("\n");
		sb.append("  user: ").append(user).append("\n");
		sb.append("  topup: ").append(topup).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}

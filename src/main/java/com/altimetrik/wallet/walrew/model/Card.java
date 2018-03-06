package com.altimetrik.wallet.walrew.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;

	private String nameoncard = null;
	private String cardnumber = null;
	private String cvv = null;
	private String networktype = null;

	private Date expirydate = null;

	@OneToOne
	@JoinColumn(name="walletid", referencedColumnName="id")
	private Wallet wallet = null;

	@OneToOne
	@JoinColumn(name="productid", referencedColumnName="id")
	private Product product;
	
	@Column(columnDefinition="bigint")
	private BigDecimal availablebalance;

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
	public String getNameoncard() {
		return nameoncard;
	}

	public void setNameoncard(String nameoncard) {
		this.nameoncard = nameoncard;
	}

	/**
	 * {}
	 **/
	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	/**
	 * {}
	 **/
	public Date getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	/**
	 * {}
	 **/
	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	/**
	 * {}
	 **/
	public String getNetworktype() {
		return networktype;
	}

	public void setNetworktype(String networktype) {
		this.networktype = networktype;
	}

	/**
	 * {"foreignKeyColumn":"id","relation":"OneToOne"}
	 **/
	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	/**
	 * {"foreignKeyColumn":"id","relation":"OneToOne"}
	 **/
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	public BigDecimal getAvailableAmount() {
		return availablebalance;
	}

	public void setAvailableAmount(BigDecimal availablebalance) {
		this.availablebalance = availablebalance;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Card card = (Card) o;
		return Objects.equals(id, card.id) 
				&& Objects.equals(nameoncard, card.nameoncard)
				&& Objects.equals(cardnumber, card.cardnumber) 
				&& Objects.equals(expirydate, card.expirydate)
				&& Objects.equals(cvv, card.cvv) 
				&& Objects.equals(networktype, card.networktype)
				&& Objects.equals(wallet, card.wallet)
				&& Objects.equals(availablebalance, card.availablebalance)
				&& Objects.equals(product, card.product);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nameoncard, cardnumber, expirydate, 
				cvv, networktype, wallet, product, availablebalance);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Card {\n");

		sb.append("  id: ").append(id).append("\n");
		sb.append("  nameoncard: ").append(nameoncard).append("\n");
		sb.append("  cardnumber: ").append(cardnumber).append("\n");
		sb.append("  expirydate: ").append(expirydate).append("\n");
		sb.append("  cvv: ").append(cvv).append("\n");
		sb.append("  networktype: ").append(networktype).append("\n");
		sb.append("  wallet: ").append(wallet).append("\n");
		sb.append("  product: ").append(product).append("\n");
		sb.append("  availableAmount: ").append(availablebalance).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}

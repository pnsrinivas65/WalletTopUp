package com.altimetrik.wallet.walrew.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;

	private String transactiontype = null;
	private String transaction = null;

	@Column(columnDefinition = "decimal")
	private Double points = null;

	// @OneToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name="cardid", referencedColumnName="id")
	private String card = null;

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
	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	/**
	 * {}
	 **/
	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	/**
	 * {}
	 **/
	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	/**
	 * {"foreignKeyColumn":"id","relation":"OneToOne"}
	 **/
	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Transaction transaction = (Transaction) o;
		return Objects.equals(id, transaction.id) && Objects.equals(transactiontype, transaction.transactiontype)
				&& Objects.equals(transaction, transaction.transaction) && Objects.equals(points, transaction.points)
				&& Objects.equals(card, transaction.card);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, transactiontype, transaction, points, card);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Transaction {\n");

		sb.append("  id: ").append(id).append("\n");
		sb.append("  transactiontype: ").append(transactiontype).append("\n");
		sb.append("  transaction: ").append(transaction).append("\n");
		sb.append("  points: ").append(points).append("\n");
		sb.append("  card: ").append(card).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}

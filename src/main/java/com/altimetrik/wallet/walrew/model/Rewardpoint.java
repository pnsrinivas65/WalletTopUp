package com.altimetrik.wallet.walrew.model;

import java.util.Date;
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
public class Rewardpoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;

	private String rewardname = null;
	private String rewardtype = null;

	@Column(columnDefinition = "decimal")
	private Double value = null;
	private Date rewardexpiry = null;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user = null;

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
	public String getRewardname() {
		return rewardname;
	}

	public void setRewardname(String rewardname) {
		this.rewardname = rewardname;
	}

	/**
	 * {}
	 **/
	public String getRewardtype() {
		return rewardtype;
	}

	public void setRewardtype(String rewardtype) {
		this.rewardtype = rewardtype;
	}

	/**
	 * {}
	 **/
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * {}
	 **/
	public Date getRewardexpiry() {
		return rewardexpiry;
	}

	public void setRewardexpiry(Date rewardexpiry) {
		this.rewardexpiry = rewardexpiry;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Rewardpoint rewardpoint = (Rewardpoint) o;
		return Objects.equals(id, rewardpoint.id) && Objects.equals(rewardname, rewardpoint.rewardname)
				&& Objects.equals(rewardtype, rewardpoint.rewardtype) && Objects.equals(value, rewardpoint.value)
				&& Objects.equals(rewardexpiry, rewardpoint.rewardexpiry) && Objects.equals(user, rewardpoint.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, rewardname, rewardtype, value, rewardexpiry, user);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Rewardpoint {\n");

		sb.append("  id: ").append(id).append("\n");
		sb.append("  rewardname: ").append(rewardname).append("\n");
		sb.append("  rewardtype: ").append(rewardtype).append("\n");
		sb.append("  value: ").append(value).append("\n");
		sb.append("  rewardexpiry: ").append(rewardexpiry).append("\n");
		sb.append("  user: ").append(user).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}

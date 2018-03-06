package com.altimetrik.wallet.walrew.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Smscodes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userid", referencedColumnName="id")
	private User user;

	private String code = null;
	private String status = null;
	private long creationtimestamp;

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
	 * {"foreignKeyColumn":"id","relation":"OneToOne"}
	 **/
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * {}
	 **/
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * {}
	 **/
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * {}
	 **/
	public long getCreationtimestamp() {
		return creationtimestamp;
	}

	public void setCreationtimestamp(long creationtimestamp) {
		this.creationtimestamp = creationtimestamp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Smscodes smscodes = (Smscodes) o;
		return Objects.equals(id, smscodes.id) && Objects.equals(user, smscodes.user)
				&& Objects.equals(code, smscodes.code) && Objects.equals(status, smscodes.status)
				&& Objects.equals(creationtimestamp, smscodes.creationtimestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, user, code, status, creationtimestamp);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Smscodes {\n");

		sb.append("  id: ").append(id).append("\n");
		sb.append("  user: ").append(user).append("\n");
		sb.append("  code: ").append(code).append("\n");
		sb.append("  status: ").append(status).append("\n");
		sb.append("  creationtimestamp: ").append(creationtimestamp).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}

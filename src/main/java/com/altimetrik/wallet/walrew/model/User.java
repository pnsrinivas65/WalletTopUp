package com.altimetrik.wallet.walrew.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;

	private String username = null;
	private String password = null;
	private String email = null;
	private String mobileno = null;
	private String apikey = null;
	private String createdby = null;
	private String status = null;
	private String firstname = null;
	private String lastname = null;
	private String gender = null;

	private Date dateofbirth = null;
	private Date creationtimestamp = null;

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
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * {}
	 **/
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * {}
	 **/
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * {}
	 **/
	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	/**
	 * {}
	 **/
	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	/**
	 * {}
	 **/
	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * {}
	 **/
	public Date getCreationtimestamp() {
		return creationtimestamp;
	}

	public void setCreationtimestamp(Date creationtimestamp) {
		this.creationtimestamp = creationtimestamp;
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
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * {}
	 **/
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * {}
	 **/
	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	/**
	 * {}
	 **/
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(username, user.username)
				&& Objects.equals(password, user.password) && Objects.equals(email, user.email)
				&& Objects.equals(mobileno, user.mobileno) && Objects.equals(apikey, user.apikey)
				&& Objects.equals(createdby, user.createdby)
				&& Objects.equals(creationtimestamp, user.creationtimestamp) && Objects.equals(status, user.status)
				&& Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname)
				&& Objects.equals(dateofbirth, user.dateofbirth) && Objects.equals(gender, user.gender);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, email, mobileno, apikey, createdby, creationtimestamp, status,
				firstname, lastname, dateofbirth, gender);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class User {\n");

		sb.append("  id: ").append(id).append("\n");
		sb.append("  username: ").append(username).append("\n");
		sb.append("  password: ").append(password).append("\n");
		sb.append("  email: ").append(email).append("\n");
		sb.append("  mobileno: ").append(mobileno).append("\n");
		sb.append("  apikey: ").append(apikey).append("\n");
		sb.append("  createdby: ").append(createdby).append("\n");
		sb.append("  creationtimestamp: ").append(creationtimestamp).append("\n");
		sb.append("  status: ").append(status).append("\n");
		sb.append("  firstname: ").append(firstname).append("\n");
		sb.append("  lastname: ").append(lastname).append("\n");
		sb.append("  dateofbirth: ").append(dateofbirth).append("\n");
		sb.append("  gender: ").append(gender).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
package com.altimetrik.wallet.walrew.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;

	private String producttype = null;
	private String productname = null;
	private String productvalues = null;

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
	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	/**
	 * {}
	 **/
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	/**
	 * {}
	 **/
	public String getProductvalues() {
		return productvalues;
	}

	public void setProductvalues(String productvalues) {
		this.productvalues = productvalues;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Product product = (Product) o;
		return Objects.equals(id, product.id) 
				&& Objects.equals(producttype, product.producttype)
				&& Objects.equals(productname, product.productname)
				&& Objects.equals(productvalues, product.productvalues);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, producttype, productname, productvalues);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Product {\n");

		sb.append("  id: ").append(id).append("\n");
		sb.append("  producttype: ").append(producttype).append("\n");
		sb.append("  productname: ").append(productname).append("\n");
		sb.append("  productvalues: ").append(productvalues).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}

package com.qingzhou.client.domain;

import java.io.Serializable;

public class BaseDetail implements Serializable{
	
	private String basName;
	private String basUnit;
	private String basePrice;
	private String practicalcount;
	private String practicalprice;
	
	public String getBasName() {
		return basName;
	}
	public void setBasName(String basName) {
		this.basName = basName;
	}
	public String getBasUnit() {
		return basUnit;
	}
	public void setBasUnit(String basUnit) {
		this.basUnit = basUnit;
	}
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public String getPracticalcount() {
		return practicalcount;
	}
	public void setPracticalcount(String practicalcount) {
		this.practicalcount = practicalcount;
	}
	public String getPracticalprice() {
		return practicalprice;
	}
	public void setPracticalprice(String practicalprice) {
		this.practicalprice = practicalprice;
	}
	

}

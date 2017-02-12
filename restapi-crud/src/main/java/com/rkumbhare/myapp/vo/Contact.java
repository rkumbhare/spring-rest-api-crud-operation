/**
 * @auther Rakesh
 * @time Feb 12, 2017
 */

package com.rkumbhare.myapp.vo;

public class Contact {

	private String mobile;
	private String phone;

	public Contact() {
	}

	public Contact(String mobile, String phone) {
		this.mobile = mobile;
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}

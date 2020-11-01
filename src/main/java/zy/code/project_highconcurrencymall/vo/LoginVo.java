package zy.code.project_highconcurrencymall.vo;

import org.hibernate.validator.constraints.Length;
import zy.code.project_highconcurrencymall.validator.IsMobile;

import javax.validation.constraints.NotNull;

/**
 * 登录者
 */

public class LoginVo {
	@NotNull
	@IsMobile(required = true)
	private String mobile;
	
	@NotNull
	@Length(min = 32)
	private String password;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginVo [mobile=" + mobile + ", password=" + password + "]";
	}
}

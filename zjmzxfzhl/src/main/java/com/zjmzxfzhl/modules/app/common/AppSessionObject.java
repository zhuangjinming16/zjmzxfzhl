package com.zjmzxfzhl.modules.app.common;

import java.io.Serializable;
import java.util.Date;

import com.zjmzxfzhl.modules.app.entity.AppUser;

import lombok.Data;

@Data
public class AppSessionObject implements Serializable {
	private static final long serialVersionUID = 1L;
	public String token;
	private String userId;
	private AppUser appUser;
	private Date loginTime;
	private String ipAddr;
}

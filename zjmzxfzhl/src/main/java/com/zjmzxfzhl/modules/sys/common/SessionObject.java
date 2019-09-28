package com.zjmzxfzhl.modules.sys.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zjmzxfzhl.modules.sys.entity.SysOrg;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.Route;

import lombok.Data;

@Data
public class SessionObject implements Serializable {
	public static final String BEAN_ID = "sessionObject";
	private static final long serialVersionUID = 1L;
	public String token;
	private SysUser sysUser;
	private SysOrg sysOrg;
	private SysRole sysRole;
	private List<SysRole> sysRoles;
	private List<Route> routes;
	private List<String> funcIds;
	private List<String> permissions;
	private Date loginTime;
	private String ipAddr;
}

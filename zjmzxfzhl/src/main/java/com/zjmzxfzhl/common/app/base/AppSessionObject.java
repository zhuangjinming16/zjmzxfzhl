package com.zjmzxfzhl.common.app.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class AppSessionObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public String token;
    private String userId;
    private String userName;
    private Date loginTime;
    private String ipAddr;
}

package com.zjmzxfzhl.modules.app.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class AppTransVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String transId;
    @NotBlank
    private String version;
    @NotBlank
    private String data;
    @NotBlank
    private String checkcode;
}

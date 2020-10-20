package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Data;

/**
 * @author 庄金明
 */
@Data
public class CcToVo {
    private String userId;
    private String userName;

    @Override
    public String toString(){
        return String.format("%s[%s]",this.userName,this.userId);
    }
}

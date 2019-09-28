package com.zjmzxfzhl.modules.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.app.entity.AppUser;

/**
 * 用户Mapper
 * 
 * @author 庄金明
 */
public interface AppUserMapper extends BaseMapper<AppUser> {
	public List<AppUser> list(IPage<AppUser> page, @Param("entity") AppUser entity);
}

package com.zjmzxfzhl.modules.sys.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.Constants;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.common.exception.SysException;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.RedisUtil;
import com.zjmzxfzhl.modules.sys.entity.SysConfig;
import com.zjmzxfzhl.modules.sys.mapper.SysConfigMapper;

/**
 * 系统参数Service
 * 
 * @author 庄金明
 */
@Service
public class SysConfigService extends BaseService<SysConfigMapper, SysConfig> {

	@Autowired
	private RedisUtil redisUtil;

	public IPage<SysConfig> list(IPage<SysConfig> page, SysConfig sysConfig) {
		return page.setRecords(baseMapper.list(page, sysConfig));
	}

	public void loadSysConfigToRedis(String configIds) {
		String[] configIdsArr = null;
		if (CommonUtil.isNotEmptyAfterTrim(configIds)) {
			configIdsArr = configIds.split(",");
			for (String configId : configIdsArr) {
				redisUtil.del(Constants.PREFIX_SYS_CONFIG + configId);// 先清除
			}
		} else {
			redisUtil.delPattern(Constants.PREFIX_SYS_CONFIG + "*");// 先清除
		}
		QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
		if (configIdsArr != null && configIdsArr.length > 0) {
			queryWrapper.in("config_id", (Object[]) configIdsArr);
		}
		queryWrapper.orderByAsc("SORT_NO");
		List<SysConfig> list = this.list(queryWrapper);
		for (SysConfig sysConfig : list) {
			redisUtil.set(Constants.PREFIX_SYS_CONFIG + sysConfig.getConfigId(), sysConfig.getConfigValue());
		}
	}

	/**
	 * 保存系统参数，并加载进redis缓存
	 * 
	 * @param sysConfig
	 */
	public void saveSysConfig(SysConfig sysConfig) {
		this.save(sysConfig);
		this.loadSysConfigToRedis(sysConfig.getConfigId());
	}

	/**
	 * 修改系统参数，并加载进redis缓存
	 * 
	 * @param sysConfig
	 */
	public void updateSysConfig(SysConfig sysConfig) {
		this.updateById(sysConfig);
		this.loadSysConfigToRedis(sysConfig.getConfigId());
	}

	/**
	 * 删除系统参数，并重新加载redis缓存
	 * 
	 * @param ids
	 */
	public void deleteSysConfig(String ids) {
		if (ids == null || ids.trim().length() == 0) {
			throw new SysException("ids can't be empty");
		}
		String[] idsArr = ids.split(",");
		if (idsArr.length > 1) {
			this.removeByIds(Arrays.asList(idsArr));
		} else {
			this.removeById(idsArr[0]);
		}
		this.loadSysConfigToRedis(null);
	}
}

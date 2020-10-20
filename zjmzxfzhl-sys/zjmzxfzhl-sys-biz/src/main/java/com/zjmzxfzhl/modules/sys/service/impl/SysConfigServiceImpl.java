package com.zjmzxfzhl.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.common.core.constant.CacheConstants;
import com.zjmzxfzhl.common.core.exception.SysException;
import com.zjmzxfzhl.common.core.redis.util.RedisUtil;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.modules.sys.entity.SysConfig;
import com.zjmzxfzhl.modules.sys.mapper.SysConfigMapper;
import com.zjmzxfzhl.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 系统参数Service
 *
 * @author 庄金明
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IPage<SysConfig> list(IPage<SysConfig> page, SysConfig sysConfig) {
        return page.setRecords(baseMapper.list(page, sysConfig));
    }

    @Override
    public void loadSysConfigToRedis(String configIds) {
        String[] configIdsArr = null;
        if (CommonUtil.isNotEmptyAfterTrim(configIds)) {
            configIdsArr = configIds.split(",");
            for (String configId : configIdsArr) {
                // 先清除
                redisUtil.del(CacheConstants.SYS_CONFIG + configId);
            }
        } else {
            // 先清除
            redisUtil.delPattern(CacheConstants.SYS_CONFIG + "*");
        }
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        if (configIdsArr != null && configIdsArr.length > 0) {
            queryWrapper.in("config_id", (Object[]) configIdsArr);
        }
        queryWrapper.orderByAsc("SORT_NO");
        List<SysConfig> list = this.list(queryWrapper);
        for (SysConfig sysConfig : list) {
            redisUtil.set(CacheConstants.SYS_CONFIG + sysConfig.getConfigId(), sysConfig.getConfigValue());
        }
    }

    /**
     * 保存系统参数，并加载进redis缓存
     *
     * @param sysConfig
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysConfig(SysConfig sysConfig) {
        this.save(sysConfig);
        this.loadSysConfigToRedis(sysConfig.getConfigId());
    }

    /**
     * 修改系统参数，并加载进redis缓存
     *
     * @param sysConfig
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysConfig(SysConfig sysConfig) {
        this.updateById(sysConfig);
        this.loadSysConfigToRedis(sysConfig.getConfigId());
    }

    /**
     * 删除系统参数，并重新加载redis缓存
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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

package com.zjmzxfzhl.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.common.core.constant.CacheConstants;
import com.zjmzxfzhl.common.core.exception.SysException;
import com.zjmzxfzhl.common.core.redis.util.RedisUtil;
import com.zjmzxfzhl.modules.sys.entity.SysCodeInfo;
import com.zjmzxfzhl.modules.sys.entity.SysCodeType;
import com.zjmzxfzhl.modules.sys.mapper.SysCodeTypeMapper;
import com.zjmzxfzhl.modules.sys.service.SysCodeInfoService;
import com.zjmzxfzhl.modules.sys.service.SysCodeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * 代码类别Service
 *
 * @author 庄金明
 */
@Service
public class SysCodeTypeServiceImpl extends BaseServiceImpl<SysCodeTypeMapper, SysCodeType> implements SysCodeTypeService {

    @Autowired
    private SysCodeInfoService sysCodeInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IPage<SysCodeType> list(IPage<SysCodeType> page, SysCodeType sysCodeType) {
        return page.setRecords(baseMapper.list(page, sysCodeType));
    }

    /**
     * 删除数据字典信息
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysCodeType(String ids) {
        if (ids == null || ids.trim().length() == 0) {
            throw new SysException("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        if (idsArr.length > 1) {
            removeByIds(Arrays.asList(idsArr));
        } else {
            removeById(idsArr[0]);
        }
        sysCodeInfoService.remove(new QueryWrapper<SysCodeInfo>().in("code_type_id", (Object[]) idsArr));
        for (String codeTypeId : idsArr) {
            redisUtil.del(CacheConstants.SYS_CODE_TYPE + codeTypeId);
        }
    }
}

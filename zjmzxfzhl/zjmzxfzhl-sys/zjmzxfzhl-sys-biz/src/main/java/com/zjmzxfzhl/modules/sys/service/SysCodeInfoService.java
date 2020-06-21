package com.zjmzxfzhl.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysCodeInfo;

/**
 * 代码信息Service
 * 
 * @author 庄金明
 */
public interface SysCodeInfoService extends BaseService<SysCodeInfo> {

    /**
     * 分页查询代码信息
     * 
     * @param page
     * @param sysCodeInfo
     * @return
     */
    IPage<SysCodeInfo> list(IPage<SysCodeInfo> page, SysCodeInfo sysCodeInfo);

    /**
     * 加载数据字典进redis
     * 
     * @param codeTypeIds
     *            数据字典数组，以逗号隔开， 若传空则加载所有数据字典
     */
    void loadSysCodeInfoToRedis(String codeTypeIds);

    /**
     * 从数据库查询数据字典
     * 
     * @param codeTypeIds
     *            数据字典数组，以逗号隔开， 若传空则加载所有数据字典
     * @return
     */
    Map<String, List<SysCodeInfo>> getSysCodeInfosFromDb(String codeTypeIds);

    /**
     * 查询数据字典
     * 
     * @param codeTypeIds
     *            数据字典数组，以逗号隔开， 若传空则加载所有数据字典
     * @return
     */
    Map<String, List<SysCodeInfo>> getSysCodeInfosFromRedis(String codeTypeIds);

    /**
     * 新增代码信息，并加载进redis缓存
     * 
     * @param sysCodeInfo
     */
    void saveSysCodeInfo(SysCodeInfo sysCodeInfo);

    /**
     * 修改代码信息，并加载进redis缓存
     * 
     * @param sysCodeInfo
     */
    void updateSysCodeInfo(SysCodeInfo sysCodeInfo);

    /**
     * 删除代码信息，并重新加载redis缓存
     * 
     * @param ids
     */
    void deleteSysCodeInfo(String ids);
}

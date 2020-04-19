package com.zjmzxfzhl.modules.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;

/**
 * 开发示例Service
 * 
 * @author 庄金明
 */
public interface DemoZjmzxfzhlService extends BaseService<DemoZjmzxfzhl> {
    /**
     * 分页查询开发实例
     * 
     * @param page
     * @param demoZjmzxfzhl
     * @return
     */
    public IPage<DemoZjmzxfzhl> list(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl);

    /**
     * 数据权限示例一
     * 
     * 通过配置表 T_SYS_DATA_PERMISSION 实现简单数据权限
     * 
     * @ 1.配置 数据源策略：1-用户录入
     * @ 2.配置 角色数据权限 或者 人员数据权限
     * @ 3.若未指定fieldName则默认注入authFilterSql属性中
     * @param page
     * @param demoZjmzxfzhl
     * @return
     */
    public IPage<DemoZjmzxfzhl> listDataPermission1(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl);

    /**
     * 数据权限示例二
     * 
     * 通过实现AbstractDataPermissionProvider配置数据权限
     * 
     * @param page
     * @param demoZjmzxfzhl
     * @return
     */
    public IPage<DemoZjmzxfzhl> listDataPermission2(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl);

    /**
     * 数据权限示例三，示例一与示例二结合体，二则是且的关系
     * 
     * 通过配置表T_SYS_DATA_PERMISSION + 实现AbstractDataPermissionProvider配置数据权限
     * 
     * @param page
     * @param demoZjmzxfzhl
     * @return
     */
    public IPage<DemoZjmzxfzhl> listDataPermission3(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl);

    /**
     * 数据权限示例四
     * 
     * DataPermission是可以重复注解,配置多个DataPermission注入不同参数
     * 
     * @1.注入第2个参数DemoZjmzxfzhl1中
     * @2.注入第3个参数DemoZjmzxfzhl2中
     * @param page
     * @param demoZjmzxfzhl1
     * @param demoZjmzxfzhl2
     * @return
     */
    public IPage<DemoZjmzxfzhl> listDataPermission4(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl1,
            DemoZjmzxfzhl demoZjmzxfzhl2);

    /**
     * 数据权限示例五
     * 
     * DataPermission是可以重复注解,配置多个DataPermission注入相同参数不同属性
     * 
     * @param page
     * @param demoZjmzxfzhl
     * @return
     */
    public IPage<DemoZjmzxfzhl> listDataPermission5(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl);
}

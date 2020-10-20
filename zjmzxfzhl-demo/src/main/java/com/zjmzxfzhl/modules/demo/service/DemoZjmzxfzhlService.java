package com.zjmzxfzhl.modules.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
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
}

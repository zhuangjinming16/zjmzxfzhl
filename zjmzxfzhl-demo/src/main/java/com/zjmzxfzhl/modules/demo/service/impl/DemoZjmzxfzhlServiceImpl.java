package com.zjmzxfzhl.modules.demo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;
import com.zjmzxfzhl.modules.demo.mapper.DemoZjmzxfzhlMapper;
import com.zjmzxfzhl.modules.demo.service.DemoZjmzxfzhlService;

/**
 * 开发示例Service
 *
 * @author 庄金明
 */
@Service
public class DemoZjmzxfzhlServiceImpl extends BaseServiceImpl<DemoZjmzxfzhlMapper, DemoZjmzxfzhl> implements DemoZjmzxfzhlService {
    @Override
    public IPage<DemoZjmzxfzhl> list(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl) {
        return page.setRecords(baseMapper.list(page, demoZjmzxfzhl));
    }
}

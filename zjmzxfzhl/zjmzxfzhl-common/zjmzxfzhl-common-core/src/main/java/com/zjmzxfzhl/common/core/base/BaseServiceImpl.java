package com.zjmzxfzhl.common.core.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
}

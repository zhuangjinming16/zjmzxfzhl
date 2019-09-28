package com.zjmzxfzhl.common.base;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
}

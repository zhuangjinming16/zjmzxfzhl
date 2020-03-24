package com.zjmzxfzhl.modules.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;

/**
 * 开发示例Mapper
 * 
 * @author 庄金明
 */
public interface DemoZjmzxfzhlMapper extends BaseMapper<DemoZjmzxfzhl> {
	/**
	 * 查询开发示例列表
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public List<DemoZjmzxfzhl> list(IPage<DemoZjmzxfzhl> page, @Param("entity") DemoZjmzxfzhl entity);

	/**
	 * 数据权限示例使用
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public List<DemoZjmzxfzhl> list1(IPage<DemoZjmzxfzhl> page, @Param("entity") DemoZjmzxfzhl entity);

	/**
	 * 数据权限示例使用
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public List<DemoZjmzxfzhl> list2(IPage<DemoZjmzxfzhl> page, @Param("entity") DemoZjmzxfzhl entity);

	/**
	 * 数据权限示例使用
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public List<DemoZjmzxfzhl> list3(IPage<DemoZjmzxfzhl> page, @Param("entity") DemoZjmzxfzhl entity);
}

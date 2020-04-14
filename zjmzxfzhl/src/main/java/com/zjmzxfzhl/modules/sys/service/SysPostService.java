package com.zjmzxfzhl.modules.sys.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.common.permission.FilterOperate;
import com.zjmzxfzhl.common.query.QueryWrapperGenerator;
import com.zjmzxfzhl.modules.sys.entity.SysPost;
import com.zjmzxfzhl.modules.sys.entity.SysPostUser;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.mapper.SysPostMapper;

/**
 * 岗位Service
 * 
 * @author 庄金明
 */
@Service
public class SysPostService extends BaseService<SysPostMapper, SysPost> {
	@Autowired
	private SysPostUserService sysPostUserService;

	public IPage<SysPost> list(IPage<SysPost> page, SysPost sysPost) {
		return page.setRecords(baseMapper.list(page, sysPost));
	}

	/**
	 * 查询岗位用户
	 * 
	 * @param page
	 * @param sysPostUser
	 * @return
	 */
	public IPage<SysUser> getPostUser(Page<SysUser> page, SysPostUser sysPostUser) {
		return page.setRecords(baseMapper.getPostUser(page, sysPostUser));
	}

	/**
	 * 保存岗位用户
	 * 
	 * @param postId
	 * @param userIds
	 */
	public void savePostUsers(String postId, String userIds) {
		String[] userIdArray = userIds.split(",");
		// 【1】先删除岗位用户
		QueryWrapper<SysPostUser> queryWrapper = new QueryWrapper<>();
		QueryWrapperGenerator.addEasyQuery(queryWrapper, "postId", FilterOperate.EQ, postId);
		QueryWrapperGenerator.addEasyQuery(queryWrapper, "userId", FilterOperate.IN, userIdArray);
		this.sysPostUserService.remove(queryWrapper);

		// 【2】保存岗位用户
		for (int i = 0; i < userIdArray.length; i++) {
			SysPostUser sysPostUser = new SysPostUser(postId, userIdArray[i]);
			this.sysPostUserService.save(sysPostUser);
		}
	}

	/**
	 * 删除岗位用户
	 * 
	 * @param postId
	 * @param userIds
	 */
	public void deletePostUsers(String postId, String userIds) {
		String[] userIdArray = userIds.split(",");
		QueryWrapper<SysPostUser> queryWrapper = new QueryWrapper<>();
		QueryWrapperGenerator.addEasyQuery(queryWrapper, "postId", FilterOperate.EQ, postId);
		QueryWrapperGenerator.addEasyQuery(queryWrapper, "userId", FilterOperate.IN, userIdArray);
		this.sysPostUserService.remove(queryWrapper);
	}

	/**
	 * 删除岗位
	 * 
	 * @param ids
	 */
	public void delete(String ids) {
		String[] idsArr = ids.split(",");
		if (idsArr.length > 1) {
			this.removeByIds(Arrays.asList(idsArr));
		} else {
			this.removeById(idsArr[0]);
		}
		this.sysPostUserService.remove(new QueryWrapper<SysPostUser>().in("POST_ID", (Object[]) idsArr));
	}
}

package com.zjmzxfzhl.modules.flowable.identity;

import java.util.ArrayList;
import java.util.List;

import org.flowable.idm.api.User;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityManagerImpl;
import org.flowable.idm.engine.impl.persistence.entity.data.UserDataManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.service.SysUserService;

public class CustomUserEntityManager extends UserEntityManagerImpl {
	@Autowired
	private SysUserService sysUserService;

	public CustomUserEntityManager(IdmEngineConfiguration idmEngineConfiguration, UserDataManager userDataManager) {
		super(idmEngineConfiguration, userDataManager);
	}

	@Override
	public UserEntity findById(String entityId) {
		SysUser sysUser = sysUserService.getById(entityId);
		if (sysUser == null) {
			return null;
		}
		UserEntity userEntity = new UserEntityImpl();
		userEntity.setId(sysUser.getUserId());
		userEntity.setFirstName(sysUser.getUserName());
		userEntity.setLastName(sysUser.getUserName());
		userEntity.setDisplayName(sysUser.getUserName());
		userEntity.setEmail(sysUser.getEmail());
		return userEntity;
	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl query) {
		List<SysUser> sysUsers = sysUserService.getBaseMapper().selectList(flowableQueryToWrapper(query));
		if (sysUsers == null || sysUsers.size() == 0) {
			return new ArrayList<>();
		}
		List<User> users = new ArrayList<>();
		for (SysUser sysUser : sysUsers) {
			User user = new UserEntityImpl();
			user.setId(sysUser.getUserId());
			user.setFirstName(sysUser.getUserName());
			user.setLastName(sysUser.getUserName());
			user.setDisplayName(sysUser.getUserName());
			user.setEmail(sysUser.getEmail());
			users.add(user);
		}
		return users;
	}

	@Override
	public long findUserCountByQueryCriteria(UserQueryImpl query) {
		return sysUserService.getBaseMapper().selectCount(flowableQueryToWrapper(query));
	}

	private QueryWrapper<SysUser> flowableQueryToWrapper(UserQueryImpl query) {
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		if (query.getId() != null) {
			queryWrapper.eq("user_id", query.getId());
		}
		if (query.getIds() != null) {
			queryWrapper.in("user_id", query.getIds());
		}
		if (query.getFirstName() != null) {
			queryWrapper.eq("user_name", query.getFirstName());
		}
		if (query.getFirstNameLike() != null) {
			queryWrapper.like("user_name", query.getFirstName());
		}
		return queryWrapper;
	}
}

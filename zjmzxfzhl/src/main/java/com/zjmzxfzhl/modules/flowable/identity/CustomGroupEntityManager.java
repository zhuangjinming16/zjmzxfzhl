package com.zjmzxfzhl.modules.flowable.identity;

import java.util.ArrayList;
import java.util.List;

import org.flowable.idm.api.Group;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.GroupQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntity;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityManagerImpl;
import org.flowable.idm.engine.impl.persistence.entity.data.GroupDataManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.service.SysRoleService;

public class CustomGroupEntityManager extends GroupEntityManagerImpl {
	@Autowired
	private SysRoleService sysRoleService;

	public CustomGroupEntityManager(IdmEngineConfiguration idmEngineConfiguration, GroupDataManager groupDataManager) {
		super(idmEngineConfiguration, groupDataManager);
	}

	@Override
	public GroupEntity findById(String entityId) {
		SysRole sysGroup = sysRoleService.getById(entityId);
		if (sysGroup == null) {
			return null;
		}
		GroupEntity groupEntity = new GroupEntityImpl();
		groupEntity.setId(sysGroup.getRoleId());
		groupEntity.setName(sysGroup.getRoleName());
		return groupEntity;
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query) {
		List<SysRole> sysRoles = sysRoleService.getBaseMapper().getRolesByFlowableGroupQueryImpl(query);
		if (sysRoles == null || sysRoles.size() == 0) {
			return new ArrayList<>();
		}
		List<Group> groups = new ArrayList<>();
		for (SysRole sysRole : sysRoles) {
			Group group = new GroupEntityImpl();
			group.setId(sysRole.getRoleId());
			group.setName(sysRole.getRoleName());
			groups.add(group);
		}
		return groups;
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		return sysRoleService.getBaseMapper().getRolesByFlowableGroupQueryImpl(query).size();
	}
}

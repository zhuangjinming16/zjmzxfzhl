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

import com.zjmzxfzhl.modules.sys.entity.SysPost;
import com.zjmzxfzhl.modules.sys.service.SysPostService;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class CustomGroupEntityManager extends GroupEntityManagerImpl {
	@Autowired
	private SysPostService sysPostService;

	public CustomGroupEntityManager(IdmEngineConfiguration idmEngineConfiguration, GroupDataManager groupDataManager) {
		super(idmEngineConfiguration, groupDataManager);
	}

	@Override
	public GroupEntity findById(String entityId) {
		SysPost sysPost = sysPostService.getById(entityId);
		if (sysPost == null) {
			return null;
		}
		GroupEntity groupEntity = new GroupEntityImpl();
		groupEntity.setId(sysPost.getPostId());
		groupEntity.setName(sysPost.getPostName());
		return groupEntity;
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query) {
		List<SysPost> sysPosts = sysPostService.getBaseMapper().getPostsByFlowableGroupQueryImpl(query);
		if (sysPosts == null || sysPosts.size() == 0) {
			return new ArrayList<>();
		}
		List<Group> groups = new ArrayList<>();
		for (SysPost sysPost : sysPosts) {
			Group group = new GroupEntityImpl();
			group.setId(sysPost.getPostId());
			group.setName(sysPost.getPostName());
			groups.add(group);
		}
		return groups;
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		return sysPostService.getBaseMapper().getPostsByFlowableGroupQueryImpl(query).size();
	}
}

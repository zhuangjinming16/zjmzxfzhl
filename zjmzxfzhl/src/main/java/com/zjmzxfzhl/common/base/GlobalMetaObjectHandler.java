package com.zjmzxfzhl.common.base;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zjmzxfzhl.common.util.DateUtil;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.app.common.AppBaseEntity;
import com.zjmzxfzhl.modules.app.common.AppSessionObject;
import com.zjmzxfzhl.modules.app.interceptor.AppLoginInterceptor;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Component
public class GlobalMetaObjectHandler implements MetaObjectHandler {
	private static final String CREATE_BY = "createBy";
	private static final String CREATE_DATE = "createDate";
	private static final String CREATE_TIME = "createTime";
	private static final String UPDATE_BY = "updateBy";
	private static final String UPDATE_DATE = "updateDate";
	private static final String UPDATE_TIME = "updateTime";

	@Override
	public void insertFill(MetaObject metaObject) {
		if (isBaseEntity(metaObject)) {
			setBaseEntityInsertFill(metaObject);
		} else if (isAppBaseEntity(metaObject)) {
			setAppBaseEntityInsertFill(metaObject);
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if (isBaseEntity(metaObject)) {
			setBaseEntityUpdateFill(metaObject);
		} else if (isAppBaseEntity(metaObject)) {
			setAppBaseEntityUpdateFill(metaObject);
		}
	}

	private boolean isBaseEntity(MetaObject metaObject) {
		return metaObject.getOriginalObject() instanceof BaseEntity || (metaObject.hasGetter(Constants.ENTITY)
				&& metaObject.getValue(Constants.ENTITY) != null && metaObject.getValue(Constants.ENTITY) instanceof BaseEntity);
	}

	private boolean isAppBaseEntity(MetaObject metaObject) {
		return metaObject.getOriginalObject() instanceof AppBaseEntity || (metaObject.hasGetter(Constants.ENTITY)
				&& metaObject.getValue(Constants.ENTITY) != null && metaObject.getValue(Constants.ENTITY) instanceof AppBaseEntity);
	}

	private void setAppBaseEntityInsertFill(MetaObject metaObject) {
		Object createBy = getFieldValByName(CREATE_BY, metaObject);
		if (createBy == null) {
			AppSessionObject appSessionObject = (AppSessionObject) RequestContextHolder.getRequestAttributes()
					.getAttribute(AppLoginInterceptor.APP_SESSION_OBJECT, RequestAttributes.SCOPE_REQUEST);
			if (appSessionObject != null) {
				String currUser = appSessionObject.getUserId();
				setFieldValByName(CREATE_BY, currUser, metaObject);
			}
		}
		Date now = DateUtil.getNow();
		Object createDate = getFieldValByName(CREATE_DATE, metaObject);
		if (createDate == null) {
			setFieldValByName(CREATE_DATE, now, metaObject);
		}
		Object createTime = getFieldValByName(CREATE_TIME, metaObject);
		if (createTime == null) {
			setFieldValByName(CREATE_TIME, now, metaObject);
		}
	}

	private void setBaseEntityInsertFill(MetaObject metaObject) {
		Object createBy = getFieldValByName(CREATE_BY, metaObject);
		if (createBy == null) {
			String currUser = ShiroUtils.getUserId();
			setFieldValByName(CREATE_BY, currUser, metaObject);
		}
		Date now = DateUtil.getNow();
		Object createDate = getFieldValByName(CREATE_DATE, metaObject);
		if (createDate == null) {
			setFieldValByName(CREATE_DATE, now, metaObject);
		}
		Object createTime = getFieldValByName(CREATE_TIME, metaObject);
		if (createTime == null) {
			setFieldValByName(CREATE_TIME, now, metaObject);
		}
	}

	private void setAppBaseEntityUpdateFill(MetaObject metaObject) {
		Object updateBy = getFieldValByName(UPDATE_BY, metaObject);
		if (updateBy == null) {
			AppSessionObject appSessionObject = (AppSessionObject) RequestContextHolder.getRequestAttributes()
					.getAttribute(AppLoginInterceptor.APP_SESSION_OBJECT, RequestAttributes.SCOPE_REQUEST);
			if (appSessionObject != null) {
				String currUser = appSessionObject.getUserId();
				setFieldValByName(UPDATE_BY, currUser, metaObject);
			}
		}
		Date now = DateUtil.getNow();
		Object updateDate = getFieldValByName(UPDATE_DATE, metaObject);
		if (updateDate == null) {
			setFieldValByName(UPDATE_DATE, now, metaObject);
		}
		Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
		if (updateTime == null) {
			setFieldValByName(UPDATE_TIME, now, metaObject);
		}
	}

	private void setBaseEntityUpdateFill(MetaObject metaObject) {
		Object updateBy = getFieldValByName(UPDATE_BY, metaObject);
		if (updateBy == null) {
			String currUser = ShiroUtils.getUserId();
			setFieldValByName(UPDATE_BY, currUser, metaObject);
		}
		Date now = DateUtil.getNow();
		Object updateDate = getFieldValByName(UPDATE_DATE, metaObject);
		if (updateDate == null) {
			setFieldValByName(UPDATE_DATE, now, metaObject);
		}
		Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
		if (updateTime == null) {
			setFieldValByName(UPDATE_TIME, now, metaObject);
		}
	}
}

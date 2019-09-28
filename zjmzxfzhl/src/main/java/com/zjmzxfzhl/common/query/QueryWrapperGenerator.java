package com.zjmzxfzhl.common.query;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjmzxfzhl.common.permission.FilterOperate;
import com.zjmzxfzhl.common.util.ColumnUtils;
import com.zjmzxfzhl.common.xss.SQLFilter;

/**
 * MyBatis Plus 查询条件生成器
 * 
 * @author 庄金明
 *
 */
public class QueryWrapperGenerator {
	private static final String ORDER_TYPE_ASC = "ASC";
	private static final String ORDER_TYPE_DESC = "DESC";

	/**
	 * 封装查询条件，排序使用默认主键升序排序
	 * 
	 * @param searchObj
	 * @return
	 */
	public static <T> QueryWrapper<T> initQueryWrapperSimple(T searchObj) {
		return initQueryWrapperSimple(searchObj, null, null);
	}

	/**
	 * 封装查询条件，排序使用默认主键升序排序
	 * 
	 * @param searchObj
	 * @param searchObjRule
	 * @return
	 */
	public static <T> QueryWrapper<T> initQueryWrapperSimple(T searchObj, Map<String, FilterOperate> searchObjRule) {
		return initQueryWrapperSimple(searchObj, searchObjRule, null);
	}

	/**
	 * 封装查询条件及排序
	 * 
	 * @param searchObj
	 * @param searchObjRule
	 * @param orderRule
	 * @return
	 */
	public static <T> QueryWrapper<T> initQueryWrapperSimple(T searchObj, Map<String, FilterOperate> searchObjRule, String orderRule) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
		installMplusSimple(queryWrapper, searchObj, searchObjRule, orderRule);
		return queryWrapper;
	}

	/**
	 * 简单组装Mybatis Plus 查询条件
	 * <p>
	 * 使用此方法 需要有如下几点注意: <br>
	 * 1.使用QueryWrapper 而非LambdaQueryWrapper; <br>
	 * 2.实例化QueryWrapper时不可将实体传入参数 <br>
	 * 错误示例:如QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<SysConfig>(sysConfig); <br>
	 * 正确示例:QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<SysConfig>(); <br>
	 * 3.也可以通过该类中的 initQueryWrapperSimple 方法调用到该方法
	 */
	public static void installMplusSimple(QueryWrapper<?> queryWrapper, Object searchObj, Map<String, FilterOperate> searchObjRule, String orderRule) {
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(searchObj);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			try {
				if (judgedIsUselessField(name) || !PropertyUtils.isReadable(searchObj, name)) {
					continue;
				}
				Object value = PropertyUtils.getSimpleProperty(searchObj, name);
				if (searchObjRule != null && searchObjRule.containsKey(name)) {
					FilterOperate rule = searchObjRule.get(name);
					addEasyQuery(queryWrapper, name, rule, value);
				} else {
					addEasyQuery(queryWrapper, name, FilterOperate.EQ, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 处理排序
		if (orderRule != null && orderRule.length() > 0) {
			String[] orderColumnRules = orderRule.split(",");
			for (String orderColumnRule : orderColumnRules) {
				if (orderColumnRule.length() == 0) {
					continue;
				}
				String[] rule = orderColumnRule.split("\\|");
				String orderColumn = ColumnUtils.camelToUnderline(rule[0]);
				orderColumn = SQLFilter.sqlInject(orderColumn);
				if (rule.length == 1) {
					queryWrapper.orderByAsc(orderColumn);
				} else if (rule.length == 2) {
					if (ORDER_TYPE_ASC.equals(rule[1].toUpperCase())) {
						queryWrapper.orderByAsc(orderColumn);
					} else if (ORDER_TYPE_DESC.equals(rule[1].toUpperCase())) {
						queryWrapper.orderByDesc(orderColumn);
					}
				}
			}
		}
		// 默认按照主键升序排序
		else {
			List<Field> fields = getFieldList(searchObj.getClass());
			for (Field field : fields) {
				if (field.isAnnotationPresent(TableId.class)) {
					queryWrapper.orderByAsc(ColumnUtils.camelToUnderline(field.getName()));
				}
			}
		}
	}

	/**
	 * 获取类的所有属性包含父类
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getFieldList(Class<?> clazz) {
		if (null == clazz) {
			return null;
		}
		List<Field> fieldList = new LinkedList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			/** 过滤静态属性 **/
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			/** 过滤transient 关键字修饰的属性 **/
			if (Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			fieldList.add(field);
		}
		/** 处理父类字段 **/
		Class<?> superClass = clazz.getSuperclass();
		if (superClass.equals(Object.class)) {
			return fieldList;
		}
		fieldList.addAll(getFieldList(superClass));
		return fieldList;
	}

	/**
	 * 根据规则走不同的查询
	 * 
	 * @param queryWrapper
	 *            QueryWrapper
	 * @param name
	 *            字段名字
	 * @param rule
	 *            查询规则
	 * @param value
	 *            查询条件值
	 */
	public static void addEasyQuery(QueryWrapper<?> queryWrapper, String name, FilterOperate rule, Object value) {
		if (value == null || "".equals(value) || rule == null) {
			return;
		}
		name = ColumnUtils.camelToUnderline(name);
		// log.info("--查询规则-->" + name + " " + rule.getValue() + " " + value);
		switch (rule) {
		case GT:
			queryWrapper.gt(name, value);
			break;
		case GE:
			queryWrapper.ge(name, value);
			break;
		case LT:
			queryWrapper.lt(name, value);
			break;
		case LE:
			queryWrapper.le(name, value);
			break;
		case EQ:
			queryWrapper.eq(name, value);
			break;
		case NE:
			queryWrapper.ne(name, value);
			break;
		case IN:
			if (value instanceof String) {
				queryWrapper.in(name, (Object[]) value.toString().split(","));
			} else if (value instanceof String[]) {
				queryWrapper.in(name, (Object[]) value);
			} else {
				queryWrapper.in(name, value);
			}
			break;
		case LIKE:
			queryWrapper.like(name, value);
			break;
		case LIKE_LEFT:
			queryWrapper.likeLeft(name, value);
			break;
		case LIKE_RIGHT:
			queryWrapper.likeRight(name, value);
			break;
		default:
			// log.info("--查询规则未匹配到---");
			break;
		}
	}

	/**
	 * 是否无用属性
	 * 
	 * @param name
	 * @return
	 */
	public static boolean judgedIsUselessField(String name) {
		return "class".equals(name) || "ids".equals(name) || "page".equals(name) || "rows".equals(name) || "sort".equals(name) || "order".equals(name);
	}

}

package com.zjmzxfzhl.common.core.config.mybatis.permission;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zjmzxfzhl.common.core.aspect.annotation.DataPermission;
import com.zjmzxfzhl.common.core.aspect.annotation.DataPermission.DataPermissions;
import com.zjmzxfzhl.common.core.config.mybatis.permission.mapper.CommonDataPermissionMapper;
import com.zjmzxfzhl.common.core.constant.Constants;
import com.zjmzxfzhl.common.core.exception.SysException;
import com.zjmzxfzhl.common.core.permission.SourceStrategy;
import com.zjmzxfzhl.common.core.permission.provider.AbstractDataPermissionProvider;
import com.zjmzxfzhl.common.core.permission.wrapper.PermissionWrapper;
import com.zjmzxfzhl.common.core.security.SecurityUser;
import com.zjmzxfzhl.common.core.util.*;
import com.zjmzxfzhl.common.core.xss.SqlFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

/**
 * @author 庄金明
 * @date 2020年5月16日
 */
public class PermissionParser implements ISqlParser, InitializingBean {
    private final static String PERMISSION_WRAPPER = "permissionWrapper";
    private final static String DELEGATE_BOUNDSQL = "delegate.boundSql";
    private final static String DELEGATE_BOUNDSQL_SQL = "delegate.boundSql.sql";
    private final static String DELEGATE_BOUNDSQL_ADDITIONALPARAMETERS = "delegate.boundSql.additionalParameters";
    private final static String DELEGATE_BOUNDSQL_PARAMETERMAPPINGS = "delegate.boundSql.parameterMappings";
    private final static Integer BETWEEN_LENGTH = 2;
    private final static String OPEN_TOKEN = "{{";
    private final static String CLOSE_TOKEN = "}}";
    private final static GenericTokenParser PARSER_EMPTY = new GenericTokenParser(OPEN_TOKEN, CLOSE_TOKEN,
            new TokenHandler() {
        @Override
        public String handleToken(String content) {
            return "";
        }
    });
    private final static Map<String, SqlKeyword> SQLKEYWORD_OPERATORS = new HashMap<>(16);

    static {
        SQLKEYWORD_OPERATORS.put(SqlKeyword.IN.getSqlSegment(), SqlKeyword.IN);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.LIKE.getSqlSegment(), SqlKeyword.LIKE);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.EQ.getSqlSegment(), SqlKeyword.EQ);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.NE.getSqlSegment(), SqlKeyword.NE);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.GT.getSqlSegment(), SqlKeyword.GT);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.GE.getSqlSegment(), SqlKeyword.GE);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.LT.getSqlSegment(), SqlKeyword.LT);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.LE.getSqlSegment(), SqlKeyword.LE);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.IS_NULL.getSqlSegment(), SqlKeyword.IS_NULL);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.IS_NOT_NULL.getSqlSegment(), SqlKeyword.IS_NOT_NULL);
        SQLKEYWORD_OPERATORS.put(SqlKeyword.BETWEEN.getSqlSegment(), SqlKeyword.BETWEEN);
    }

    @Value("${zjmzxfzhl.data-permission.table-name:T_SYS_DATA_PERMISSION}")
    private String dataPermissionTableName;

    @SuppressWarnings("unchecked")
    @Override
    public SqlInfo parser(MetaObject metaObject, String sql) {
        MappedStatement mappedStatement = SqlParserHelper.getMappedStatement(metaObject);
        Method targetMethod = findTargetMethod(mappedStatement);
        if (targetMethod == null) {
            return null;
        }
        String methodId = mappedStatement.getId();
        DataPermission[] dataPermissions = targetMethod.getAnnotationsByType(DataPermission.class);
        SecurityUser securityUser = (SecurityUser) SecurityUtils.getUserDetails();
        if (securityUser == null) {
            throw new SysException("登录超时，请重新登录");
        }
        String userId = securityUser.getUsername();
        String roleId = securityUser.getRoleId();

        // admin用户拥有所有数据权限
        if (Constants.ADMIN.equals(userId) || dataPermissions == null || dataPermissions.length == 0) {
            return SqlInfo.newInstance().setSql(PARSER_EMPTY.parse(sql));
        }
        Configuration configuration = mappedStatement.getConfiguration();
        BoundSql boundSql = (BoundSql) metaObject.getValue(DELEGATE_BOUNDSQL);
        List<ParameterMapping> parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        Map<String, Object> additionalParameters =
                (Map<String, Object>) metaObject.getValue(DELEGATE_BOUNDSQL_ADDITIONALPARAMETERS);
        String retSql = sql;
        for (int i = 0; i < dataPermissions.length; i++) {
            DataPermission dataPermission = dataPermissions[i];
            String[] tableNames = dataPermission.tableNames();
            String[] aliasNames = dataPermission.aliasNames();
            if (tableNames.length != aliasNames.length) {
                throw new SysException("数据权限tableNames和aliasNames配置错误");
            }
            Class<AbstractDataPermissionProvider>[] providers =
                    (Class<AbstractDataPermissionProvider>[]) dataPermission.providers();
            String[] providerParams = dataPermission.providerParams();
            if (providers.length > 1 && providers.length != providerParams.length) {
                throw new SysException("数据权限providers和providerParams配置错误");
            }
            String fieldName = dataPermission.fieldName();
            String additionalParameterName = PERMISSION_WRAPPER + i;
            PermissionWrapper permissionWrapper = new PermissionWrapper(configuration, additionalParameterName);
            wrapByProviders(permissionWrapper, methodId, providers, providerParams, userId, roleId);
            wrapByTableNames(permissionWrapper, methodId, tableNames, aliasNames, userId, roleId);
            String replaceStr = OPEN_TOKEN + fieldName + CLOSE_TOKEN;
            String permissionWrapperSql = permissionWrapper.getSqlSegment();
            if (CommonUtil.isEmptyAfterTrim(permissionWrapperSql)) {
                retSql = StringUtils.replace(retSql, replaceStr, "");
                continue;
            }
            String authSql = " AND " + permissionWrapperSql;
            int index = retSql.indexOf(replaceStr);
            while (index != -1) {
                if (permissionWrapper.getParameterMappings() != null && permissionWrapper.getParameterMappings().size() > 0) {
                    String tmpSql = retSql.substring(0, index);
                    int count = tmpSql.length() - tmpSql.replaceAll("\\?", "").length();
                    if (parameterMappings.size() < count) {
                        throw new SysException("数据权限封装参数个数错误");
                    }
                    parameterMappings.addAll(count, permissionWrapper.getParameterMappings());
                }
                retSql = StringUtils.replaceOnce(retSql, replaceStr, authSql);
                index = retSql.indexOf(replaceStr);
            }
            additionalParameters.put(additionalParameterName, permissionWrapper);
        }
        metaObject.setValue(DELEGATE_BOUNDSQL_SQL, retSql);
        metaObject.setValue(DELEGATE_BOUNDSQL_PARAMETERMAPPINGS, parameterMappings);
        return SqlInfo.newInstance().setSql(retSql);
    }

    private Method findTargetMethod(MappedStatement mappedStatement) {
        Method targetMethod = null;
        String id = mappedStatement.getId();
        int lastIndex = id.lastIndexOf(StringPool.DOT);
        String interfaceName = id.substring(0, lastIndex);
        String methodName = id.substring(lastIndex + 1);
        Class<?> cls;
        try {
            cls = Class.forName(interfaceName);
        } catch (ClassNotFoundException e) {
            throw new SysException("【" + interfaceName + "】不存在，请联系管理员");
        }
        final Method[] methods = cls.getMethods();

        for (Method method : methods) {
            boolean isFind =
                    method.getName().equals(methodName) && (method.isAnnotationPresent(DataPermission.class) || method.isAnnotationPresent(DataPermissions.class));
            if (isFind) {
                targetMethod = method;
                break;
            }
        }
        return targetMethod;
    }

    private void wrapByTableNames(PermissionWrapper permissionWrapper, String methodId, String[] tableNames,
                                  String[] aliasNames, String userId, String roleId) {
        if (tableNames.length == 0) {
            return;
        }
        Map<String, String> aliasNamesMap = new HashMap<>(16);
        for (int i = 0; i < tableNames.length; i++) {
            // 防止别名注入
            SqlFilter.sqlInject(aliasNames[i]);
            aliasNamesMap.put(tableNames[i], aliasNames[i]);
        }

        CommonDataPermissionVO commonDataPermissionVO = new CommonDataPermissionVO();
        commonDataPermissionVO.setTableName(dataPermissionTableName);
        commonDataPermissionVO.setMethodId(methodId);
        commonDataPermissionVO.setTableNames(tableNames);
        commonDataPermissionVO.setSourceStrategy(SourceStrategy.TEXT.getKey());
        commonDataPermissionVO.setRoleId(roleId);
        commonDataPermissionVO.setUserId(userId);

        List<CommonDataPermissionVO> sysDataPermissionsByTableName =
                SpringContextUtils.getBean(CommonDataPermissionMapper.class).selectDataPermissions(commonDataPermissionVO);

        Map<String, List<CommonDataPermissionVO>> tableNameColumnNameMap = new LinkedHashMap<>();
        for (CommonDataPermissionVO sdp : sysDataPermissionsByTableName) {
            if (sdp.getTableName() == null || sdp.getTableName().length() == 0) {
                continue;
            }
            // 防止数据库字段名注入
            SqlFilter.sqlInject(sdp.getColumnName());
            String key = sdp.getTableName().toUpperCase() + "-" + sdp.getColumnName().toUpperCase();
            List<CommonDataPermissionVO> tableNameColumnNameList = tableNameColumnNameMap.computeIfAbsent(key,
                    k -> new ArrayList<>());
            tableNameColumnNameList.add(sdp);
        }

        for (Entry<String, List<CommonDataPermissionVO>> entry : tableNameColumnNameMap.entrySet()) {
            List<CommonDataPermissionVO> tableNameColumnList = entry.getValue();
            if (tableNameColumnList == null || tableNameColumnList.size() == 0) {
                continue;
            }
            CommonDataPermissionVO firstSdp = tableNameColumnList.iterator().next();
            String alias = aliasNamesMap.get(firstSdp.getTableName());
            String type = getColumnTypeByClassName(firstSdp.getClassName(), firstSdp.getColumnName());
            wrapSingleTableNameColumn(permissionWrapper, alias, type, tableNameColumnList);
        }
    }

    private void wrapSingleTableNameColumn(PermissionWrapper permissionWrapper, String alias, String type,
                                           List<CommonDataPermissionVO> tableNameColumnList) {
        if (tableNameColumnList != null && tableNameColumnList.size() == 1) {
            CommonDataPermissionVO commonDataPermissionVO = tableNameColumnList.get(0);
            String value = CommonUtil.isEmptyDefault(commonDataPermissionVO.getValue(), "");
            String operate = CommonUtil.isEmptyDefault(commonDataPermissionVO.getOperate(),
                    SqlKeyword.EQ.getSqlSegment());
            SqlKeyword keyword = SQLKEYWORD_OPERATORS.getOrDefault(operate, SqlKeyword.EQ);
            if (keyword.equals(SqlKeyword.IS_NULL)) {
                permissionWrapper.isNull(alias, commonDataPermissionVO.getColumnName());
            } else if (keyword.equals(SqlKeyword.IS_NOT_NULL)) {
                permissionWrapper.isNotNull(alias, commonDataPermissionVO.getColumnName());
            } else if (keyword.equals(SqlKeyword.IN)) {
                Object[] realValue = (Object[]) resolveRealValue(commonDataPermissionVO, value, type, keyword);
                permissionWrapper.in(alias, commonDataPermissionVO.getColumnName(), realValue);
            } else if (keyword.equals(SqlKeyword.BETWEEN)) {
                Object[] realValue = (Object[]) resolveRealValue(commonDataPermissionVO, value, type, keyword);
                permissionWrapper.between(alias, commonDataPermissionVO.getColumnName(), realValue[0], realValue[1]);
            } else {
                Object realValue = resolveRealValue(commonDataPermissionVO, value, type, keyword);
                permissionWrapper.addCondition(alias, commonDataPermissionVO.getColumnName(), keyword, realValue);
            }
        } else if (tableNameColumnList != null && tableNameColumnList.size() > 1) {
            BiConsumer<PermissionWrapper, Object> consumer = (pw, obj) -> {
                CommonDataPermissionVO commonDataPermissionVO = (CommonDataPermissionVO) obj;
                String value = CommonUtil.isEmptyDefault(commonDataPermissionVO.getValue(), "");
                String operate = CommonUtil.isEmptyDefault(commonDataPermissionVO.getOperate(),
                        SqlKeyword.EQ.getSqlSegment());
                SqlKeyword keyword = SQLKEYWORD_OPERATORS.getOrDefault(operate, SqlKeyword.EQ);
                if (keyword.equals(SqlKeyword.IS_NULL)) {
                    pw.or().isNull(alias, commonDataPermissionVO.getColumnName());
                } else if (keyword.equals(SqlKeyword.IS_NOT_NULL)) {
                    pw.or().isNotNull(alias, commonDataPermissionVO.getColumnName());
                } else if (keyword.equals(SqlKeyword.IN)) {
                    Object[] realValue = (Object[]) resolveRealValue(commonDataPermissionVO, value, type, keyword);
                    pw.or().in(alias, commonDataPermissionVO.getColumnName(), realValue);
                } else if (keyword.equals(SqlKeyword.BETWEEN)) {
                    Object[] realValue = (Object[]) resolveRealValue(commonDataPermissionVO, value, type, keyword);
                    pw.or().between(alias, commonDataPermissionVO.getColumnName(), realValue[0], realValue[1]);
                } else {
                    Object realValue = resolveRealValue(commonDataPermissionVO, value, type, keyword);
                    pw.or().addCondition(alias, commonDataPermissionVO.getColumnName(), keyword, realValue);
                }
            };
            permissionWrapper.and(consumer, tableNameColumnList);
        }
    }

    private Object resolveRealValue(CommonDataPermissionVO commonDataPermissionVO, String value, String type,
                                    SqlKeyword keyword) {
        Object realValue = null;
        try {
            switch (type) {
                case "java.lang.String":
                    realValue = resolveRealValueString(value, keyword);
                    break;
                case "java.lang.Integer":
                    realValue = resolveRealValueInteger(value, keyword);
                    break;
                case "java.math.BigDecimal":
                    realValue = resolveRealValueBigDecimal(value, keyword);
                    break;
                case "java.lang.Short":
                    realValue = resolveRealValueShort(value, keyword);
                    break;
                case "java.lang.Long":
                    realValue = resolveRealValueLong(value, keyword);
                    break;
                case "java.lang.Float":
                    realValue = resolveRealValueFloat(value, keyword);
                    break;
                case "java.lang.Double":
                    realValue = resolveRealValueDouble(value, keyword);
                    break;
                case "java.util.Date":
                    realValue = resolveRealValueDate(value, keyword);
                    break;
                default:
                    throw new SysException("数据权限【" + commonDataPermissionVO.getDataPermissionId() + "】配置错误，不支持配置【" + type + "】类型的字段，请联系管理员");
            }
        } catch (Exception e) {
            throw new SysException("数据权限【" + commonDataPermissionVO.getDataPermissionId() + "】配置错误，请联系管理员");
        }
        if (keyword.equals(SqlKeyword.IN)) {
            Object[] temp = (Object[]) realValue;
            if (temp == null || temp.length == 0) {
                throw new SysException("数据权限【" + commonDataPermissionVO.getDataPermissionId() + "】配置错误，请联系管理员");
            }
        } else if (keyword.equals(SqlKeyword.BETWEEN)) {
            Object[] temp = (Object[]) realValue;
            if (temp == null || temp.length != BETWEEN_LENGTH) {
                throw new SysException("数据权限【" + commonDataPermissionVO.getDataPermissionId() + "】配置错误，请联系管理员");
            }
        }
        return realValue;
    }

    private Object resolveRealValueDate(String value, SqlKeyword keyword) throws ParseException {
        Object realValue;
        if (keyword.equals(SqlKeyword.IN) || keyword.equals(SqlKeyword.BETWEEN)) {
            String[] values = value.split(StringPool.COMMA);
            Date[] realValues = new Date[values.length];
            for (int i = 0; i < values.length; i++) {
                realValues[i] = DateUtil.getSimpleDateFormat(DateUtil.DATE_FORMAT_DEFAULT).parse(values[i]);
            }
            realValue = realValues;
        } else {
            realValue = DateUtil.getSimpleDateFormat(DateUtil.DATE_FORMAT_DEFAULT).parse(value);
        }
        return realValue;
    }

    private Object resolveRealValueDouble(String value, SqlKeyword keyword) {
        Object realValue;
        if (keyword.equals(SqlKeyword.IN) || keyword.equals(SqlKeyword.BETWEEN)) {
            String[] values = value.split(StringPool.COMMA);
            Double[] realValues = new Double[values.length];
            for (int i = 0; i < values.length; i++) {
                realValues[i] = Double.parseDouble(values[i]);
            }
            realValue = realValues;
        } else {
            realValue = Double.parseDouble(value);
        }
        return realValue;
    }

    private Object resolveRealValueFloat(String value, SqlKeyword keyword) {
        Object realValue;
        if (keyword.equals(SqlKeyword.IN) || keyword.equals(SqlKeyword.BETWEEN)) {
            String[] values = value.split(StringPool.COMMA);
            Float[] realValues = new Float[values.length];
            for (int i = 0; i < values.length; i++) {
                realValues[i] = Float.parseFloat(values[i]);
            }
            realValue = realValues;
        } else {
            realValue = Float.parseFloat(value);
        }
        return realValue;
    }

    private Object resolveRealValueLong(String value, SqlKeyword keyword) {
        Object realValue;
        if (keyword.equals(SqlKeyword.IN) || keyword.equals(SqlKeyword.BETWEEN)) {
            String[] values = value.split(StringPool.COMMA);
            Long[] realValues = new Long[values.length];
            for (int i = 0; i < values.length; i++) {
                realValues[i] = Long.parseLong(values[i]);
            }
            realValue = realValues;
        } else {
            realValue = Long.parseLong(value);
        }
        return realValue;
    }

    private Object resolveRealValueShort(String value, SqlKeyword keyword) {
        Object realValue;
        if (keyword.equals(SqlKeyword.IN) || keyword.equals(SqlKeyword.BETWEEN)) {
            String[] values = value.split(StringPool.COMMA);
            Short[] realValues = new Short[values.length];
            for (int i = 0; i < values.length; i++) {
                realValues[i] = Short.parseShort(values[i]);
            }
            realValue = realValues;
        } else {
            realValue = Short.parseShort(value);
        }
        return realValue;
    }

    private Object resolveRealValueBigDecimal(String value, SqlKeyword keyword) {
        Object realValue;
        if (keyword.equals(SqlKeyword.IN) || keyword.equals(SqlKeyword.BETWEEN)) {
            String[] values = value.split(StringPool.COMMA);
            BigDecimal[] realValues = new BigDecimal[values.length];
            for (int i = 0; i < values.length; i++) {
                realValues[i] = new BigDecimal(values[i]);
            }
            realValue = realValues;
        } else {
            realValue = new BigDecimal(value);
        }
        return realValue;
    }

    private Object resolveRealValueInteger(String value, SqlKeyword keyword) {
        Object realValue;
        if (keyword.equals(SqlKeyword.IN) || keyword.equals(SqlKeyword.BETWEEN)) {
            String[] values = value.split(StringPool.COMMA);
            Integer[] realValues = new Integer[values.length];
            for (int i = 0; i < values.length; i++) {
                realValues[i] = Integer.parseInt(values[i]);
            }
            realValue = realValues;
        } else {
            realValue = Integer.parseInt(value);
        }
        return realValue;
    }

    private Object resolveRealValueString(String value, SqlKeyword keyword) {
        Object realValue;
        if (keyword.equals(SqlKeyword.IN) || keyword.equals(SqlKeyword.BETWEEN)) {
            realValue = value.split(StringPool.COMMA);
        } else {
            realValue = value;
        }
        return realValue;
    }

    private String getColumnTypeByClassName(String className, String columnName) {
        String fieldName = ColumnUtils.camelName(columnName);
        Class<?> clazz = null;
        Field field = null;
        try {
            clazz = Class.forName(className);
            for (; field == null && clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                }
            }
        } catch (Exception e1) {
            throw new SysException("获取[" + className + "]的属性[" + columnName + "]类型失败");
        }
        if (field == null) {
            throw new SysException("获取[" + className + "]的属性[" + columnName + "]类型失败");
        }
        return field.getType().getName();
    }

    @SuppressWarnings("unchecked")
    private void wrapByProviders(PermissionWrapper permissionWrapper, String methodId,
                                 Class<AbstractDataPermissionProvider>[] providers, String[] providerParams,
                                 String userId, String roleId) {
        // 【1】处理注解配置
        Map<Class<AbstractDataPermissionProvider>, String> providerMap = new HashMap<>(16);
        for (int i = 0; i < providers.length; i++) {
            Class<AbstractDataPermissionProvider> providerClass = providers[i];
            // 若未配置providerParams则直接设置null
            if (i > (providerParams.length - 1)) {
                providerMap.put(providerClass, null);
            } else {
                providerMap.put(providerClass, providerParams[i]);
            }
        }

        // 【2】处理methodId个性配置
        CommonDataPermissionVO commonDataPermissionVO = new CommonDataPermissionVO();
        commonDataPermissionVO.setTableName(dataPermissionTableName);
        commonDataPermissionVO.setMethodId(methodId);
        commonDataPermissionVO.setSourceStrategy(SourceStrategy.SYSTEM.getKey());
        commonDataPermissionVO.setRoleId(roleId);
        commonDataPermissionVO.setUserId(userId);
        List<CommonDataPermissionVO> sysDataPermissionsByProviderAndMethodIdIsNotNull =
                SpringContextUtils.getBean(CommonDataPermissionMapper.class).selectDataPermissions(commonDataPermissionVO);

        for (CommonDataPermissionVO sysDataPermission : sysDataPermissionsByProviderAndMethodIdIsNotNull) {
            Class<AbstractDataPermissionProvider> providerClass = null;
            try {
                providerClass =
                        (Class<AbstractDataPermissionProvider>) Class.forName(sysDataPermission.getSourceProvider());
            } catch (ClassNotFoundException e) {
                throw new SysException("【" + sysDataPermission.getSourceProvider() + "】不存在，请联系管理员");
            }
            // 直接注入数据库设置的参数值，以methodId配置的数据库参数为准
            providerMap.put(providerClass, sysDataPermission.getSourceProviderParams());
        }

        for (Entry<Class<AbstractDataPermissionProvider>, String> entry : providerMap.entrySet()) {
            AbstractDataPermissionProvider provider = null;
            try {
                provider = entry.getKey().newInstance();
            } catch (Exception e) {
                throw new SysException("无法创建数据权限提供器:" + entry.getKey().getName());
            }
            // 注入参数值，若有特殊参数需要特殊处理，可以在实现类中重写该方法
            provider.setProviderParams(entry.getValue());
            provider.wrap(permissionWrapper);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 防止配置中sql注入
        SqlFilter.sqlInject(dataPermissionTableName);
    }
}

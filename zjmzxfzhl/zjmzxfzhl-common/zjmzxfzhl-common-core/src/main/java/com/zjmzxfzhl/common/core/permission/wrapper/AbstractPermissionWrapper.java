package com.zjmzxfzhl.common.core.permission.wrapper;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import static com.baomidou.mybatisplus.core.enums.SqlKeyword.*;
import static com.baomidou.mybatisplus.core.enums.WrapperKeyword.APPLY;
import static com.baomidou.mybatisplus.core.enums.WrapperKeyword.BRACKET;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @param <R>
 * @param <Children>
 * @author 庄金明
 */
@SuppressWarnings("unchecked")
public abstract class AbstractPermissionWrapper<R, Children extends AbstractPermissionWrapper<R, Children>> implements ISqlSegment {

    private static final long serialVersionUID = 1L;

    /**
     * 占位符
     */
    protected final Children typedThis = (Children) this;
    /**
     * 必要度量
     */
    protected AtomicInteger paramNameSeq;
    protected Map<String, Object> paramNameValuePairs;
    protected MergeSegments expression;
    protected Configuration configuration;
    protected List<ParameterMapping> parameterMappings;
    protected String additionalParameterName = "permissionWrapper";

    public <V> Children allEq(String alias, Map<R, V> params, boolean null2IsNull) {
        if (CollectionUtils.isNotEmpty(params)) {
            params.forEach((k, v) -> {
                if (StringUtils.checkValNotNull(v)) {
                    eq(alias, k, v);
                } else {
                    if (null2IsNull) {
                        isNull(alias, k);
                    }
                }
            });
        }
        return typedThis;
    }

    public <V> Children allEq(String alias, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) {
        if (CollectionUtils.isNotEmpty(params)) {
            params.forEach((k, v) -> {
                if (filter.test(k, v)) {
                    if (StringUtils.checkValNotNull(v)) {
                        eq(alias, k, v);
                    } else {
                        if (null2IsNull) {
                            isNull(alias, k);
                        }
                    }
                }
            });
        }
        return typedThis;
    }

    public Children eq(String alias, R column, Object val) {
        return addCondition(alias, column, EQ, val);
    }

    public Children ne(String alias, R column, Object val) {
        return addCondition(alias, column, NE, val);
    }

    public Children gt(String alias, R column, Object val) {
        return addCondition(alias, column, GT, val);
    }

    public Children ge(String alias, R column, Object val) {
        return addCondition(alias, column, GE, val);
    }

    public Children lt(String alias, R column, Object val) {
        return addCondition(alias, column, LT, val);
    }

    public Children le(String alias, R column, Object val) {
        return addCondition(alias, column, LE, val);
    }

    public Children like(String alias, R column, Object val) {
        return likeValue(alias, column, val, SqlLike.DEFAULT);
    }

    public Children notLike(String alias, R column, Object val) {
        return not().like(alias, column, val);
    }

    public Children likeLeft(String alias, R column, Object val) {
        return likeValue(alias, column, val, SqlLike.LEFT);
    }

    public Children likeRight(String alias, R column, Object val) {
        return likeValue(alias, column, val, SqlLike.RIGHT);
    }

    public Children between(String alias, R column, Object val1, Object val2) {
        return doIt(() -> aliasAndColumnToString(alias, column), BETWEEN, () -> formatSql("{0}", val1), AND,
                () -> formatSql("{0}", val2));
    }

    public Children notBetween(String alias, R column, Object val1, Object val2) {
        return not().between(alias, column, val1, val2);
    }

    public Children and(Consumer<Children> consumer) {
        return and().addNestedCondition(consumer);
    }

    public Children and(BiConsumer<Children, Object> consumer, List<?> list) {
        return and().addNestedCondition(consumer, list);
    }

    public Children or(Consumer<Children> consumer) {
        return or().addNestedCondition(consumer);
    }

    public Children or(BiConsumer<Children, Object> consumer, List<?> list) {
        return or().addNestedCondition(consumer, list);
    }

    public Children nested(Consumer<Children> consumer) {
        return addNestedCondition(consumer);
    }

    public Children or() {
        return doIt(OR);
    }

    public Children apply(String applySql, Object... value) {
        return doIt(APPLY, () -> formatSql(applySql, value));
    }

    public Children exists(String existsSql) {
        return doIt(EXISTS, () -> String.format("(%s)", existsSql));
    }

    public Children notExists(String notExistsSql) {
        return not().exists(notExistsSql);
    }

    public Children isNull(String alias, R column) {
        return doIt(() -> aliasAndColumnToString(alias, column), IS_NULL);
    }

    public Children isNotNull(String alias, R column) {
        return doIt(() -> aliasAndColumnToString(alias, column), IS_NOT_NULL);
    }

    public Children in(String alias, R column, Collection<?> coll) {
        return doIt(() -> aliasAndColumnToString(alias, column), IN, inExpression(coll));
    }

    public Children notIn(String alias, R column, Collection<?> coll) {
        return not().in(alias, column, coll);
    }

    public Children in(String alias, R column, Object... values) {
        return in(alias, column,
                Arrays.stream(Optional.ofNullable(values).orElseGet(() -> new Object[]{})).collect(toList()));
    }

    public Children inSql(String alias, R column, String inValue) {
        return doIt(() -> aliasAndColumnToString(alias, column), IN, () -> String.format("(%s)", inValue));
    }

    public Children notInSql(String alias, R column, String inValue) {
        return not().inSql(alias, column, inValue);
    }

    public Children groupBy(String[] aliases, R[] columns) {
        if (ArrayUtils.isEmpty(aliases) || ArrayUtils.isEmpty(columns) || aliases.length != columns.length) {
            return typedThis;
        }
        return doIt(GROUP_BY, () -> aliases.length == 1 ? aliasAndColumnToString(aliases[0], columns[0]) :
                aliasesAndColumnsToString(aliases, columns));
    }

    public Children orderBy(String[] aliases, R[] columns, SqlKeyword[] ascOrDescs) {
        if (ArrayUtils.isEmpty(aliases) || ArrayUtils.isEmpty(columns) || ArrayUtils.isEmpty(ascOrDescs) || aliases.length != columns.length || aliases.length != ascOrDescs.length || columns.length != ascOrDescs.length) {
            return typedThis;
        }
        for (int i = 0; i < aliases.length; i++) {
            String aliasAndColumn = aliasAndColumnToString(aliases[i], columns[i]);
            doIt(ORDER_BY, () -> aliasAndColumn, ascOrDescs[i]);
        }
        return typedThis;
    }

    public Children having(String alias, String sqlHaving, Object... params) {
        return doIt(HAVING, () -> formatSqlIfNeed(alias, sqlHaving, params));
    }

    public Children func(Consumer<Children> consumer) {
        consumer.accept(typedThis);
        return typedThis;
    }

    /**
     * 内部自用
     * <p>
     * NOT 关键词
     * </p>
     */
    protected Children not() {
        return doIt(NOT);
    }

    /**
     * 内部自用
     * <p>
     * 拼接 AND
     * </p>
     */
    protected Children and() {
        return doIt(AND);
    }

    /**
     * 内部自用
     * <p>
     * 拼接 LIKE 以及 值
     * </p>
     */
    protected Children likeValue(String alias, R column, Object val, SqlLike sqlLike) {
        return doIt(() -> aliasAndColumnToString(alias, column), LIKE, () -> formatSql("{0}", SqlUtils.concatLike(val
                , sqlLike)));
    }

    /**
     * 普通查询条件
     *
     * @param condition  是否执行
     * @param column     属性
     * @param sqlKeyword SQL 关键词
     * @param val        条件值
     */
    public Children addCondition(String alias, R column, SqlKeyword sqlKeyword, Object val) {
        return doIt(() -> aliasAndColumnToString(alias, column), sqlKeyword, () -> formatSql("{0}", val));
    }

    protected String aliasString(String alias) {
        if (alias != null && alias.length() > 0) {
            return alias + StringPool.DOT;
        }
        return "";
    }

    protected String aliasAndColumnToString(String alias, R column) {
        return aliasString(alias) + columnToString(column);
    }

    /**
     * 多重嵌套查询条件
     *
     * @param condition 查询条件值
     */
    protected Children addNestedCondition(Consumer<Children> consumer) {
        final Children instance = instance();
        consumer.accept(instance);
        return doIt(BRACKET, instance);
    }

    /**
     * 多重嵌套查询条件
     *
     * @param consumer
     * @param list
     * @return
     */
    protected Children addNestedCondition(BiConsumer<Children, Object> consumer, List<?> list) {
        final Children instance = instance();
        for (Object object : list) {
            consumer.accept(instance, object);
        }
        return doIt(BRACKET, instance);
    }

    /**
     * 子类返回一个自己的新对象
     *
     * @return
     */
    protected abstract Children instance();

    protected String formatSql(String sqlStr, Object... params) {
        return formatSqlIfNeed(sqlStr, params);
    }

    protected String formatSqlIfNeed(String sqlStr, Object... params) {
        if (StringUtils.isBlank(sqlStr)) {
            return null;
        }
        if (ArrayUtils.isNotEmpty(params)) {
            for (int i = 0; i < params.length; ++i) {
                String genParamName = Constants.WRAPPER_PARAM + paramNameSeq.incrementAndGet();
                sqlStr = "?";
                if (configuration != null) {
                    paramNameValuePairs.put(genParamName, params[i]);
                    parameterMappings.add(new ParameterMapping.Builder(configuration, additionalParameterName +
                            ".paramNameValuePairs." + genParamName, params[i].getClass()).build());
                }

            }
        }
        return sqlStr;
    }

    private ISqlSegment inExpression(Collection<?> value) {
        return () -> value.stream().map(i -> formatSql("{0}", i)).collect(joining(StringPool.COMMA,
                StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
    }

    protected void initNeed(Configuration configuration, String additionalParameterName) {
        this.expression = new MergeSegments();
        this.configuration = configuration;
        this.paramNameSeq = new AtomicInteger(0);
        this.paramNameValuePairs = new HashMap<>(16);
        this.parameterMappings = new ArrayList<>();
        this.additionalParameterName = additionalParameterName;
    }

    /**
     * 对sql片段进行组装
     *
     * @param condition   是否执行
     * @param sqlSegments sql片段数组
     * @return children
     */
    protected Children doIt(ISqlSegment... sqlSegments) {
        expression.add(sqlSegments);
        return typedThis;
    }

    @Override
    public String getSqlSegment() {
        return expression.getSqlSegment();
    }

    public Map<String, Object> getParamNameValuePairs() {
        return paramNameValuePairs;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    /**
     * 获取 columnName
     */
    protected String columnToString(R column) {
        return (String) column;
    }

    protected String aliasesAndColumnsToString(String[] aliases, R[] columns) {
        List<String> aliasAndColumns = new ArrayList<>();
        for (int i = 0; i < aliases.length; i++) {
            String aliasAndColumn = aliasAndColumnToString(aliases[i], columns[i]);
            aliasAndColumns.add(aliasAndColumn);
        }
        return aliasAndColumns.stream().collect(joining(StringPool.COMMA));
    }

    @Override
    @SuppressWarnings("all")
    public Children clone() {
        return SerializationUtils.clone(typedThis);
    }

}

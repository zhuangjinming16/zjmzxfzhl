package com.zjmzxfzhl.codecreate.util;

/**
 * @author 庄金明
 * @date 2020年3月25日
 */
public class TableObject {

    /**
     * 表英文名
     */
    private String tableName;
    /**
     * 表中文名
     */
    private String tableNameCn;
    /**
     * 字段序号
     */
    private String sortNo;
    /**
     * 字段英文名
     */
    private String columnNameEn;
    /**
     * 字段中文名
     */
    private String columnNameCn;
    /**
     * 字段类型
     */
    private String dataType;
    /**
     * 字段长度
     */
    private String dataLength;
    /**
     * 字段精度
     */
    private String dataPrecision;
    /**
     * 字段是否可空
     */
    private String isNull;
    /**
     * 字段是否列表
     */
    private boolean isList;
    /**
     * 字段是否查询
     */
    private boolean isSearch;
    /**
     * 下拉框ID
     */
    private String codeTypeId;
    /**
     * 详细说明
     */
    private String remark;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableNameCn() {
        return tableNameCn;
    }

    public void setTableNameCn(String tableNameCn) {
        this.tableNameCn = tableNameCn;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getColumnNameEn() {
        return columnNameEn;
    }

    public void setColumnNameEn(String columnNameEn) {
        this.columnNameEn = columnNameEn;
    }

    public String getColumnNameCn() {
        return columnNameCn;
    }

    public void setColumnNameCn(String columnNameCn) {
        this.columnNameCn = columnNameCn;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getDataPrecision() {
        return dataPrecision;
    }

    public void setDataPrecision(String dataPrecision) {
        this.dataPrecision = dataPrecision;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public boolean getIsList() {
        return isList;
    }

    public void setIsList(boolean isList) {
        this.isList = isList;
    }

    public boolean getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }

    public String getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(String codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

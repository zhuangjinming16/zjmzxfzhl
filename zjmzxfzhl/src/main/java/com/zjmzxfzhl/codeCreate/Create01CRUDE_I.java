package com.zjmzxfzhl.codeCreate;

import com.zjmzxfzhl.codeCreate.single.CreateController;
import com.zjmzxfzhl.codeCreate.single.CreateEntity;
import com.zjmzxfzhl.codeCreate.single.CreateMybatisMapper;
import com.zjmzxfzhl.codeCreate.single.CreateMybatisMapperXml;
import com.zjmzxfzhl.codeCreate.single.CreateService;
import com.zjmzxfzhl.codeCreate.single.CreateSql;
import com.zjmzxfzhl.codeCreate.single.CreateVue;
import com.zjmzxfzhl.codeCreate.util.CodeUtil;

public class Create01CRUDE_I {
	public static void main(String[] args) {
		CreateSql.create(CodeUtil.createTableName);// 创建建表语句
		CreateEntity.create(CodeUtil.createTableName);// 创建对象实体类和数据库一一对应
		CreateMybatisMapperXml.create(CodeUtil.createTableName, "MapperXml"); // 创建mybatisMapper.xml文件
		CreateMybatisMapper.create(CodeUtil.createTableName, "01CRUDE_I"); // 创建mybatisMapper.java文件
		CreateService.create(CodeUtil.createTableName, "01CRUDE_I");// 创建操作数据库的Service类
		CreateController.create(CodeUtil.createTableName, "01CRUDE_I");// 创建SpringMvc控制器的Controller类
		CreateVue.create(CodeUtil.createTableName, "01CRUDE_I");// 创建Vue前端文件
	}
}

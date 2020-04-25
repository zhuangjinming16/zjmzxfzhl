<template>
    <div class="dashboard-container">
        # 1.1.1 升级日志 2020-04-25 <br/>
        1.新增岗位管理、flowable由关联角色改成关联岗位 <br/>
        2.数据权限优化，不合并多角色用户的数据权限，只查询当前角色的数据权限 <br/>
        3.新增监控管理：数据监控、服务监控 <br/>
        4.代码整理、代码生成器优化 <br/>
        # 1.1.0 升级日志 2020-03-17 <br/>
        1.加入flowable6.4.1工作流模块 <br/>
        2.升级mybatis-plus到3.3.1.tmp <br/>
        3.升级到mysql8.0 <br/>
        4.数据权限配置表，新增 方法Id 字段，用于配置部分特殊方法不使用全局权限配置，而使用自己的配置 <br/>
        5.代码生成器优化、代码整理 <br/>
    </div>
</template>

<style>
    .dashboard-container {
        padding: 10px;
        line-height: 2;
    }
</style>


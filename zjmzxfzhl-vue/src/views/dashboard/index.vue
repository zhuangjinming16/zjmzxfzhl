<template>
    <div class="dashboard-container">
        # 1.1.6 升级日志 2020-06-21 <br/>
        1.使用spring-security-oauth2鉴权 <br/>
        2.代码重构 <br/>
        # 1.1.5 升级日志 2020-05-23 <br/>
        1.代码重构，改成maven父子工程 <br/>
        # 1.1.4 升级日志 2020-05-20 <br/>
        1.代码整理，1个tab换成4个空格 <br/>
        # 1.1.3 升级日志 2020-05-17 <br/>
        1.数据权限优化，使用MyBatis-Plus的SqlParser <br/>
        2.完成任务支持修改流程表单（流程设计时UserTask的formKey=startEvent的formKey） <br/>
        3.Lombok@Getter@Setter注解改成使用@Data <br/>
        # 1.1.2 升级日志 2020-05-06 <br/>
        1.新增定时任务 <br/>
        2.新增异步线程池 <br/>
        3.新增阿里easyexcel导入导出工具 <br/>
        4.数据权限优化 <br/>
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


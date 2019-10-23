<template>
    <div class="dashboard-container">
        <h1>项目介绍</h1>

        <p>当前最新版本: 1.0.0（发布日期: 20191001）</p>

        <h2>项目介绍</h2>

        <p>zjmzxfzhl 是基于 SpringBoot + Vue + Elementui + 代码生成器 的快速开发平台，采用前后端分离架构。</p>

        <h2>技术架构</h2>

        <h3>后端技术</h3>

        <ul><li>Maven、Jdk8</li><li>Mysql5.5,oracle,db2</li><li>Spring-boot2.1.3</li><li>Shiro1.4.0-RC2</li><li>Java-jwt3.4.1</li><li>Mybatis-Plus3.0.7.1</li><li>Druid1.1.10数据源</li><li>Redis3.0</li><li>P6spy3.8.0 sql打印，生产应关闭</li><li>Kaptcha0.0.9验证码</li><li>Jsoup1.12.1防XSS攻击</li><li>Swagger2-2.9.2接口文档生成</li><li>Redisson3.11.1-redlock分布式锁</li><li>Lombok1.18.4代码简化</li></ul>

        <h3>前端技术</h3>

        <ul><li>Vue,Vuex,Vue-router</li><li>ElementUI</li><li>Vue-Element-Admin</li><li>Mock</li></ul>

        <h2>主要实现内容</h2>

        <ul><li>前后分离开发，前后端可以独立部署，也可以合并部署</li><li>系统管理模块，包含：参数管理、代码类别、代码信息、菜单管理、功能管理、机构管理、角色管理、用户管理、数据权限、系统日志</li><li>功能权限，菜单权限、按钮权限细粒度配置</li><li>数据权限，注解实现 + 后台配置实现</li><li>Redis分布式锁，可实现交易防重发、秒杀等业务场景</li><li>APP开发示例，包含：注册、登录、获取用户信息，可设置注解定义不需要登录就能访问的APP接口</li><li>代码生成器，包含前端和后端</li></ul>

        <h2>文档地址</h2>

        <p><a href="https://zjm16.gitee.io/zjmzxfzhl-doc">https://zjm16.gitee.io/zjmzxfzhl-doc</a></p>

        <h2>技术交流</h2>

        <p>QQ群 : 913659692</p>
    </div>
</template>

<style>
    .dashboard-container {
        padding: 10px;
        line-height: 2;
    }
</style>


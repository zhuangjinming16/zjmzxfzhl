# Zjmzxfzhl

## 开源不易，如果`Zjmzxfzhl`对您有帮助，请点右上角 `Star`或者 `Fork` 支持一下。

## 您的支持，是我继续做开源项目的动力，谢谢！

# 项目介绍

`Zjmzxfzhl` 集成了 `SpringBoot` `Flowable` `Vue` `Elementui` `FormMaking`，采用前后端分离架构。

[Gitee:Zjmzxfzhl](https://gitee.com/zjm16/zjmzxfzhl) [Github:Zjmzxfzhl](https://github.com/zhuangjinming16/zjmzxfzhl)

若需要微服务架构，请移步：[Gitee:Zjmzxfzhl-Cloud](https://gitee.com/zjm16/zjmzxfzhl-cloud) 或 [Github:Zjmzxfzhl-Cloud](https://github.com/zhuangjinming16/zjmzxfzhl-cloud)

`v1.1.6`以后版本使用`spring-security-oauth2`鉴权，若需要使用 `shiro` 鉴权请移步 [zjmzxfzhl-shiro](https://gitee.com/zjm16/zjmzxfzhl/tree/v1.1.5)

<font color="red">特别提醒：Zjmzxfzhl master 分支 （即 v1.2.0 分支）新增了 我的汇总、我的待阅、抄送功能，用到的 flowable6.5.1.28 相关 jar 包官方没有发布到maven中央仓库，我已打包好并放在群共享 可加群下载。</font>

## 技术架构
### 后端技术

- 基础框架：Java8 & Spring Boot & Maven
- 数据库：Mysql 等
- 鉴权框架：Spring Security OAuth2
- 缓存框架：Redis & Redisson
- 持久层框架：Mybatis Plus
- 日志记录：Logback
- 工作流框架：Flowable
- 其他依赖：Lombok、Kaptcha、EasyExcel 等
### 前端技术
- vue , vuex , vue-router
- elementui
- vue-element-admin
- vue-form-making
- mock

## 主要实现内容
1. 前后分离开发，前后端可以独立部署，也可以合并部署
2. `我的流程`、`系统管理`、`示例管理`、`流程管理`、`监控管理`
3. `功能权限`，`菜单权限`、`按钮权限`细粒度配置
4. `数据权限`，注解实现或数据库配置实现
5. `我的流程` 包含 `我的汇总`、`发起流程`、`我的待办`、`我的待阅`、`我的已办`，`流程管理` 包含 `表单设计`、`流程设计`、`流程定义`、`流程管理`、`任务管理`，`任务执行` 包含 `提交`、`抄送`、`转办`、`委派`、`终止`、`退回`（已实现退回并行网关节点、子流程退回）、`(批量)已阅` 等

   流程设计约定：
   - 发起者启动流程后若要自动完成第一个用户任务，则第一个 `userTask` 的 `id` 要定义为`__initiator__`，若涉及流程表单，则可设置`__initiator__`的任务表单 `formKey` 与流程表单相同
   - 如果涉及并行网关，并行网关需成对出现，且发散节点要以 `_begin` 结尾，汇聚节点要以 `_end` 结尾，可以嵌套但不能交叉嵌套，这样就能确保可以退回到并行网关的单个节点上（不会退回到并行网关的其他分支）
   - 如果流程涉及业务主键 `businessKey`，流程设计时加入 `流程扩展属性` 或者 `流程数据对象` 即可
    ```xml
    <extensionElements>
        <flowable:properties>
            <flowable:property name="showBusinessKey" value="true" />
        </flowable:properties>
    </extensionElements>
    ```
    ```xml
    <dataObject id="showBusinessKey" name="showBusinessKey" itemSubjectRef="xsd:boolean">
        <extensionElements>
            <flowable:value>true</flowable:value>
        </extensionElements>
    </dataObject>
    ```
   - 流程设计可参考 `zjmzxfzhl/zjmzxfzhl-admin/src/main/resources/processes_test` 下的流程
   - 自定义表单使用LGPL协议开源的 [FormMaking](http://form.xiaoyaoji.cn/pricing/#/zh-CN/)，若使用的 `FormMaking` 安装包（及通过 `npm` 引入）的方式，不需要购买授权，但若有使用 `FormMaking` 的源代码，需要到官方购买授权
6. `Redis` 分布式锁，可实现交易防重发等业务场景
7. `App` 开发示例，包含：注册、登录、获取用户信息等
8. 代码生成器，包含前端和后端
9. 定时任务、异步任务线程池管理
10. `Excel` 导入导出

## 文档及演示环境
文档地址：[https://zjm16.gitee.io/zjmzxfzhl-doc](https://zjm16.gitee.io/zjmzxfzhl-doc)

演示环境：[http://118.190.100.3:8080/zjmzxfzhl](http://118.190.100.3:8080/zjmzxfzhl)

测试用户（默认密码都是 `1`）：

`admin`用户，拥有所有权限，测试通用功能、工作流程连贯性等，可以使用`admin`用户，例如执行工程内的`complex-嵌套并行网关子流程`可以使用`admin`用户

`zjmzxfzhl`普通员工岗位，可以发起请假流程

`zjm`经理岗位，可以审批员工的请假流程

`zxf`老板岗位，可以审批员工的请假流程

请假流程详见 `zjmzxfzhl/zjmzxfzhl-admin/src/main/resources/processes_test/leave.bpmn20.xml` 或 `zjmzxfzhl/zjmzxfzhl-admin/src/main/resources/processes_test/leaveBusinessKey.bpmn20.xml`

## 技术交流
QQ群 : 913659692

## 界面展示

![](https://img-blog.csdnimg.cn/20201006123139193.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/2020032821122660.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211234880.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211245801.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/2020100612340279.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20201006123431902.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20201006123102845.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20200328211256213.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211323783.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211441117.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211448303.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211505420.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/2020100612355468.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20201006123609592.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20201006123617560.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20200328211756182.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211559996.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211539437.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20201006123921431.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20200328211904277.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211910561.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211924102.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211930452.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211936458.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20201006124138717.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20201006125002125.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20201006131531738.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70#pic_center)
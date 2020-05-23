# 项目介绍
当前最新版本: 1.1.5（发布日期: 2020-05-23）
## 项目介绍
zjmzxfzhl 是基于 SpringBoot + Vue + Elementui + FormMaking + Flowable + 代码生成器 的快速开发平台，采用前后端分离架构。
## 技术架构
### 后端技术

- Maven、Jdk8
- Mysql8.0,oracle,db2
- Spring-boot2.1.3
- Shiro1.4.0-RC2 
- Flowable6.4.1
- Java-jwt3.4.1
- Mybatis-Plus3.3.1.tmp
- Druid1.1.10数据源
- Redis3.0
- P6spy3.8.0 sql打印，生产应关闭
- Kaptcha0.0.9验证码
- Jsoup1.12.1防XSS攻击
- Swagger2-2.9.2接口文档生成
- Redisson3.11.1-redlock分布式锁
- Lombok1.18.4代码简化
- EasyExcel2.2.0-beta1

### 前端技术
- Vue,Vuex,Vue-router

- ElementUI

- Vue-Element-Admin

- FormMaking

- Mock

## 主要实现内容
1.  前后分离开发，前后端可以独立部署，也可以合并部署

2.  系统管理、流程管理、示例管理

3.  功能权限，菜单权限、按钮权限细粒度配置

4.  数据权限，注解实现 + 后台配置实现

5. 流程管理，包含自定义表单、流程定义、流程实例、任务管理、发起流程、我的流程、我的待办、我的已办，任务执行包含提交、终止、转办、委派、退回（已实现退回并行网关节点、子流程退回）等

   流程设计约定：

   - 发起者启动流程后若要自动完成第一个用户任务，则第一个userTask的id要定义为`__initiator__`，若涉及流程表单，则可设置`__initiator__`的任务表单formKey与流程表单相同

   - 如果涉及并行网关，并行网关需成对出现，且发散节点要以 _begin 结尾，汇聚节点要以 _end 结尾，可以嵌套但不能交叉嵌套，这样就能确保可以退回到并行网关的单个节点上（不会退回到并行网关的其他分支）

   - 如果流程涉及业务主键key，流程设计时加入流程数据对象即可

     ```xml
     <dataObject id="showBusinessKey" name="showBusinessKey" itemSubjectRef="xsd:boolean">
     	<extensionElements>
     		<activiti:value>true</activiti:value>
     	</extensionElements>
     </dataObject>
     ```

   - 流程设计可参考工程源代码 `zjmzxfzhl/src/main/resources/processes_test` 下的流程（使用的是flowable的eclipse插件 Flowable Diagram Editor 设计的）

   - 自定义表单使用LGPL协议开源的 [FormMaking](http://form.xiaoyaoji.cn/pricing/#/zh-CN/)，若使用的FormMaking安装包（及通过npm引入）的方式，不需要购买授权，但若有使用FormMaking的源代码，需要到官方购买授权

6. Redis分布式锁，可实现交易防重发等业务场景

7. APP开发示例，包含：注册、登录、获取用户信息，可设置注解定义不需要登录就能访问的APP接口

8. 代码生成器，包含前端和后端

9. 定时任务、异步任务线程池管理

10. excel导入导出

## 文档及演示环境
文档地址：[https://zjm16.gitee.io/zjmzxfzhl-doc](https://zjm16.gitee.io/zjmzxfzhl-doc) (文档尚未更新到最新)

演示环境：[http://118.190.100.3:8080/zjmzxfzhl](http://118.190.100.3:8080/zjmzxfzhl)

测试用户（默认密码都是1）：

`admin`用户，拥有所有权限，测试通用功能、工作流程连贯性等，可以使用`admin`用户，例如执行工程内的`complex-嵌套并行网关子流程`可以使用`admin`用户

`zjmzxfzhl`普通员工岗位，可以发起请假流程

`zjm`经理岗位，可以审批员工的请假流程

`zxf`老板岗位，可以审批员工的请假流程

请假流程详见`zjmzxfzhl/src/main/resources/processes_test/leave.bpmn20.xml`或者`zjmzxfzhl/src/main/resources/processes_test/leaveBusinessKey.bpmn20.xml`

## 技术交流
QQ群 : 913659692

## 界面展示

![](https://img-blog.csdnimg.cn/20200328211217434.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/2020032821122660.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211234880.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211245801.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211256213.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211323783.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211441117.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211448303.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211505420.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211524258.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211756182.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211559996.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211539437.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211642491.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211850894.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211904277.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211910561.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211924102.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211930452.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200328211936458.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pqbTE2,size_16,color_FFFFFF,t_70)
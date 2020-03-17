# 项目介绍
当前最新版本: 1.1.0（发布日期: 20200317）
## 项目介绍
zjmzxfzhl 是基于 SpringBoot + Vue + Elementui + FormMaking + Flowable + 代码生成器 的快速开发平台，采用前后端分离架构。
## 技术架构
### 后端技术
	1. Maven、Jdk8
 	2. Mysql8.0,oracle,db2
 	3. Spring-boot2.1.3
 	4. Shiro1.4.0-RC2
 	5. Flowable6.4.1
 	6. Java-jwt3.4.1
 	7. Mybatis-Plus3.3.1.tmp
 	8. Druid1.1.10数据源
 	9. Redis3.0
 	10. P6spy3.8.0 sql打印，生产应关闭
 	11. Kaptcha0.0.9验证码
 	12. Jsoup1.12.1防XSS攻击
 	13. Swagger2-2.9.2接口文档生成
 	14. Redisson3.11.1-redlock分布式锁
 	15. Lombok1.18.4代码简化

### 前端技术
1. Vue,Vuex,Vue-router
2. ElementUI
3. Vue-Element-Admin
4. FormMaking
5. Mock

## 主要实现内容
1.  前后分离开发，前后端可以独立部署，也可以合并部署

2.  系统管理、流程管理、示例管理

3.  功能权限，菜单权限、按钮权限细粒度配置

4.  数据权限，注解实现 + 后台配置实现

5.  流程管理，包含自定义表单、流程定义、流程实例、任务管理、发起流程、我的流程、我的待办、我的已办，任务执行包含提交、终止、转办、委派、退回（已实现退回并行网关节点、子流程退回）等

   流程设计约定：

   - 发起者启动流程后若要自动完成第一个用户任务，则第一个userTask的id要定义为`__INITIATOR__`，若涉及流程表单，则可设置`__INITIATOR__`的任务表单formKey与流程表单相同

   - 如果涉及并行网关，并行网关需成对出现，且开始要以_begin 结尾，结束要以_end 结尾，可以嵌套但不能交叉嵌套，这样就能确保可以退回到并行网关的节点上，但同时会退回到并行网关的所有分支

   - 如果流程涉及业务主键key，流程设计时加入流程数据对象即可

     ```xml
     <dataObject id="showBusinessKey" name="showBusinessKey" itemSubjectRef="xsd:boolean">
     	<extensionElements>
     		<activiti:value>true</activiti:value>
     	</extensionElements>
     </dataObject>
     ```

   - 自定义表单使用LGPL协议开源的 [FormMaking](http://form.xiaoyaoji.cn/pricing/#/zh-CN/)，若使用的FormMaking安装包（及通过npm引入）的方式，不需要购买授权，但若有使用FormMaking的源代码，需要到官方购买授权

6. Redis分布式锁，可实现交易防重发等业务场景

7. APP开发示例，包含：注册、登录、获取用户信息，可设置注解定义不需要登录就能访问的APP接口

8. 代码生成器，包含前端和后端

## 文档及演示环境
文档地址：[https://zjm16.gitee.io/zjmzxfzhl-doc](https://zjm16.gitee.io/zjmzxfzhl-doc)

演示环境：[http://118.190.100.3:8080/zjmzxfzhl](http://118.190.100.3:8080/zjmzxfzhl)

## 技术交流
QQ群 : 913659692
# zjmzxfzhl（前后端分离）
当前最新版本: 1.0.0（发布日期: 20191001）
#### 项目介绍
zjmzxfzhl 是基于 SpringBoot + Vue + Elementui + 代码生成器 的快速开发平台，采用前后端分离架构。
#### 技术架构
##### 后端技术(Maven、Jdk8)
* mysql5.5,oracle,db2
* spring-boot2.1.3
* shiro1.4.0-RC2
* java-jwt3.4.1
* mybatis-plus3.0.7.1
* druid1.1.10数据源
* redis3.0
* p6spy3.8.0 sql打印，生产应关闭
* kaptcha0.0.9验证码
* jsoup1.12.1防XSS攻击
* Swagger2-2.9.2接口文档生成
* redisson3.11.1-redlock分布式锁
* lombok1.18.4代码简化

##### 前端技术
* vue,vuex,vue-router
* elementui
* mock
* vue-element-admin

#### 主要实现内容
* 前后分离开发，前后端可以独立部署，也可以合并部署
* 系统管理模块，包含：参数管理、代码类别、代码信息、菜单管理、功能管理、机构管理、角色管理、用户管理、数据权限、系统日志
* 功能权限，菜单权限、按钮权限细粒度配置
* 数据权限，注解实现 + 后台配置实现
* redis分布式锁，可实现交易防重发、秒杀等业务场景
* APP开发示例接口，包含：注册、登录、获取用户信息，可设置注解定义不需要登录就能访问的APP接口
* 代码生成器，包含前端和后端
* 后续会陆续增加其他功能及相关开发、部署文档

#### 部署启动后访问路径

##### 登录首页
* http://localhost:8081/zjmzxfzhl

##### 接口文档
* http://localhost:8081/zjmzxfzhl/swagger-ui.html

##### druid
* http://localhost:8081/zjmzxfzhl/druid

##### 系统监控
* http://localhost:8081/zjmzxfzhl/actuator/
* http://localhost:8081/zjmzxfzhl/actuator/redis/info
* http://localhost:8081/zjmzxfzhl/actuator/redis/keysSize
* http://localhost:8081/zjmzxfzhl/actuator/redis/memoryInfo
* http://localhost:8081/zjmzxfzhl/actuator/queryDiskInfo

#### 演示地址
* http://119.23.106.234:8080/zjmzxfzhl

#### 技术交流
##### QQ群 : 913659692
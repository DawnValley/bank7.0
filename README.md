# bank7.0
## 开发环境
MyEclipse2017+Tomcat8.5+JDK1.7+MySQL5.5
## 项目描述
项目的主要功能有用户的登录与注册、存款、取款、转账、余额查询与交易记录查询，管理员查询、冻结、解冻用户账户、日志查询等。 
## 技术描述
采用MVC设计模式，使用了Spring MVC+Spring+MyBatis集成开发，应用Spring注解和IOC注入完成框架的整合。
设置管理员和普通用户两种角色，使用文件存储并对密码进行MD5加密，对用户的其他数据用数据库存储。
管理员可以查询用户的数据库信息，修改用户状态信息完成对用户状态的管理。
用户被冻结后部分操作权限将关闭，使用Spring的AOP技术对用户的权限进行验证。
在业务层中，注册和登录方法访问了文件查询用户名和密码，并使用MyBatis进行数据查询及存储；
其他方法则不需要文件操作，仅使用MyBatis对数据库数据操作。
系统功能的操作都会生成相应的日志信息，存入数据库。
在管理员对用户管理和日志管理时，使用了数据库分页技术对结果数据进行显示。
## 数据存储描述
使用MySQL数据库存储数据，数据库文件为bank_database.sql，可直接通过SQLyog、Navicat Lite for MySQL等工具直接导入数据库。
### 注意：
在项目文件中，已经设置了数据库用户名为root密码为root。需要使用不同的用户名密码时，可修改bank7.0\WebRoot\WEB-INF\applicationContext-common.xml中的<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">的配置，对数据库连接进行修改。

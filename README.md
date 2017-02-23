# EStore

### 项目介绍

使用JSP/Servlet/MySQL，完成简易电子商城项目，实现用户注册，登录，浏览商品到购买商品的完整流程

### 功能实现：

- 用户登录模块：用户注册、激活、自动登录、注销的基本功能

- 商品模块：商品添加、图片上传、商品删除、展示等基本内容；前端数据校验；商品展示使用分页功能

- 购物车模块： 使用session完成简单的购物城功能；后台校验数据

- 订单模块：从购物车生成订单

- 事务控制：在用户注册，添加订单时使用事务处理


### 从这个项目中学习到

- 较为完成的开发流程和业务处理

- 使用工厂模式解耦dao和service

- 使用动态代理、注解和反射简化事务的控制，实现AOP的功能

- ThreadLocal保存线程中的变量，使事务的控制线程安全

- 邮件发送注册激活

### 部分项目截图
- 用户注册

![用户注册](http://wx4.sinaimg.cn/mw690/d901f61bly1fd0elk3d9qj209l08daac.jpg)

- 添加商品

![添加商品](http://wx4.sinaimg.cn/mw690/d901f61bly1fd0eqsanyqj209608pdfz.jpg)

- 主页

![用户](http://wx1.sinaimg.cn/mw690/d901f61bly1fd0elix79aj20dw05kglk.jpg)
![管理员](http://wx4.sinaimg.cn/mw690/d901f61bly1fd0eqrqzc0j20bu03s749.jpg)

- 浏览商品

![浏览商品](http://wx2.sinaimg.cn/mw690/d901f61bly1fd0eljaiu0j20qy0enjsu.jpg)

- 购物车

![购物车](http://wx2.sinaimg.cn/mw690/d901f61bly1fd0eqrazxtj20rp0db0ug.jpg)




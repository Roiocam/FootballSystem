# FootBallSystem

# 项目介绍

本项目为足协内部管理系统，出发点为解决每日踢球人数的提醒.后衍生了多个模块，主要解决了协会赛事报名，分组功能，使用程序实现部分自动化

# 项目结构
    src/main/java
    |---club.pypzx.FootballSystem
    |---|---config  
    |---|---|---dao     配置数据源和会话
    |---|---|---sevice  配置事务管理器
    |---|---|---web	    配置拦截器、视图解析器
    |---|---dao			数据访问层接口
    |---|---|---JPA    持久层框架JPA外部接口
    |---|---|---MyBatis    持久层框架MyBatis外部接口
    |---|---datasource  自己实现的动态数据源相关
    |---|---dbmgr		管理项目编码与数据库IP、名称的映射关系	
    |---|---dto			封装的请求返回数据对象（视图层的模型）
    |---|---entity		映射模型
    |---|---enums		枚举类
    |---|---exception	异常类
    |---|---interceptor	拦截类
    |---|---service		业务逻辑层接口
    |---|---|---impl	实现类
    |---|---template	模板类
    |---|---utils		工具类
    |---|---web         控制层
    |---|---|---admin	后台管理
    |---|---|---app		Web App
    src/main/resources
    |---db		数据库语句
    
# [API接口](https://github.com/Roiocam/FootballSystem/edit/master/API.md)
# 项目设计

## DAO层设计

### 多数据源切换/数据访问层框架切换
- ....

![switch_datasource](https://github.com/Roiocam/FootballSystem/raw/master/image/switch_datasource.png)

### MyBatis

- 使用反射+MyBatis的SQL构建器自动生成基础CRUD

### Spring Data JPA

- ....

### WEB API示意图

![system_line](https://github.com/Roiocam/FootballSystem/raw/master/image/system_line.png)

## 业务规范
- 使用泛型类BaseExcution作为统一返回类型
- 使用枚举类BaseStateEnum作为统一返回标识
- 封装IDUtils生成根据时间戳和随机数的ID标识
## 控制层规范
- 封装ModelMapUtil作为统一接口响应体格式
- 封装ParamUtils做参数校验工具
- 封装HttpServletRequestUtil获取参数
- 封装ResultUtil判断业务结果

# 系统模块

## 模块业务图

### 系统模块

![admin_logic](https://github.com/Roiocam/FootballSystem/raw/master/image/admin_logic.png)

### APP模块

![app_logic](https://github.com/Roiocam/FootballSystem/raw/master/image/app_logic.png)

### APP业务图

![system_block](https://github.com/Roiocam/FootballSystem/raw/master/image/system_block.png)

## 后台管理系统
赛事，球队，球员，赛程，管理员五个主要模块

主要使用Vue.js + jQuery + Ajax技术，部分用到了axios

封装/resources/js/tab.js作为浏览器标签化管理各个模块(iframe)

封装/resources/js/tableutil.js实现表格单击选择，点击table的header实现全选

封装/resources/js/loadingUtils.js为Bootstrap的modal弹出框实现ajax请求时的加载动画

封装/resources/js/pageUtils.js实现前端分页功能

### 赛程模块
1. 自动化业务为赛事下的9个球队进行随机分组
2. 自动化业务为每个分组随机生成赛程表

## 前台APP
今日踢球,赛事报名/创建球队,赛事图(40%),赛程表(0%),排行榜(0%)五个主要模块
### 今日踢球
### 赛事报名/创建球队
### 赛事分组
### 赛程表





# 测试
> 感谢张同学提供页面流程测试,提供细节化体验意见（已解决部分）

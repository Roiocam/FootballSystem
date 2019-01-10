# 统一接口模版
### URL头部
> https://ilove33.cn/FootballSystem/

### 支持格式
> 表单

### HTTP请求方式
> POST

### 统一请求参数
> | 参数|可选|类型|说明|
> | ------ | ------ | ------ | ------ |
> | pageIndex | 部分 | INT | 分页页码 |
> | pageSize | 部分 | INT | 分页大小 |
> | dbCode | 必填 | String | 数据库名 |
> | dbType | 必填 | String | 持久框架 |

### 统一返回报文
> | 参数|返回确定性|类型|说明|
> | ------ | ------ | ------ | ------ |
> | statue | 一定 | INT | 状态编码 |
> | stateInfo | 一定 | INT | 状态信息|
> | message | 一定 | String | 操作信息 |
> | count | 部分 | INT | 查询数据总数|
> | result | 部分 | Object/List | 查询数据|
> | resultOne | 小部分 | Object/List | 返回双数据时的主级数据|
> | resultTwo | 小部分 | Object/List | 返回双数据时的次级数据|

### 接口示例
> 地址：https://ilove33.cn/FootballSystem/admin/service/user/getUsers
>
> 参数：pageIndex=1&pageSize=99&dbType=JPA&dbCode=FootballSystem
>
> 方法：POST
>
> Content-Type：application/x-www-form-urlencoded

### 返回报文示例

    {
    "result": [
        {
            "username": "asd",
            "password": "asd"
        },
        {
            "username": "abc",
            "password": "abc"
        }
    ],
    "count": 11,
    "stateInfo": "操作成功",
    "state": 0,
    "message": "查询管理员列表成功"
}

## API表
> | URL|方法|参数|说明|
> | ------ | ------ | ------ | ------ |
> | /admin/service/cup/getCups | POST | pageIndex&pageSize | 返回赛事列表 |
> | /admin/service/team/getTeams | POST | pageIndex&pageSize | 返回球队列表 |
> | /app/service/plusKick | POST | 无 | 报名今日踢球,请保存session |
> | /app/service/reduceKick | POST | 无 | 取消今日踢球,请保存session |
> | /app/service/getKick | POST | 无 | 获取今日踢球人数,请保存session |
> | /app/service/getTeamDetail | POST |team.teamId | 根据球队id获取球队详情 |
> | /app/service/checkValidId| POST | playerInfo.playerStuno | 动态校验学号正确性 |
> | /app/service/getByOpenid | POST | openid | 获取已报名/已创建信息 |
> | /app/service/getKick | POST | 无 | 获取今日踢球人数,请保存session |
> | /admin/service/game/getGames |POST|pageIndex&pageSize&cup.cupId|根据赛事id查询赛程表
> | /admin/service/team/getTeamGroup|POST|cup.cupId|根据赛事id获取赛事分组

### 新增球员API接口
> 地址：https://ilove33.cn/FootballSystem/app/service/addPlayer
>
> 参数：vaildCode&teamId&openid&playerName&playerNum&playerStuno&playerDepart&playerTel
>
> 方法：POST
>
> Content-Type：application/x-www-form-urlencoded

#### 新增球员参数字典
> | 参数名|必须|类型|说明|
> | ------ | ------ | ------ | ------ |
> | team.vaildCode | 必须 | String | 4位球队验证码 |
> | team.teamId | 必须 | String | 球队id |
> | openid | 可选/小程序必须 | String | 微信openid |
> | player.playerName | 必须 | String | 球员姓名 |
> | player.playerNum | 必须 | INT | 球衣号码 |
> | playerInfo.playerStuno | 必须 | INT | 学号 |
> | playerInfo.playerDepart | 必须 | String | 学院 |
> | playerInfo.playerTel | 必须 | Long | 手机号码 |


### 创建球队API接口
> 地址：https://ilove33.cn/FootballSystem/app/service/createTeam
>
> 参数：team.vaildCode&team.cupId&openid&team.teamName&team.teamDesc&player.playerName&player.playerNum&playerInfo.playerStuno&playerInfo.playerDepart&playerInfo.playerTel
>
> 方法：POST
>
> Content-Type：application/x-www-form-urlencoded

#### 创建球队参数字典
> | 参数名|必须|类型|说明|
> | ------ | ------ | ------ | ------ |
> | openid | 可选/小程序必须 | String | 微信openid |
> | team.cupId | 必须 | String | 所属赛事id |
> | team.vaildCode | 必须 | String | 4位球队验证码 |
> | team.teamName | 必须 | String | 球队名称 |
> | team.teamDesc | 必须 | String | 球队介绍 |
> | player.playerName | 必须 | String | 球员姓名 |
> | player.playerNum | 必须 | INT | 球衣号码 |
> | playerInfo.playerStuno | 必须 | INT | 学号 |
> | playerInfo.playerDepart | 必须 | String | 学院 |
> | playerInfo.playerTel | 必须 | Long | 手机号码 |

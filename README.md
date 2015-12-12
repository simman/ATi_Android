个人用的小程序，用于监听短信与来电，通过leancloud.cn SDK push 到 iPhone


### 配置

1、[Leancloud](https://leancloud.cn) 注册账号并添加应用，拿到 `AppId`, `AppKey`.

2、在 Leancloud 后台选择存储，创建 名称为 `SMSMessage` 的Class, 字段如下：

字段 | 类型 | 描述
------------ | ------------- | ------------
sender | String  | 发送者
receiver | String  | 接收者
content | String  | 内容
sendTime | Date  | 发送时间
isRead | Boolean | 是否阅读


3、SMSHelperApp 配置

```
AVOSCloud.initialize(this, "AppId", "AppKey");
```

4、Build App

注：为了保证应用可以后台运行进行监听, 请对应加入所在系统的白名单并赋予一定的权限。
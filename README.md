
# Sentinel: The Sentinel of Your Microservices

## 修改内容 

基于官方 1.8.2 版本代码，修改 sentinel 的 dashboard 项目，增加规则持久化到 nacos 的功能，即
- 从 nacos 下载限流规则
- 把 sentinel 控制台修改的规则保存到 nacos

参考：（这两篇文章写的是同一个方法，相互补充）
- https://blog.csdn.net/weixin_44963129/article/details/118498463  
- https://blog.csdn.net/wenwang3000/article/details/108153459
snowflake 参考：
- https://blog.csdn.net/rodbate/article/details/89763669

### 修改过程（具体查看 git 修改历史 ）
- 下载官方 v1.8.2 版本代码，整个项目 maven 编译通过
- 在 子项目 sentinel-dashboard 的运行配置增加参数 `-Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard`，运行项目
- pom.xml 把 sentinel-datasource-nacos 限制打开，即删掉 `<scope>test</scope>`
- 把 sentinel-dashboard 项目的 test/java/com.alibaba.csp.sentinel.dashboard/rule/nacos 目录复制到 main/java/com.alibaba.csp.sentinel.dashboard/rule 下面
- 在已运行的 sentinel-dashboard 网站上任意增加/修改一条规则，浏览器控制台看到调用的是 /v1/flow/xxx，所以修改 FlowControllerV1 这个类
- 在 FlowControllerV1 里改用 Nacos 的 FlowRuleNacosProvider 和 FlowRuleNacosPublisher 从 Nacos 获取限流规则
- 项目配置文件增加 Nacos 的参数配置 `nacos.addr` 和 `nacos.namespace`，默认值分别为 `127.0.0.1:8848` 和 `sentinel_namespace`
- sentinel-dashboard 重启后，新建的规则的 ID 会重新从 1 开始，会覆盖以前的规则。需要改为 snowflake：
  - 项目配置文件增加 snowflake 初始参数 id.dataCenterId 和 id.workerId，默认值均为 0
  - package `com.alibaba.csp.sentinel.dashboard.repository.rule` 的父类 `InMemoryRuleRepositoryAdapter` 使用 snowflake 实现 nextId 函数
  - 删掉其派生类 @override nextId() 的行为

### 注意事项
- 运行前在 Nacos 添加对应的命令空间

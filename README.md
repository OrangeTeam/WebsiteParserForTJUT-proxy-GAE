# WebsiteParserForTJUT-proxy-GAE

本项目是[WebsiteParserForTJUT][]的代理服务器GAE实现，它可以代理通知等非个人的通用信息的解析工作，以提高用户解析速度降低资源消耗，减轻被解析网站的负担。

本项目包含api、server、client三个子项目。api是服务接口；server中的后端scanner负责定时解析网站信息，server中的前端default实现了api，为用户提供服务；client为大陆墙后用户提供了简单的迂回功能，client是可选的，可以不使用。

## 从源代码构建
本项目使用[Gradle][]自动化构建系统。

### 准备

在进行以下操作前，请安装并配置好[JDK][] 7+、[Google App Engine Java SDK][appengine-java-sdk]和[Git][]。

建议把GAE Java SDK的根目录设为`APPENGINE_HOME`环境变量。

client子项目依赖于`com.exadel.flamingo.android:flamingo-android-hessian-client:2.2.0`，请把它安装在本地Maven仓库中。

### 克隆代码库
`git clone https://github.com/OrangeTeam/WebsiteParserForTJUT-proxy-GAE.git`

### 编译，测试，构建jar包
`./gradlew build`

### 发布本项目生成的jar产品到本地Maven缓存
`./gradlew install`

本地Maven缓存的使用及路径请参考Gradle[手册][man:MavenLocal]。

您可以通过`./gradlew tasks`查找到更多可用任务。

[WebsiteParserForTJUT]: https://github.com/OrangeTeam/WebsiteParserForTJUT
[Gradle]: http://gradle.org
[JDK]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[appengine-java-sdk]: https://developers.google.com/appengine/downloads#Google_App_Engine_SDK_for_Java
[Git]: http://git-scm.com
[man:MavenLocal]: http://www.gradle.org/docs/current/userguide/dependency_management.html#sub:maven_local

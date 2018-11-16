# tio-websocket-spring-boot-starter

#### Intro
spring-spring-boot-starter for tio-websocket  

#### Starter Repo
[Github Sample](https://github.com/zhfish/tio-websocket-spring-boot-starter)  
[Gitee Sample](https://gitee.com/zhfish/tio-websocket-spring-boot-starter)

#### T-io Repo
[Gitee Sample](https://gitee.com/tywo45/t-io)

### Sample Repo
[Github Sample](https://github.com/zhfish/tio-websocket-spring-boot-starter/tree/master/sample)  
[Gitee Sample](https://gitee.com/zhfish/tio-websocket-spring-boot-starter/tree/master/sample)

#### Install
- dependency Spring-boot 2.0 +
- dependency Lombok Plugins
```xml
<dependency>
    <groupId>net.zhfish.tio</groupId>
    <artifactId>tio-websocket-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```

#### Use

Require:
- `@EnableTioWebsocketConfiguration`
- `@TioController`
- `@TIOnHandshake`
- `@TIOnClose`

Require eigher:
- `@TIOn(Text or Bytes)`
- or
- `@TIOnBefore(Text or Bytes)`
- `@TIOnMap`
- `@TIOnMap("event")`



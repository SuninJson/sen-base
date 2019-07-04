# SpringApplication为什么使用观察者模式

## 观察者模式简述

（摘抄自《大话设计模式》）

### 什么是观察者模式

通知者在自己的状态变动后，将这个变动通知给 之前向自己登记过需要关注该变动的 观察者

![1562222157247](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562222157247.png)

![1562222969656](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562222969656.png)

### 观察者模式的动机

![1562152747159](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562152747159.png)

#### 依赖倒置原则

![1562202980558](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562202980558.png)

### 生活中的例子

![1562222361323](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562222361323.png)



## SpringApplication中的观察者模式

### Spring Boot繁多的启动处理

使用Spring Boot启动项目时，程序会发布一系列的事件，为了观察到其启动时发布了哪些事件，我们修改启动类将这些事件打印到控制台，代码如下：

```java
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        new SpringApplicationBuilder(FrameworkApplication.class)
                .listeners(event -> {
                    String name = event.getClass().getName();
                    System.err.println("监听到事件：" + name);
                    stringBuilder.append(name).append("\n");
                })
                .run(args);
        FileUtils.write("D:/eventName.txt", stringBuilder.toString());
    }
```

运行后查看控制台可以看到下列事件被发布：

- `org.springframework.boot.context.event.ApplicationStartingEvent`
- `org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent`
- `org.springframework.boot.context.event.ApplicationContextInitializedEvent`
- `org.springframework.boot.context.event.ApplicationPreparedEvent`
- `springfox.documentation.schema.configuration.ObjectMapperConfigured`
- `springfox.documentation.schema.configuration.ObjectMapperConfigured`
- `org.springframework.context.event.ContextRefreshedEvent`
- `org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent`
- `org.springframework.boot.context.event.ApplicationStartedEvent`
- `org.springframework.boot.context.event.ApplicationReadyEvent`



### 监听者调用流程

以订阅了 `ApplicationStartingEvent` 的 `LoggingApplicationListener` 为例其调用过程如下

![img](https://mmbiz.qpic.cn/mmbiz_png/ahtRxLfia0eKYCRKu1MtbTKibibGn3I8BoQ56yNGZ5XEXC8PqKoFX29cGibBcPxINtfbBqkjHjh4XX0Qh521I9aAVw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



### LoggingApplicationListener  的创建流程

其实这个类在`org.springframework.boot:spring-boot` 包下的 

`META-INF/spring.factories` 文件中被定义，在构造   

`SpringApplication` 时然后由

`org.springframework.core.io.support.SpringFactoriesLoader#loadSringFactories` 方法通过读取 `spring.factories` 文件获取其类名，最后通过 

`org.springframework.boot.SpringApplication#createSpringFactoriesInstances` 方法进行初始化后存储到 

`org.springframework.boot.SpringApplication#listeners` 成员属性中



获取类名的执行栈如下：

![img](https://mmbiz.qpic.cn/mmbiz_png/ahtRxLfia0eKYCRKu1MtbTKibibGn3I8BoQe1OdChTWcLcbCm84jUuv5rwg9hCBMs4CjwPwY1r1h311TkPFW40JJw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



实例化的执行栈如下：

![img](https://mmbiz.qpic.cn/mmbiz_png/ahtRxLfia0eKYCRKu1MtbTKibibGn3I8BoQO2mCCgI2PWw2K2o1zd9D7PtWExLppDcfibicbMOoMtWPwPicjAPmqMVlQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



## 总结

ObserverModel.uml


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



程序启动时面对众多的准备如环境检测、配置初始化、数据初始化等操作，若`org.springframework.boot.SpringApplication`去依赖众多操作所对应的类，这会使得`SpringApplicaton`非常臃肿，为了解除耦合，Spring利用观察者模式对`SpringApplication`与众多操作所对应的类进行解耦，这样`SpringApplication`只需要发布事件就可以让各个类自行的执行相应的操作



以订阅了 `ApplicationStartingEvent` 的 `LoggingApplicationListener` 为例其调用过程如下

![img](https://mmbiz.qpic.cn/mmbiz_png/ahtRxLfia0eKYCRKu1MtbTKibibGn3I8BoQ56yNGZ5XEXC8PqKoFX29cGibBcPxINtfbBqkjHjh4XX0Qh521I9aAVw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

了解到其调用过程后，或许我们会有一个疑问

## `LoggingApplicationListener`  是在什么地方创建的

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



总结：

通过


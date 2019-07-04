# Spring Boot 如何启动Tomcat

## Tomcat启动的方法执行栈

查看Spring Boot启动日志：

```json
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080
```

![1562206309542](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562206309542.png)

`SpringApplication#refresh`方法：

![1562206457378](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562206457378.png)



## AnnotationConfigServletWebServerApplicationContext的初始化



回到`SpringApplication#run:310` context在此处创建

进入这行代码的`SpringApplication#createApplicationContext`方法我们可以发现该方法会根据`SpringApplication#webApplicationType`成员变量来判断需要返回什么类型的Web容器的实例

![1562208215495](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562208215495.png)



### webApplicationType在哪设置的

查看代码中Write该成员变量的位置，发现在`SpringApplication`初始化的时候会去设置该成员变量

![1562207576987](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562207576987.png)



`SpringApplication#SpringApplication `  -> `WebApplicationType#deduceFromClasspath`

`WebApplicationType#deduceFromClasspath`方法会根据应用目前具有的Web包去判断其类型



### SpringApplication在哪初始化的

![1562208545507](C:\Users\15383\AppData\Roaming\Typora\typora-user-images\1562208545507.png)

 

由上述可以发现一个情况：

`AnnotationConfigServletWebServerApplicationContext` 

- -> `ConfigurableApplicationContext`
  - -> `ApplicationContext `
    - ->`AbstractApplicationContext` 
      - -> `ServletWebServerApplicationContext#refresh`
        - -> `AbstractApplicationContext#refresh`
          - -> `ServletWebServerApplicationContext#onRefresh`

为什么这样设计?

AnnotationConfigServletWebServerApplicationContext.uml


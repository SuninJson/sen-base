# Spring Boot简述

​		Boot是引导的意思，Spring的引导，意味着Spring Boot就是服务于Spring框架的框架，基于约定优于配置的理念，让Spring能够更加快速的启动，提高开发人员对Spring的使用效率



## 一、什么是约定优于配置

​		约定优于配置又称为约定大于配置，但并不意味着无配置，只是基于约定使用默认的环境去减少大部分非必要的配置，当默认配置不符合开发人员需求时，开发人员仅需更改少量配置，即可满足需求，例如当数据库中有一个`sys_user`表，那我们就约定代码中具有一个`SysUser`实体，仅当该约定不满足我们的需求的时候，例如我们需要一个`SystemUser`实体时，我们仅需标注该实体对应的数据库表为`sys_user`

​		在Spring Boot当中有许多的starter包，当项目引入`spring-boot-starter-web`时，Spring Boot就约定了这是一个Web项目，当这是一个Web项目，就约定了开发人员就需要Spring MVC等框架的支持，Spring Boot就会自动装配Spring MVC的类与配置

## 二、@SpringBootApplication

​		`@SpringBootApplication`是Spring Boot用于标注启动类的注解，其由下列注解组成：

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {...}
```

### @Target(ElementType.TYPE)

​		`@Target`表示注解的作用目标，其值为`ElementType.TYPE`表示`@SpringBootApplication`可以作用于接口、方法、枚举以及注解

### @Retention(RetentionPolicy.RUNTIME)

​		Retention汉译为保留，顾名思义该注解表示，被标注的代码会被保留到什么时候，其值为`RetentionPolicy.RUNTIME`表示`@SpringBootApplication`在运行时会被JVM保留，可以通过反射获取到它的值

### @Documented

​		Documented汉译为文档，表示@SpringBootApplication被包含在Javadoc当中

### @Inherited

 		@Inherited表示子类可以继承父类中的`@SpringBootApplication`注解

### @SpringBootConfiguration

​		该注解表明被@SpringBootApplication注解标注的类表示这个类是一个为Spring提供引导程序的类，由于`@SpringBootConfiguration`被`@Configuration`标注，所以被`@SpringBootConfiguration`所标注的类相当于被`@Configuration`所标注，该类中可以定义被`@Bean`标注的方法，其方法返回的实例会交给Spring容器进行管理，其容器通常是`AnnotationConfigApplicationContext`或`AnnotationConfigWebApplicationContext`，这两个类都实现了`AnnotationConfigRegistry`接口。

​		同时`@Configuration`也被元注解`@Compent`所标注，因此被`@SpringApplicationContext`所标注的类也会被扫描组件扫描并注入到Spring容器当中

### @EnableAutoConfiguration

被`@EnableAutoConfiguration`所标注的类、接口、注解或方法具备Spring的自动装配功能，例如如果项目中含有tomcat-embedded.jar，标注上该注解就会帮你自动装配`TomcatServletWebServerFactory`到Spring容器中而不是装配一个`ServletWebServerFactory`

### @ComponentScan

​		`@ComponentScan`会去扫描指定包下被`@Configuration`所描述的类，若未指定包，则默认从被该注解标注的类的包开始扫描

​		原来XML配置中的`<context:component-scan>`具有`annotation-config`的属性，而`@ComponentScan`没有，这是因为Spring Boot基于约定优于配置，默认其对应的注入注解为`@Autowired`



## 三、Spring Boot自动装配的实现原理

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {...}
```

​		从上例代码中，我们可以发现`@EnableAutoConfiguration`具有两个特别注解`@AutoConfigurationPackage`

以及`@Import`，下面我们来了解下这两个注解。

### @AutoConfigurationPackage

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {}
```

该注解使用了`@Import`，意味着包含`@AutoConfigurationPackage`的类在初始化时会将`AutoconfigurationPackages.Registrar `装配到Spring容器

### @Import(AutoConfigurationImportSelector.class)

该注解表示将`AutoConfigurationImportSelector`导入Spring容器，`AutoConfigurationImportSelector`实现了继承自`ImportSelector`接口的`DeferredImportSelector`接口，则`AutoConfigurationImportSelector`具有`ImportSelector`的功能，即根据项目中一个或多个元注解判断需要导入哪些被`@Configuration`标注的类



在Spring Boot启动时，`SpringApplication#run(java.lang.String...)`会去调用`SpringApplication#refreshContext`，

`refreshContext`方法会解析启动类上的标签，

当`@EnableAutoConfiguration`被解析后会导入`AutoConfigurationImportSelector`

`AutoConfigurationImportSelector`类中有这个方法`SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());`

`SpringFactoriesLoader#loadFactoryNames`的作用就是读取jar包中的/项目中的META-INF/spring.factories文件



## 四、Spring中的spi机制的原理

SPI的全名为Service Provider Interface，为某个接口寻找服务实现的机制。
当服务的提供者，提供了服务接口的一种实现之后，在jar包的META-INF/services/目录里同时创建一个以服务接口命名的文件。该文件里就是实现该服务接口的具体实现类。而当外部程序装配这个模块的时候，就能通过该jar包META-INF/services/里的配置文件找到具体的实现类名，并装载实例化，完成模块的注入。通过这个约定，就不需要把服务放在代码中了，通过模块被装配的时候就可以发现服务类了。
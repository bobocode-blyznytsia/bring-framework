<p align="center">
    <img src="doc/bring.png" width="50%" alt="Bring Framework"/>
</p>

**Bring Framework** is an inversion of control and dependency injection framework. It allows you to declare what objects you need and then it processes configuration, creates required objects, sets dependencies and **brings objects that are ready to use**.

## Main features
- Configuration
    - Beans are configured by annotations
    - Configuration bean definition scannng
    - Bean definition scanning
- Context
    - Automatic bean creation
    - Dependency injection *(field injection)*
    - Method parameter injection *(configuration beans)*

## Provided annotations
| Annotation      | Target              | Description   |
| :-------------  |:--------------------| :-------------|
| `@Autowired`    | FIELD               |*Marks a field as to be autowired by Bring's dependency injection facilities*|
| `@Component`    | TYPE                |*Used to mark a class as a bean that can be managed by an IoC container*|
| `@Configuration`| TYPE                |*Indicates that a class declares one or more `@Bean` methods and may be processed by the Bring container*|
| `@Bean`         | TYPE                |*Used to indicate that a method produces a bean to be managed by the Bring container*|
| `@Qualifier`    | FIELD, PARAMETER    |*May be used on a field or parameter as a qualifier for candidate beans when autowiring*|

## Get started
### Configure you environment
You should setup such tools:
- [Git](https://git-scm.com/)
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.6.3 (or higher)](https://maven.apache.org/)

## Build from source
#### 1. Clone the repository
To create application with usage of the Bring Framework, you have to clone Bring source code repository [Bring Framework](https://github.com/bobocode-blyznytsia/bring-framework).
```shell
git clone git@github.com:bobocode-blyznytsia/bring-framework.git
cd bring-framework
```
#### 2. Build the Bring Framework locally by using Maven
```shell
mvn clean install
```
#### 3. Add the Bring Framework dependency into your project `pom.xml`
```xml
<dependencies>
    ...
    <dependency>
        <groupId>com.bobocode.blyznytsia</groupId>
        <artifactId>bring-framework</artifactId>
        <version>${bring-framework.version}</version>
    </dependency>
    ...
</dependencies>
```
The variable `bring-framework.version` is the current version of BringFramework
#### 4. Build your application by using maven
```shell
mvn clean package
```
## Quick start
There are 2 ways to start with **Bring Framework**
- Go over basic usage instruction
- Clone the <a href="https://github.com/bobocode-blyznytsia/bring-framework-demo" target="_blank"/>bring-framework-demo</a> project and check what is provided by Bring Framework
Choose what is more suitable for you.

### Basic usage
To start new application with **Bring Framework**, you should create the instance of **`BringApplication` class frist**. It accepts the **`packageName`**, which will be scanned for bean components. You can use `public static void main(String[] args)` method for that.


`BringApplication` has public method **`run()` which starts the specified package scanning and context initialization**. It returns the `ApplicationContext`.
```java
 public static void main(String[] args) {
        BringApplication bringApplication = new BringApplication("com.bobocode.bring.demo.app");
        ApplicationContext context = bringApplication.run();
 }
```

To guide `Bring Fremework` for wich beans should be created in your application, **you need to add an annotation on that**:
- You can use `@Configuration` and `@Bean` for configuration beans
- `@Component` for application beans

Let's start from `@Component` for simple case.
```java
@Component
public class WeekDayComponent {
    public String getWeekDay() {
        return LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
```

To **inject dependency** into your component you can use `@Autowired` annotation on the class field:
```java
@Component
public class HelloComponent {
    @Autowired
    private GreetingComponent greetingComponent;
    
    public void sayHello() {
        System.out.println("Hello, today is " + greetingComponent.getWeekDay());
    }
}
```

The `ApplicationContext` has api to retreive any bean from the context. For example you can use method `<T> T getBean(Class<T> beanType)`:
```java
 public static void main(String[] args) {
        BringApplication bringApplication = new BringApplication("com.bobocode.bring.demo.app");
        ApplicationContext context = bringApplication.run();
        HelloComponent helloComponent = context.getBean(HelloComponent.class);
        helloComponent.sayHello();
 }
```
The application should write into the log message with current weekday.

🏆 **Congratulations!!! You have started your first applicaton on Bring Framework***

### Configuration 
The Bring Framework provides ability to create and inject beans of external dependencies. You can use `@Configuration` annotation for your configuration class, and `@Bean` annotation on method.

```java
@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```

You are able to inject one configuration bean into another:

```java
@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    public FooBean fooBean(ObjectMapper objectMapper) {
        return FooBean(objectMapper);
    }
}
```

You can use `@Qualifier` annotation with name, in case when you have multiple configuration beans of the same type:
```java
@Configuration
public class AppConfig {
    @Bean
    public FooBean fooBeanFirst() {
        return new FooBean();
    }
    
    @Bean
    public FooBean fooBeanSecond() {
        return new OtherFooBean();
    }
    
    @Bean
    public BarBean barBean(@Qualifier("fooBeanFirst") FooBean fooBean) {
        return BarBean(fooBean);
    }
}
```


## Limitations
- Configuration beans can be injected into another configuration beans or into component beans
- The injection of component bean into configuration is not supported

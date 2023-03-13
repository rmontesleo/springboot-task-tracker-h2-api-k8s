# Configuration


## Initial Configuration from Spring Initializer

- [Configuration from Spring Boot Initializer](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.6.6&packaging=jar&jvmVersion=17&groupId=com.demo.todoapi&artifactId=springboot-todo-h2-api-container&name=todo-api-container&description=Basic%20TODO%20API%20demo%20with%20SpringBoot%20and%20H2%20using%20containers%20for%20Demos&packageName=com.demo.todoapi&dependencies=web,data-jpa,h2,lombok,devtools,validation)

## Set JAVA_HOME and MVN_HOME

```bash
export JAVA_HOME=/usr/lib/jvm/msopenjdk-17-amd64
export M2_HOME=/usr/local/apache-maven/apache-maven-3.8.5
export PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin
```

## Configure settings.xml
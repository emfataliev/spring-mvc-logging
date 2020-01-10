## Overview
This repository contains the lightweight library for logging HTTP requests and responses in Spring MVC

## Details

The following fields are logged for requests:
* request id
* method (GET, POST, PUT and etc)
* uri
* headers
* payload (for HTTP method which have body)

The following fields are logged for response:
* request id
* status
* headers
* payload

## Using 

Add maven dependency

```xml
<dependency>
  <groupId>com.github.emfataliev</groupId>
  <artifactId>spring-mvc-logging</artifactId>
  <version>1.0</version>    
</dependency>
```

Add spring configuration

```java
@Configuration
@ComponentScan("com.github.emfataliev")
public class MvcLoggingConfiguration {

    @Bean
    public MvcLoggingFilter mvcLoggingFilter(RequestId requestId) {
        // Pattern for logging requests */api/v2.0/*  
        String logEndpointsPattern = ".*\\/api\\/v2\\.0\\/.*";
        int logMessageMaxLength = 16384;
        String[] excludeLoggingHeaders = new String[]{HttpHeaders.AUTHORIZATION, HttpHeaders.COOKIE};
        return new MvcLoggingFilter(requestId, logMessageMaxLength, logEndpointsPattern, excludeLoggingHeaders);
    }
}
```

## Note

Use `RequestId` bean correctly, spring request scope is registered only for the web application requests. If the thread was instantiated outside web request, it does not have the required attributes in the ThreadLocal variables and hence it throws the exception.

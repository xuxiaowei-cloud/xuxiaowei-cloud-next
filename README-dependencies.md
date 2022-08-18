# dependencies

```
cloud.xuxiaowei.next:xuxiaowei-cloud-starter-parent:pom:0.0.1-SNAPSHOT


cloud.xuxiaowei.next:admin-server:jar:0.0.1-SNAPSHOT
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──de.codecentric:spring-boot-admin-starter-server:jar:3.0.0-M4:compile


cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT
│        └──cn.hutool:hutool-all:jar:5.8.5:provided
│        └──com.google.guava:guava:jar:31.1-jre:provided
│        └──com.fasterxml.jackson.core:jackson-databind:jar:2.13.3:provided
│        └──com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.13.3:provided
│        └──io.projectreactor:reactor-core:jar:3.5.0-M4:provided
│        └──org.springframework.security:spring-security-core:jar:6.0.0-M6:provided
│        └──org.springframework.security:spring-security-web:jar:6.0.0-M6:provided
│        └──org.apache.tomcat.embed:tomcat-embed-core:jar:10.0.22:provided
│        └──org.springframework.security:spring-security-oauth2-jose:jar:6.0.0-M6:provided
│        └──ch.qos.logback:logback-classic:jar:1.2.11:provided
│        └──org.springframework:spring-web:jar:6.0.0-M5:provided
│        └──commons-io:commons-io:jar:2.11.0:provided
│        └──org.dom4j:dom4j:jar:2.1.3:provided
│        └──org.projectlombok:lombok:jar:1.18.24:compile


cloud.xuxiaowei.next:cloud-commons-parent:pom:0.0.1-SNAPSHOT
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT
│        └──cn.hutool:hutool-all:jar:5.8.5:provided
│        └──org.springframework.cloud:spring-cloud-context:jar:4.0.0-M4:provided
│        └──org.springframework:spring-web:jar:6.0.0-M5:provided
│        └──org.apache.tomcat.embed:tomcat-embed-core:jar:10.0.22:provided
│        └──org.springframework.session:spring-session-core:jar:3.0.0-M3:provided
│        └──org.springframework.security:spring-security-web:jar:6.0.0-M6:provided
│        └──org.springframework.security:spring-security-config:jar:6.0.0-M6:provided
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-redis:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-data-redis:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-json:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-idempotent:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-redis:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-loadbalancer:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework:spring-web:jar:6.0.0-M5:compile
│        └──org.springframework.cloud:spring-cloud-starter-loadbalancer:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-mybatis:jar:0.0.1-SNAPSHOT
│        └──org.mybatis.spring.boot:mybatis-spring-boot-starter:jar:2.3.0-SNAPSHOT:compile
│        └──com.baomidou:mybatis-plus-boot-starter:jar:3.5.2:compile
│        └──com.baomidou:dynamic-datasource-spring-boot-starter:jar:3.5.1:compile
│        └──p6spy:p6spy:jar:3.9.1:compile
│        └──com.baomidou:mybatis-plus-generator:jar:3.5.3:test
│        └──org.springframework.boot:spring-boot-starter-velocity:jar:1.4.7.RELEASE:test
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M4:compile
│        └──mysql:mysql-connector-java:jar:8.0.29:runtime
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──com.baomidou:mybatis-plus-extension:jar:3.5.2:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-log:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-mybatis:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.cloud:spring-cloud-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-oauth2:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-mybatis:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-oauth2-resource-server:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-openfeign:jar:0.0.1-SNAPSHOT
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-openfeign:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-session-redis:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-redis:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.session:spring-session-data-redis:jar:3.0.0-M3:compile
│        └──org.apache.tomcat.embed:tomcat-embed-core:jar:10.0.22:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-validation:jar:0.0.1-SNAPSHOT
│        └──com.google.guava:guava:jar:31.1-jre:compile
│        └──org.springframework.boot:spring-boot-starter-validation:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:provided
│        └──com.fasterxml.jackson.core:jackson-databind:jar:2.13.3:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.commons:cloud-starter-system:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-log:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-validation:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-redis:jar:0.0.1-SNAPSHOT:compile
│        └──cn.hutool:hutool-all:jar:5.8.5:compile
│        └──commons-io:commons-io:jar:2.11.0:compile
│        └──org.springframework.boot:spring-boot-starter-json:jar:3.0.0-M4:provided
│        └──com.fasterxml.jackson.core:jackson-annotations:jar:2.13.3:provided
│        └──org.apache.tomcat.embed:tomcat-embed-core:jar:10.0.22:provided
│        └──org.springframework.security:spring-security-core:jar:6.0.0-M6:provided
│        └──org.springframework:spring-webmvc:jar:6.0.0-M5:compile
│        └──org.springframework.boot:spring-boot-starter-aop:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-mail:jar:3.0.0-M4:compile
│        └──org.springframework.security:spring-security-oauth2-jose:jar:6.0.0-M6:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile


cloud.xuxiaowei.next.example:oauth2-client:jar:0.0.1-SNAPSHOT
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M3:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M3:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile


cloud.xuxiaowei.next.example:example-parent:pom:0.0.1-SNAPSHOT


cloud.xuxiaowei.next:gateway:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-loadbalancer:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-log:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-mybatis:jar:0.0.1-SNAPSHOT:compile
│        └──cn.hutool:hutool-all:jar:5.8.5:compile
│        └──io.xuxiaowei.security.next:spring-security-oauth2-authorization-server:jar:0.3.1:compile
│        └──org.springframework.boot:spring-boot-starter-oauth2-resource-server:jar:3.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-gateway:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile


cloud.xuxiaowei.next:passport-ui:jar:0.0.1-SNAPSHOT


cloud.xuxiaowei.next:passport:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next:passport-ui:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-system:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-session-redis:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-oauth2:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-loadbalancer:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-thymeleaf:jar:3.0.0-M4:compile
│        └──io.xuxiaowei.security.next:spring-security-oauth2-authorization-server:jar:0.3.1:compile
│        └──cn.com.xuxiaowei.boot.next:spring-boot-starter-wechat-miniprogram:jar:0.0.1-alpha.2:compile
│        └──p6spy:p6spy:jar:3.9.1:compile
│        └──cn.hutool:hutool-all:jar:5.8.5:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile


cloud.xuxiaowei.next:resource-services-parent:pom:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-system:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-oauth2:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test


cloud.xuxiaowei.next:canal:jar:0.0.1-SNAPSHOT
│        └──com.alibaba.otter:canal.client:jar:1.1.6:compile
│        └──com.alibaba.otter:canal.protocol:jar:1.1.6:compile
│        └──mysql:mysql-connector-java:jar:8.0.29:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-system:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-oauth2:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test


cloud.xuxiaowei.next:user:jar:0.0.1-SNAPSHOT
│        └──org.apache.commons:commons-lang3:jar:3.12.0:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-system:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-oauth2:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test


cloud.xuxiaowei.next:webservice:jar:0.0.1-SNAPSHOT
│        └──org.springframework.boot:spring-boot-starter-web-services:jar:3.0.0-M4:compile
│        └──com.sun.xml.ws:httpspi-servlet:jar:4.0.0:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-system:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-oauth2:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test


cloud.xuxiaowei.next:websocket:jar:0.0.1-SNAPSHOT
│        └──org.springframework.boot:spring-boot-starter-websocket:jar:3.0.0-M4:compile
│        └──com.alibaba:fastjson:jar:2.0.11:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-system:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-oauth2:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test


cloud.xuxiaowei.next:wechat-miniprogram:jar:0.0.1-SNAPSHOT
│        └──com.github.binarywang:wx-java-miniapp-spring-boot-starter:jar:4.3.0:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-system:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-oauth2:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M4:test


cloud.xuxiaowei.next:ui:jar:0.0.1-SNAPSHOT


cloud.xuxiaowei.next:xxl-job-admin:jar:0.0.1-SNAPSHOT
│        └──com.xuxueli:xxl-job-core:jar:2.3.1:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M4:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M4:compile
│        └──p6spy:p6spy:jar:3.9.1:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-freemarker:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-mail:jar:3.0.0-M4:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M4:compile
│        └──org.mybatis.spring.boot:mybatis-spring-boot-starter:jar:2.3.0-SNAPSHOT:compile
│        └──mysql:mysql-connector-java:jar:8.0.29:runtime
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M4:compile
│        └──org.projectlombok:lombok:jar:1.18.24:compile
```

# dependencies

```
cloud.xuxiaowei.next:xuxiaowei-cloud-starter-parent:pom:0.0.1-SNAPSHOT


cloud.xuxiaowei.next:admin-server:jar:0.0.1-SNAPSHOT
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M2:compile
│        └──de.codecentric:spring-boot-admin-starter-server:jar:3.0.0-M1:compile


cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT
│        └──com.fasterxml.jackson.core:jackson-databind:jar:2.13.2:provided
│        └──com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.13.2:provided
│        └──io.projectreactor:reactor-core:jar:3.4.16:provided
│        └──org.springframework.security:spring-security-core:jar:6.0.0-M3:provided
│        └──org.springframework.security:spring-security-web:jar:6.0.0-M3:provided
│        └──org.springframework.security:spring-security-oauth2-jose:jar:6.0.0-M3:provided
│        └──ch.qos.logback:logback-classic:jar:1.2.11:provided
│        └──org.apache.tomcat.embed:tomcat-embed-core:jar:10.0.18:provided
│        └──org.springframework:spring-web:jar:6.0.0-M3:provided
│        └──org.projectlombok:lombok:jar:1.18.22:compile


cloud.xuxiaowei.next:cloud-commons-parent:pom:0.0.1-SNAPSHOT
│        └──org.springframework.boot:spring-boot-starter:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M2:compile


cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next:utils:jar:0.0.1-SNAPSHOT:compile
│        └──cn.hutool:hutool-all:jar:5.8.3:provided
│        └──org.springframework.cloud:spring-cloud-context:jar:4.0.0-M2:provided
│        └──org.springframework:spring-web:jar:6.0.0-M3:provided
│        └──org.apache.tomcat.embed:tomcat-embed-core:jar:10.0.18:provided
│        └──org.springframework.session:spring-session-core:jar:3.0.0-M1:provided
│        └──org.springframework.security:spring-security-web:jar:6.0.0-M3:provided
│        └──org.springframework.security:spring-security-config:jar:6.0.0-M3:provided
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M2:test
│        └──org.springframework.boot:spring-boot-starter:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M2:compile


cloud.xuxiaowei.next.commons:cloud-starter-redis:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-data-redis:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-json:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M2:test
│        └──org.springframework.boot:spring-boot-starter:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M2:compile


cloud.xuxiaowei.next.commons:cloud-starter-session-redis:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-redis:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.session:spring-session-data-redis:jar:3.0.0-M1:compile
│        └──org.apache.tomcat.embed:tomcat-embed-core:jar:10.0.18:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M2:test
│        └──org.springframework.boot:spring-boot-starter:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M2:compile


cloud.xuxiaowei.next:authorization-server:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next.commons:cloud-starter-session-redis:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M2:compile
│        └──io.xuxiaowei.security.next:spring-security-oauth2-authorization-server:jar:0.3.0-SNAPSHOT:compile
│        └──p6spy:p6spy:jar:3.9.1:compile
│        └──cn.hutool:hutool-all:jar:5.8.3:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-loadbalancer:jar:4.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M2:compile
│        └──mysql:mysql-connector-java:jar:8.0.28:compile
│        └──org.projectlombok:lombok:jar:1.18.22:compile


cloud.xuxiaowei.next.commons:cloud-starter-loadbalancer:jar:0.0.1-SNAPSHOT
│        └──org.springframework:spring-web:jar:6.0.0-M3:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-core:jar:0.0.1-SNAPSHOT:compile
│        └──org.springframework.cloud:spring-cloud-starter-loadbalancer:jar:4.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M2:test
│        └──org.springframework.boot:spring-boot-starter:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-configuration-processor:jar:3.0.0-M2:compile


cloud.xuxiaowei.next:gateway:jar:0.0.1-SNAPSHOT
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-gateway:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-loadbalancer:jar:4.0.0-M2:compile


cloud.xuxiaowei.next:passport-ui:jar:0.0.1-SNAPSHOT


cloud.xuxiaowei.next:passport:jar:0.0.1-SNAPSHOT
│        └──cloud.xuxiaowei.next:passport-ui:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-session-redis:jar:0.0.1-SNAPSHOT:compile
│        └──cloud.xuxiaowei.next.commons:cloud-starter-loadbalancer:jar:0.0.1-SNAPSHOT:compile
│        └──cn.hutool:hutool-all:jar:5.8.3:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-config:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-consul-discovery:jar:4.0.0-M2:compile
│        └──org.springframework.cloud:spring-cloud-starter-bootstrap:jar:4.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-actuator:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-jdbc:jar:3.0.0-M2:compile
│        └──mysql:mysql-connector-java:jar:8.0.28:compile
│        └──p6spy:p6spy:jar:3.9.1:compile
│        └──org.springframework.boot:spring-boot-starter-thymeleaf:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-oauth2-resource-server:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-security:jar:3.0.0-M2:compile
│        └──org.springframework.boot:spring-boot-starter-web:jar:3.0.0-M2:compile
│        └──org.projectlombok:lombok:jar:1.18.22:compile
│        └──org.springframework.boot:spring-boot-starter-test:jar:3.0.0-M2:test
│        └──io.projectreactor:reactor-test:jar:3.4.16:test
```

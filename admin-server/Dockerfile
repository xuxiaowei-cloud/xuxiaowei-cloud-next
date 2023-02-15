FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/admin-server-0.0.1-SNAPSHOT.jar admin-server-0.0.1-SNAPSHOT.jar

EXPOSE 1201

ENV TZ=Asia/Shanghai

CMD java -jar admin-server-0.0.1-SNAPSHOT.jar

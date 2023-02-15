FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/websocket-0.0.1-SNAPSHOT.jar websocket-0.0.1-SNAPSHOT.jar

EXPOSE 1801

ENV TZ=Asia/Shanghai

CMD java -jar websocket-0.0.1-SNAPSHOT.jar

FROM xuxiaoweicomcn/dragonwell17:17.0.6.0.6.9

WORKDIR /home

COPY target/websocket-0.0.1-SNAPSHOT.jar websocket-0.0.1-SNAPSHOT.jar

EXPOSE 1801

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

CMD java -jar websocket-0.0.1-SNAPSHOT.jar

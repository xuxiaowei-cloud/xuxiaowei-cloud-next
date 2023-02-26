FROM xuxiaoweicomcn/dragonwell17:17.0.6.0.6.9

WORKDIR /home

COPY target/admin-server-0.0.1-SNAPSHOT.jar admin-server-0.0.1-SNAPSHOT.jar

EXPOSE 1201

ENV TZ=Asia/Shanghai

CMD java -jar admin-server-0.0.1-SNAPSHOT.jar

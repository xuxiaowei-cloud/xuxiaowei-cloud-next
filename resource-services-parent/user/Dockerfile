FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/user-0.0.1-SNAPSHOT.jar user-0.0.1-SNAPSHOT.jar

EXPOSE 1701

ENV TZ=Asia/Shanghai

CMD java -jar user-0.0.1-SNAPSHOT.jar

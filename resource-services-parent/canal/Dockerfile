FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/canal-0.0.1-SNAPSHOT.jar canal-0.0.1-SNAPSHOT.jar

EXPOSE 1601

ENV TZ=Asia/Shanghai

CMD java -jar canal-0.0.1-SNAPSHOT.jar

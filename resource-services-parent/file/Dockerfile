FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/file-0.0.1-SNAPSHOT.jar file-0.0.1-SNAPSHOT.jar

EXPOSE 2301

ENV TZ=Asia/Shanghai

CMD java -jar file-0.0.1-SNAPSHOT.jar

FROM xuxiaoweicomcn/dragonwell17:17.0.6.0.6.9

WORKDIR /home

COPY target/file-0.0.1-SNAPSHOT.jar file-0.0.1-SNAPSHOT.jar

EXPOSE 2301

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

CMD java -jar file-0.0.1-SNAPSHOT.jar

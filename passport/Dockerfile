FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/passport-0.0.1-SNAPSHOT.jar passport-0.0.1-SNAPSHOT.jar

EXPOSE 1401

ENV TZ=Asia/Shanghai

CMD java -jar passport-0.0.1-SNAPSHOT.jar

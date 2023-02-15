FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/master-data-0.0.1-SNAPSHOT.jar master-data-0.0.1-SNAPSHOT.jar

EXPOSE 2201

ENV TZ=Asia/Shanghai

CMD java -jar master-data-0.0.1-SNAPSHOT.jar

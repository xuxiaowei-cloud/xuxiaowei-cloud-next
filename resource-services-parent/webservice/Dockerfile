FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/webservice-0.0.1-SNAPSHOT.jar webservice-0.0.1-SNAPSHOT.jar

EXPOSE 1901

ENV TZ=Asia/Shanghai

CMD java -jar webservice-0.0.1-SNAPSHOT.jar

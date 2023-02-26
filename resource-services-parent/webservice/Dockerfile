FROM xuxiaoweicomcn/dragonwell17:17.0.6.0.6.9

WORKDIR /home

COPY target/webservice-0.0.1-SNAPSHOT.jar webservice-0.0.1-SNAPSHOT.jar

EXPOSE 1901

ENV TZ=Asia/Shanghai

CMD java -jar webservice-0.0.1-SNAPSHOT.jar

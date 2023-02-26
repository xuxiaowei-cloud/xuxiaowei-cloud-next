FROM xuxiaoweicomcn/dragonwell17:17.0.6.0.6.9

WORKDIR /home

COPY target/wechat-offiaccount-0.0.1-SNAPSHOT.jar wechat-offiaccount-0.0.1-SNAPSHOT.jar

EXPOSE 2101

ENV TZ=Asia/Shanghai

CMD java -jar wechat-offiaccount-0.0.1-SNAPSHOT.jar

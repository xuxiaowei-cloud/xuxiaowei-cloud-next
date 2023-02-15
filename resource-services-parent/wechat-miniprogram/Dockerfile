FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/wechat-miniprogram-0.0.1-SNAPSHOT.jar wechat-miniprogram-0.0.1-SNAPSHOT.jar

EXPOSE 2001

ENV TZ=Asia/Shanghai

CMD java -jar wechat-miniprogram-0.0.1-SNAPSHOT.jar

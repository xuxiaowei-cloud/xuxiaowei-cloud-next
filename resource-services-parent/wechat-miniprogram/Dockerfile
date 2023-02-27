FROM xuxiaoweicomcn/dragonwell17:17.0.6.0.6.9

WORKDIR /home

COPY target/wechat-miniprogram-0.0.1-SNAPSHOT.jar wechat-miniprogram-0.0.1-SNAPSHOT.jar

EXPOSE 2001

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

CMD java -jar wechat-miniprogram-0.0.1-SNAPSHOT.jar

FROM alibabadragonwell/dragonwell:17-anolis

WORKDIR /home

COPY target/gateway-0.0.1-SNAPSHOT.jar gateway-0.0.1-SNAPSHOT.jar

EXPOSE 1101

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

CMD java -jar gateway-0.0.1-SNAPSHOT.jar

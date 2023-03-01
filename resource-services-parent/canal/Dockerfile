FROM alibabadragonwell/dragonwell:17-anolis

WORKDIR /home

COPY target/canal-0.0.1-SNAPSHOT.jar canal-0.0.1-SNAPSHOT.jar

EXPOSE 1601

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

CMD java -jar canal-0.0.1-SNAPSHOT.jar

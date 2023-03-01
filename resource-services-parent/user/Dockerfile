FROM alibabadragonwell/dragonwell:17-anolis

WORKDIR /home

COPY target/user-0.0.1-SNAPSHOT.jar user-0.0.1-SNAPSHOT.jar

EXPOSE 1701

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

CMD java -jar user-0.0.1-SNAPSHOT.jar

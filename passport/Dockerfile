FROM alibabadragonwell/dragonwell:17-anolis

WORKDIR /home

COPY target/passport-0.0.1-SNAPSHOT.jar passport-0.0.1-SNAPSHOT.jar

EXPOSE 1401

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

CMD java -jar passport-0.0.1-SNAPSHOT.jar

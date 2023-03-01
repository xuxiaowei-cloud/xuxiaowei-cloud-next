FROM alibabadragonwell/dragonwell:17-anolis

WORKDIR /home

COPY target/master-data-0.0.1-SNAPSHOT.jar master-data-0.0.1-SNAPSHOT.jar

EXPOSE 2201

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

CMD java -jar master-data-0.0.1-SNAPSHOT.jar

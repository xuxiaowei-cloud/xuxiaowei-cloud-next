FROM xuxiaoweicomcn/dragonwell17:17.0.6.0.6.9

WORKDIR /home

COPY target/xxl-job-admin-0.0.1-SNAPSHOT.jar xxl-job-admin-0.0.1-SNAPSHOT.jar

EXPOSE 5101

ENV TZ=Asia/Shanghai

CMD java -jar xxl-job-admin-0.0.1-SNAPSHOT.jar

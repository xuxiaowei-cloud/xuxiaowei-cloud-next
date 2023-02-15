FROM xuxiaoweicomcn/dragonwell8:8.13.14-Standard

WORKDIR /home

COPY target/xxl-job-admin-0.0.1-SNAPSHOT.jar xxl-job-admin-0.0.1-SNAPSHOT.jar

EXPOSE 5101

ENV TZ=Asia/Shanghai

CMD java -jar xxl-job-admin-0.0.1-SNAPSHOT.jar

FROM docker.io/library/nginx:1.25.1

WORKDIR /home

COPY target/classes/static /usr/share/nginx/html
COPY target/classes/git.json /usr/share/nginx/git.json

EXPOSE 80

ENV TZ=Asia/Shanghai

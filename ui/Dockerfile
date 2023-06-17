# FROM docker.io/library/nginx:1.25.1

# 使用 极狐GitLab 加速镜像构建，网站：https://jihulab.com/xuxiaowei-cloud/xuxiaowei-cloud-next/container_registry
FROM registry.jihulab.com/xuxiaowei-cloud/xuxiaowei-cloud-next/alibabadragonwell/nginx:1.25.1

WORKDIR /home

COPY target/classes/static /usr/share/nginx/html
COPY target/classes/git.json /usr/share/nginx/git.json

EXPOSE 80

ENV TZ=Asia/Shanghai

# FROM docker.io/alibabadragonwell/dragonwell:17-anolis

# 使用 极狐GitLab 加速镜像构建，网站：https://jihulab.com/xuxiaowei-cloud/xuxiaowei-cloud-next/container_registry
FROM registry.jihulab.com/xuxiaowei-cloud/xuxiaowei-cloud-next/alibabadragonwell/dragonwell:17-anolis

WORKDIR /home

# 使用 pom.xml 中的 buildArgs 获取 groupId、artifactId、version 来确定 jar 包名称
# 使用 GitLab Runner 中的 CI_PIPELINE_ID、CI_JOB_ID 来确定流水线ID（CI_PIPELINE_ID）与作业ID（CI_JOB_ID）

ARG GROUP_ID=${GROUP_ID}
ARG ARTIFACT_ID=${ARTIFACT_ID}
ARG VERSION=${VERSION}
ARG CI_PIPELINE_ID=${CI_PIPELINE_ID}
ARG CI_JOB_ID=${CI_JOB_ID}

RUN echo $GROUP_ID
RUN echo $ARTIFACT_ID
RUN echo $VERSION
RUN echo $CI_PIPELINE_ID
RUN echo $CI_JOB_ID

COPY target/$ARTIFACT_ID-$VERSION.jar app.jar

EXPOSE 1601

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

ENV GROUP_ID=${GROUP_ID}
ENV ARTIFACT_ID=${ARTIFACT_ID}
ENV VERSION=${VERSION}
ENV CI_PIPELINE_ID=${CI_PIPELINE_ID}
ENV CI_JOB_ID=${CI_JOB_ID}

CMD java -jar app.jar

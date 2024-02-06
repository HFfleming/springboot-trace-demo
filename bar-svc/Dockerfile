#基于官方的Maven镜像
FROM maven:3.8.7-openjdk-18-slim AS build

#将本地代码复制到Docker容器中的 /usr/src/app 目录下
COPY src /usr/src/bar-app/src
COPY pom.xml /usr/src/bar-app
COPY opentelemetry-javaagent.jar /usr/src/bar-app

# 在容器的 /usr/src/bar-app 目录下，运行mvn clean package 命令，构建项目
RUN mvn -f /usr/src/bar-app/pom.xml clean package

# 使用官方的openjdk 镜像作为基础镜像
FROM  openjdk:19-jdk-slim

# 将打包生成的jar文件复制到容器中
COPY --from=build /usr/src/bar-app/target/*.jar  /usr/app/bar-app.jar
COPY --from=build /usr/src/bar-app/opentelemetry-javaagent.jar /usr/app/opentelemetry-javaagent.jar


# 声明服务运行在8080端口
EXPOSE 8080

# 通过环境变量的形式配置java agent并且通过环境变量传递配置属性
ENV JAVA_TOOL_OPTIONS="-javaagent:/usr/app/opentelemetry-javaagent.jar"
# 服务的k8s servicename
ENV OTEL_SERVICE_NAME="bar-svc"
# 使用的是otlp协议的导出器
ENV OTEL_TRACES_EXPORTER="otlp"
# 关闭java agent的指标 otlp导出器
ENV OTEL_METRICS_EXPORTER="none"
## 关闭java agent的日志 otlp导出器
ENV OTEL_LOGS_EXPORTER="none"
# 指定OTLP导出器的端点，跨ns的场景下建议写上otel的ns
ENV OTEL_EXPORTER_OTLP_ENDPOINT="http://opentelemetry-collector.tracing.svc.cluster.local:4317"

#指定docker容器的启动命令
ENTRYPOINT ["java", "-jar","/usr/app/bar-app.jar"]
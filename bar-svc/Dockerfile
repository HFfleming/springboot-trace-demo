#基于官方的Maven镜像
FROM maven:3.8.7-openjdk-18-slim AS build

#将本地代码复制到Docker容器中的 /usr/src/app 目录下
COPY src /usr/src/bar-app/src
COPY pom.xml /usr/src/bar-app

# 在容器的 /usr/src/bar-app 目录下，运行mvn clean package 命令，构建项目
RUN mvn -f /usr/src/bar-app/pom.xml clean package

# 使用官方的openjdk 镜像作为基础镜像
FROM  openjdk:19-jdk-slim

# 将打包生成的jar文件复制到容器中
COPY --from=build /usr/src/bar-app/target/*.jar  /usr/app/bar-app.jar

# 声明服务运行在8080端口
EXPOSE 8080

#指定docker容器的启动命令
ENTRYPOINT ["java", "-jar","/usr/app/bar-app.jar"]
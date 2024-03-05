## Dockerfile
#
## jdk17 Image Start
#FROM openjdk:17
#
## 인자 설정 - JAR_File
#ARG JAR_FILE=build/libs/*.jar
#
## jar 파일 복제
#COPY build/libs/*.jar mung.jar
#
## 인자 설정 부분과 jar 파일 복제 부분 합쳐서 진행해도 무방
##COPY build/libs/*.jar app.jar
#
## 실행 명령어
#ENTRYPOINT ["java", "-jar", "/mung.jar"]

FROM gradle:7.6-jdk17-alpine as builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build
RUN gradle build -x test --parallel

# APP
FROM openjdk:17.0-slim
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

# root 대신 nobody 권한으로 실행
USER nobody
ENTRYPOINT [                                                \
    "java",                                                 \
    "-jar",                                                 \
    "-Djava.security.egd=file:/dev/./urandom",              \
    "-Dsun.net.inetaddr.ttl=0",                             \
    "app.jar"              \
]
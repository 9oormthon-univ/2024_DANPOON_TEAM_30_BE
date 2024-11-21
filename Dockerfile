FROM openjdk:17

# JAR 파일 경로
ARG JAR_FILE=build/libs/ready_action-0.0.1-SNAPSHOT.jar

# application.yml 파일 경로
ARG YML_FILE=src/main/resources/application.yml

# JAR 파일과 application.yml 파일을 컨테이너로 복사
COPY ${JAR_FILE} app.jar
COPY ${YML_FILE} /app/application.yml

# 컨테이너 실행 시 JAR 파일을 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

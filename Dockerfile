FROM openjdk:17
#COPY /build/libs/project-0.0.1-SNAPSHOT.jar project-0.0.1-SNAPSHOT.jar
VOLUME /temp
EXPOSE 8080
ARG JAR_FILE=build/libs/employee-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} employee.jar
ENTRYPOINT ["java","-jar","employee.jar"]
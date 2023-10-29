#FROM openjdk:18-jdk-slim
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#WORKDIR /app
#ENTRYPOINT ["java","-jar","/app.jar"]

# ngix
FROM nginx:1.21.3-alpine
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
FROM openjdk:17-oracle
EXPOSE 8080
COPY /target/customerService.jar customerService.jar
ENTRYPOINT ["java","-jar","/customerService.jar"]
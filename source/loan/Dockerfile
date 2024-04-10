#Start with base image containing java runtime 

FROM openjdk:17

# Information of owner or maintainer of image 

MAINTAINER namelbank

# Add the application's jar to the container

COPY target/loan-0.0.1-SNAPSHOT.jar loan-0.0.1-SNAPSHOT.jar

#Execute the applicatoin : target/account-0.0.1-SNAPSHOT.jar (array of string) 

ENTRYPOINT [ "java","-jar","/loan-0.0.1-SNAPSHOT.jar" ]







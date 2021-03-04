FROM adoptopenjdk/openjdk11:latest
COPY build/libs/notes-0.0.1-SNAPSHOT.jar /srv
WORKDIR /srv
CMD java -jar /srv/notes-0.0.1-SNAPSHOT.jar


#ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo:27017/abernathy","-jar","notes-0.0.1-SNAPSHOT.jar"]

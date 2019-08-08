FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY target/mn-hello-world*.jar mn-hello-world.jar
EXPOSE 8080
CMD java -Xmx2G -XX:+UnlockExperimentalVMOptions  -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar mn-hello-world.jar

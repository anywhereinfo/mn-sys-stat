FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY target/mn-hello-world*.jar mn-hello-world.jar
EXPOSE 8080
CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:ConcGCThreads=1 -XX:ParallelGCThreads=2 -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar mn-hello-world.jar
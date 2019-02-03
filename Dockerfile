FROM ubuntu:xenial

RUN apt-get update && apt-get install -y openjdk-8-jdk maven
ADD . /usr/local/protobuff-sample
RUN cd /usr/local/protobuff-sample && mvn clean package
ENV folder /usr/local/protobuff-sample
ENV port 8090
ENV timeout 86400

CMD ["sh", "-c", "java -jar /usr/local/protobuff-sample/target/protobuff-sample-1.0-SNAPSHOT-jar-with-dependencies.jar -p ${port} -t ${timeout} -f ${folder}"]

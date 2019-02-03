# Protobuff Web Server Sample

This application is a web service run on Jetty Connectors with Jersey that saves received data in protobuff format to a rolling file. The server has one endpoint '/simpleobject' that receives a JSON with the following schema {"id": <integer>, "name": <string>}. In order to reduce the bottleneck that the file writer provokes, the application uses a Producer-Consumer pattern so that the thread that writes to the file reads from a queue filled by the service requests.

## How to install
The project uses maven and the maven assembly project, so you need to install Java 8 or higher and maven. Then just run this command to build a uber jar:
    
    mvn clean package

Also you can install and deploy a docker container using docker compose:

    docker-compose up

In both deployments Maven will take care of running the tests before the installation so that it won't deploy the application if something goes wrong.

## How to use
In case you are not using the docker container you need to run the application. For that you can either run the JAR directly or use the run.sh script.

The JAR has the following arguments:

-f,&nbsp;&nbsp;--folder <arg>&nbsp;&nbsp;&nbsp;&nbsp;Folder where the rolling files will be saved.
 
-p,&nbsp;&nbsp;--port <arg>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Port the server will listen to. Default 8090.
 
-t,&nbsp;&nbsp;--timeout <arg>&nbsp;Time it takes to rollover files in seconds. Default 1 day.

So you need to run the following command:

    java -jar target/protobuff-sample-1.0-SNAPSHOT-jar-with-dependencies.jar -p <port> -f <folder> -t <timeout>

The run.sh script takes the port, folder and rolling file timeout as arguments.
    
    ./run.sh <port> <folder> <rolling-timeout>
    
## Endpoints

- /simpleobject [POST]

Receives a JSON with the following schema: {"id": <integer>, "name": <string>}. If the received data is not in the desired schema nor is a JSON an error will be returned. 

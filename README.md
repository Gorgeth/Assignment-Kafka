# README

## How-to-run
- The project is built using maven
  - Use "mvn clean install" to build code, generate avro classes, and run unit tests
- Run the docker compose yaml file in the RESTApplication module, located at "src/main/resources/docker-compose.yaml"
- Execute the generated jar files for the two modules 
- Swagger for the REST application is available at "localhost:8080/swagger-ui.html"
- Heartbeat endpoint to check life signs is available at "localhost:8080/heartbeat"
- The POST endpoint for submission of CustomerAddress is available at "localhost:8080/customerAddress"
  - The endpoint consumes JSON payload, with the model defined under com.cegal.model.CustomerAddress
- Customer address' that have been submitted to the endpoint are consumed, and written to an .avro file
  - This file can be found in the output/ folder in the projects parent folder

## Assumptions/Explanations/Excuses
- Application requires docker and docker compose to run the zookeeper and kafka images
  - Assumes that user either has the images available or ability to download them 
- User either has a local docker deamon, or can modify the application.properties in the two modules
- Port 8080 and 8081 are available
- I was unable to get the avro model to work directly with the swagger documentation, as such I wrote a simple jackson object with some annotations.
- Topics in kafka are set to be auto generated on submission, this was purely out of laziness
- The AVRO (de)serializer inherits both as it made testing it easier
- I did not do any exception testing as I did not add any exception handling, except failure states
- spring.mvc.pathmatch.matching-strategy=ant_path_matcher was required due to what is apparently a bug in the path matcher

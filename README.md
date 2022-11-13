Assignment assumption

    - Split producer and consumer
        - Makes integration test difficult
        - Ports: 8080 and 8081 respectively 
    - Swagger version 2
    - Maven build system
        - Build the entire assignment as a single project, but run each module separately
    - Auto generated topics in kafka - laziness

Properties

    - spring.mvc.pathmatch.matching-strategy=ant_path_matcher
            - This was required due to what is apparently a bug in the path matcher


Questions:

    Why Avro, and not something like jackson
    Two different microservices, or can I move the consumer into the producer
        - Makes an actual integration test possible

TODO
    
    - Complicate the customerAddress object - long
    - Write unit tests 
    - Add comments
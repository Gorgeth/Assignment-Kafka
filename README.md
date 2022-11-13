Assignment assumption

	- Default profile
        - port 8080
    - Swagger version 2
    - Maven build system
        - Build the entire assignment as a single application, but run each module separately

Properties

    - spring.mvc.pathmatch.matching-strategy=ant_path_matcher
            - This was required due to what is apparently a bug in the path matcher


Questions:
    Why Avro, and not something like jackson
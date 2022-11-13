package com.cegal.avro;

import org.apache.avro.Schema;
import org.apache.avro.compiler.specific.SpecificCompiler;

import java.io.File;
import java.io.IOException;


public class AvroClassGenerator {

  private static final String SCHEMA_CUSTOMER_ADDRESS = "src/main/resources/schema/CustomerAddress.avsc";


  public void generateClasses() throws IOException {
    SpecificCompiler compiler = new SpecificCompiler(new Schema.Parser()
                    .parse(new File(SCHEMA_CUSTOMER_ADDRESS)));

    compiler.compileToDestination(new File("src/main/resources"), new File("src/main/java"));
  }
}

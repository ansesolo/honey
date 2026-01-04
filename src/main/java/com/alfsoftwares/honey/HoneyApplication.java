package com.alfsoftwares.honey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@SpringBootApplication
@Modulithic(
    sharedModules = {"com.alfsoftwares.honey.core"},
    useFullyQualifiedModuleNames = true)
public class HoneyApplication {

  public static void main(String[] args) {
    SpringApplication.run(HoneyApplication.class, args);
  }
}

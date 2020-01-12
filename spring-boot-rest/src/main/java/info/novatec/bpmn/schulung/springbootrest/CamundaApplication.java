package info.novatec.bpmn.schulung.springbootrest;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication("bpmn-schulung-springboot")
public class CamundaApplication {
  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  }
}

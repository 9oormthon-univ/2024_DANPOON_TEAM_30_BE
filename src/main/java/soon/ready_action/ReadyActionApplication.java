package soon.ready_action;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReadyActionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadyActionApplication.class, args);
    }

    @PostConstruct
    public void timeSetUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}

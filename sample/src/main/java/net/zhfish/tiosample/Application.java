package net.zhfish.tiosample;

import lombok.extern.slf4j.Slf4j;
import net.zhfish.tio.annotation.EnableTioWebsocketConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhfish
 */
@Slf4j
@SpringBootApplication
@EnableTioWebsocketConfiguration
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}

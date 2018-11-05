package net.zhfish.tio.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Enable Tio-Websocket for spring boot application
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface TioController {
}

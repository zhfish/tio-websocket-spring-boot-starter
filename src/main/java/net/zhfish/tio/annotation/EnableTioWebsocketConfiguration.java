package net.zhfish.tio.annotation;

import java.lang.annotation.*;

/**
 * Enable Tio-Websocket for spring boot application
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableTioWebsocketConfiguration {
}

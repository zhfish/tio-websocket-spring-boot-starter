package net.zhfish.tio.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Tio-Websocket properties
 * @author zhfish
 */
@Data
@Component
@ConfigurationProperties(prefix = TioProps.PREFIX)
public class TioProps {
    final public static String PREFIX = "tio";

    /**
     * websocket properties
     */
    private Websocket websocket = new Websocket();

    /**
     * websocket properties class
     */
    @Data
    public static class Websocket {

        /**
         * enable websocket
         * default：true
         */
        private Boolean enable = true;

        /**
         * bind port
         * default：9099
         */
        private Integer port = 9099;

        /**
         * bind ip
         * default: null (0.0.0.0)
         */
        private String ip = null;

        /**
         * charset
         * default: UTF-8
         */
        private String charset = "UTF-8";

        /**
         * protocal's name (any)
         * default: tio
         */
        private String protocalName = "tio";

        /**
         * heartbeat's timeout (ms)
         * default: 60s
         */
        private Integer heartbeatTimeout = 1000 * 60;

    }
}

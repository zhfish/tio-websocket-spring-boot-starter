package net.zhfish.tio.bean;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * Tio-Websocket Clazz-Method Mapper
 * @author zhfish
 */
@Data
@Builder
public class TioWebsocketMethodMapper {
    private Object instance;
    private Method method;
}

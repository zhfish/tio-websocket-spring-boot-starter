package net.zhfish.tio.bean;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.zhfish.tio.annotation.*;
import net.zhfish.tio.exception.TioWebsocketException;
import net.zhfish.tio.utils.TioProps;
import net.zhfish.tio.utils.TioWebsocketUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Tio-Websocket session manager
 * @author zhfish
 */
@Slf4j
public class TioWebsocketManager extends ApplicationObjectSupport implements SmartInitializingSingleton {

    final private WsServerStarter starter;
    final private TioWebSocketMethods methods;

    @Getter
    final private ServerGroupContext groupContext;

    public TioWebsocketManager(TioProps.Websocket config, IWsMsgHandler wsMsgHandler) throws IOException {
        if (!config.getEnable()) {
            starter = null;
            groupContext = null;
            methods = null;
            return;
        }

        WsServerConfig wsConfig = new WsServerConfig(config.getPort());
        wsConfig.setBindIp(config.getIp());
        wsConfig.setCharset(config.getCharset());

        starter = new WsServerStarter(wsConfig, wsMsgHandler);
        groupContext = starter.getServerGroupContext();
        groupContext.setName(config.getProtocalName());
        groupContext.setHeartbeatTimeout(config.getHeartbeatTimeout());
        methods = new TioWebSocketMethods();
    }


    @Override
    public void afterSingletonsInstantiated() {
        if (starter == null) {
            return;
        }

        init();

        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("websocket start failed");
        }
    }

    private void init() {
        Set<Class<?>> classes = new LinkedHashSet<>();

        ApplicationContext context = getApplicationContext();
        if (context == null) {
            return;
        }
        TioWebsocketUtil.putManager(this);
        String[] neanNames = context.getBeanNamesForAnnotation(TioController.class);
        for (String beanName : neanNames) {
            classes.add(context.getType(beanName));
        }

        classes.forEach(item -> {
            Method[] currentClazzMethods = item.getDeclaredMethods();
            Object bean = context.getBean(item);
            for (Method method : currentClazzMethods) {
                if (!Modifier.isPublic(method.getModifiers())) {
                    continue;
                }
                if (method.isAnnotationPresent(TIOnHandshake.class)) {
                    Class<?> returnType = method.getReturnType();
                    if (!returnType.equals(void.class)) {
                        continue;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 3 ||
                            !parameterTypes[0].equals(HttpRequest.class) ||
                            !parameterTypes[1].equals(HttpResponse.class) ||
                            !parameterTypes[2].equals(ChannelContext.class)
                    ) {
                        continue;
                    }
                    if (methods.getHandshake() != null) {
                        throw new TioWebsocketException("duplicate TIOnHandshake");
                    }
                    methods.setHandshake(TioWebsocketMethodMapper.builder()
                            .instance(bean)
                            .method(method)
                            .build());
                } else if (method.isAnnotationPresent(TIOnAfterHandshake.class)) {
                    Class<?> returnType = method.getReturnType();
                    if (!returnType.equals(void.class)) {
                        continue;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 3 ||
                            !parameterTypes[0].equals(HttpRequest.class) ||
                            !parameterTypes[1].equals(HttpResponse.class) ||
                            !parameterTypes[2].equals(ChannelContext.class)
                    ) {
                        continue;
                    }
                    if (methods.getOnAfterHandshaked() != null) {
                        throw new TioWebsocketException("duplicate TIOnAfterHandshake");
                    }
                    methods.setOnAfterHandshaked(TioWebsocketMethodMapper.builder()
                            .instance(bean)
                            .method(method)
                            .build());
                } else if (method.isAnnotationPresent(TIOnClose.class)) {
                    Class<?> returnType = method.getReturnType();
                    if (!returnType.equals(void.class)) {
                        continue;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1 ||
                            !parameterTypes[0].equals(ChannelContext.class)
                    ) {
                        continue;
                    }
                    if (methods.getOnClose() != null) {
                        throw new TioWebsocketException("duplicate TIOnClose");
                    }
                    methods.setOnClose(TioWebsocketMethodMapper.builder()
                            .instance(bean)
                            .method(method)
                            .build());
                } else if (method.isAnnotationPresent(TIOnBeforeBytes.class)) {
                    Class<?> returnType = method.getReturnType();
                    if (!returnType.equals(TioWebsocketRequest.class)) {
                        continue;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 2 ||
                            !parameterTypes[0].equals(ChannelContext.class) ||
                            !parameterTypes[1].equals(byte[].class)
                    ) {
                        continue;
                    }
                    if (methods.getOnBeforeBytes() != null) {
                        throw new TioWebsocketException("duplicate TIOnBeforeBytes");
                    }
                    methods.setOnBeforeBytes(TioWebsocketMethodMapper.builder()
                            .instance(bean)
                            .method(method)
                            .build());
                } else if (method.isAnnotationPresent(TIOnBeforeText.class)) {
                    Class<?> returnType = method.getReturnType();
                    if (!returnType.equals(TioWebsocketRequest.class)) {
                        continue;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 2 ||
                            !parameterTypes[0].equals(ChannelContext.class) ||
                            !parameterTypes[1].equals(String.class)
                    ) {
                        continue;
                    }
                    if (methods.getOnBeforeText() != null) {
                        throw new TioWebsocketException("duplicate TIOnBeforeText");
                    }
                    methods.setOnBeforeText(TioWebsocketMethodMapper.builder()
                            .instance(bean)
                            .method(method)
                            .build());
                } else if (method.isAnnotationPresent(TIOnText.class)) {
                    Class<?> returnType = method.getReturnType();
                    if (!returnType.equals(void.class)) {
                        continue;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 2 ||
                            !parameterTypes[0].equals(ChannelContext.class) ||
                            !parameterTypes[1].equals(String.class)
                    ) {
                        continue;
                    }
                    if (methods.getOnText() != null) {
                        throw new TioWebsocketException("duplicate TIOnText");
                    }
                    methods.setOnText(TioWebsocketMethodMapper.builder()
                            .instance(bean)
                            .method(method)
                            .build());

                } else if (method.isAnnotationPresent(TIOnBytes.class)) {
                    Class<?> returnType = method.getReturnType();
                    if (!returnType.equals(void.class)) {
                        continue;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 2 ||
                            !parameterTypes[0].equals(ChannelContext.class) ||
                            !parameterTypes[1].equals(byte[].class)
                    ) {
                        continue;
                    }

                    if (methods.getOnBytes() != null) {
                        throw new TioWebsocketException("duplicate TIOnBytes");
                    }

                    methods.setOnBytes(TioWebsocketMethodMapper.builder()
                            .instance(bean)
                            .method(method)
                            .build());
                } else if (method.isAnnotationPresent(TIOnMap.class)) {
                    TIOnMap annotation = method.getAnnotation(TIOnMap.class);
                    if (annotation.value().length() == 0) {
                        Class<?> returnType = method.getReturnType();
                        if (!returnType.equals(void.class)) {
                            continue;
                        }
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length != 3 ||
                                !parameterTypes[0].equals(ChannelContext.class) ||
                                !parameterTypes[1].equals(String.class)
                        ) {
                            continue;
                        }
                        if (methods.getOnMap() != null) {
                            throw new TioWebsocketException("duplicate default TIOnMap");
                        }
                        methods.setOnMap(TioWebsocketMethodMapper.builder()
                                .instance(bean)
                                .method(method)
                                .build());
                    } else {
                        Class<?> returnType = method.getReturnType();
                        if (!returnType.equals(void.class)) {
                            continue;
                        }
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length != 2 ||
                                !parameterTypes[0].equals(ChannelContext.class)
                        ) {
                            continue;
                        }
                        if (methods.getOnMapEvent().get(annotation.value()) != null) {
                            throw new TioWebsocketException("duplicate TIOnMap with event " + annotation.value());
                        }
                        methods.getOnMapEvent().put(annotation.value(), TioWebsocketMethodMapper.builder()
                                .instance(bean)
                                .method(method)
                                .build());
                    }
                }
            }
        });

        if (methods.getHandshake() == null) {
            throw new TioWebsocketException("miss @TIOnHandshake");
        }
        if (methods.getOnClose() == null) {
            throw new TioWebsocketException("miss @TIOnClose");
        }

        TioWebsocketMsgHandler handler = (TioWebsocketMsgHandler) starter.getWsMsgHandler();
        handler.setMethods(methods);
    }


    private void start() throws IOException {
        starter.start();
    }
}

package net.zhfish.tio.autoconfigure;

import net.zhfish.tio.annotation.EnableTioWebsocketConfiguration;
import net.zhfish.tio.bean.TioWebsocketManager;
import net.zhfish.tio.bean.TioWebsocketMsgHandler;
import net.zhfish.tio.utils.TioProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author zhfish
 */
@Configuration
@ConditionalOnClass({TioWebsocketManager.class})
@ConditionalOnBean(annotation = EnableTioWebsocketConfiguration.class)
@EnableConfigurationProperties(TioProps.class)
public class TioWebsocketAutoConfiguration {

    final public static String MANAGER_BEAN_NAME = "TioWebsocketManager";

    @Autowired
    private TioProps tioProps;

    @Bean
    @ConditionalOnMissingBean(TioWebsocketManager.class)
    public TioWebsocketManager tioWebsocketBean() throws IOException {
        return new TioWebsocketManager(tioProps.getWebsocket(), tioWebsocketMsgBean());
    }

    @Bean
    @ConditionalOnMissingBean(TioWebsocketMsgHandler.class)
    public TioWebsocketMsgHandler tioWebsocketMsgBean() {
        return new TioWebsocketMsgHandler();
    }
}

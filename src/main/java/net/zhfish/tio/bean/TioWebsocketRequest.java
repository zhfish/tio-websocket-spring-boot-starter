package net.zhfish.tio.bean;

import lombok.Builder;
import lombok.Data;

/**
 * Tio-Websocket Response for beforeOnText and beforeOnBytes
 * @author zhfish
 */
@Data
@Builder
public class TioWebsocketRequest
{
    /**
     * event
     */
    private String event;

    /**
     * request
     */
    private Object object;
}

package net.zhfish.tio.bean;

import lombok.Data;

import java.util.HashMap;

/**
 * Tio-WebSocket methods
 * @author zhfish
 */
@Data
public class TioWebSocketMethods {
    private TioWebsocketMethodMapper handshake;
    private TioWebsocketMethodMapper onAfterHandshaked;
    private TioWebsocketMethodMapper onClose;
    private TioWebsocketMethodMapper onBeforeText;
    private TioWebsocketMethodMapper onBeforeBytes;
    private TioWebsocketMethodMapper onText;
    private TioWebsocketMethodMapper onBytes;
    private TioWebsocketMethodMapper onMap;

    private HashMap<String, TioWebsocketMethodMapper> onMapEvent = new HashMap<>();
}

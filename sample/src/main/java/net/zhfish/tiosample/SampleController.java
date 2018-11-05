package net.zhfish.tiosample;

import lombok.extern.slf4j.Slf4j;
import net.zhfish.tio.annotation.*;
import net.zhfish.tio.bean.TioWebsocketRequest;
import net.zhfish.tio.utils.TioWebsocketUtil;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsResponse;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * SampleController
 * @author zhfish
 */
@TioController
@Slf4j
public class SampleController {
    private static String user = "hanmeimei";
    private static String token = "token12345";
    @TIOnHandshake
    public void handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {
        Tio.bindToken(channelContext, token);
        Tio.bindUser(channelContext, user);
        log.info("{} handshake in sample", user);
    }

    @TIOnAfterHandshake
    public void onAfterHandshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {
        log.info("{} onAfterHandshake in sample", channelContext.userid);
    }

    @TIOnClose
    public void onClose(ChannelContext channelContext) {
        log.info("{} onClose in sample", channelContext.userid);
    }

    @TIOnBeforeText
    public TioWebsocketRequest OnBeforeText(ChannelContext channelContext, String text) {
        if (text.equals("ping")) {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("hello");
            strings.add("world");

            return TioWebsocketRequest.builder()
                    .event("ping")
                    .object(strings)
                    .build();
        }
        return TioWebsocketRequest.builder()
                .event("unknown")
                .object(text)
                .build();
    }

    @TIOnMap
    public void onMapDefault(ChannelContext channelContext, String event, String object) {
        log.info("{}:{} onMapDefault in sample: {}", channelContext.userid, event, object);
        Tio.sendToUser(TioWebsocketUtil.getGroupContext(),
                user,
                WsResponse.fromText(event + "+" + object, "UTF-8"));
        Tio.send(channelContext, WsResponse.fromText("another send method", "UTF-8"));
    }

    @TIOnMap("ping")
    public void onMap(ChannelContext channelContext, ArrayList<String> strings) {
        log.info("{} onMapDefault in sample: {}", channelContext.userid, Arrays.toString(strings.toArray()));
        Tio.sendToUser(TioWebsocketUtil.getGroupContext(),
                user,
                WsResponse.fromText(Arrays.toString(strings.toArray()), "UTF-8"));
        Tio.send(channelContext, WsResponse.fromText("another send method", "UTF-8"));
    }
/*
    @TIOnText
    public void onText(ChannelContext channelContext, String text) {
        log.info("{} OnText in sample: {}", channelContext.userid, text);
    }

    @TIOnBytes
    public void onBytes(ChannelContext channelContext, byte[] bytes) {
        log.info("{} onBytes in sample: {}", channelContext.userid, Arrays.toString(bytes));
    }
*/
}

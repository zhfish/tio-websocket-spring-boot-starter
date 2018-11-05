package net.zhfish.tio.utils;

import lombok.Getter;
import net.zhfish.tio.bean.TioWebsocketManager;
import org.tio.core.GroupContext;

/**
 * Tio-Websocket's Util
 * @author zhfish
 */
public class TioWebsocketUtil {
    private static TioWebsocketUtil util = null;

    @Getter
    private TioWebsocketManager manager;


    private TioWebsocketUtil(TioWebsocketManager manager) {
        this.manager = manager;
    }

    public static void putManager(TioWebsocketManager manager) {
        if (util == null && manager != null) {
            util = new TioWebsocketUtil(manager);
        }
    }
    public static GroupContext getGroupContext() {
        if (util == null) {
            return null;
        }
        return util.getManager().getGroupContext();
    }
}

package net.zhfish.tio.annotation;

import java.lang.annotation.*;

/**
 * example:
 *
 * import org.tio.core.ChannelContext;
 *
 * \@TIOnBytes
 * public void onBytes(ChannelContext channelContext, byte[] bytes) {}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TIOnBytes {

}

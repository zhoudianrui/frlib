package io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock;

import io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock.LocalTimeProtocol.Continent;
import io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock.LocalTimeProtocol.LocalTime;
import io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock.LocalTimeProtocol.LocalTimes;
import io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock.LocalTimeProtocol.Location;
import io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock.LocalTimeProtocol.Locations;
import io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock.LocalTimeProtocol.Locations.Builder;
import org.jboss.netty.channel.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/29 16:35
 */

public class LocalTimeClientHandler extends SimpleChannelUpstreamHandler {

    private static final Pattern DELIMITER = Pattern.compile("/");

    private final BlockingQueue<LocalTimes> answer = new LinkedBlockingQueue<>();
    private volatile Channel channel;

    public List<String> getLocalTimes(Collection<String> cities) {
        Builder builder = Locations.newBuilder();
        for (String city : cities) {
            String[] components = DELIMITER.split(city);
            builder.addLocation(Location.newBuilder()
                                        .setContinent(Continent.valueOf(components[0].toUpperCase()))
                                        .setCity(components[1]).build());
        }
        Locations request = builder.build();
        channel.write(request);

        LocalTimes localTimes;
        boolean interrupted = false;
        for(;;) {
            try {
                localTimes = answer.take();
                break;
            } catch (InterruptedException e) {
                 interrupted = true;
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        List<String> result = new ArrayList<>();
        for (LocalTime localTime : localTimes.getLocalTimeList()) {
            result.add(new Formatter().format("%4d-%02d-%02d %02d:%02d:%02d %s",
                                                localTime.getYear(),
                                                localTime.getMonth(),
                                                localTime.getDayOfMonth(),
                                                localTime.getHour(),
                                                localTime.getMinute(),
                                                localTime.getSecond(),
                                                localTime.getDayOfWeek().name()).toString());
        }
        return result;
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.out.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        channel = e.getChannel();
        super.channelOpen(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        answer.add((LocalTimes)e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

package io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock;

import org.jboss.netty.channel.*;

import java.util.Calendar;
import java.util.TimeZone;

import static java.util.Calendar.*;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/28 15:32
 */

public class LocalTimeServerHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.out.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Locations locations = (Locations)e.getMessage();
        long currentTime = System.currentTimeMillis();

        LocalTimes.Builder builder = LocalTimes.newBuilder();
        for (Location location : locations.getLocationList()) {
            TimeZone timeZone = TimeZone.getTimeZone(toString(location.getContinent()) + "/" + location.getCity());// 获取区域/城市所在的时区
            Calendar calendar = Calendar.getInstance(timeZone);
            calendar.setTimeInMillis(currentTime);

            builder.addLocalTime(LocalTime.newBuilder()
                                .setYear(calendar.get(YEAR))
                                .setMonth(calendar.get(MONTH) + 1)
                                .setDayOfMonth(calendar.get(DAY_OF_MONTH))
                                .setDayOfWeek(DayOfWeek.valueOf(calendar.get(DAY_OF_WEEK)))
                                .setHour(calendar.get(HOUR_OF_DAY))
                                .setMinute(calendar.get(MINUTE))
                                .setSecond(calendar.get(SECOND)).build());
        }
        e.getChannel().write(builder.build());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    public static String toString(Continent continent) {
        return continent.name().charAt(0) + continent.name().toLowerCase().substring(1);
    }
}

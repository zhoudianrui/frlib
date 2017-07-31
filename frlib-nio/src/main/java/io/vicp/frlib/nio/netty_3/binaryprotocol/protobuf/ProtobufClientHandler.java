package io.vicp.frlib.nio.netty_3.binaryprotocol.protobuf;

import org.jboss.netty.channel.*;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/30 10:27
 */

public class ProtobufClientHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.out.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        final Channel channel = e.getChannel();
        /*Person.Builder personBuilder = Person.newBuilder();
        personBuilder.setName("张三");
        Person person = personBuilder.build();*/
        AddressBook.Builder addressBookBuilder = AddressBook.newBuilder();
        /*addressBookBuilder.addPeople(person);*/
        AddressBook addressBook = addressBookBuilder.build();
        channel.write(addressBook);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        AddressBook addressBook = (AddressBook)e.getMessage();
        if (addressBook != null) {
            addressBook.getPeopleList().forEach(p -> System.out.println(p.getName()));
            e.getChannel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

package io.vicp.frlib.nio.netty_3.binaryprotocol.protobuf;

import io.vicp.frlib.nio.netty_3.binaryprotocol.protobuf.AddressBookProtocol.AddressBook;
import io.vicp.frlib.nio.netty_3.binaryprotocol.protobuf.AddressBookProtocol.Person;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.netty.channel.*;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/29 18:39
 */

public class ProtobufServerHandler extends SimpleChannelUpstreamHandler{

    private static AddressBook addressBook = AddressBook.getDefaultInstance();

    public ProtobufServerHandler() {
        AddressBook.Builder builder = AddressBook.newBuilder();
        Person.Builder personBuilder = Person.newBuilder();
        Person.PhoneNumber.Builder phoneNumberBuilder = Person.PhoneNumber.newBuilder();
        builder.addPeople(personBuilder.setName("张三").setId(1).setEmail("zhangsan@126.com").addPhones(
                    phoneNumberBuilder.setType(Person.PhoneType.MOBILE).setNumber("13111111111").build()).addPhones(
                    phoneNumberBuilder.setType(Person.PhoneType.WORK).setNumber("18888888888").build()).build())
                .addPeople(personBuilder.setName("李四").setId(2).setEmail("lisi@163.com").addPhones(
                        phoneNumberBuilder.setType(Person.PhoneType.HOME).setNumber("02188888888").build()).build());
        addressBook = builder.build();
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.out.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        // 查询姓张的用户
        AddressBook request = (AddressBook)e.getMessage();
        Collection<Person> people = addressBook.getPeopleList().stream().filter(person -> {
            if (CollectionUtils.isNotEmpty(request.getPeopleList())) {
                Collection<Person> tempPeople = request.getPeopleList().stream().filter(requestPerson ->
                    requestPerson.getName() == null || person.getName().equalsIgnoreCase(requestPerson.getName())
                ).collect(Collectors.toList());
                return CollectionUtils.isNotEmpty(tempPeople);
            }
            return true;
        }).collect(Collectors.toList());
        AddressBook addressBook = AddressBook.newBuilder().addAllPeople(people).build();
        e.getChannel().write(addressBook);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

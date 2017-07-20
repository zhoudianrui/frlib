package io.vicp.frlib.nio.netty_3.serializer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: ByteBufferSerializer, v 0.1 2017/7/15 13:09 zhoudr Exp $$
 */

public class ByteBufferSerializer {
    public static void main(String[] args) {
        short age = 30;
        int height = 170;
        ByteBuffer writeBuffer = ByteBuffer.allocate(6);
        writeBuffer.putShort(age);
        writeBuffer.putInt(height);
        writeBuffer.flip();
        byte[] bytes = writeBuffer.array();
        System.out.println(Arrays.toString(bytes));

        ByteBuffer readBuffer = ByteBuffer.wrap(bytes);
        short originalAge = readBuffer.getShort();
        int originalHeight = readBuffer.getInt();
        System.out.println("age:" + originalAge);
        System.out.println("height:" + originalHeight);
    }
}

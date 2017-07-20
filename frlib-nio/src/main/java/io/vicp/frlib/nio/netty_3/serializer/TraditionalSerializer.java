package io.vicp.frlib.nio.netty_3.serializer;

import java.io.*;
import java.util.Arrays;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: TraditionalSerializer, v 0.1 2017/7/14 21:17 zhoudr Exp $$
 */

public class TraditionalSerializer {

    public static void main(String[] args) throws IOException {
        int age = 30;
        int height = 65535;
        byte[] bytes = int2bytes(height);
        System.out.println(Arrays.toString(bytes));
        System.out.println(bytes2int(bytes));
        //withByteArrayStream(age, height);
        //withDataStream(age, height);
    }

    private static void withByteArrayStream(int value1, int value2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(int2bytes(value1));
        byteArrayOutputStream.write(int2bytes(value2));
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(Arrays.toString(bytes));

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        byte[] ageByte = new byte[4];
        byteArrayInputStream.read(ageByte);
        System.out.println("value1:" + bytes2int(ageByte));

        byte[] heightByte = new byte[4];
        byteArrayInputStream.read(heightByte);
        System.out.println("value2:" + bytes2int(heightByte));
    }

    private static void withDataStream(int value1, int value2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(value1);
        dataOutputStream.writeInt(value2);

        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(Arrays.toString(bytes));

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        int originalValue1 = dataInputStream.readInt();
        int originalValue2 = dataInputStream.readInt();
        System.out.println("value1:" + originalValue1);
        System.out.println("value2:" + originalValue2);
    }

    /**
     * 采用大端方式，高位在左，低位在右
     * @param value
     * @return
     */
    private static byte[] int2bytes(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value >> 3 * 8);
        bytes[1] = (byte) (value >> 2 * 8);
        bytes[2] = (byte) (value >> 1 * 8);
        bytes[3] = (byte) (value);
        return bytes;
    }

    /**
     * 注意负数的情况
     * @param bytes
     * @return
     */
    private static int bytes2int(byte[] bytes) {
        return (bytes[0] << 3 * 8) | (bytes[1] & 0xff) << 2 * 8 | (bytes[2] & 0xff) << 1 * 8 | (bytes[3] & 0xff) ;
    }
}

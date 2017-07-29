package io.vicp.frlib.chat.serializer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * <p>序列化<p>
 *
 * @author zhoudr
 * @version $Id: Serializer, v 0.1 2017/7/20 19:54 zhoudr Exp $$
 */

public abstract class Serializer {

    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    private static final short SHORT_ZERO = 0;

    private static final short SHORT_ONE = 1;

    private static final byte BYTE_ZERO = 0;

    private static final byte BYTE_ONE = 1;

    protected ChannelBuffer writer;

    protected  ChannelBuffer reader;

    protected byte readByte() {
        return this.reader.readByte();
    }

    protected short readShort() {
        return this.reader.readShort();
    }

    protected int readInt() {
        return this.reader.readInt();
    }

    protected long readLong() {
        return this.reader.readLong();
    }

    protected float readFloat() {
        return this.reader.readFloat();
    }

    protected double readDouble() {
        return this.reader.readDouble();
    }

    protected String readString() {
        short length = this.reader.readShort(); // 表示字符串的长度
        if (length == SHORT_ZERO) {
            return null;
        } else if (length > 0){
            byte[] byteArray = new byte[length];
            this.reader.readBytes(byteArray);
            return new String(byteArray, Charset.forName(DEFAULT_CHARSET_NAME));
        } else {
            throw new RuntimeException("字符串长度无效");
        }
    }

    protected <T> List<T> readList(Class<T> cls) {
        short length = this.readShort(); // 表示列表的长度
        if (length == SHORT_ZERO) {
            return null;
        } else if (length > 0) {
            List<T> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                T instance = readObject(cls);
                list.add(instance);
            }
            return list;
        } else {
            throw new RuntimeException("列表长度无效");
        }
    }

    private <T> T readObject(Class<T> cls) {
        // 第一个字节表明对象是否为空(0:空,1:非空)
        byte nullable = this.readByte();
        if (nullable == BYTE_ZERO) {
            return null;
        } else {
            Object t = null; // 把对象转换成Object，然后强制转换为T类型
            if (cls == byte.class || cls == Byte.class) {
                t = this.readByte();
            } else if (cls == short.class || cls == Short.class) {
                t = this.readShort();
            } else if (cls == int.class || cls == Integer.class) {
                t = this.readInt();
            } else if (cls == long.class || cls == Long.class) {
                t = this.readLong();
            } else if (cls == float.class || cls == Float.class) {
                t = this.readFloat();
            } else if (cls == double.class || cls == Double.class) {
                t = this.readDouble();
            } else if (cls == String.class) {
                t = this.readString();
            } else if (Serializer.class.isAssignableFrom(cls)) {
                try {
                    t = cls.newInstance();
                    Serializer serializer = (Serializer)t;
                    serializer.read();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException("对象类型错误");
            }
            return (T)t;
        }
    }

    protected abstract void read();

    protected <K, V> Map<K, V> readMap(Class<K> keyClass, Class<V> valueClass) {
        short size = this.readShort();
        if (size == SHORT_ZERO) {
            return null;
        } else {
            Map<K, V> map = new HashMap<>();
            for (int i = 0; i < size; i++) {
                K key = readObject(keyClass);
                V value = readObject(valueClass);
                map.put(key, value);
            }
            return map;
        }
    }

    public byte[] getBytes() {
        this.writer = ChannelBuffers.dynamicBuffer();
        write();
        byte[] bytes;
        if (writer.writerIndex() == 0) {
            bytes = new byte[0];
        } else {
            bytes = new byte[writer.writerIndex()];
            writer.readBytes(bytes);
        }
        writer.clear();
        return bytes;
    }

    public Serializer readFromBytes(byte[] byteArray) {
        this.reader = ChannelBuffers.wrappedBuffer(byteArray);
        read();
        return this;
    }

    protected void writeByte(byte value) {
        this.writer.writeByte(value);
    }

    protected void writeShort(short value) {
        this.writer.writeShort(value);
    }

    protected void writeInt(int value) {
        this.writer.writeInt(value);
    }

    protected void writeLong(long value) {
        this.writer.writeLong(value);
    }

    protected void writeFloat(float value) {
        this.writer.writeFloat(value);
    }

    protected void writeDouble(double value) {
        this.writer.writeDouble(value);
    }

    protected void writeString(String value) {
        if (StringUtils.isEmpty(value)) {
            writeShort(SHORT_ZERO);
        } else {
            byte[] data = value.getBytes(Charset.forName(DEFAULT_CHARSET_NAME));
            writeShort((short)data.length); // 应该是字节的长度，不是字符串的长度
            this.writer.writeBytes(data);
        }
    }

    protected <T> void writeList(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            this.writeShort(SHORT_ZERO);
        } else {
            this.writeShort((short)list.size());
            list.forEach(instance -> writeObject(instance));
        }
    }

    private <T> void writeObject(Object instance) {
        // 先写入一个字节，表明该对象是否为空(0:空,1:非空)
        if (instance == null) {
            writeByte(BYTE_ZERO);
        } else {
            writeByte(BYTE_ONE);
            if (instance instanceof Byte) {
                writeByte((Byte)instance);
            } else if (instance instanceof Short) {
                writeShort((Short) instance);
            } else if (instance instanceof Integer) {
                writeInt((Integer)instance);
            } else if (instance instanceof Long) {
                writeLong((Long)instance);
            } else if (instance instanceof Float) {
                writeFloat((Float)instance);
            } else if (instance instanceof Double) {
                writeDouble((Double)instance);
            } else if (instance instanceof String) {
                writeString((String)instance);
            } else if (instance instanceof Serializer) {
                Serializer serializer = (Serializer)instance;
                serializer.write();
            } else {
                throw new RuntimeException("实例类型无法处理");
            }
        }
    }

    protected abstract void write();

    protected <K, V> void writeMap(Map<K, V> map) {
        // 先写入键值对的个数
        if (map == null || map.size() == 0) {
            writeShort(SHORT_ZERO);
        } else {
            writeShort((short)map.size());
            for (Map.Entry<K, V> entry : map.entrySet()) {
                writeObject(entry.getKey());
                writeObject(entry.getValue());
            }
        }
    }
}

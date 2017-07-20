package io.vicp.frlib.nio.netty_3.serializer;

import org.jboss.netty.buffer.ChannelBuffer;

import java.nio.charset.Charset;
import java.util.*;

/**
 * <p>序列化<p>
 *
 * @author zhoudr
 * @version $Id: Serializer, v 0.1 2017/7/14 21:02 zhoudr Exp $$
 */

public abstract class Serializer {

    protected ChannelBuffer writeBuffer = BufferFactory.getBuffer();

    protected ChannelBuffer readBuffer = BufferFactory.getBuffer();

    private static final short SHORT_ZERO = 0;

    private static final byte BYTE_ZERO = 0;

    private static final byte BYTE_ONE = 1;

    private static final Charset CHARSET = Charset.forName("UTF-8");

    public Serializer writeByte(byte value) {
        writeBuffer.writeByte(value);
        return this;
    }

    public Serializer writeShort(short value) {
        writeBuffer.writeShort(value);
        return this;
    }

    public Serializer writeInt(int value) {
        writeBuffer.writeInt(value);
        return this;
    }

    public Serializer writeLong(long value) {
        writeBuffer.writeLong(value);
        return this;
    }

    public Serializer writeFloat(float value) {
        writeBuffer.writeFloat(value);
        return this;
    }

    public Serializer writeDouble(double value) {
        writeBuffer.writeDouble(value);
        return this;
    }

    public Serializer writeString(String value) {
        if (value == null || value.isEmpty()) {
            writeShort(SHORT_ZERO);
            return this;
        }
        byte[] data = value.getBytes(CHARSET);
        short length = (short)data.length;
        writeBuffer.writeShort(length);
        writeBuffer.writeBytes(data);
        return this;
    }

    public <T> Serializer writeList(List<T> valueList) {
        if (isEmpty(valueList)) {
            writeBuffer.writeShort((short)0);
            return this;
        }
        writeBuffer.writeShort((short)valueList.size());
        for(T item : valueList) {
            writeObject(item);
        }
        return this;
    }

    public Serializer writeObject(Object object) {
        if (object == null) {
            writeByte(BYTE_ZERO);
        } else {
            if (object instanceof Byte) {
                writeByte((byte)object);
            } else if (object instanceof Short) {
                writeShort((short)object);
            } else if (object instanceof Integer) {
                writeInt((int) object);
            } else if (object instanceof Long) {
                writeLong((long)object);
            } else if (object instanceof Float) {
                writeFloat((float) object);
            } else if (object instanceof Double) {
                writeDouble((double)object);
            } else if (object instanceof String) {
                writeString((String) object);
            } else if (object instanceof Serializer) {
                writeByte(BYTE_ONE);
                Serializer value = (Serializer)object;
                value.writeToBuffer();
            } else {
                throw new RuntimeException("不可序列化的类型:" + object.getClass());
            }
        }
        return this;
    }

    public <K, V> Serializer writeMap(Map<K, V> map) {
        if (isEmpty(map)) {
            writeShort(SHORT_ZERO);
            return this;
        }
        int size = map.size();
        writeShort((short)size);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            writeObject(entry.getKey());
            writeObject(entry.getValue());
        }
        return this;
    }

    private <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.size() == 0;
    }

    private <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    protected abstract void write();

    private ChannelBuffer writeToBuffer() {
        this.writeBuffer = BufferFactory.getBuffer();
        write(); // 属性写入
        return writeBuffer;
    }

    public byte[] getBytes() {
        this.writeToBuffer();
        byte[] bytes;
        if (writeBuffer.writerIndex() == 0) {
            bytes = new byte[0];
        } else {
            bytes = new byte[writeBuffer.writerIndex()];
            writeBuffer.readBytes(bytes);
        }
        writeBuffer.clear();
        return bytes;
    }

    protected abstract void read();

    public Serializer readFromBytes(byte[] bytes) {
        readBuffer = BufferFactory.getBuffer(bytes);
        read(); // 属性赋值
        readBuffer.clear();
        return this;
    }

    public byte readByte() {
        return readBuffer.readByte();
    }

    public short readShort() {
        return readBuffer.readShort();
    }

    public int readInt() {
        return readBuffer.readInt();
    }

    public long readLong() {
        return readBuffer.readLong();
    }

    public float readFloat() {
        return readBuffer.readFloat();
    }

    public Double readDouble() {
        return readBuffer.readDouble();
    }

    public String readString() {
        int size = readBuffer.readShort(); // 长度
        if (size <= 0) {
            return "";
        }
        byte[] bytes = new byte[size];
        readBuffer.readBytes(bytes);
        String s = new String(bytes, CHARSET);
        return s;
    }

    public <T> List<T> readList(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        int size = readBuffer.readShort(); // list内的对象个数
        for (int i = 0; i < size; i++) {
            list.add(read(clazz));
        }
        return list;
    }

    public <K,V> Map<K, V> readMap(Class<K> keyClass, Class<V> valueClazz) {
        Map<K, V> map = new HashMap<>();
        int size = readBuffer.readShort(); // 获取个数
        for (int i = 0; i < size; i++) {
            K key = read(keyClass);
            V value = read(valueClazz);
            map.put(key, value);
        }
        return map;
    }

    public <T> T read(Class<T> clazz) {
        Object t = null;
        if (clazz == byte.class || clazz == Byte.class) {
            t = this.readByte();
        } else if (clazz == short.class || clazz == Short.class) {
            t = this.readShort();
        } else if (clazz == int.class || clazz == Integer.class) {
            t = this.readInt();
        } else if (clazz == long.class || clazz == Long.class) {
            t = this.readLong();
        } else if (clazz == float.class || clazz == Float.class) {
            t = readFloat();
        } else if (clazz == double.class || clazz == Double.class) {
            t = readDouble();
        } else if (clazz == String.class) {
            t = readString();
        }  else if (Serializer.class.isAssignableFrom(clazz)) {
            byte hasObject = this.readBuffer.readByte();
            try{
                if (hasObject == 1) {
                    Serializer temp = (Serializer)clazz.newInstance();
                    temp.read();
                    t = temp;
                } else{
                    t = null;
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException(String.format("不支持类型:[%s]", clazz));
        }
        return (T)t;
    }
}

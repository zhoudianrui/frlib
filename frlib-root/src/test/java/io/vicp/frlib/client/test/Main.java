package io.vicp.frlib.client.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoudr on 2017/4/7.
 */
public class Main {

    public static void main(String[] args) {
        /*long value = 1L << 32;
        Long a = Long.valueOf(value);
        System.out.println(a + ":" + Long.toBinaryString(value));
        System.out.println((int)value ^ value >>> 32);
        System.out.println((1 << 16) - 1);
        System.out.println((1 << 16) ^ 1);
        int b = (1 << 16) + 1;
        int c = (1 << 16) + 2;
        int d = (1 << 16) + 3;
        System.out.println(b);
        System.out.println(b ^ (b >>> 16));
        System.out.println(c);
        System.out.println(c ^ (c >>> 16));
        System.out.println(d);
        System.out.println(d ^ (d >>> 16));*/
        Map<Integer, String> map = new HashMap(64);
        map.put(1, "a");
        map.put(65, "b");
        map.put(129, "c");
        map.put(193, "d");
        map.put(257, "e");
        map.put(321, "f");
        map.put(385, "g");
        map.put(449, "h");
        map.put(513, "i");
    }
}

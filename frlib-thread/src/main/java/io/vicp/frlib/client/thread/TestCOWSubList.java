package io.vicp.frlib.client.thread;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zhoudr on 2017/6/19.
 */
public class TestCOWSubList {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List subList = list.subList(1, 2);
        list.add(4); // 导致原列表变化而子列表的数组为变化，所以下面的l.getArray() != expectedArray(oldArray)
        subList.get(0);
    }
}

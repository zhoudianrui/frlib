import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhoudr on 2017/4/24.
 */
public class TestArrayListIterator {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(16);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Iterator<Integer> iterator = list.iterator();
        Thread removeThread = new RemoveThread(list);
        removeThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i; iterator.hasNext();) {
            i = iterator.next();
            System.out.println(i);
        }
    }
}

class RemoveThread extends Thread {

    private List<Integer> list;

    public RemoveThread(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        list.remove(1);
    }
}


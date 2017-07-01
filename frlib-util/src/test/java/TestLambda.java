import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Created by zhoudr on 2017/6/19.
 */
public class TestLambda {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        System.out.println(list);
        //list = list.stream().map(name->name.toLowerCase()).collect(Collectors.toList());
        list = list.stream().map(String::toLowerCase).collect(Collectors.toList());
        System.out.println(list);

        String[] datas = list.toArray(new String[0]);
        Arrays.sort(datas, (v1, v2) -> Integer.compare(v1.length(), v2.length()));
        Stream.of(datas).forEach(System.out::println);

        List<Integer> nums = Arrays.asList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        nums.stream().filter(num -> num != null).distinct().mapToInt(num -> num * 2).skip(2).limit(4).forEach(System.out::println);
        System.out.println("sum:" + nums.stream().filter(num -> num != null).reduce((sum, item) -> sum + item).get());
        nums.stream().filter(num -> num != null).max((o1, o2) -> o1.compareTo(o2)).ifPresent(System.out::println);

        IntSummaryStatistics numsSummaryStatistics = nums.stream().filter(x -> x != null).mapToInt(x -> x).summaryStatistics();
        System.out.println("count:" + numsSummaryStatistics.getCount());
        System.out.println("max:" + numsSummaryStatistics.getMax());
        System.out.println("sum:" + numsSummaryStatistics.getSum());
        System.out.println("average:" + numsSummaryStatistics.getAverage());
    }

    public <T> List<T> create(IMyCreator<List<T>> creator, T...a) {
        List<T> list = creator.create();
        for(T t : a) {
            list.add(t);
        }
        return list;
    }

    @Test
    public void testCreator() {
        List<Integer> list = this.create(LinkedList::new, 1, 2,3); // 存在无参构造函数
        list.forEach(System.out::println);
    }

}

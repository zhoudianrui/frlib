import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhoudr on 2017/4/18.
 */
public class TestReflectionUtil {
    public static void main(String[] args) {
        /*List<String> s = Arrays.asList("abc");
        Type[] types = ReflectionUtil.getParameterizedTypes(s);
        System.out.println(types);*/
        Map<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "a");
        map.put(17, "b");
    }
}

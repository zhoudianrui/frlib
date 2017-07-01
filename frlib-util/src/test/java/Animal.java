/**
 * Created by zhoudr on 2017/6/21.
 */
public abstract class Animal {
    static {
        System.out.println("parent static");
    }
    {
        System.out.println("parent statement block");
    }
    abstract void fly();
}

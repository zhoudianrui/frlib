/**
 * Created by zhoudr on 2017/6/21.
 */
public class Bird extends Animal implements IFlyable {
    static {
        System.out.println("static");
    }

    {
        System.out.println("statement block");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Animal bird = new Bird();
        bird.fly();
        //IFlyable.fly();
    }

    public void fly() {
        IFlyable.fly();
    }
}

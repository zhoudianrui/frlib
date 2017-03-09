package io.vicp.frlib.dp.observer;

/**
 * Created by zhoudr on 2017/3/8.
 */
public class Main {
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        Observer observer1 = new ConcreteObserver(subject);
        Observer observer2 = new ConcreteObserver(subject);
        subject.setChanaged();
        System.out.println("============我是分割线===============");
        observer2.cancelListen();
        observer2 = null;
        subject.setChanaged();
    }
}

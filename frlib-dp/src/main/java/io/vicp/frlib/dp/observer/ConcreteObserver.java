package io.vicp.frlib.dp.observer;

/**
 * Created by zhoudr on 2017/3/8.
 */
public class ConcreteObserver implements Observer {

    private Subject subject;

    public ConcreteObserver(Subject subject) {
        this.subject = subject;
        this.subject.registerObserver(this);
    }

    @Override
    public void update(Subject subject, Object arg) {
        if (this.subject == subject) { // 监听对象是否一致
            if (arg != null) {
                System.out.println("根据arg更新监听对象的内部状态");
            } else {
                System.out.println("arg is null");
            }
        }
    }

    @Override
    public void cancelListen() {
        subject.deleteObserver(this);
    }
}

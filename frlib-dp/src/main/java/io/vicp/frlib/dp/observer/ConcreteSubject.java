package io.vicp.frlib.dp.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoudr on 2017/3/8.
 */
public class ConcreteSubject implements Subject {

    private List<Observer> observerList = new ArrayList<>();

    private volatile boolean changed = false;

    @Override
    public void registerObserver(Observer observer) {
        synchronized(observerList) {
            observerList.add(observer);
        }
    }

    @Override
    public void deleteObserver(Observer observer) {
        synchronized(observerList) {
            observerList.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        this.notifyObservers(null);
    }

    @Override
    public void notifyObservers(Object arg) {
        if(changed) {
            synchronized (observerList){
                for (Observer observer : observerList) {
                    observer.update(this, arg);
                }
            }
        }
    }

    public void setChanaged() {
        this.changed = true;
        notifyObservers();
    }
}

package io.vicp.frlib.dp.observer;

/**
 * Created by zhoudr on 2017/3/8.
 */
public interface Subject {
    public void registerObserver(Observer observer);

    public void deleteObserver(Observer observer);

    public void notifyObservers();

    public void notifyObservers(Object arg);

    public void setChanaged();
}

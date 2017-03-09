package io.vicp.frlib.dp.observer;

/**
 * Created by zhoudr on 2017/3/8.
 */
public interface Observer {
    public void update(Subject subject, Object arg);

    public void cancelListen();
}

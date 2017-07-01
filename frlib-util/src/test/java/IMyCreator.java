import java.util.List;

/**
 * Created by zhoudr on 2017/6/20.
 */
@FunctionalInterface
public interface IMyCreator<T extends List<?>> {
    T create();
}

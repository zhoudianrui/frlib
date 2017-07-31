package io.vicp.frlib.chat.codc;

/**
 * <p>常量<p>
 *
 * @author zhoudr
 * @version $Id: Constants, v 0.1 2017/7/29 16:20 zhoudr Exp $$
 */

public class Constants {

    // 请求头部魔数
    public static final int MAGIC_HEAD = -234457623;

    // 防止socket数据流攻击限定的最大长度
    public static final int LONGEST_PACKAGE_LENGTH = 2048;
}

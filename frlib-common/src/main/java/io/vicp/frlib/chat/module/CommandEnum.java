package io.vicp.frlib.chat.module;

/**
 * <p>命令枚举<p>
 *
 * @author zhoudr
 * @version $Id: CommandEnum, v 0.1 2017/7/29 17:12 zhoudr Exp $$
 */

public enum CommandEnum {
    INVALID((short)0, "无效"),
    REGISTER((short)1, "注册");

    private short value;

    private String desc;

    private CommandEnum(short value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public short getValue() {
        return value;
    }
}

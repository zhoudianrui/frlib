package io.vicp.frlib.chat.module;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: ModuleEnum, v 0.1 2017/7/29 17:05 zhoudr Exp $$
 */

public enum ModuleEnum {
    INVALID((short)0, "无效"),
    PLAYER((short)1, "游戏玩家");

    private short value;

    private String desc;

    ModuleEnum(short value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public short getValue() {
        return value;
    }
}

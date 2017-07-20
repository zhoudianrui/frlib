package io.vicp.frlib.nio.netty_3.customprotocol.entity;

import io.vicp.frlib.nio.netty_3.serializer.Serializer;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * <p>玩家<p>
 *
 * @author zhoudr
 * @version $Id: Player, v 0.1 2017/7/15 16:48 zhoudr Exp $$
 */
@Data
public class Player extends Serializer {
    private int id;

    private String aliasName;

    private List<Integer> skills;

    @Override
    protected void write() {
        writeInt(id);
        writeString(aliasName);
        writeList(skills);
    }

    @Override
    protected void read() {
        this.id = readInt();
        this.aliasName = readString();
        this.skills = readList(Integer.class);
    }

    public String toString() {
        return "id:" + id + ", aliasName:" + aliasName + ", skills:" + Arrays.toString(skills.toArray(new Integer[]{}));
    }
}

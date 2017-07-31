package io.vicp.frlib.chat.module;

import io.vicp.frlib.chat.serializer.Serializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>游戏玩家<p>
 *
 * @author zhoudr
 * @version $Id: Player, v 0.1 2017/7/22 16:09 zhoudr Exp $$
 */
@Data
public class Player extends Serializer {
    private int id;

    private String name;

    private List<Integer> skills;

    private Map<Integer, String> idNameMap;

    @Override
    protected void read() {
        id = readInt();
        name = readString();
        skills = readList(Integer.class);
        idNameMap = readMap(Integer.class, String.class);
    }

    @Override
    protected void write() {
        writeInt(id);
        writeString(name);
        writeList(skills);
        writeMap(idNameMap);
    }

    @Override
    public String toString() {
        return "id:" + id + ",name:" + name + ",skills:" + skills + ",idNameMap:" + idNameMap;
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.setId(1);
        player.setName("张三");
        player.setSkills(new ArrayList<>(Arrays.asList(101)));
        Map<Integer, String> map = new HashMap<>();
        map.put(player.getId(), player.getName());
        player.setIdNameMap(map);
        byte[] bytes = player.getBytes();
        System.out.println(Arrays.toString(bytes));

        Player memoryPlayer = new Player();
        memoryPlayer.readFromBytes(bytes);
        System.out.println(memoryPlayer);
    }
}

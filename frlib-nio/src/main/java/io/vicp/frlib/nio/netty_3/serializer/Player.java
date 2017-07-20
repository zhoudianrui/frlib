package io.vicp.frlib.nio.netty_3.serializer;

import lombok.Data;

import java.util.*;

/**
 * <p>玩家<p>
 *
 * @author zhoudr
 * @version $Id: Player, v 0.1 2017/7/15 15:54 zhoudr Exp $$
 */
@Data
public class Player extends Serializer {

    private int id;

    private int age;

    private String name;

    private List<Integer> skill;

    private Map<Integer, String> idNameMap;

    @Override
    protected void write() {
        writeInt(id);
        writeInt(age);
        writeString(name);
        writeList(skill);
        writeMap(idNameMap);
    }

    @Override
    protected void read() {
        this.id = readInt();
        this.age = readInt();
        this.name = readString();
        this.skill = readList(Integer.class);
        this.idNameMap = readMap(Integer.class, String.class);
    }

    public String toString() {
        return "id:" + id + ", age:" + age + ", name:" + name + ", skill:" + Arrays.toString(skill.toArray(new Integer[]{}))
                + ", idEntityMap:" + idNameMap;
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.setId(1);
        player.setName("张三");
        player.setAge(20);
        player.setSkill(new ArrayList<>(Arrays.asList(13, 101)));
        Map<Integer, String> idEntityMap= new HashMap<>();
        idEntityMap.put(1, player.getName());
        player.setIdNameMap(idEntityMap);

        System.out.println(player);
        byte[] bytes = player.getBytes();
        System.out.println(Arrays.toString(bytes));

        Player originalPlayer = (Player) player.readFromBytes(bytes);
        System.out.println(originalPlayer);
    }
}

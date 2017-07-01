package io.vicp.frlib.nio.netty_3.binaryprotocol.objectecho;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * description : 普通对象
 * user : zhoudr
 * time : 2017/6/28 14:40
 */
@Data
@ToString(exclude = {"id"})
public class User implements Serializable{

    private Integer id;

    private String name;

    private Integer age;
}

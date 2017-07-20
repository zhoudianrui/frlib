package io.vicp.frlib.nio.netty_3.customprotocol.entity;

import lombok.Data;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: Request, v 0.1 2017/7/15 16:51 zhoudr Exp $$
 */
@Data
public class Request {

    public short module; // 所属模块

    private short command; // 命令号

    private byte[] data; // 数据

    public int getDataLength() { // 数据长度
        return data.length;
    }
}

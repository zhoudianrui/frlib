package io.vicp.frlib.chat.module;

import lombok.Data;

/**
 * <p>请求对象<p>
 *
 * @author zhoudr
 * @version $Id: Request, v 0.1 2017/7/22 16:03 zhoudr Exp $$
 */
@Data
public class Request {
    private short module;

    private short command;

    private byte[] data;

    public int getDataLength() {
        return data != null ? data.length : 0;
    }
}

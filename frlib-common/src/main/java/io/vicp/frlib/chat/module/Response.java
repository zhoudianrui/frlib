package io.vicp.frlib.chat.module;

import lombok.Data;

/**
 * <p>响应<p>
 *
 * @author zhoudr
 * @version $Id: Response, v 0.1 2017/7/29 16:56 zhoudr Exp $$
 */
@Data
public class Response {
    private Request request;

    private short module;

    private short command;

    private short resultCode;

    private byte[] data;

    public Response() {

    }

    public Response(Request request) {
        module = request.getModule();
        command = request.getCommand();
    }

    public int getDataLength() {
        return data == null ? 0 : data.length;
    }
}

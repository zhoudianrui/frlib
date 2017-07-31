package io.vicp.frlib.chat.module;

import io.vicp.frlib.chat.serializer.Serializer;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: Result, v 0.1 2017/7/29 17:40 zhoudr Exp $$
 */

public class Result<T extends Serializer> {

    private int resultCode;

    private T content;

    public static <T extends Serializer> Result<T> SUCCESS(T content) {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCode.SUCCESS);
        result.setContent(content);
        return result;
    }

    public static <T extends Serializer> Result<T> FAIL(T content) {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCode.FAILED);
        result.setContent(content);
        return result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

}

package io.vicp.frlib.nio.netty_3;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/27 18:15
 */

public class Constants {
    public static final int FRAME_MAX_LENGTH = 8192; // 帧的最大长度
    public static final String LINE_DELIMITER = "\r\n"; // 传输文本的分隔符
    public static final String CMD_LINE_DELIMITER = "\n"; // 命令窗口发送文本的分隔符
    public static final int MESSAGE_HEADER = -245443256;
    public static final short STATUS_SUCCESS = 1;
}

package io.vicp.frlib.handler;

import io.vicp.frlib.chat.annotation.Command;
import io.vicp.frlib.chat.annotation.Module;
import io.vicp.frlib.chat.module.CommandEnum;
import io.vicp.frlib.chat.module.ModuleEnum;
import io.vicp.frlib.chat.module.Player;
import io.vicp.frlib.chat.module.Result;
import io.vicp.frlib.service.PlayerService;
import org.jboss.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>游戏玩家相关业务的处理器<p>
 *
 * @author zhoudr
 * @version $Id: PlayerHandler, v 0.1 2017/7/29 17:00 zhoudr Exp $$
 */
@Module(ModuleEnum.PLAYER)
@Component
public class PlayerHandler {

    @Autowired
    private PlayerService playerService;

    /**
     * 注册
     * @param channel
     * @param data
     * @return
     */
    @Command(CommandEnum.REGISTER)
    public Result<Player> register(Channel channel, byte[] data) {
        Result<Player> response;
        Player player = new Player();
        player.readFromBytes(data);
        Player result = playerService.register(player);
        if (result != null) {
            response = Result.SUCCESS(result);
        } else {
            response = Result.FAIL(result);
        }
        return response;
    }
}

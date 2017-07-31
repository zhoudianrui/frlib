package io.vicp.frlib.service;

import io.vicp.frlib.chat.module.Player;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: PlayerService, v 0.1 2017/7/29 16:34 zhoudr Exp $$
 */

@Service
public class PlayerService {

    public Player register(Player player) {
        Player response = null;
        try {
            String name = player.getName();
            // TODO 数据库查询该名称是否存在
            System.out.println("注册用户姓名：" + name);
            player.setId(new Random().nextInt(1000));
            response = new Player();
            response.setId(player.getId());
            response.setName(player.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}

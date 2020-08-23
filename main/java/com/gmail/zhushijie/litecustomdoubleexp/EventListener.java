package com.gmail.zhushijie.litecustomdoubleexp;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.List;
import java.util.Objects;

public class EventListener implements Listener {
    //不能一开始就获取config,否则在游戏内重载插件配置的功能无效
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(BlockBreakEvent e) {
        FileConfiguration config = LiteCustomDoubleEXP.INSTANCE.getConfig();
        Player player = e.getPlayer();
        if (player.hasPermission("litecustomdoubleexp.moreexp")) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                Material block = e.getBlock().getType();
                String playername = player.getName();
                String blockname = String.valueOf(block);
                List<String> getblocks = config.getStringList("blocks");
                if (getblocks.contains(blockname)) {
                    e.setExpToDrop((int) (e.getExpToDrop() * config.getDouble("more-exp")));
                    String prefix = config.getString("prefix").replace("&","§").replace("<玩家ID>",playername).replace("<倍数>",config.getString("more-exp"));
                    String title = config.getString("get-exp-title").replace("&","§").replace("<方块名>",blockname).replace("<玩家ID>",playername).replace("<倍数>",config.getString("more-exp"));
                    String subtitle = config.getString("ore-exp-subtitle").replace("&","§").replace("<方块名>",blockname).replace("<玩家ID>",playername).replace("<倍数>",config.getString("more-exp"));
                    player.sendTitle(title, subtitle, 10, 70, 20);
                    if (Objects.equals(config.getString("broadcast"), "true")) {
                        String broadcastmsg = config.getString("ore-broadcast").replace("&","§").replace("<方块名>",blockname).replace("<玩家ID>",playername).replace("<倍数>",config.getString("more-exp"));
                        Bukkit.broadcastMessage(prefix + broadcastmsg);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent e) {
        FileConfiguration config = LiteCustomDoubleEXP.INSTANCE.getConfig();
        Player player = e.getPlayer();
        if (player.hasPermission("litecustomdoubleexp.moreexp")) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                if (e.getAmount() >= 0) {
                    e.setAmount((int) (e.getAmount() * config.getDouble("more-exp")));
                    String playername = player.getName();
                    String prefix = config.getString("prefix").replace("&","§").replace("<玩家ID>",playername).replace("<倍数>",config.getString("more-exp"));
                    String title = config.getString("get-exp-title").replace("&","§").replace("<玩家ID>",playername).replace("<倍数>",config.getString("more-exp"));
                    String subtitle = config.getString("other-exp-subtitle").replace("&","§").replace("<玩家ID>",playername).replace("<倍数>",config.getString("more-exp"));
                    player.sendTitle(title, subtitle, 10, 70, 20);
                    if (Objects.equals(config.getString("broadcast"), "true")) {
                        String broadcastmsg = config.getString("other-broadcast").replace("&","§").replace("<玩家ID>",playername).replace("<倍数>",config.getString("more-exp"));
                        Bukkit.broadcastMessage(prefix + broadcastmsg);
                    }
                }
            }
        }
    }
}

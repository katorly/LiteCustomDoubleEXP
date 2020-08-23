package com.gmail.zhushijie.litecustomdoubleexp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class CommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("litecustomdoubleexp")) {
            FileConfiguration config = LiteCustomDoubleEXP.INSTANCE.getConfig();
            if (args.length < 1) { //若只输入了个/lcdoubleexp则弹出插件帮助
                if (!(sender instanceof Player)) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"litecustomdoubleexp help");
                } else {
                    Player player = (Player) sender;
                    player.performCommand("litecustomdoubleexp help");
                }
            } else if (Objects.equals(args[0], "reload")) { //重载插件配置的指令 lcdoubleexp reload
                LiteCustomDoubleEXP.INSTANCE.reloadConfig();
                LiteCustomDoubleEXP.INSTANCE.saveConfig();
                sender.sendMessage(config.getString("prefix").replace("&","§") + config.getString("reload-success").replace("&","§"));
            } else if (Objects.equals(args[0], "help")) { //查看插件帮助的指令 lcdoubleexp help
                List<String> helpmessage = config.getStringList("help-message");
                int i = 0;
                for (int length = helpmessage.size(); i < length; i++) {
                    sender.sendMessage(helpmessage.get(i).replace("&","§"));
                }
            } else { //若指令输入错误
                if (!(sender instanceof Player)) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"litecustomdoubleexp help");
                } else {
                    Player player = (Player) sender;
                    player.performCommand("litecustomdoubleexp help");
                }
            }
        }
        return true;
    }
}

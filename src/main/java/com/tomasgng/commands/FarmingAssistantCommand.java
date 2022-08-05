package com.tomasgng.commands;

import com.tomasgng.FarmingAssistant;
import com.tomasgng.utils.DataUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FarmingAssistantCommand implements CommandExecutor {

    public DataUtil dataUtil = FarmingAssistant.getInstance().getDataUtil();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player!");
            return false;
        }
        Player player = (Player) sender;
        if(args.length < 1) {
            player.sendMessage("Help msg");
            return false;
        }
        if(args[0].equalsIgnoreCase("toggle")) {
            dataUtil.togglePlayersFarmingMode(player);
        } else
        if(args[0].equalsIgnoreCase("list")) {
            dataUtil.showWhitelistedGUI(player);
        } else
        if(args[0].equalsIgnoreCase("add") && args.length > 1) {
            dataUtil.addItemToWhitelist(player, args[1]);
        } else
        if(args[0].equalsIgnoreCase("remove") && args.length > 1) {
            dataUtil.removeItemFromWhitelist(player, args[1]);
        } else {
            player.sendMessage("Help msg");
            return false;
        }
        return false;
    }
}

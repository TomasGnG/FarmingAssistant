package com.tomasgng.commands;

import com.tomasgng.FarmingAssistant;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FarmingAssistantTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("farmingassistant") || cmd.getName().equalsIgnoreCase("farma")) {
            if(args.length == 1) {
                list.add("add");
                list.add("remove");
                list.add("list");
                list.add("toggle");
                return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("add")) {
                    for(Material item : Material.values()) {
                        if(item != Material.AIR)
                            list.add(item.name());
                    }
                    return StringUtil.copyPartialMatches(args[1], list, new ArrayList<>());
                } else if(args[0].equalsIgnoreCase("remove")) {
                    for(Material item : FarmingAssistant.getInstance().getDataUtil().getWhitelistedItems((Player) sender)) {
                        list.add(item.name());
                    }
                    return StringUtil.copyPartialMatches(args[1], list, new ArrayList<>());
                }
            }
        }
        return null;
    }
}

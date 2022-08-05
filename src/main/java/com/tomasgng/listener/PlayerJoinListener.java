package com.tomasgng.listener;

import com.tomasgng.FarmingAssistant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent event) {
        FarmingAssistant.getInstance().getDataUtil().createUserIfNotExists(event.getPlayer());
    }

}

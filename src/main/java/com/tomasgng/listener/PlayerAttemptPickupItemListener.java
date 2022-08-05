package com.tomasgng.listener;

import com.tomasgng.FarmingAssistant;
import com.tomasgng.utils.DataUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;

public class PlayerAttemptPickupItemListener implements Listener {

    DataUtil dataUtil = FarmingAssistant.getInstance().getDataUtil();

    @EventHandler
    public void on(PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();
        if(dataUtil.playerIsInFarmingMode(player) && !dataUtil.getWhitelistedItems(player).contains(event.getItem().getItemStack().getType())) {
            event.setCancelled(true);
        }
    }

}

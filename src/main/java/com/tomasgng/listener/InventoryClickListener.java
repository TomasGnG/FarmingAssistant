package com.tomasgng.listener;

import com.tomasgng.FarmingAssistant;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if(clickedItem != null && clickedItem.getItemMeta() != null && clickedItem.getType() != Material.AIR) {
            if(clickedItem.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(FarmingAssistant.getInstance(), "whitelist-gui"), PersistentDataType.DOUBLE)) {
                event.setCancelled(true);
            }
        }
    }

}

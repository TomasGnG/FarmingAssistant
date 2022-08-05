package com.tomasgng.utils;

import com.tomasgng.FarmingAssistant;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataUtil {

    private final File file = new File("plugins/FarmingAssistant/Data.yml");
    private final File folder = new File("plugins/FarmingAssistant");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    private final ArrayList<Player> playersInFarmingMode = new ArrayList<>();
    private ConfigUtil configUtil = FarmingAssistant.getInstance().getConfigUtil();

    public DataUtil() {
        createFiles();
    }

    private void createFiles() {
        if(!folder.exists()) folder.mkdirs();

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void save() {
        try {
            getCfg().save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reload();
    }

    public void reload() {
        setCfg(file);
    }

    public void createUserIfNotExists(Player player) {
        if(getCfg().isSet(player.getName() + ".enabled")) {
            return;
        }

        getCfg().set(player.getName() + ".whitelist", new ArrayList<String>());

        save();
    }

    public void togglePlayersFarmingMode(Player player) {
        if(playerIsInFarmingMode(player)) {
            playersInFarmingMode.remove(player);
            player.sendMessage(configUtil.getTogglingModeOff());
            if(configUtil.getSoundWhenToggleModeBoolean())
                player.playSound(player.getLocation(), configUtil.getSoundWhenToggleMode(), 1.0f, 1.0f);
            return;
        }
        playersInFarmingMode.add(player);
        player.sendMessage(configUtil.getTogglingModeOn());
        if(configUtil.getSoundWhenToggleModeBoolean())
            player.playSound(player.getLocation(), configUtil.getSoundWhenToggleMode(), 1.0f, 1.0f);
    }

    public void showWhitelistedGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, Component.text(configUtil.getWhitelistedItemsGUIName()));

        int i = 0;
        for (Material item : getWhitelistedItems(player)) {
            inventory.setItem(i, new ItemBuilder(item).setKey("whitelist-gui").build());
            i++;
        }
        player.openInventory(inventory);
    }

    public void addItemToWhitelist(Player player, String item) {
        Material material = Material.getMaterial(item);
        if(material == null) {
            player.sendMessage(configUtil.getInvalidItem(item));
            return;
        }
        if(material == Material.AIR) {
            player.sendMessage(configUtil.getInvalidItem(item));
            return;
        }
        if(itemAlreadyWhitelisted(player, material)) {
            player.sendMessage(configUtil.getItemAlreadyWhitelisted(material.name()));
            return;
        }
        ArrayList<String> whitelistedItems = getWhitelistedItemsRaw(player);
        whitelistedItems.add(material.name());
        getCfg().set(player.getName() + ".whitelist", whitelistedItems);
        save();
        player.sendMessage(configUtil.getItemAddedToWhitelist(material.name()));
    }

    public void removeItemFromWhitelist(Player player, String item) {
        Material material = Material.getMaterial(item);
        if(material == null) {
            player.sendMessage(configUtil.getInvalidItem(item));
            return;
        }
        if(!itemAlreadyWhitelisted(player, material)) {
            player.sendMessage(configUtil.getItemNotWhitelisted(material.name()));
            return;
        }
        ArrayList<String> whitelistedItems = getWhitelistedItemsRaw(player);
        whitelistedItems.remove(material.name());
        getCfg().set(player.getName() + ".whitelist", whitelistedItems);
        save();
        player.sendMessage(configUtil.getItemRemovedFromWhitelist(material.name()));
    }


    public boolean playerIsInFarmingMode(Player player) {
        reload();
        return playersInFarmingMode.contains(player);
    }

    public ArrayList<Material> getWhitelistedItems(Player player) {
        ArrayList<Material> list = new ArrayList<>();
        for (String item : getCfg().getStringList(player.getName() + ".whitelist")) {
            list.add(Material.getMaterial(item));
        }
        return list;
    }

    public ArrayList<String> getWhitelistedItemsRaw(Player player) {
        return (ArrayList<String>) getCfg().getStringList(player.getName() + ".whitelist");
    }

    private boolean itemAlreadyWhitelisted(Player player, Material material) {
        if(getWhitelistedItems(player).contains(material)) {
            return true;
        }
        return false;
    }

    private YamlConfiguration getCfg() {
        return cfg;
    }

    private void setCfg(File cfgFile) {
        this.cfg = YamlConfiguration.loadConfiguration(cfgFile);
    }
}

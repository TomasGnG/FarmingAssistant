package com.tomasgng.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {

    private final File file = new File("plugins/FarmingAssistant/Config.yml");
    private final File folder = new File("plugins/FarmingAssistant");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    private boolean soundWhenToggleModeBoolean;
    private String soundWhenToggleMode;
    private String prefix;
    private String togglingModeOn;
    private String togglingModeOff;
    private String whitelistedItemsGUIName;
    private String invalidItem;
    private String itemAlreadyWhitelisted;
    private String itemAddedToWhitelist;
    private String itemNotWhitelisted;
    private String itemRemovedFromWhitelist;

    public ConfigUtil() {
        createFiles();
        reload();
    }

    private void createFiles() {
        if(!folder.exists()) folder.mkdirs();

        if(!file.exists()) {
            try {
                file.createNewFile();

                getCfg().set("SoundWhenToggleMode.enabled", true);
                getCfg().set("SoundWhenToggleMode.sound", Sound.ENTITY_PLAYER_LEVELUP.name());
                getCfg().set("Messages.Prefix", "§aFarmA §8| §7");
                getCfg().set("Messages.TogglingMode.on", "%prefix%§aYou entered the farming mode!");
                getCfg().set("Messages.TogglingMode.off", "%prefix%§cYou left the farming mode!");
                getCfg().set("Messages.WhitelistedItemsGUIName", "%prefix%§aYour whitelisted items!");
                getCfg().set("Messages.InvalidItem", "%prefix%§cItem %item% is not valid!");
                getCfg().set("Messages.ItemAlreadyWhitelisted", "%prefix%§cItem %item% already in whitelist!");
                getCfg().set("Messages.ItemAddedToWhitelist", "%prefix%§aItem %item% was added to your whitelist!");
                getCfg().set("Messages.ItemNotWhitelisted", "%prefix%§cItem %item% is not whitelisted!");
                getCfg().set("Messages.ItemRemovedFromWhitelist", "%prefix%§aItem %item% was removed from your whitelist!");

                save();
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
        soundWhenToggleModeBoolean = getCfg().getBoolean("SoundWhenToggleMode.enabled");
        soundWhenToggleMode = getCfg().getString("SoundWhenToggleMode.sound");
        prefix = getCfg().getString("Messages.Prefix");
        togglingModeOn = getCfg().getString("Messages.TogglingMode.on");
        togglingModeOff = getCfg().getString("Messages.TogglingMode.off");
        whitelistedItemsGUIName = getCfg().getString("Messages.WhitelistedItemsGUIName");
        invalidItem = getCfg().getString("Messages.InvalidItem");
        itemAlreadyWhitelisted = getCfg().getString("Messages.ItemAlreadyWhitelisted");
        itemAddedToWhitelist = getCfg().getString("Messages.ItemAddedToWhitelist");
        itemNotWhitelisted = getCfg().getString("Messages.ItemNotWhitelisted");
        itemRemovedFromWhitelist = getCfg().getString("Messages.ItemRemovedFromWhitelist");
    }

    private void setCfg(File _file) {
        cfg = YamlConfiguration.loadConfiguration(_file);
    }

    public YamlConfiguration getCfg() {
        return cfg;
    }

    public Sound getSoundWhenToggleMode() {
        if(soundWhenToggleMode == null) {
            Bukkit.getLogger().severe("Sound configured in Config.yml is invalid. Using default sound...");
            return Sound.ENTITY_PLAYER_LEVELUP;
        }
        return Sound.valueOf(soundWhenToggleMode);
    }

    public boolean getSoundWhenToggleModeBoolean() {
        return soundWhenToggleModeBoolean;
    }

    public String getPrefix() {
        reload();
        if(prefix == null) {
            Bukkit.getLogger().severe("Prefix configured in Config.yml is invalid. Using default prefix...");
            return "§aFarmA §8| §7";
        }
        return prefix.replaceAll("&", "§");
    }

    public String getTogglingModeOn() {
        reload();
        if(togglingModeOn == null) {
            Bukkit.getLogger().severe("TogglingMode.on configured in Config.yml is invalid. Using default message...");
            return "%prefix%§aYou entered the farming mode!".replace("%prefix%", getPrefix());
        }
        return togglingModeOn.replace("%prefix%", getPrefix()).replaceAll("&", "§");
    }

    public String getTogglingModeOff() {
        reload();
        if(togglingModeOff == null) {
            Bukkit.getLogger().severe("TogglingMode.on configured in Config.yml is invalid. Using default message...");
            return "%prefix%§cYou left the farming mode!".replace("%prefix%", getPrefix());
        }
        return togglingModeOff.replace("%prefix%", getPrefix()).replaceAll("&", "§");
    }

    public String getWhitelistedItemsGUIName() {
        reload();
        if(whitelistedItemsGUIName == null) {
            Bukkit.getLogger().severe("WhitelistedGUIName configured in Config.yml is invalid. Using default name...");
            return "%prefix%§aYour whitelisted items!".replace("%prefix%", getPrefix());
        }
        return whitelistedItemsGUIName.replace("%prefix%", getPrefix());
    }

    public String getInvalidItem(String item) {
        reload();
        if(invalidItem == null) {
            Bukkit.getLogger().severe("InvalidItem configured in Config.yml is invalid. Using default message...");
            return "%prefix%§cItem %item% is not valid!".replace("%prefix%", getPrefix()).replace("%item%", item);
        }
        return invalidItem.replace("%prefix%", getPrefix()).replace("%item%", item);
    }

    public String getItemAlreadyWhitelisted(String item) {
        reload();
        if(itemAlreadyWhitelisted == null) {
            Bukkit.getLogger().severe("ItemAlreadyWhitelisted configured in Config.yml is invalid. Using default message...");
            return "%prefix%§cItem %item% already in whitelist!".replace("%prefix%", getPrefix()).replace("%item%", item);
        }
        return itemAlreadyWhitelisted.replace("%prefix%", getPrefix()).replace("%item%", item);
    }

    public String getItemAddedToWhitelist(String item) {
        reload();
        if(itemAddedToWhitelist == null) {
            Bukkit.getLogger().severe("ItemAlreadyWhitelisted configured in Config.yml is invalid. Using default message...");
            return "%prefix%§cItem %item% was added to your whitelist!".replace("%prefix%", getPrefix()).replace("%item%", item);
        }
        return itemAddedToWhitelist.replace("%prefix%", getPrefix()).replace("%item%", item);
    }

    public String getItemNotWhitelisted(String item) {
        reload();
        if(itemNotWhitelisted == null) {
            Bukkit.getLogger().severe("ItemNotWhitelisted configured in Config.yml is invalid. Using default message...");
            return "%prefix%§aItem %item% is not whitelisted!".replace("%prefix%", getPrefix()).replace("%item%", item);
        }
        return itemNotWhitelisted.replace("%prefix%", getPrefix()).replace("%item%", item);
    }

    public String getItemRemovedFromWhitelist(String item) {
        reload();
        if(itemRemovedFromWhitelist == null) {
            Bukkit.getLogger().severe("ItemRemovedFromWhitelist configured in Config.yml is invalid. Using default message...");
            return "%prefix%§aItem %item% was removed from your whitelist!".replace("%prefix%", getPrefix()).replace("%item%", item);
        }
        return itemRemovedFromWhitelist.replace("%prefix%", getPrefix()).replace("%item%", item);
    }

}

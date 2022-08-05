package com.tomasgng;

import com.tomasgng.commands.FarmingAssistantCommand;
import com.tomasgng.commands.FarmingAssistantTabCompleter;
import com.tomasgng.listener.InventoryClickListener;
import com.tomasgng.listener.PlayerAttemptPickupItemListener;
import com.tomasgng.listener.PlayerJoinListener;
import com.tomasgng.utils.ConfigUtil;
import com.tomasgng.utils.DataUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FarmingAssistant extends JavaPlugin {

    private static FarmingAssistant instance;
    private DataUtil dataUtil;
    private ConfigUtil configUtil;

    @Override
    public void onEnable() {
        setInstance(this);
        setConfigUtil(new ConfigUtil());
        setDataUtil(new DataUtil());
        Metrics metrics = new Metrics(this, 16038);

        register();
    }

    private void register() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new PlayerJoinListener(), this);
        manager.registerEvents(new InventoryClickListener(), this);
        manager.registerEvents(new PlayerAttemptPickupItemListener(), this);

        getCommand("farmingassistant").setExecutor(new FarmingAssistantCommand());
        getCommand("farmingassistant").setTabCompleter(new FarmingAssistantTabCompleter());
    }

    private void setInstance(FarmingAssistant instance) {
        FarmingAssistant.instance = instance;
    }

    public static FarmingAssistant getInstance() {
        return instance;
    }

    private void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public DataUtil getDataUtil() {
        return dataUtil;
    }

    private void setConfigUtil(ConfigUtil configUtil) {
        this.configUtil = configUtil;
    }

    public ConfigUtil getConfigUtil() {
        return configUtil;
    }
}

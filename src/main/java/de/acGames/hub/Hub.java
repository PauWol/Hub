package de.acGames.hub;

import de.acGames.hub.commands.SpawnTp;
import de.acGames.hub.commands.SetSpawn;
import de.acGames.hub.helpers.Config;
import de.acGames.hub.listeners.Movement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Hub extends JavaPlugin {

    public static Hub plugin;
    public Config configClass;
    public Set<Player> PlayerMoveCache = new HashSet<>();

    @Override
    public void onEnable() {
        getCommand("spawnset").setExecutor(new SetSpawn());
        getCommand("spawn").setExecutor(new SpawnTp());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Movement(),this);


        saveDefaultConfig();
        plugin = this;
        configClass = new Config();

        getLogger().info("Hub enabled!");

    }

    @Override
    public void onDisable() {
        getLogger().info("Hub disabled!");
    }
}

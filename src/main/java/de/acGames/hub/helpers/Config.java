package de.acGames.hub.helpers;

import de.acGames.hub.Hub;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public FileConfiguration config = Hub.plugin.getConfig();

    public Location getLocation(String path) {
        return this.config.getLocation(path);
    }

    public boolean isLocation(String path) {
        return  this.config.contains(path);
    }

    public Integer getInt(String path) {
        return this.config.getInt(path);
    }

    public void setInt(String path,Integer number) {
        this.config.set(path,number);
        Hub.plugin.saveConfig();
        this.config = Hub.plugin.getConfig();
    }

    public void defaultCheck() {
        if (!this.config.contains("teleport.timeout")) {
            this.config.set("teleport.timeout",5);
        }
    }

    public void setLocation(String path,Location location) {
        this.config.set(path,location);
        Hub.plugin.saveConfig();
        this.config = Hub.plugin.getConfig();
    }
}

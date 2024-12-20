package de.acGames.hub.commands;

import de.acGames.hub.Hub;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnTp implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player player)) { commandSender.sendMessage(ChatColor.RED + "Executor has to be player!"); return false;};
        if (! Hub.plugin.configClass.isLocation("spawn.location")) { commandSender.sendMessage(ChatColor.RED + "No spawn has been set!"); return false; }
        Hub.plugin.PlayerMoveCache.add(player);
        countdownAndTeleport(player);

        return false;
    }


    private void countdownAndTeleport(Player player) {
        new BukkitRunnable() {
            int countdown = Hub.plugin.configClass.getInt("teleport.timeout");

             @Override
            public void run() {
                 player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1, false, false, false));
                if (!Hub.plugin.PlayerMoveCache.contains(player)) {
                    //player.sendMessage(ChatColor.YELLOW + "You moved, teleport canceled.");
                    player.sendTitle(ChatColor.RED + "Teleport Cancelled", null, 10, 40, 10);


                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    cancel(); // Stop the task
                    return;
                }

                if (countdown <= 0) {
                    // Teleport the player
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    player.teleport(Hub.plugin.configClass.getLocation("spawn.location"));
                    //player.sendMessage(ChatColor.GREEN + "You have been teleported!");
                    player.sendTitle(ChatColor.GREEN + "You have been", ChatColor.YELLOW + "teleported!", 10, 40, 10);


                    cancel(); // Stop the task
                } else {
                    // Notify the player of the countdown
                    player.sendTitle(ChatColor.YELLOW + "Teleporting in", ChatColor.RED + "" + countdown + "s", 10, 40, 10);

                    //player.sendMessage( ChatColor.BLUE + "Teleporting in " + countdown + " seconds...");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    countdown--;
                }
            }
        }.runTaskTimer(Hub.plugin, 0L, 20L); // Run every second (20 ticks)
    }
}

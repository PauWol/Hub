package de.acGames.hub.commands;

import de.acGames.hub.Hub;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetSpawn implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player player)) { commandSender.sendMessage(ChatColor.RED + "Executor has to be player!"); return false;};
        if (args.length < 2) { player.sendMessage(ChatColor.RED + "Usage: /spawnset set <spawn|timeout> <seconds>"); return false; };
        if (!args[0].equalsIgnoreCase("set")) { player.sendMessage(ChatColor.RED + "Usage: /spawnset set <spawn|timeout> <seconds>"); return false;};

        switch (args[1].toLowerCase()) {
            case "spawn":
                Hub.plugin.configClass.setLocation("spawn.location", player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Spawn set successful!");
            case "timeout":
                if (args.length >= 3) {
                    Hub.plugin.configClass.setInt("teleport.timeout", Integer.parseInt(args[2]));
                    player.sendMessage(ChatColor.GREEN + "Teleport timeout set to " + args[2] + ".");
                }
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 1 ) { list.add("set"); };
        if (args.length == 2 ) { list.add("spawn"); list.add("timeout"); };
        if (args.length == 3 && args[1].equalsIgnoreCase("timeout")) {
            list.add(String.valueOf(Hub.plugin.configClass.getInt("teleport.timeout"))); list.add("3");
        }

        return list;
    }
}

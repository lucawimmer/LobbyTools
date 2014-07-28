package de.lucawimmer.lobbytools.commands;

import de.lucawimmer.lobbytools.LobbyTools;
import de.lucawimmer.lobbytools.utils.SimpleConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class LobbyToolsCommand implements CommandExecutor {
    private SimpleConfig config;

    public LobbyToolsCommand() {
        this.config = LobbyTools.getDefaultConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = null;
        if (!(sender instanceof ConsoleCommandSender)) p = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("lobbytools.admin.reload")) {
            config.reloadConfig();
            if (sender instanceof ConsoleCommandSender)
                Bukkit.getLogger().info("The configuration file for LobbyTools was reloaded successfully.");
            else
                sender.sendMessage("§c[§6LobbyTools§c] §fThe configuration file was reloaded successfully.");
        } else if (args.length == 1 && (args[0].equalsIgnoreCase("toggle") || args[0].equalsIgnoreCase("t")) && sender.hasPermission("lobbytools.admin.toggle") && !(sender instanceof ConsoleCommandSender)) {
            if (LobbyTools.TOGGLE.contains(p)) {
                LobbyTools.TOGGLE.remove(p);
                sender.sendMessage("§c[§6LobbyTools§c] §fYou're now restricted by LobbyTools again.");
            } else {
                LobbyTools.TOGGLE.add(p);
                sender.sendMessage("§c[§6LobbyTools§c] §fYou have no LobbyTools restrictions anymore.");
            }
        } else if (args.length == 1 && (args[0].equalsIgnoreCase("globalmute") || args[0].equalsIgnoreCase("mute") || args[0].equalsIgnoreCase("gm")) && sender.hasPermission("lobbytools.admin.globalmute") && !(sender instanceof ConsoleCommandSender)) {
            if (!LobbyTools.CHAT) {
                LobbyTools.CHAT = true;
                sender.sendMessage("§c[§6LobbyTools§c] §fThe LobbyTools.CHAT is now muted.");
            } else if (LobbyTools.CHAT) {
                LobbyTools.CHAT = false;
                sender.sendMessage("§c[§6LobbyTools§c] §fThe LobbyTools.CHAT is now open.");
            }
        } else if (args.length == 1 && (args[0].equalsIgnoreCase("setspawn") || args[0].equalsIgnoreCase("spawnset") || args[0].equalsIgnoreCase("sp")) && sender.hasPermission("lobbytools.admin.setspawn") && !(sender instanceof ConsoleCommandSender)) {
            config.saveLocation("lobbytools.exact-spawn-loc", ((Player) sender).getLocation());
            sender.sendMessage("§c[§6LobbyTools§c] §fYou have set the exact spawn-point.");
            config.saveConfig();
        } else if (args.length == 1 && (args[0].equalsIgnoreCase("settploc") || args[0].equalsIgnoreCase("settl") || args[0].equalsIgnoreCase("setteleport")) && sender.hasPermission("lobbytools.admin.teleportloc") && !(sender instanceof ConsoleCommandSender)) {
            config.saveLocation("lobbytools.teleport-height-location", ((Player) sender).getLocation());
            sender.sendMessage("§c[§6LobbyTools§c] §fYou have set the teleport-height location.");
            config.saveConfig();
        } else {
            if ((sender instanceof ConsoleCommandSender))
                Bukkit.getLogger().info("This command is player only.");
            else if (!sender.hasPermission("lobbytools.admin.setspawn") || !sender.hasPermission("lobbytools.admin.globalmute"))
                sender.sendMessage("§c[§6LobbyTools§c] §fYou don't have the permissions to do that!");
            else
                sender.sendMessage("§c[§6LobbyTools§c] §fWrong syntax! Use /lobbytools <reload/toggle/globalmute/setpsawn>");
        }
        return true;
    }

}

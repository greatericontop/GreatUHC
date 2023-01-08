package io.github.greatericontop.greatuhc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GreatUHCCommand implements CommandExecutor {

    private final GreatUHCMain plugin;
    public GreatUHCCommand(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§3GreatUHC by greateric");
            sender.sendMessage("§3Usage: /greatuhc [double-heads | powerful-heads]");
            return false;
        }
        if (args[0].equals("double-heads")) {
            plugin.uhcDoubleHeads = !plugin.uhcDoubleHeads;
            sender.sendMessage(String.format("§cDouble Heads §3is now %s", plugin.uhcDoubleHeads ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("powerful-heads")) {
            plugin.uhcPowerfulHeads = !plugin.uhcPowerfulHeads;
            sender.sendMessage(String.format("§cPowerful Heads §3is now %s", plugin.uhcPowerfulHeads ? "§2ON" : "§4OFF"));
            return true;
        }
        return false;
    }

}

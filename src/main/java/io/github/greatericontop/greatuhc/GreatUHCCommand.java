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
            sender.sendMessage("");
            sender.sendMessage(String.format("§cDouble Heads§7: %s", plugin.uhcDoubleHeads ? "§2ON" : "§4OFF"));
            sender.sendMessage(String.format("§cPowerful Heads§7: %s", plugin.uhcPowerfulHeads ? "§2ON" : "§4OFF"));
            sender.sendMessage(String.format("§cSurvivalism§7: %s", plugin.uhcSurvivalism ? "§2ON" : "§4OFF"));
            sender.sendMessage("");
            sender.sendMessage(String.format("§eDebug Mode§7: %s", plugin.debugMode ? "§2ON" : "§4OFF"));
            sender.sendMessage("§3Usage: /uhc [double-heads | powerful-heads | survivalism]");
            return true;
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
        if (args[0].equals("survivalism")) {
            plugin.uhcSurvivalism = !plugin.uhcSurvivalism;
            sender.sendMessage(String.format("§cSurvivalism §3is now %s", plugin.uhcSurvivalism ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("debug-mode")) {
            plugin.debugMode = !plugin.debugMode;
            sender.sendMessage(String.format("§cDebug Mode §3is now %s", plugin.debugMode ? "§2ON" : "§4OFF"));
            return true;
        }
        return false;
    }

}

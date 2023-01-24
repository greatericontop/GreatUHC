package io.github.greatericontop.greatuhc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipesCommand implements CommandExecutor {

    private static final String NAMESPACE = "uhc";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cPlayers only!");
            return true;
        }

        Bukkit.recipeIterator().forEachRemaining(recipe -> {
            if (recipe instanceof ShapedRecipe shapedRecipe && shapedRecipe.getKey().getNamespace().equals(NAMESPACE)) {
                player.discoverRecipe(shapedRecipe.getKey());
            }
            if (recipe instanceof ShapelessRecipe shapelessRecipe && shapelessRecipe.getKey().getNamespace().equals(NAMESPACE)) {
                player.discoverRecipe(shapelessRecipe.getKey());
            }
        });

        player.sendMessage("§aAll custom recipes unlocked!");
        return true;
    }

}

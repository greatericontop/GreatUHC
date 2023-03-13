package io.github.greatericontop.greatuhc;

/*
 * Copyright (C) 2023-present greateric.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty  of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

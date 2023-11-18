package io.github.greatericontop.greatuhc.customitems;

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

import io.github.greatericontop.greatuhc.game.GameManager;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PeriodicItemUpgradeListener implements Listener {

    private GameManager gameManager;
    public PeriodicItemUpgradeListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAnyInteract(PlayerInteractEvent event) {
        ItemStack mainHandItem = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta im = mainHandItem.getItemMeta();
        if (im == null)  return;

        int pvpTime = gameManager.getPlugin().getConfig().getInt("pvp_time");

        if (im.getPersistentDataContainer().has(new NamespacedKey("uhc", "apprentice_sword"), PersistentDataType.INTEGER)) {
            int previousLevel = im.getEnchantLevel(Enchantment.DAMAGE_ALL);
            im.removeEnchant(Enchantment.DAMAGE_ALL);
            if (gameManager.getCurrentPhase() == GameManager.GamePhase.DEATHMATCH) {
                im.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
                if (previousLevel != 3) {
                    event.getPlayer().sendMessage("§aYour §cApprentice Sword §areceived Sharpness III");
                }
            } else if (gameManager.getCurrentPhase() == GameManager.GamePhase.PVP && gameManager.getTicksLeft() < pvpTime-12000) {
                im.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                if (previousLevel != 2) {
                    event.getPlayer().sendMessage("§aYour §cApprentice Sword §areceived Sharpness II");
                }
            } else if (gameManager.getCurrentPhase() == GameManager.GamePhase.PVP) {
                im.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                if (previousLevel != 1) {
                    event.getPlayer().sendMessage("§aYour §cApprentice Sword §areceived Sharpness I");
                }
            }
            mainHandItem.setItemMeta(im);
        }

        if (im.getPersistentDataContainer().has(new NamespacedKey("uhc", "apprentice_bow"), PersistentDataType.INTEGER)) {
            int previousLevel = im.getEnchantLevel(Enchantment.ARROW_DAMAGE);
            im.removeEnchant(Enchantment.ARROW_DAMAGE);
            if (gameManager.getCurrentPhase() == GameManager.GamePhase.DEATHMATCH) {
                im.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
                if (previousLevel != 2) {
                    event.getPlayer().sendMessage("§aYour §cApprentice Bow §areceived Power II");
                }
            } else if (gameManager.getCurrentPhase() == GameManager.GamePhase.PVP && gameManager.getTicksLeft() < pvpTime-12000) {
                im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                if (previousLevel != 1) {
                    event.getPlayer().sendMessage("§aYour §cApprentice Bow §areceived Power I");
                }
            }
            mainHandItem.setItemMeta(im);
        }
    }

}

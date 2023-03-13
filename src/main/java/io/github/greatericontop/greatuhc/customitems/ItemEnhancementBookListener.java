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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class ItemEnhancementBookListener implements Listener {

    private boolean checkEnhancementBook(ItemStack itemStack) {
        if (itemStack == null)  return false;
        ItemMeta im = itemStack.getItemMeta();
        if (im == null)  return false;
        if (im.getLore() == null)  return false;
        return im.getLore().get(0).equals("Enhancement Book");
    }


    @EventHandler()
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null)  return;
        if (event.getView().getType() != InventoryType.ANVIL)  return;
        Player player = (Player) event.getWhoClicked();
        if (event.getRawSlot() == 2 && checkEnhancementBook(event.getClickedInventory().getItem(1))) {
            ItemStack newItem = event.getCurrentItem().enchantWithLevels(30, false, new Random());
            event.setCurrentItem(newItem);
            event.getWhoClicked().setItemOnCursor(event.getCurrentItem());
            event.getClickedInventory().clear();
            player.sendMessage("§3Success!");
        }
    }

}

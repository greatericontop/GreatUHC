package io.github.greatericontop.greatuhc.customitems;

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

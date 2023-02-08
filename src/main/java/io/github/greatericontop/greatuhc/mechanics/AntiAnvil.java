package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class AntiAnvil implements Listener {
    private static final NamespacedKey NO_ANVIL_KEY = new NamespacedKey("uhc", "noanvil");

    @EventHandler(priority = EventPriority.HIGH)
    public void onAnvilSource(PrepareAnvilEvent event) {
        ItemStack source = event.getInventory().getItem(0);
        ItemStack sacrifice = event.getInventory().getItem(1);
        if (source == null)  return;
        // only disable anvil when there is also no sacrifice item
        // if there's no sacrifice item you can only rename, which is ok
        if (sacrifice == null)  return;
        ItemMeta imSource = source.getItemMeta();
        ItemMeta imSacrifice = sacrifice.getItemMeta();
        if (isAnvilDisabled(imSource) || isAnvilDisabled(imSacrifice)) {
            // I COULD try setting this to an error item, but that requires checking InventoryClickEvent
            // to delete it if someone actually takes it, and I'm too lazy for that.
            event.setResult(null);
            event.getViewers().get(0).sendMessage("§cYou can't use this in an anvil!");
        }
    }

    private boolean isAnvilDisabled(ItemMeta im) {
        return im != null && im.getPersistentDataContainer().has(NO_ANVIL_KEY, PersistentDataType.INTEGER);
    }

    public static void disallowAnvil(ItemMeta im) {
        im.getPersistentDataContainer().set(NO_ANVIL_KEY, PersistentDataType.INTEGER, 1);
    }

}

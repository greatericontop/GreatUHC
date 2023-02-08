package io.github.greatericontop.greatuhc.customitems;

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

public class ItemBloodlustListener implements Listener {

    private GameManager gameManager;
    public ItemBloodlustListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAnyInteract(PlayerInteractEvent event) {
        ItemStack mainHandItem = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta im = mainHandItem.getItemMeta();
        if (im == null)  return;
        if (im.getPersistentDataContainer().has(new NamespacedKey("uhc", "bloodlust"), PersistentDataType.INTEGER)) {
            im.removeEnchant(Enchantment.DAMAGE_ALL);
            if (gameManager.getCurrentPhase() == GameManager.GamePhase.PVP) {
                im.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            }
            if (gameManager.getCurrentPhase() == GameManager.GamePhase.PVP && gameManager.getTicksLeft() < GameManager.PVP_TIME-300) {
                im.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
            }
            if (gameManager.getCurrentPhase() == GameManager.GamePhase.DEATHMATCH) {
                im.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
            }
            mainHandItem.setItemMeta(im);
        }
    }

}

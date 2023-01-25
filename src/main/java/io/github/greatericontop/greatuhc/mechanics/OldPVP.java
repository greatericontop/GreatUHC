package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class OldPVP implements Listener {

    @EventHandler()
    public void onCraft(CraftItemEvent event) {
        Material resultType = event.getRecipe().getResult().getType();
        if (resultType == Material.SHIELD) {
            event.getWhoClicked().sendMessage("§cShields are not available in UHC.");
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGH) // runs later
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))  return;
        ItemStack meleeItem = player.getInventory().getItemInMainHand();
        Material mat = meleeItem.getType();
        if (mat == Material.WOODEN_AXE || mat == Material.STONE_AXE || mat == Material.IRON_AXE || mat == Material.GOLDEN_AXE || mat == Material.DIAMOND_AXE || mat == Material.NETHERITE_AXE) {
            event.setDamage(event.getDamage() * 0.01);
            player.sendActionBar("§cYou shouldn't use axes to attack in UHC.");
        }
    }

}

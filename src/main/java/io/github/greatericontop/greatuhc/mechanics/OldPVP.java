package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class OldPVP implements Listener {

    @EventHandler()
    public void onCraft(CraftItemEvent event) {
        Material resultType = event.getRecipe().getResult().getType();
        if (resultType == Material.WOODEN_AXE || resultType == Material.STONE_AXE || resultType == Material.IRON_AXE || resultType == Material.GOLDEN_AXE || resultType == Material.DIAMOND_AXE) {
            ItemStack newResult = event.getInventory().getResult();
            ItemMeta im = newResult.getItemMeta();
            im.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "OldPVP", 0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            newResult.setItemMeta(im);
            event.getInventory().setResult(newResult);
        }
        if (resultType == Material.SHIELD) {
            event.getWhoClicked().sendMessage("§cShields are not available in UHC.");
            event.setCancelled(true);
        }

    }

}

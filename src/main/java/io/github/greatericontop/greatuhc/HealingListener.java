package io.github.greatericontop.greatuhc;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealingListener implements Listener {

    @EventHandler()
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player victim = (Player) event.getEntity();
            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta im = (SkullMeta) head.getItemMeta();
            im.setOwningPlayer(victim);
            im.setDisplayName(String.format("§c%s§7's §bHead §e§lRIGHT CLICK", victim.getName()));
            head.setItemMeta(im);
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);
        }
    }

    @EventHandler()
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.PLAYER_HEAD) {
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 0));
        player.sendMessage("§aYou ate a player head!");
        item.setAmount(item.getAmount() - 1);
    }

    @EventHandler()
    public void onEat(PlayerItemConsumeEvent event) {
        Material foodType = event.getItem().getType();
        if (foodType == Material.GOLDEN_APPLE) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
        }
        if (foodType == Material.ENCHANTED_GOLDEN_APPLE) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 4));
        }
    }

}

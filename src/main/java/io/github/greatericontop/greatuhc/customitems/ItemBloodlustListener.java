package io.github.greatericontop.greatuhc.customitems;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemBloodlustListener implements Listener {
    private static final NamespacedKey BLOODLUST_DAMAGE = new NamespacedKey("uhc", "bloodlust_damage");

    private void doBloodlust(Player player, double damage) {
        ItemMeta im = player.getInventory().getItemInMainHand().getItemMeta();
        if (im == null)  return;
        if (im.getPersistentDataContainer().has(BLOODLUST_DAMAGE, PersistentDataType.DOUBLE)) {
            double newTotal = im.getPersistentDataContainer().get(BLOODLUST_DAMAGE, PersistentDataType.DOUBLE) + damage;
            player.sendMessage("§7newTotal  " + newTotal);
            im.getPersistentDataContainer().set(BLOODLUST_DAMAGE, PersistentDataType.DOUBLE, newTotal);
            int necessarySharpnessLevel;
            if (newTotal >= 600) {
                necessarySharpnessLevel = 4;
            } else if (newTotal >= 300) {
                necessarySharpnessLevel = 3;
            } else if (newTotal >= 150) {
                necessarySharpnessLevel = 2;
            } else if (newTotal >= 50) {
                necessarySharpnessLevel = 1;
            } else {
                necessarySharpnessLevel = 0;
            }
            if (im.getEnchantLevel(Enchantment.DAMAGE_ALL) != necessarySharpnessLevel) {
                im.removeEnchant(Enchantment.DAMAGE_ALL);
                //if (necessarySharpnessLevel != 0) {
                    im.addEnchant(Enchantment.DAMAGE_ALL, necessarySharpnessLevel, false);
                //}
                player.sendMessage("§aYour §cBloodlust §awas upgraded!");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
            }
            player.getInventory().getItemInMainHand().setItemMeta(im);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamageByEntityPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker) {
            doBloodlust(attacker, event.getDamage()); // note: raw damage (NOT final damage) is used here
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamageByEntityProjectile(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player shooter) {
            doBloodlust(shooter, event.getDamage());
        }
    }

}

package io.github.greatericontop.greatuhc.mechanics;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class DamageEngine implements Listener {

    private final GreatUHCMain plugin;
    public DamageEngine(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH) // runs last
    public void onDamageNonPlayers(EntityDamageEvent event) {
        if (event.getEntity() instanceof Mob) {
            // mobs take extra damage from all sources
            event.setDamage(event.getDamage() * 1.25);
        }
    }

    @EventHandler(priority = EventPriority.HIGH) // runs last
    public void onDamageSurvivalism(EntityDamageEvent event) {
        if (!plugin.uhcSurvivalism)  return;
        if (!(event.getEntity() instanceof Player))  return;
        if (event.isCancelled())  return;
        Player player = (Player) event.getEntity();
        DamageCause cause = event.getCause();

        if (cause == DamageCause.VOID
                || cause == DamageCause.ENTITY_ATTACK
                || cause == DamageCause.ENTITY_SWEEP_ATTACK
                || cause == DamageCause.PROJECTILE) {
            // TODO: check this in EntityDamageByEntityEvent
            return;
        }
        if (cause == DamageCause.MAGIC || cause == DamageCause.POISON || cause == DamageCause.WITHER) {
            plugin.debugMsg(player, "survivalism: potion damage cause, slightly reducing damage");
            event.setDamage(event.getDamage() * 0.8);
            return;
        }
        plugin.debugMsg(player, "survivalism: other damage cause, reducing damage");
        event.setDamage(event.getDamage() * 0.6);
    }

    @EventHandler(priority = EventPriority.HIGHEST) // runs last
    public void onDamageByEntitySurvivalism(EntityDamageByEntityEvent event) {
        if (!plugin.uhcSurvivalism)  return;
        if (!(event.getEntity() instanceof Player))  return;
        if (event.isCancelled())  return;
        Entity damagingEntity = event.getDamager();
        Player player = (Player) event.getEntity();
        DamageCause cause = event.getCause();

        if (cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.ENTITY_SWEEP_ATTACK) {
            if (!(damagingEntity instanceof Player)) {
                plugin.debugMsg(player, "survivalism: entity attack, reducing damage");
                event.setDamage(event.getDamage() * 0.6);
            }
            return;
        }

        if (cause == DamageCause.PROJECTILE) {
            ProjectileSource shooter = ((Projectile) damagingEntity).getShooter();
            if (!(shooter instanceof Player)) {
                plugin.debugMsg(player, "survivalism: projectile hit, reducing damage");
                event.setDamage(event.getDamage() * 0.6);
            }
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST) // runs first
    public void onDamageProtection(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))  return;
        if (event.isCancelled())  return;
        Player player = (Player) event.getEntity();

        // buff protection
        int totalProtectionLevel = 0;
        ItemStack helmet = player.getInventory().getHelmet();
        totalProtectionLevel += helmet != null ? helmet.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0;
        ItemStack chestplate = player.getInventory().getChestplate();
        totalProtectionLevel += chestplate != null ? chestplate.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0;
        ItemStack leggings = player.getInventory().getLeggings();
        totalProtectionLevel += leggings != null ? leggings.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0;
        ItemStack boots = player.getInventory().getBoots();
        totalProtectionLevel += boots != null ? boots.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0;
        if (totalProtectionLevel > 0) {
            totalProtectionLevel = Math.min(totalProtectionLevel, 18); // arbitrary threshold
            double damageMultiplier = Math.pow(0.9725, totalProtectionLevel); // multiplicative reduction makes high levels less overpowered
            plugin.debugMsg(player, "protection levels: %d | reduction: %.3f", totalProtectionLevel, 1-damageMultiplier);
            event.setDamage(event.getDamage() * damageMultiplier);
        }

        event.setDamage(event.getDamage() * 0.89); // 11% damage reduction for everything for players
    }

    @EventHandler(priority = EventPriority.LOWEST) // runs first
    public void onDamageSharpness(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player))  return; // melee attacks from players only
        if (event.isCancelled())  return;
        Player player = (Player) event.getDamager();

        ItemStack attackingItem = player.getInventory().getItemInMainHand();
        int sharpnessLevel = attackingItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
        if (sharpnessLevel > 0) {
            // add 0.75 damage per sharpness level (now 1.25 each - the first level starts at 1, so we subtract 0.5)
            // this isn't perfect as EntityDamageByEntityEvent isn't called ALWAYS
            double bonusDamage = sharpnessLevel * 0.75 - 0.5;
            // decrease the power if the attack wasn't fully charged (linearly, getCooledAttackStrength is linear)
            double adjustedBonusDamage = bonusDamage * player.getCooledAttackStrength(0.0F);
            event.setDamage(event.getDamage() + adjustedBonusDamage);
            plugin.debugMsg(player, "sharpness buff +%.2f, new damage §f%.2f", adjustedBonusDamage, event.getDamage());
        }
    }

    @EventHandler()
    public void onShootBow(EntityShootBowEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow))  return;
        AbstractArrow projectile = (AbstractArrow) event.getProjectile();
        double multi = 0.8;
        // TODO: undead bow
        // if (event.getEntity() instanceof Player) {}
        projectile.setDamage(projectile.getDamage() * multi);
    }

}

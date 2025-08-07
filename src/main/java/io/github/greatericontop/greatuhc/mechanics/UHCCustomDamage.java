package io.github.greatericontop.greatuhc.mechanics;

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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;

public class UHCCustomDamage implements Listener {

    private final GreatUHCMain plugin;
    public UHCCustomDamage(GreatUHCMain plugin) {
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
        if (!(event.getEntity() instanceof Player player))  return;
        if (event.isCancelled())  return;
        DamageCause cause = event.getCause();

        if (cause == DamageCause.VOID
                || cause == DamageCause.ENTITY_ATTACK
                || cause == DamageCause.ENTITY_SWEEP_ATTACK
                || cause == DamageCause.PROJECTILE) {
            return;
        }
        if (cause == DamageCause.MAGIC || cause == DamageCause.POISON || cause == DamageCause.WITHER) {
            plugin.debugMsg(player, "survivalism: potion damage cause, slightly reducing damage");
            event.setDamage(event.getDamage() * 0.725);
            return;
        }
        if (cause == DamageCause.LAVA || cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK) {
            plugin.debugMsg(player, "survivalism: fire damage cause, slightly reducing damage");
            event.setDamage(event.getDamage() * 0.65);
            return;
        }
        plugin.debugMsg(player, "survivalism: other damage cause, reducing damage");
        event.setDamage(event.getDamage() * 0.4);
    }

    @EventHandler(priority = EventPriority.HIGHEST) // runs last
    public void onDamageByEntitySurvivalism(EntityDamageByEntityEvent event) {
        if (!plugin.uhcSurvivalism)  return;
        if (!(event.getEntity() instanceof Player player))  return;
        if (event.isCancelled())  return;
        Entity damagingEntity = event.getDamager();
        DamageCause cause = event.getCause();

        if (cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.ENTITY_SWEEP_ATTACK) {
            if (!(damagingEntity instanceof Player)) {
                plugin.debugMsg(player, "survivalism: entity attack, reducing damage");
                event.setDamage(event.getDamage() * 0.4);
            }
            return;
        }

        if (cause == DamageCause.PROJECTILE) {
            ProjectileSource shooter = ((Projectile) damagingEntity).getShooter();
            if (!(shooter instanceof Player)) {
                plugin.debugMsg(player, "survivalism: projectile hit, reducing damage");
                event.setDamage(event.getDamage() * 0.4);
            }
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST) // runs first
    public void onDamageProtection(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player))  return;
        if (event.isCancelled())  return;

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
            double damageMultiplier = Math.pow(0.9825, totalProtectionLevel); // multiplicative reduction makes high levels less overpowered
            plugin.debugMsg(player, "protection levels: %d | reduction: %.3f", totalProtectionLevel, 1-damageMultiplier);
            event.setDamage(event.getDamage() * damageMultiplier);
        }

        event.setDamage(event.getDamage() * 0.95); // 5% damage reduction for everything for players
    }

    @EventHandler(priority = EventPriority.LOWEST) // runs first
    public void onDamageSharpness(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))  return; // melee attacks from players only
        if (event.isCancelled())  return;

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
        if (!(event.getProjectile() instanceof AbstractArrow projectile))  return;
        double multi = 0.8;
        if (event.getEntity() instanceof Player) {
            ItemStack bow = event.getBow();
            if (bow != null) {
                ItemMeta im = bow.getItemMeta();
                if (im != null) {
                    List<String> lore = im.getLore();
                    if (lore != null && lore.get(0).equals("Undead")) {
                        multi += 0.2;
                    }
                    if (lore != null && lore.get(0).equals("id: ARTEMIS_BOW")) {
                        multi += 0.1; // make it just barely stronger (not even close to power 1) since it can't be enchanted
                    }
                }
            }
        }
        projectile.setDamage(projectile.getDamage() * multi);
    }

}

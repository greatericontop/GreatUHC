package io.github.greatericontop.greatuhc.mechanics;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbsorptionEffectFix implements Listener {

    private final GreatUHCMain plugin;
    public AbsorptionEffectFix(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onAbsorptionEffectAdd(EntityPotionEffectEvent event) {
        if (!(event.getEntity() instanceof Player player))  return;
        // only applicable to the CHANGED event
        // when an active absorption effect is replaced with a WEAKER effect
        // weird things can happen: either nothing happens, or all hearts of the old effect are replenished
        if (event.getAction() == EntityPotionEffectEvent.Action.CHANGED
                && event.getNewEffect().getType().getId() == PotionEffectType.ABSORPTION.getId() // getId is deprecated but .equals/== is bugged so we have to use this
        ) {
            if (event.getOldEffect().getAmplifier() > event.getNewEffect().getAmplifier()) {
                double healthNewEffectShouldGive = 4.0 * (event.getNewEffect().getAmplifier() + 1);
                if (player.getAbsorptionAmount() < healthNewEffectShouldGive) {
                    // if this ends up being "helpful", we'll remove the old effect and let this one overwrite it
                    // setOverride does nothing, and you need to wait a tick or else the game becomes annoying
                    player.removePotionEffect(PotionEffectType.ABSORPTION);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.addPotionEffect(event.getNewEffect());
                        }
                    }.runTaskLater(plugin, 1L);
                } else {
                    // if it's not helpful, then just ignore it
                    event.setCancelled(true);
                }
            } else if (event.getOldEffect().getAmplifier() == event.getNewEffect().getAmplifier()) {
                // the amplifier is the same & duration is lower - we'll almost always replace the effect
                // (if the duration is higher it will auto-overwrite)
                // the only exception is if we haven't lost any hearts yet
                //   because then, if they're equal, we keep the longer one
                double healthNewEffectShouldGive = 4.0 * (event.getNewEffect().getAmplifier() + 1);
                if (player.getAbsorptionAmount() < healthNewEffectShouldGive
                        && event.getNewEffect().getDuration() < event.getOldEffect().getDuration()
                ) {
                    player.removePotionEffect(PotionEffectType.ABSORPTION);
                    player.addPotionEffect(event.getNewEffect()); // no delay needed here
                }
            }
        }

    }

}

package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;

public class HungerListener implements Listener {
    private static final double BASE_HUNGER_MULTI = 0.75;

    @EventHandler()
    public void onExhaustion(EntityExhaustionEvent event) {
        Player player = (Player) event.getEntity();
        // from 20 points to 0, hunger exhaustion up to 150% faster (2.5x)
        double healthPenalty = 1 + 0.075 * Math.max(0, 20 - player.getHealth() - player.getAbsorptionAmount());
        // penalty for being too far from 0, 0
        double distancePenalty = 1;
        WorldBorder border = player.getWorld().getWorldBorder();
        double directDistance = Math.max(Math.abs(player.getLocation().getX()-border.getCenter().getX()), Math.abs(player.getLocation().getZ()-border.getCenter().getZ()));
        double percentageToBorder = directDistance / border.getSize();
        if (directDistance >= 200 && percentageToBorder >= 0.8) {
            // 80% to 100% of the border (if you're at least 200 blocks out) exhaustion up to 50% faster (1.5x)
            distancePenalty = 1 + 2.5 * (percentageToBorder - 0.8);
        }
        // (the theoretical maximum penalty is 3.75x, or 2.8125x vanilla)
        event.setExhaustion(event.getExhaustion() * (float) (BASE_HUNGER_MULTI * healthPenalty * distancePenalty));
    }

}

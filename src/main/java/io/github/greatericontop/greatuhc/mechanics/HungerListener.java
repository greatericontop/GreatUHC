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
        // from 20 points to 0, hunger exhaustion up to 200% faster (3x)
        double healthPenalty = 1 + 0.1 * Math.max(0, 20 - player.getHealth() - player.getAbsorptionAmount());
        // penalty for being too far from 0, 0
        double distancePenalty = 1;
        WorldBorder border = player.getWorld().getWorldBorder();
        double directDistance = Math.max(Math.abs(player.getLocation().getX()-border.getCenter().getX()), Math.abs(player.getLocation().getZ()-border.getCenter().getZ()));
        double percentageToBorder = directDistance / border.getSize();
        if (directDistance >= 100 && percentageToBorder >= 0.75) {
            // 75% to 100% of the border (if you're at least 100 blocks out) exhaustion up to 100% faster (2x)
            distancePenalty = 1 + 4.0 * (percentageToBorder - 0.75);
        }
        event.setExhaustion(event.getExhaustion() * (float) (BASE_HUNGER_MULTI * healthPenalty * distancePenalty));
    }

}

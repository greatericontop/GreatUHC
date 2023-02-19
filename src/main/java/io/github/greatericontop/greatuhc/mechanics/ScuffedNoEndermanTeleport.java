package io.github.greatericontop.greatuhc.mechanics;

import com.destroystokyo.paper.event.entity.EndermanEscapeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ScuffedNoEndermanTeleport implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onEndermanTeleport(EndermanEscapeEvent event) {
        event.setCancelled(true);
    }

}

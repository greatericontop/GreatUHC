package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class DiamondSpreading implements Listener {

    private static final int[] dx = {1, -1, 0, 0, 0, 0};
    private static final int[] dy = {0, 0, 1, -1, 0, 0};
    private static final int[] dz = {0, 0, 0, 0, 1, -1};

    private final Random random;
    public DiamondSpreading() {
        this.random = new Random();
    }

    public boolean isExposed(Location loc) {
        for (int i = 0; i < 6; i++) {
            Material mat = loc.clone().add(dx[i], dy[i], dz[i]).getBlock().getType();
            if (mat.isAir() || !mat.isSolid()) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        if (blockType != Material.DIAMOND_ORE && blockType != Material.DEEPSLATE_DIAMOND_ORE) {
            return;
        }
        if (Math.random() >= 0.3) {
            return;
        }
        // Make some attempts to spawn a diamond ore.
        for (int i = 0; i < 3; i++) {
            Location loc = event.getBlock().getLocation();
            int offset = random.nextInt(6);
            loc.add(dx[offset], dy[offset], dz[offset]);
            // the target block must be covered (unexposed to air)
            if (isExposed(loc)) {
                continue;
            }
            // it must be stone or deepslate
            if (loc.getBlock().getType() == Material.STONE) {
                loc.getBlock().setType(Material.DIAMOND_ORE);
                return;
            }
            if (loc.getBlock().getType() == Material.DEEPSLATE) {
                loc.getBlock().setType(Material.DEEPSLATE_DIAMOND_ORE);
                return;
            }
        }
    }

}

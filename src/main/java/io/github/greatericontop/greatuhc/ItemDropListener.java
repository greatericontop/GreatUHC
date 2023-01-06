package io.github.greatericontop.greatuhc;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ItemDropListener implements Listener {

    private final Random random;
    public ItemDropListener() {
        this.random = new Random();
    }

    public int randint(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    @EventHandler()
    public void onBlockBreak(BlockBreakEvent event) {
        Material typeBroken = event.getBlock().getType();
        // TODO: require tools?
        if (typeBroken == Material.OBSIDIAN) {
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.OBSIDIAN, 1));
        }
        if (typeBroken == Material.GRAVEL) {
            // +10% chance (19% total) for a flint
            if (Math.random() < 0.1) {
                event.setDropItems(false);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.FLINT, 1));
            }
        }
        if (typeBroken == Material.IRON_ORE || typeBroken == Material.DEEPSLATE_IRON_ORE) {
            event.setDropItems(false);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
            event.setExpToDrop(randint(1, 2));
        }
        if (typeBroken == Material.GOLD_ORE || typeBroken == Material.DEEPSLATE_GOLD_ORE) {
            event.setDropItems(false);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
            event.setExpToDrop(randint(1, 3));
        }
        if (typeBroken == Material.OAK_LEAVES) {
            if (Math.random() < 0.045) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE, 1));
            }
        }
    }

    @EventHandler()
    public void onDeath(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.getType() == EntityType.SKELETON) {
            // additional arrows & small chance to drop a full durability bow
            event.getDrops().add(new ItemStack(Material.ARROW, randint(0, 3)));
            if (Math.random() < 0.15) {
                event.getDrops().add(new ItemStack(Material.BOW, 1));
            }
        }
    }

}

package io.github.greatericontop.greatuhc.mechanics;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ItemDropListener implements Listener {

    private final Random random;
    private final GreatUHCMain plugin;
    public ItemDropListener(GreatUHCMain plugin) {
        this.random = new Random();
        this.plugin = plugin;
    }

    public int randint(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    @EventHandler()
    public void onBlockBreak(BlockBreakEvent event) {
        Material typeBroken = event.getBlock().getType();

        if (typeBroken == Material.COAL_ORE || typeBroken == Material.DEEPSLATE_COAL_ORE
                || typeBroken == Material.COPPER_ORE || typeBroken == Material.DEEPSLATE_COPPER_ORE
                || typeBroken == Material.IRON_ORE || typeBroken == Material.DEEPSLATE_IRON_ORE
                || typeBroken == Material.GOLD_ORE || typeBroken == Material.DEEPSLATE_GOLD_ORE
                || typeBroken == Material.DIAMOND_ORE || typeBroken == Material.DEEPSLATE_DIAMOND_ORE
                || typeBroken == Material.EMERALD_ORE || typeBroken == Material.DEEPSLATE_EMERALD_ORE
                || typeBroken == Material.REDSTONE_ORE || typeBroken == Material.DEEPSLATE_REDSTONE_ORE
                || typeBroken == Material.LAPIS_ORE || typeBroken == Material.DEEPSLATE_LAPIS_ORE
                || typeBroken == Material.SAND
                || typeBroken == Material.GRAVEL
                || typeBroken == Material.OBSIDIAN
        ) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 700, 0));
        }

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
            int amount = Math.random() < 0.15 ? 2 : 1;
            amount += (plugin.uhcMiningModifier && Math.random() < 0.4) ? 1 : 0;
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, amount));
            event.setExpToDrop(randint(1, 2));
        }
        if (typeBroken == Material.GOLD_ORE || typeBroken == Material.DEEPSLATE_GOLD_ORE) {
            event.setDropItems(false);
            int amount = Math.random() < 0.05 ? 2 : 1;
            amount += (plugin.uhcMiningModifier && Math.random() < 0.3) ? 1 : 0;
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, amount));
            event.setExpToDrop(randint(1, 3));
        }
        if (typeBroken == Material.DIAMOND_ORE || typeBroken == Material.DEEPSLATE_DIAMOND_ORE) {
            if (plugin.uhcMiningModifier && Math.random() < 0.4) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 1));
            }
        }
        if (typeBroken == Material.NETHER_GOLD_ORE) {
            event.setDropItems(false);
            if (Math.random() < 0.8) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_NUGGET, 1));
            }
            event.setExpToDrop(randint(0, 1));
        }

        if (plugin.uhcAllDropStone && (typeBroken == Material.ANDESITE || typeBroken == Material.DIORITE || typeBroken == Material.GRANITE)) {
            event.setDropItems(false);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE, 1));
        }

        if (typeBroken == Material.OAK_LEAVES || typeBroken == Material.DARK_OAK_LEAVES) {
            if (Math.random() < 0.035) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE, 1));
            }
        }
        if (typeBroken == Material.ACACIA_LEAVES
                || typeBroken == Material.AZALEA_LEAVES
                || typeBroken == Material.FLOWERING_AZALEA_LEAVES
                || typeBroken == Material.BIRCH_LEAVES
                || typeBroken == Material.JUNGLE_LEAVES
                || typeBroken == Material.SPRUCE_LEAVES
        ) {
            if (Math.random() < 0.01) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE, 1));
            }
        }
    }

    @EventHandler()
    public void onDeathItemDrops(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.getType() == EntityType.SKELETON) {
            // additional arrows & small chance to drop a full durability bow
            event.getDrops().add(new ItemStack(Material.ARROW, randint(1, 5)));
            if (Math.random() < 0.15) {
                event.getDrops().add(new ItemStack(Material.BOW, 1));
            }
        }
        if (victim.getType() == EntityType.CREEPER) {
            // chance to get extra gunpowder
            if (Math.random() < 0.6) {
                event.getDrops().add(new ItemStack(Material.GUNPOWDER, 1));
            }
        }
        if (victim.getType() == EntityType.ENDERMAN) {
            // always drop a pearl
            if (event.getDrops().stream().noneMatch(itemStack -> itemStack.getType() == Material.ENDER_PEARL)) {
                event.getDrops().add(new ItemStack(Material.ENDER_PEARL, 1));
            }
        }
    }

    @EventHandler()
    public void onDeathExperienceDrops(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.getType() == EntityType.PLAYER) {
            Player victimPlayer = (Player) victim;
            // Players drop 100% their XP on death (with a much more generous limit of 0->40 levels).
            int totalXp = victimPlayer.getTotalExperience();
            event.setDroppedExp(Math.min(totalXp, 3000));
        } else {
            // All other entities drop more XP (probability rounded).
            double newXpRaw = event.getDroppedExp() * 1.4;
            int newXp = (int) newXpRaw;
            newXp += Math.random() < (newXpRaw - newXp) ? 1 : 0;
            event.setDroppedExp(newXp);
        }
    }

}

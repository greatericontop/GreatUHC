package io.github.greatericontop.greatuhc.game.pregame;

import io.github.greatericontop.greatuhc.game.GameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PreGameManager {

    public enum Kit {
        ARMORER, STONE_GEAR, ECOLOGIST, ENCHANTER //, ARCHER, FIRE_LORD
    }

    private Map<UUID, Kit> playerKits = new HashMap<>();
    public Kit getPlayerKit(UUID player) {
        return playerKits.getOrDefault(player, Kit.ARMORER);
    }
    public void setPlayerKit(UUID player, Kit kit) {
        playerKits.put(player, kit);
    }

    public void giveKitTo(Player player, boolean isEnhanced) {
        switch (getPlayerKit(player.getUniqueId())) {
            case ARMORER -> giveArmorerTo(player, isEnhanced);
            case STONE_GEAR -> giveStoneGearTo(player, isEnhanced);
            case ECOLOGIST -> giveEcologistTo(player, isEnhanced);
            case ENCHANTER -> giveEnchanterTo(player, isEnhanced);
        }
    }


    private void giveArmorerTo(Player player, boolean isEnhanced) {
        ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        leatherHelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack leatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        leatherChestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack leatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        leatherLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS, 1);
        leatherBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        player.getInventory().addItem(leatherHelmet, leatherChestplate, leatherLeggings, leatherBoots);
    }
    private void giveStoneGearTo(Player player, boolean isEnhanced) {
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        stoneSword.addEnchantment(Enchantment.DURABILITY, 1);
        ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
        stonePickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
        stonePickaxe.addEnchantment(Enchantment.DURABILITY, 1);
        ItemStack stoneAxe = new ItemStack(Material.STONE_AXE);
        stoneAxe.addEnchantment(Enchantment.DIG_SPEED, 1);
        stoneAxe.addEnchantment(Enchantment.DURABILITY, 1);
        ItemStack stoneShovel = new ItemStack(Material.STONE_SHOVEL);
        stoneShovel.addEnchantment(Enchantment.DIG_SPEED, 1);
        stoneShovel.addEnchantment(Enchantment.DURABILITY, 1);
        player.getInventory().addItem(stoneSword, stonePickaxe, stoneAxe, stoneShovel);
    }
    private void giveEcologistTo(Player player, boolean isEnhanced) {
        ItemStack lilyPad = new ItemStack(Material.LILY_PAD, 64);
        ItemStack oakLog = new ItemStack(Material.OAK_LOG, 20);
        ItemStack apple = new ItemStack(Material.APPLE, 3);
        ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE, 3);
        ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
        stonePickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
        player.getInventory().addItem(lilyPad, oakLog, apple, sugarCane, stonePickaxe);
    }
    private void giveEnchanterTo(Player player, boolean isEnhanced) {
        ItemStack leather = new ItemStack(Material.LEATHER, 6);
        ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE, 12);
        ItemStack experienceBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 30);
        ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
        stonePickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
        player.getInventory().addItem(leather, sugarCane, experienceBottle, stonePickaxe);
    }


    public static void startPreGame(GameManager gameManager) {
        World overworld = gameManager.getOverworld();

        Bukkit.broadcast(Component.text("§9------------------------------"));
        Bukkit.broadcast(Component.text("§4§lGreatUHC"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§aThe game will start in §e45 §aseconds."));
        Bukkit.broadcast(Component.text("§aChoose a kit and get ready to fight!"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§9------------------------------"));

        overworld.getWorldBorder().setSize(30.0);
        overworld.getWorldBorder().setCenter(2000.0, 2000.0);

        // TODO: what stuff do we do in pregame vs start of grace period

        for (int x = 1985; x <= 2015; x++) {
            for (int z = 1985; z <= 2015; z++) {
                overworld.getBlockAt(x, 319, z).setType(Material.BEDROCK);
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            ItemStack kitSelector = new ItemStack(Material.DIAMOND, 1);
            ItemMeta im = kitSelector.getItemMeta();
            im.setDisplayName("§6Kit Selector");
            im.setLore(List.of("Kit Selector"));
            im.addEnchant(Enchantment.LUCK, 1, true);
            kitSelector.setItemMeta(im);
            player.getInventory().addItem(kitSelector);

            player.teleport(new Location(overworld, 2000.0, 320.0, 2000.0));
        }

    }

}

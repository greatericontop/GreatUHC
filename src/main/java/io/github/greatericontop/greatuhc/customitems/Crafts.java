package io.github.greatericontop.greatuhc.customitems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Crafts implements Listener {

    public static void registerCrafts() {

        ItemStack lightApple = new ItemStack(Material.GOLDEN_APPLE, 1);
        ShapedRecipe lightAppleRecipe = new ShapedRecipe(new NamespacedKey("uhc", "light_apple"), lightApple);
        lightAppleRecipe.shape(" g ", "gAg", " g ");
        lightAppleRecipe.setIngredient('g', Material.GOLD_INGOT);
        lightAppleRecipe.setIngredient('A', Material.APPLE);
        Bukkit.getServer().addRecipe(lightAppleRecipe);

        ItemStack quickPick = new ItemStack(Material.IRON_PICKAXE, 1);
        quickPick.addEnchantment(Enchantment.DIG_SPEED, 1);
        ShapedRecipe quickPickRecipe = new ShapedRecipe(new NamespacedKey("uhc", "quick_pick"), quickPick);
        quickPickRecipe.shape("III", "CsC", " s ");
        quickPickRecipe.setIngredient('I', Material.IRON_INGOT);
        quickPickRecipe.setIngredient('C', Material.COAL);
        quickPickRecipe.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(quickPickRecipe);

        ItemStack dragonSword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta im1 = dragonSword.getItemMeta();
        im1.setDisplayName("§aDragon Sword");
        im1.setLore(List.of("id: DRAGON_SWORD", "§7WeaponMaster", "§7A very powerful sword.", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        dragonSword.setItemMeta(im1);
        ShapedRecipe dragonSwordRecipe = new ShapedRecipe(new NamespacedKey("uhc", "uhc_dragon_sword"), dragonSword);
        dragonSwordRecipe.shape(" D ", " S ", "oDo");
        dragonSwordRecipe.setIngredient('D', Material.DIAMOND);
        dragonSwordRecipe.setIngredient('S', Material.DIAMOND_SWORD);
        dragonSwordRecipe.setIngredient('o', Material.OBSIDIAN);
        Bukkit.getServer().addRecipe(dragonSwordRecipe);

        ItemStack excalibur = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta im2 = excalibur.getItemMeta();
        im2.setDisplayName("§bExcalibur");
        im2.setLore(List.of("id: EXCALIBUR", "§7WeaponMaster", "§7A very powerful sword."));
        excalibur.setItemMeta(im2);
        ShapedRecipe excaliburRecipe = new ShapedRecipe(new NamespacedKey("uhc", "uhc_excalibur"), excalibur);
        excaliburRecipe.shape(" T ", "TST");
        excaliburRecipe.setIngredient('T', Material.TNT);
        excaliburRecipe.setIngredient('S', Material.DIAMOND_SWORD);
        Bukkit.getServer().addRecipe(excaliburRecipe);

        ItemStack exodus = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta im3 = exodus.getItemMeta();
        im3.setDisplayName("§cExodus");
        im3.setLore(List.of("id: EXODUS", "§7WeaponMaster", "§7A very powerful helmet."));
        exodus.setItemMeta(im3);
        ShapedRecipe exodusRecipe = new ShapedRecipe(new NamespacedKey("uhc", "uhc_exodus"), exodus);
        exodusRecipe.shape("ddd", "d d", "eGe");
        exodusRecipe.setIngredient('d', Material.DIAMOND);
        exodusRecipe.setIngredient('e', Material.EMERALD);
        exodusRecipe.setIngredient('G', Material.GOLDEN_CARROT);
        Bukkit.getServer().addRecipe(exodusRecipe);

        ItemStack lightAnvil = new ItemStack(Material.ANVIL, 1);
        ShapedRecipe lightAnvilRecipe = new ShapedRecipe(new NamespacedKey("uhc", "light_anvil"), lightAnvil);
        lightAnvilRecipe.shape("iii", " I ", "iii");
        lightAnvilRecipe.setIngredient('i', Material.IRON_INGOT);
        lightAnvilRecipe.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(lightAnvilRecipe);

        ItemStack sharpBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im4 = (EnchantmentStorageMeta) sharpBook.getItemMeta();
        im4.setDisplayName("§cBook of Sharpening");
        im4.addStoredEnchant(Enchantment.DAMAGE_ALL, 1, false);
        sharpBook.setItemMeta(im4);
        ShapedRecipe sharpBookRecipe = new ShapedRecipe(new NamespacedKey("uhc", "sharp_book"), sharpBook);
        sharpBookRecipe.shape("f  ", " pp", " pS");
        sharpBookRecipe.setIngredient('f', Material.FLINT);
        sharpBookRecipe.setIngredient('p', Material.PAPER);
        sharpBookRecipe.setIngredient('S', Material.IRON_SWORD);
        Bukkit.getServer().addRecipe(sharpBookRecipe);

        ItemStack apprenticeHelmet = new ItemStack(Material.IRON_HELMET, 1);
        apprenticeHelmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        apprenticeHelmet.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
        apprenticeHelmet.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
        apprenticeHelmet.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);
        ShapedRecipe apprenticeHelmetRecipe = new ShapedRecipe(new NamespacedKey("uhc", "apprentice_helmet"), apprenticeHelmet);
        apprenticeHelmetRecipe.shape("iii", "iRi");
        apprenticeHelmetRecipe.setIngredient('i', Material.IRON_INGOT);
        apprenticeHelmetRecipe.setIngredient('R', Material.REDSTONE_TORCH);
        Bukkit.getServer().addRecipe(apprenticeHelmetRecipe);

        ItemStack assassinBlade = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta im5 = assassinBlade.getItemMeta();
        im5.setDisplayName("§bAssassin's Blade");
        im5.setLore(List.of("id: ASSASSINS_BLADE", "§7WeaponMaster", "§7A very powerful sword."));
        assassinBlade.setItemMeta(im5);
        ShapedRecipe assassinBladeRecipe = new ShapedRecipe(new NamespacedKey("uhc", "uhc_assassin_blade"), assassinBlade);
        assassinBladeRecipe.shape("  I", " S ", "f  ");
        assassinBladeRecipe.setIngredient('I', Material.IRON_BLOCK);
        assassinBladeRecipe.setIngredient('S', Material.IRON_SWORD);
        assassinBladeRecipe.setIngredient('f', Material.FLINT);
        Bukkit.getServer().addRecipe(assassinBladeRecipe);

        ItemStack tarnhelm = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta im6 = tarnhelm.getItemMeta();
        im6.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        im6.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
        im6.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
        im6.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
        im6.setDisplayName("§bTarnhelm");
        tarnhelm.setItemMeta(im6);
        ShapedRecipe tarnhelmRecipe = new ShapedRecipe(new NamespacedKey("uhc", "tarnhelm"), tarnhelm);
        tarnhelmRecipe.shape("dId", "dRd");
        tarnhelmRecipe.setIngredient('d', Material.DIAMOND);
        tarnhelmRecipe.setIngredient('I', Material.IRON_INGOT);
        tarnhelmRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        Bukkit.getServer().addRecipe(tarnhelmRecipe);

        ItemStack sevenLeagueBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta im7 = sevenLeagueBoots.getItemMeta();
        im7.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false);
        im7.addEnchant(Enchantment.PROTECTION_FALL, 3, false);
        im7.addEnchant(Enchantment.DURABILITY, 2, false);
        im7.setDisplayName("§eSeven League Boots");
        sevenLeagueBoots.setItemMeta(im7);
        ShapedRecipe sevenLeagueBootsRecipe = new ShapedRecipe(new NamespacedKey("uhc", "seven_league_boots"), sevenLeagueBoots);
        sevenLeagueBootsRecipe.shape("fPf", "fBf", "fWf");
        sevenLeagueBootsRecipe.setIngredient('f', Material.FEATHER);
        sevenLeagueBootsRecipe.setIngredient('P', Material.ENDER_PEARL);
        sevenLeagueBootsRecipe.setIngredient('B', Material.DIAMOND_BOOTS);
        sevenLeagueBootsRecipe.setIngredient('W', Material.WATER_BUCKET);
        Bukkit.getServer().addRecipe(sevenLeagueBootsRecipe);

        ItemStack hermesBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta im8 = hermesBoots.getItemMeta();
        im8.addEnchant(Enchantment.DURABILITY, 1, false);
        im8.setDisplayName("§6Hermes Boots");
        im8.setLore(List.of("id: HERMES_BOOTS", "§7WeaponMaster", "§7Some very powerful boots."));
        hermesBoots.setItemMeta(im8);
        ShapedRecipe hermesBootsRecipe = new ShapedRecipe(new NamespacedKey("uhc", "hermes_boots"), hermesBoots);
        hermesBootsRecipe.shape("d d", "bBb", "f f");
        hermesBootsRecipe.setIngredient('d', Material.DIAMOND);
        hermesBootsRecipe.setIngredient('b', Material.BLAZE_POWDER);
        hermesBootsRecipe.setIngredient('B', Material.DIAMOND_BOOTS);
        hermesBootsRecipe.setIngredient('f', Material.FEATHER);
        Bukkit.getServer().addRecipe(hermesBootsRecipe);

    }

}

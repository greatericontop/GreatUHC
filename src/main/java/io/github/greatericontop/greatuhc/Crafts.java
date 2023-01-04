package io.github.greatericontop.greatuhc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Crafts implements Listener {

    public static void registerCrafts() {

        ItemStack lightApple = new ItemStack(Material.GOLDEN_APPLE, 1);
        ShapedRecipe lightAppleRecipe = new ShapedRecipe(NamespacedKey.minecraft("light_apple"), lightApple);
        lightAppleRecipe.shape(" g ", "gAg", " g ");
        lightAppleRecipe.setIngredient('g', Material.GOLD_INGOT);
        lightAppleRecipe.setIngredient('A', Material.APPLE);
        Bukkit.getServer().addRecipe(lightAppleRecipe);

        ItemStack quickPick = new ItemStack(Material.IRON_PICKAXE, 1);
        quickPick.addEnchantment(Enchantment.DIG_SPEED, 1);
        ShapedRecipe quickPickRecipe = new ShapedRecipe(NamespacedKey.minecraft("quick_pick"), quickPick);
        quickPickRecipe.shape("III", "CsC", " s ");
        quickPickRecipe.setIngredient('I', Material.IRON_INGOT);
        quickPickRecipe.setIngredient('C', Material.COAL);
        quickPickRecipe.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(quickPickRecipe);

        ItemStack dragonSword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta im1 = dragonSword.getItemMeta();
        im1.setDisplayName("§aDragon Sword");
        im1.setLore(List.of("id: DRAGON_SWORD", "§7WeaponMaster", "§7A very powerful sword.", ".", ".", ".", ".", ".", ".", "."));
        dragonSword.setItemMeta(im1);
        ShapedRecipe dragonSwordRecipe = new ShapedRecipe(NamespacedKey.minecraft("uhc_dragon_sword"), dragonSword);
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
        ShapedRecipe excaliburRecipe = new ShapedRecipe(NamespacedKey.minecraft("uhc_excalibur"), excalibur);
        excaliburRecipe.shape(" T ", "TST");
        excaliburRecipe.setIngredient('T', Material.TNT);
        excaliburRecipe.setIngredient('S', Material.DIAMOND_SWORD);
        Bukkit.getServer().addRecipe(excaliburRecipe);

        ItemStack exodus = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta im3 = exodus.getItemMeta();
        im3.setDisplayName("§cExodus");
        im3.setLore(List.of("id: EXODUS", "§7WeaponMaster", "§7A very powerful helmet."));
        exodus.setItemMeta(im3);
        ShapedRecipe exodusRecipe = new ShapedRecipe(NamespacedKey.minecraft("uhc_exodus"), exodus);
        exodusRecipe.shape("ddd", "d d", "eGe");
        exodusRecipe.setIngredient('d', Material.DIAMOND);
        exodusRecipe.setIngredient('e', Material.EMERALD);
        exodusRecipe.setIngredient('G', Material.GOLDEN_CARROT);
        Bukkit.getServer().addRecipe(exodusRecipe);

    }

}

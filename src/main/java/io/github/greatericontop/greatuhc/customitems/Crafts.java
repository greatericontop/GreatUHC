package io.github.greatericontop.greatuhc.customitems;

import io.github.greatericontop.greatuhc.mechanics.AntiAnvil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Crafts implements Listener {

    private static void lightApple() {
        ItemStack lightApple = new ItemStack(Material.GOLDEN_APPLE, 1);
        ShapedRecipe lightAppleRecipe = new ShapedRecipe(new NamespacedKey("uhc", "light_apple"), lightApple);
        lightAppleRecipe.shape(" g ", "gAg", " g ");
        lightAppleRecipe.setIngredient('g', Material.GOLD_INGOT);
        lightAppleRecipe.setIngredient('A', Material.APPLE);
        Bukkit.getServer().addRecipe(lightAppleRecipe);
    }
    private static void quickPick() {
        ItemStack quickPick = new ItemStack(Material.IRON_PICKAXE, 1);
        quickPick.addEnchantment(Enchantment.DIG_SPEED, 1);
        ShapedRecipe quickPickRecipe = new ShapedRecipe(new NamespacedKey("uhc", "quick_pick"), quickPick);
        quickPickRecipe.shape("III", "CsC", " s ");
        quickPickRecipe.setIngredient('I', Material.IRON_INGOT);
        quickPickRecipe.setIngredient('C', Material.COAL);
        quickPickRecipe.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(quickPickRecipe);
    }
    private static void lightAnvil() {
        ItemStack lightAnvil = new ItemStack(Material.ANVIL, 1);
        ShapedRecipe lightAnvilRecipe = new ShapedRecipe(new NamespacedKey("uhc", "light_anvil"), lightAnvil);
        lightAnvilRecipe.shape("iii", " I ", "iii");
        lightAnvilRecipe.setIngredient('i', Material.IRON_INGOT);
        lightAnvilRecipe.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(lightAnvilRecipe);
    }
    private static void sharpBook() {
        ItemStack sharpBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im = (EnchantmentStorageMeta) sharpBook.getItemMeta();
        im.setDisplayName("§cBook of Sharpening");
        im.addStoredEnchant(Enchantment.DAMAGE_ALL, 1, false);
        sharpBook.setItemMeta(im);
        ShapedRecipe sharpBookRecipe = new ShapedRecipe(new NamespacedKey("uhc", "sharp_book"), sharpBook);
        sharpBookRecipe.shape("f  ", " pp", " pS");
        sharpBookRecipe.setIngredient('f', Material.FLINT);
        sharpBookRecipe.setIngredient('p', Material.PAPER);
        sharpBookRecipe.setIngredient('S', Material.IRON_SWORD);
        Bukkit.getServer().addRecipe(sharpBookRecipe);
    }
    private static void apprenticeHelmet() {
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
    }
    private static void tarnhelm() {
        ItemStack tarnhelm = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta im = tarnhelm.getItemMeta();
        im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        im.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
        im.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
        im.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
        im.setDisplayName("§bTarnhelm");
        tarnhelm.setItemMeta(im);
        ShapedRecipe tarnhelmRecipe = new ShapedRecipe(new NamespacedKey("uhc", "tarnhelm"), tarnhelm);
        tarnhelmRecipe.shape("dId", "dRd");
        tarnhelmRecipe.setIngredient('d', Material.DIAMOND);
        tarnhelmRecipe.setIngredient('I', Material.IRON_INGOT);
        tarnhelmRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        Bukkit.getServer().addRecipe(tarnhelmRecipe);
    }
    private static void flamingArtifact() {
        ItemStack flamingArtifact = new ItemStack(Material.BLAZE_ROD, 1);
        ShapedRecipe flamingArtifactRecipe = new ShapedRecipe(new NamespacedKey("uhc", "flaming_artifact"), flamingArtifact);
        flamingArtifactRecipe.shape("gLg", "gFg", "gLg");
        flamingArtifactRecipe.setIngredient('g', Material.ORANGE_STAINED_GLASS);
        flamingArtifactRecipe.setIngredient('L', Material.LAVA_BUCKET);
        flamingArtifactRecipe.setIngredient('F', Material.FIREWORK_ROCKET);
        Bukkit.getServer().addRecipe(flamingArtifactRecipe);
    }
    private static void netherBlessing() {
        ItemStack netherBlessing = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im = (EnchantmentStorageMeta) netherBlessing.getItemMeta();
        im.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
        im.addStoredEnchant(Enchantment.DURABILITY, 2, false);
        im.addStoredEnchant(Enchantment.THORNS, 1, false);
        im.setDisplayName("§4Nether Blessing");
        netherBlessing.setItemMeta(im);
        ShapedRecipe netherBlessingRecipe = new ShapedRecipe(new NamespacedKey("uhc", "nether_blessing"), netherBlessing);
        netherBlessingRecipe.shape("nnn", "nMn", "nnn");
        netherBlessingRecipe.setIngredient('n', new RecipeChoice.MaterialChoice(Material.NETHER_WART_BLOCK, Material.WARPED_WART_BLOCK));
        netherBlessingRecipe.setIngredient('M', Material.MAGMA_BLOCK);
        Bukkit.getServer().addRecipe(netherBlessingRecipe);
    }
    private static void protectionBook() {
        ItemStack protectionBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im = (EnchantmentStorageMeta) protectionBook.getItemMeta();
        im.setDisplayName("§cBook of Protection");
        im.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
        protectionBook.setItemMeta(im);
        ShapedRecipe protectionBookRecipe = new ShapedRecipe(new NamespacedKey("uhc", "protection_book"), protectionBook);
        protectionBookRecipe.shape("pp", "pI");
        protectionBookRecipe.setIngredient('p', Material.PAPER);
        protectionBookRecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.getServer().addRecipe(protectionBookRecipe);
    }
    private static void holyWater() {
        ItemStack holyWater = new ItemStack(Material.POTION, 1);
        PotionMeta im = (PotionMeta) holyWater.getItemMeta();
        im.setDisplayName("§7Holy Water");
        im.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 2), true);
        im.setColor(Color.fromRGB(0xfc2524)); // instant health color
        holyWater.setItemMeta(im);
        ShapedRecipe holyWaterRecipe = new ShapedRecipe(new NamespacedKey("uhc", "holy_water"), holyWater);
        holyWaterRecipe.shape("gRg", "gMg", " b ");
        holyWaterRecipe.setIngredient('g', Material.GOLD_INGOT);
        holyWaterRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        holyWaterRecipe.setIngredient('M', new RecipeChoice.MaterialChoice(
                Material.MUSIC_DISC_11, Material.MUSIC_DISC_13, Material.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_CAT,
                Material.MUSIC_DISC_CHIRP, Material.MUSIC_DISC_FAR, Material.MUSIC_DISC_MALL, Material.MUSIC_DISC_MELLOHI,
                Material.MUSIC_DISC_STAL, Material.MUSIC_DISC_STRAD, Material.MUSIC_DISC_WAIT, Material.MUSIC_DISC_WARD,
                Material.MUSIC_DISC_OTHERSIDE, Material.MUSIC_DISC_PIGSTEP));
        holyWaterRecipe.setIngredient('b', Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(holyWaterRecipe);
    }
    private static void undeadBow() {
        ItemStack undeadBow = new ItemStack(Material.BOW, 1);
        ItemMeta im = undeadBow.getItemMeta();
        im.setDisplayName("§aUndead Bow");
        im.setLore(List.of("Undead", "§7Arrows deal more damage."));
        undeadBow.setItemMeta(im);
        ShapedRecipe undeadBowRecipe = new ShapedRecipe(new NamespacedKey("uhc", "undead_bow"), undeadBow);
        undeadBowRecipe.shape(" tS", "tBS", " tS");
        undeadBowRecipe.setIngredient('t', Material.REDSTONE_TORCH);
        undeadBowRecipe.setIngredient('S', Material.STRING);
        undeadBowRecipe.setIngredient('B', Material.BONE);
        Bukkit.getServer().addRecipe(undeadBowRecipe);
    }
    private static void philosopherPickaxe() {
        ItemStack philosopherPickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        Damageable im = (Damageable) philosopherPickaxe.getItemMeta();
        im.setDisplayName("§ePhilosopher Pickaxe");
        im.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 2, true);
        im.setDamage(1561 - 3);
        AntiAnvil.disallowAnvil(im);
        philosopherPickaxe.setItemMeta(im);
        ShapedRecipe philosopherPickaxeRecipe = new ShapedRecipe(new NamespacedKey("uhc", "philosopher_pickaxe"), philosopherPickaxe);
        philosopherPickaxeRecipe.shape("igi", "LsL", " s ");
        philosopherPickaxeRecipe.setIngredient('i', Material.IRON_INGOT);
        philosopherPickaxeRecipe.setIngredient('g', Material.GOLD_INGOT);
        philosopherPickaxeRecipe.setIngredient('L', Material.LAPIS_BLOCK);
        philosopherPickaxeRecipe.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(philosopherPickaxeRecipe);
    }
    private static void arrowEconomy() {
        ItemStack arrowEconomy = new ItemStack(Material.ARROW, 20);
        ShapedRecipe arrowEconomyRecipe = new ShapedRecipe(new NamespacedKey("uhc", "arrow_economy"), arrowEconomy);
        arrowEconomyRecipe.shape("FFF", "sss", "fff");
        arrowEconomyRecipe.setIngredient('F', Material.FLINT);
        arrowEconomyRecipe.setIngredient('s', Material.STICK);
        arrowEconomyRecipe.setIngredient('f', Material.FEATHER);
        Bukkit.getServer().addRecipe(arrowEconomyRecipe);
    }
    private static void goldenHead() {
        ItemStack goldenHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta im = (SkullMeta) goldenHead.getItemMeta();
        im.setDisplayName("§6Golden Head");
        // https://namemc.com/skin/4ed16e1139f0f81f
        im.setOwningPlayer(Bukkit.getOfflinePlayer("_GoldenHead"));
        im.getPersistentDataContainer().set(new NamespacedKey("uhc", "golden_head"), PersistentDataType.INTEGER, 1);
        goldenHead.setItemMeta(im);
        ShapedRecipe goldenHeadRecipe = new ShapedRecipe(new NamespacedKey("uhc", "golden_head"), goldenHead);
        goldenHeadRecipe.shape("ggg", "gHg", "ggg");
        goldenHeadRecipe.setIngredient('g', Material.GOLD_INGOT);
        goldenHeadRecipe.setIngredient('H', Material.PLAYER_HEAD);
        Bukkit.getServer().addRecipe(goldenHeadRecipe);
    }
    private static void powerBook() {
        ItemStack powerBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im = (EnchantmentStorageMeta) powerBook.getItemMeta();
        im.setDisplayName("§cBook of Power");
        im.addStoredEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        powerBook.setItemMeta(im);
        ShapedRecipe bookOfPowerRecipe = new ShapedRecipe(new NamespacedKey("uhc", "power_book"), powerBook);
        bookOfPowerRecipe.shape("B  ", " pp", " pB");
        bookOfPowerRecipe.setIngredient('B', Material.BONE);
        bookOfPowerRecipe.setIngredient('p', Material.PAPER);
        Bukkit.getServer().addRecipe(bookOfPowerRecipe);
    }
    private static void appleEconomy() {
        ItemStack appleEconomy = new ItemStack(Material.APPLE, 2);
        ShapelessRecipe appleEconomyRecipe = new ShapelessRecipe(new NamespacedKey("uhc", "apple_economy"), appleEconomy);
        appleEconomyRecipe.addIngredient(Material.APPLE);
        appleEconomyRecipe.addIngredient(Material.BONE_MEAL);
        Bukkit.getServer().addRecipe(appleEconomyRecipe);
    }
    private static void chestOfFate() {
        ItemStack chestOfFate = new ItemStack(Material.POTION, 1);
        PotionMeta im18 = (PotionMeta) chestOfFate.getItemMeta();
        im18.setDisplayName("§2Chest of Fate");
        im18.setLore(List.of("§7Tempt Fate and get a powerful", "§7blessing or curse."));
        im18.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 8), true); // 18 hearts
        im18.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 900, 1), true);
        im18.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 900, 4), true);
        im18.setColor(Color.fromRGB(0x349c00)); // luck color
        chestOfFate.setItemMeta(im18);
        ShapedRecipe chestOfFateRecipe = new ShapedRecipe(new NamespacedKey("uhc", "chest_of_fate"), chestOfFate);
        chestOfFateRecipe.shape("ccc", "cHc", "ccc");
        chestOfFateRecipe.setIngredient('c', Material.CHEST);
        chestOfFateRecipe.setIngredient('H', Material.PLAYER_HEAD);
        Bukkit.getServer().addRecipe(chestOfFateRecipe);
    }
    private static void cupidsBow() {
        ItemStack cupidsBow = new ItemStack(Material.BOW, 1);
        ItemMeta im = cupidsBow.getItemMeta();
        im.setDisplayName("§cCupid's Bow");
        im.addEnchant(Enchantment.ARROW_FIRE, 1, false);
        AntiAnvil.disallowAnvil(im);
        cupidsBow.setItemMeta(im);
        ShapedRecipe cupidsBowRecipe = new ShapedRecipe(new NamespacedKey("uhc", "cupids_bow"), cupidsBow);
        cupidsBowRecipe.shape(" H ", "bBb", " L ");
        cupidsBowRecipe.setIngredient('H', Material.PLAYER_HEAD);
        cupidsBowRecipe.setIngredient('b', Material.BLAZE_ROD);
        cupidsBowRecipe.setIngredient('B', Material.BOW);
        cupidsBowRecipe.setIngredient('L', Material.LAVA_BUCKET);
        Bukkit.getServer().addRecipe(cupidsBowRecipe);
    }
    private static void apprenticeSword() {
        ItemStack apprenticeSword = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta im = apprenticeSword.getItemMeta();
        im.setDisplayName("§aApprentice Sword");
        im.setLore(List.of(
                "§bGains Sharpness §cI §bafter grace period",
                "§bGains Sharpness §cII §b10 minutes after grace period",
                "§bGains Sharpness §cIII §bduring Deathmatch",
                "§7If necessary, left or right click to update this item"
        ));
        im.getPersistentDataContainer().set(new NamespacedKey("uhc", "apprentice_sword"), PersistentDataType.INTEGER, 1);
        AntiAnvil.disallowAnvil(im);
        apprenticeSword.setItemMeta(im);
        ShapedRecipe apprenticeSwordRecipe = new ShapedRecipe(new NamespacedKey("uhc", "apprentice_sword"), apprenticeSword);
        apprenticeSwordRecipe.shape("r", "S", "r");
        apprenticeSwordRecipe.setIngredient('r', Material.REDSTONE_BLOCK);
        apprenticeSwordRecipe.setIngredient('S', Material.IRON_SWORD);
        Bukkit.getServer().addRecipe(apprenticeSwordRecipe);
    }
    private static void diceOfGod() {
        ItemStack diceOfGod = new ItemStack(Material.END_PORTAL_FRAME, 1);
        ItemMeta im = diceOfGod.getItemMeta();
        im.setDisplayName("§aDice of God");
        im.setLore(List.of(
                "§eDo you have what it takes?",
                "§7Roll the dice of god, and see!",
                "§7Get a random ultimate item."
        ));
        im.addEnchant(Enchantment.LUCK, 1, true);
        diceOfGod.setItemMeta(im);
        ShapedRecipe diceOfGodRecipe = new ShapedRecipe(new NamespacedKey("uhc", "dice_of_god"), diceOfGod);
        diceOfGodRecipe.shape("mJm", "mHm", "mmm");
        diceOfGodRecipe.setIngredient('m', Material.MOSSY_COBBLESTONE);
        diceOfGodRecipe.setIngredient('J', Material.JUKEBOX);
        diceOfGodRecipe.setIngredient('H', Material.PLAYER_HEAD);
        Bukkit.getServer().addRecipe(diceOfGodRecipe);
    }
    private static void velocityPotion() {
        ItemStack velocityPotion = new ItemStack(Material.SPLASH_POTION, 1);
        PotionMeta im = (PotionMeta) velocityPotion.getItemMeta();
        im.setDisplayName("§9Potion of Velocity");
        im.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 360, 2), true);
        im.setColor(Color.fromRGB(0x7eb2ca)); // speed color
        velocityPotion.setItemMeta(im);
        ShapedRecipe velocityPotionRecipe = new ShapedRecipe(new NamespacedKey("uhc", "velocity_potion"), velocityPotion);
        velocityPotionRecipe.shape(" P ", "PSP", " b ");
        velocityPotionRecipe.setIngredient('P', Material.PUMPKIN);
        velocityPotionRecipe.setIngredient('S', Material.SUGAR);
        velocityPotionRecipe.setIngredient('b', Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(velocityPotionRecipe);
    }
    private static void nectar() {
        ItemStack nectar = new ItemStack(Material.POTION, 1);
        PotionMeta im = (PotionMeta) nectar.getItemMeta();
        im.setDisplayName("§dNectar");
        im.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1500, 1), true);
        im.setColor(Color.fromRGB(0xfcf4d5)); // slow falling color, why not
        nectar.setItemMeta(im);
        ShapedRecipe nectarRecipe = new ShapedRecipe(new NamespacedKey("uhc", "nectar"), nectar);
        nectarRecipe.shape(" m ", "MgM", " m ");
        nectarRecipe.setIngredient('M', Material.MELON);
        nectarRecipe.setIngredient('g', Material.GLASS_BOTTLE);
        nectarRecipe.setIngredient('m', Material.GLISTERING_MELON_SLICE);
        Bukkit.getServer().addRecipe(nectarRecipe);
    }
    private static void backpack() {
        ItemStack backpack = new ItemStack(Material.SHULKER_BOX, 1);
        ItemMeta im = backpack.getItemMeta();
        im.setDisplayName("§aBackpack");
        backpack.setItemMeta(im);
        ShapedRecipe backpackRecipe = new ShapedRecipe(new NamespacedKey("uhc", "backpack"), backpack);
        backpackRecipe.shape("scs", "csc", "sLs");
        backpackRecipe.setIngredient('s', Material.STICK);
        backpackRecipe.setIngredient('c', Material.CHEST);
        backpackRecipe.setIngredient('L', Material.LEATHER);
        Bukkit.getServer().addRecipe(backpackRecipe);
    }
    private static void sugarCookie() {
        ItemStack sugarCookie = new ItemStack(Material.COOKIE, 1);
        ItemMeta im = sugarCookie.getItemMeta();
        im.addEnchant(Enchantment.LUCK, 1, true);
        im.setDisplayName("§eSugar Cookie");
        im.setLore(List.of(
                "Sugary",
                "§7Eating gives you §aSpeed II §7and",
                "§aJump Boost IV §7for §b20 §7seconds."
        ));
        sugarCookie.setItemMeta(im);
        ShapedRecipe sugarCookieRecipe = new ShapedRecipe(new NamespacedKey("uhc", "sugar_cookie"), sugarCookie);
        sugarCookieRecipe.shape("SCS", "CsC", "SCS");
        sugarCookieRecipe.setIngredient('S', Material.SUGAR);
        sugarCookieRecipe.setIngredient('C', Material.COCOA_BEANS);
        sugarCookieRecipe.setIngredient('s', Material.WHEAT_SEEDS);
        Bukkit.getServer().addRecipe(sugarCookieRecipe);
    }
    private static void apprenticeBow() {
        ItemStack apprenticeBow = new ItemStack(Material.BOW, 1);
        ItemMeta im = apprenticeBow.getItemMeta();
        im.setDisplayName("§aApprentice Bow");
        im.setLore(List.of(
                "§bGains Power §c1 §b10 minutes after grace period",
                "§bGains Power §c2 §bduring Deathmatch"
        ));
        im.getPersistentDataContainer().set(new NamespacedKey("uhc", "apprentice_bow"), PersistentDataType.INTEGER, 1);
        AntiAnvil.disallowAnvil(im);
        apprenticeBow.setItemMeta(im);
        ShapedRecipe apprenticeBowRecipe = new ShapedRecipe(new NamespacedKey("uhc", "apprentice_bow"), apprenticeBow);
        apprenticeBowRecipe.shape("aRa", "aBa", "aRa");
        apprenticeBowRecipe.setIngredient('B', Material.BOW);
        apprenticeBowRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        apprenticeBowRecipe.setIngredient('a', Material.ARROW);
        Bukkit.getServer().addRecipe(apprenticeBowRecipe);
    }
    private static void toughnessPotion() {
        ItemStack toughnessPotion = new ItemStack(Material.POTION, 1);
        PotionMeta im = (PotionMeta) toughnessPotion.getItemMeta();
        im.setDisplayName("§bPotion of Toughness");
        im.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2100, 0), true);
        im.setColor(Color.fromRGB(0x7eb2ca)); // speed color
        toughnessPotion.setItemMeta(im);
        ShapedRecipe toughnessPotionRecipe = new ShapedRecipe(new NamespacedKey("uhc", "toughness_potion"), toughnessPotion);
        toughnessPotionRecipe.shape("sIs", "sgs", "sss");
        toughnessPotionRecipe.setIngredient('s', Material.SNOW_BLOCK);
        toughnessPotionRecipe.setIngredient('I', Material.IRON_BLOCK);
        toughnessPotionRecipe.setIngredient('g', Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(toughnessPotionRecipe);
    }
    private static void deliciousMeal() {
        ItemStack deliciousMeal = new ItemStack(Material.COOKED_BEEF, 8);
        ShapedRecipe deliciousMealRecipe = new ShapedRecipe(new NamespacedKey("uhc", "delicious_meal"), deliciousMeal);
        deliciousMealRecipe.shape("xxx", "xCx", "xxx");
        deliciousMealRecipe.setIngredient('x', new RecipeChoice.MaterialChoice(Material.PORKCHOP, Material.BEEF));
        deliciousMealRecipe.setIngredient('C', Material.COAL);
        Bukkit.getServer().addRecipe(deliciousMealRecipe);
    }
    private static void glassEconomy() {
        ItemStack glassEconomy = new ItemStack(Material.GLASS, 8);
        ShapedRecipe glassEconomyRecipe = new ShapedRecipe(new NamespacedKey("uhc", "glass_economy"), glassEconomy);
        glassEconomyRecipe.shape("sss", "sCs", "sss");
        glassEconomyRecipe.setIngredient('s', Material.SAND);
        glassEconomyRecipe.setIngredient('C', Material.COAL);
        Bukkit.getServer().addRecipe(glassEconomyRecipe);
    }
    private static void fateTemptation() {
        ItemStack fateTemptation = new ItemStack(Material.REDSTONE, 1);
        ItemMeta im = fateTemptation.getItemMeta();
        im.setDisplayName("§6Fate's Temptation");
        im.setLore(List.of(
                "§7You'll be blessed with random items to help you",
                "§7along the road to victory."
        ));
        fateTemptation.setItemMeta(im);
        ShapedRecipe fateTemptationRecipe = new ShapedRecipe(new NamespacedKey("uhc", "fate_temptation"), fateTemptation);
        fateTemptationRecipe.shape("rRr", "gCg", "rRr");
        fateTemptationRecipe.setIngredient('r', Material.REDSTONE);
        fateTemptationRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        fateTemptationRecipe.setIngredient('g', Material.GUNPOWDER);
        fateTemptationRecipe.setIngredient('C', Material.CHEST);
        Bukkit.getServer().addRecipe(fateTemptationRecipe);
    }

    private static ItemStack itemStackDragonSword() {
        ItemStack dragonSword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta im = dragonSword.getItemMeta();
        im.setDisplayName("§aDragon Sword");
        im.setLore(List.of("id: DRAGON_SWORD", "§7WeaponMaster", "§7A very powerful sword.", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        dragonSword.setItemMeta(im);
        return dragonSword;
    }
    private static ItemStack itemStackExcalibur() {
        ItemStack excalibur = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta im = excalibur.getItemMeta();
        im.setDisplayName("§bExcalibur");
        im.setLore(List.of("id: EXCALIBUR", "§7WeaponMaster", "§7A very powerful sword."));
        AntiAnvil.disallowAnvil(im);
        excalibur.setItemMeta(im);
        return excalibur;
    }
    private static ItemStack itemStackExodus() {
        ItemStack exodus = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta im = exodus.getItemMeta();
        im.addEnchant(Enchantment.DURABILITY, 3, false);
        im.setDisplayName("§cExodus");
        im.setLore(List.of("id: EXODUS", "§7WeaponMaster", "§7A very powerful helmet."));
        AntiAnvil.disallowAnvil(im);
        exodus.setItemMeta(im);
        return exodus;
    }
    private static ItemStack itemStackAssassinBlade() {
        ItemStack assassinBlade = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta im = assassinBlade.getItemMeta();
        im.setDisplayName("§bAssassin's Blade");
        im.setLore(List.of("id: ASSASSINS_BLADE", "§7WeaponMaster", "§7A very powerful sword."));
        assassinBlade.setItemMeta(im);
        return assassinBlade;
    }
    private static ItemStack itemStackSevenLeagueBoots() {
        ItemStack sevenLeagueBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta im = sevenLeagueBoots.getItemMeta();
        im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false);
        im.addEnchant(Enchantment.PROTECTION_FALL, 3, false);
        im.addEnchant(Enchantment.DURABILITY, 2, false);
        im.setDisplayName("§eSeven League Boots");
        sevenLeagueBoots.setItemMeta(im);
        return sevenLeagueBoots;
    }
    private static ItemStack itemStackHermesBoots() {
        ItemStack hermesBoots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta im = hermesBoots.getItemMeta();
        im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
        im.addEnchant(Enchantment.DURABILITY, 1, false);
        im.setDisplayName("§6Hermes Boots");
        im.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "UHC", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        im.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "UHC", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        im.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "UHC", 0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET));
        hermesBoots.setItemMeta(im);
        return hermesBoots;
    }
    private static ItemStack itemStackCorn() {
        ItemStack corn = new ItemStack(Material.GOLDEN_CARROT, 3);
        ItemMeta im = corn.getItemMeta();
        im.setDisplayName("§6Cornucopia");
        im.setLore(List.of("Corn", "§7Gives Regeneration and Saturation."));
        corn.setItemMeta(im);
        return corn;
    }
    private static ItemStack itemStackHideOfLeviathan() {
        ItemStack hideOfLeviathan = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemMeta im = hideOfLeviathan.getItemMeta();
        im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        im.addEnchant(Enchantment.OXYGEN, 3, true);
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        im.setDisplayName("§9Hide of Leviathan");
        hideOfLeviathan.setItemMeta(im);
        return hideOfLeviathan;
    }
    private static ItemStack itemStackDragonArmor() {
        ItemStack dragonArmor = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta im = dragonArmor.getItemMeta();
        im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false);
        im.setDisplayName("§5Dragon Armor");
        im.setLore(List.of("id: DRAGON_ARMOR", "§7WeaponMaster", "§7A very powerful chestplate."));
        dragonArmor.setItemMeta(im);
        return dragonArmor;
    }
    private static ItemStack itemStackAnduril() {
        ItemStack anduril = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta im = anduril.getItemMeta();
        im.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
        im.setDisplayName("§aAnduril");
        im.setLore(List.of("id: ANDURIL", "§7WeaponMaster", "§7A very powerful sword."));
        AntiAnvil.disallowAnvil(im);
        anduril.setItemMeta(im);
        return anduril;
    }
    private static ItemStack itemStackEnhancementBook() {
        ItemStack enhancementBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im = (EnchantmentStorageMeta) enhancementBook.getItemMeta();
        im.addStoredEnchant(Enchantment.MENDING, 1, false);
        im.setDisplayName("§6Enhancement Book");
        im.setLore(List.of("Enhancement Book", "§7Use this in an anvil to", "§7level 30 enchant it."));
        enhancementBook.setItemMeta(im);
        return enhancementBook;
    }
    private static ItemStack itemStackExpertSeal() {
        ItemStack expertSeal = new ItemStack(Material.NETHER_STAR, 3);
        ItemMeta im = expertSeal.getItemMeta();
        im.setDisplayName("§6Expert Seal");
        im.setLore(List.of("id: EXPERT_SEAL", "§7WeaponMaster", "§eMove this over another item", "§eto upgrade its enchantments."));
        expertSeal.setItemMeta(im);
        return expertSeal;
    }
    private static ItemStack itemStackDeusExMachina() {
        ItemStack deusExMachina = new ItemStack(Material.POTION, 1);
        PotionMeta im = (PotionMeta) deusExMachina.getItemMeta();
        im.setDisplayName("§cDeus Ex Machina");
        im.setLore(List.of("§7Grants invincibility!", "§cCrafting costs half your health!"));
        im.setColor(Color.fromRGB(0x775d64)); // turtle master color
        im.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 240, 4), true);
        deusExMachina.setItemMeta(im);
        return deusExMachina;
    }
    private static ItemStack itemStackArtemisBow() {
        ItemStack artemisBow = new ItemStack(Material.BOW, 1);
        ItemMeta im = artemisBow.getItemMeta();
        im.setDisplayName("§5Artemis Bow");
        im.setLore(List.of("id: ARTEMIS_BOW", "§7WeaponMaster", "§7Arrows shot from this bow", "§7will always hit their target!"));
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        AntiAnvil.disallowAnvil(im);
        artemisBow.setItemMeta(im);
        return artemisBow;
    }
    private static ItemStack itemStackDeathScythe() {
        ItemStack deathScythe = new ItemStack(Material.IRON_HOE, 1);
        ItemMeta im = deathScythe.getItemMeta();
        im.setDisplayName("§cDeath's Scythe");
        im.setLore(List.of("id: DEATH_SCYTHE", "§7WeaponMaster",
                "§7Deals increasing true damage to your enemies!", "§7Gain §cStrength §7based on the damage dealt."));
        deathScythe.setItemMeta(im);
        return deathScythe;
    }
    private static ItemStack itemStackBookOfThoth() {
        ItemStack bookOfThoth = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im = (EnchantmentStorageMeta) bookOfThoth.getItemMeta();
        im.setDisplayName("§cBook of Thoth");
        im.addStoredEnchant(Enchantment.DAMAGE_ALL, 2, true);
        im.addStoredEnchant(Enchantment.FIRE_ASPECT, 1, true);
        im.addStoredEnchant(Enchantment.ARROW_DAMAGE, 2, true);
        im.addStoredEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
        im.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        bookOfThoth.setItemMeta(im);
        return bookOfThoth;
    }
    private static ItemStack itemStackShoesOfVidar() {
        ItemStack shoesOfVidar = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta im = shoesOfVidar.getItemMeta();
        im.setDisplayName("§9Shoes of Vidar");
        im.addEnchant(Enchantment.DEPTH_STRIDER, 2, false);
        im.addEnchant(Enchantment.DURABILITY, 2, false);
        shoesOfVidar.setItemMeta(im);
        return shoesOfVidar;
    }
    private static ItemStack itemStackWarlockPants() {
        ItemStack warlockPants = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemMeta im = warlockPants.getItemMeta();
        im.setDisplayName("§7Warlock Pants");
        im.setLore(Arrays.asList("id: WARLOCK_PANTS", "§7WeaponMaster", "§3Permanent §cStrength §3while wearing!"));
        AntiAnvil.disallowAnvil(im);
        warlockPants.setItemMeta(im);
        return warlockPants;
    }
    private static ItemStack itemStackVitalityPotion() {
        ItemStack vitalityPotion = new ItemStack(Material.SPLASH_POTION, 1);
        PotionMeta im = (PotionMeta) vitalityPotion.getItemMeta();
        im.setDisplayName("§aVitality Potion");
        im.setLore(List.of(
                "Vital",
                "§7For you:",
                "§9Speed I (0:15)",
                "§9Regeneration I (0:12)",
                "§7For others:",
                "§cSlowness I (0:15)",
                "§cPoison I (0:12)",
                "§7Anyone in range will receive §b100%",
                "§7duration of the effects"
        ));
        im.setColor(Color.fromRGB(0xff0000));
        vitalityPotion.setItemMeta(im);
        return vitalityPotion;
    }
    private static ItemStack itemStackBloodlust() {
        ItemStack bloodlust = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta im = bloodlust.getItemMeta();
        im.setDisplayName("§cBloodlust");
        im.setLore(List.of(
                "§3Gains Sharpness §cI §3after §b50 §3damage.",
                "§3Gains Sharpness §cII §3after §b150 §3damage.",
                "§3Gains Sharpness §cIII §3after §b300 §3damage.",
                "§3Gains Sharpness §cIV §3after §b600 §3damage."
        ));
        im.getPersistentDataContainer().set(new NamespacedKey("uhc", "bloodlust_damage"), PersistentDataType.DOUBLE, 0.0);
        AntiAnvil.disallowAnvil(im);
        bloodlust.setItemMeta(im);
        return bloodlust;
    }

    public static void registerCrafts() {
        Bukkit.getServer().removeRecipe(new NamespacedKey("minecraft", "anduril"));

        // Normal crafts
        lightApple();
        quickPick();
        lightAnvil();
        sharpBook();
        apprenticeHelmet();
        tarnhelm();
        flamingArtifact();
        netherBlessing();
        protectionBook();
        holyWater();
        undeadBow();
        philosopherPickaxe();
        arrowEconomy();
        goldenHead();
        powerBook();
        appleEconomy();
        apprenticeSword();
        velocityPotion();
        nectar();
        backpack();
        sugarCookie();
        apprenticeBow();
        toughnessPotion();
        deliciousMeal();
        glassEconomy();
        fateTemptation();

        // Crafts I feel like are good
        // But aren't good enough to (or shouldn't, if the craft is too weird) be ultimates
        chestOfFate();
        cupidsBow();
        diceOfGod();

        ItemStack dragonSword = itemStackDragonSword();
        ShapedRecipe dragonSwordRecipe = new ShapedRecipe(new NamespacedKey("uhc", "uhc_dragon_sword"), dragonSword);
        dragonSwordRecipe.shape(" B ", " S ", "oBo");
        dragonSwordRecipe.setIngredient('B', Material.BLAZE_POWDER);
        dragonSwordRecipe.setIngredient('S', Material.DIAMOND_SWORD);
        dragonSwordRecipe.setIngredient('o', Material.OBSIDIAN);
        Bukkit.getServer().addRecipe(dragonSwordRecipe);

        ItemStack excalibur = itemStackExcalibur();
        ShapedRecipe excaliburRecipe = new ShapedRecipe(new NamespacedKey("uhc", "uhc_excalibur"), excalibur);
        excaliburRecipe.shape("s s", "sTs", "sSs");
        excaliburRecipe.setIngredient('T', Material.TNT);
        excaliburRecipe.setIngredient('S', Material.DIAMOND_SWORD);
        excaliburRecipe.setIngredient('s', Material.SOUL_SAND);
        Bukkit.getServer().addRecipe(excaliburRecipe);

        ItemStack exodus = itemStackExodus();
        ShapedRecipe exodusRecipe = new ShapedRecipe(new NamespacedKey("uhc", "uhc_exodus"), exodus);
        exodusRecipe.shape("ddd", "d d", "eGe");
        exodusRecipe.setIngredient('d', Material.DIAMOND);
        exodusRecipe.setIngredient('e', Material.EMERALD);
        exodusRecipe.setIngredient('G', Material.GOLDEN_CARROT);
        Bukkit.getServer().addRecipe(exodusRecipe);

        ItemStack assassinBlade = itemStackAssassinBlade();
        ShapedRecipe assassinBladeRecipe = new ShapedRecipe(new NamespacedKey("uhc", "uhc_assassin_blade"), assassinBlade);
        assassinBladeRecipe.shape("  I", " S ", "f  ");
        assassinBladeRecipe.setIngredient('I', Material.IRON_BLOCK);
        assassinBladeRecipe.setIngredient('S', Material.IRON_SWORD);
        assassinBladeRecipe.setIngredient('f', Material.FLINT);
        Bukkit.getServer().addRecipe(assassinBladeRecipe);

        ItemStack sevenLeagueBoots = itemStackSevenLeagueBoots();
        ShapedRecipe sevenLeagueBootsRecipe = new ShapedRecipe(new NamespacedKey("uhc", "seven_league_boots"), sevenLeagueBoots);
        sevenLeagueBootsRecipe.shape("fPf", "fBf", "fWf");
        sevenLeagueBootsRecipe.setIngredient('f', Material.FEATHER);
        sevenLeagueBootsRecipe.setIngredient('P', Material.ENDER_PEARL);
        sevenLeagueBootsRecipe.setIngredient('B', Material.DIAMOND_BOOTS);
        sevenLeagueBootsRecipe.setIngredient('W', Material.WATER_BUCKET);
        Bukkit.getServer().addRecipe(sevenLeagueBootsRecipe);

        ItemStack hermesBoots = itemStackHermesBoots();
        ShapedRecipe hermesBootsRecipe = new ShapedRecipe(new NamespacedKey("uhc", "hermes_boots"), hermesBoots);
        hermesBootsRecipe.shape("d d", "bBb", "f f");
        hermesBootsRecipe.setIngredient('d', Material.DIAMOND);
        hermesBootsRecipe.setIngredient('b', Material.BLAZE_POWDER);
        hermesBootsRecipe.setIngredient('B', Material.DIAMOND_BOOTS);
        hermesBootsRecipe.setIngredient('f', Material.FEATHER);
        Bukkit.getServer().addRecipe(hermesBootsRecipe);

        ItemStack corn = itemStackCorn();
        ShapedRecipe cornRecipe = new ShapedRecipe(new NamespacedKey("uhc", "corn"), corn);
        cornRecipe.shape("ccc", "cGc", "ccc");
        cornRecipe.setIngredient('c', Material.CARROT);
        cornRecipe.setIngredient('G', Material.GOLDEN_APPLE);
        Bukkit.getServer().addRecipe(cornRecipe);

        ItemStack hideOfLeviathan = itemStackHideOfLeviathan();
        ShapedRecipe hideOfLeviathanRecipe = new ShapedRecipe(new NamespacedKey("uhc", "hide_of_leviathan"), hideOfLeviathan);
        hideOfLeviathanRecipe.shape("lWl", "dLd", "p p");
        hideOfLeviathanRecipe.setIngredient('l', Material.LAPIS_BLOCK);
        hideOfLeviathanRecipe.setIngredient('W', Material.WATER_BUCKET);
        hideOfLeviathanRecipe.setIngredient('d', Material.DIAMOND);
        hideOfLeviathanRecipe.setIngredient('L', Material.DIAMOND_LEGGINGS);
        hideOfLeviathanRecipe.setIngredient('p', Material.LILY_PAD);
        Bukkit.getServer().addRecipe(hideOfLeviathanRecipe);

        ItemStack dragonArmor = itemStackDragonArmor();
        ShapedRecipe dragonArmorRecipe = new ShapedRecipe(new NamespacedKey("uhc", "dragon_armor"), dragonArmor);
        dragonArmorRecipe.shape(" m ", "MCM", "oAo");
        dragonArmorRecipe.setIngredient('M', Material.MAGMA_BLOCK);
        dragonArmorRecipe.setIngredient('m', Material.MAGMA_CREAM);
        dragonArmorRecipe.setIngredient('C', Material.DIAMOND_CHESTPLATE);
        dragonArmorRecipe.setIngredient('o', Material.OBSIDIAN);
        dragonArmorRecipe.setIngredient('A', Material.ANVIL);
        Bukkit.getServer().addRecipe(dragonArmorRecipe);

        ItemStack anduril = itemStackAnduril();
        ShapedRecipe andurilRecipe = new ShapedRecipe(new NamespacedKey("uhc", "anduril"), anduril);
        andurilRecipe.shape("fIf", "fIf", "fBf");
        andurilRecipe.setIngredient('f', Material.FEATHER);
        andurilRecipe.setIngredient('I', Material.IRON_BLOCK);
        andurilRecipe.setIngredient('B', Material.BLAZE_ROD);
        Bukkit.getServer().addRecipe(andurilRecipe);

        ItemStack enhancementBook = itemStackEnhancementBook();
        ShapedRecipe enhancementBookRecipe = new ShapedRecipe(new NamespacedKey("uhc", "enhancement_book"), enhancementBook);
        enhancementBookRecipe.shape("rrr", "PdA", "bbb");
        enhancementBookRecipe.setIngredient('r', Material.REDSTONE_BLOCK);
        enhancementBookRecipe.setIngredient('P', Material.IRON_PICKAXE);
        enhancementBookRecipe.setIngredient('d', Material.DIAMOND);
        enhancementBookRecipe.setIngredient('A', Material.IRON_AXE);
        enhancementBookRecipe.setIngredient('b', Material.BOOKSHELF);
        Bukkit.getServer().addRecipe(enhancementBookRecipe);

        ItemStack expertSeal = itemStackExpertSeal();
        ShapedRecipe expertSealRecipe = new ShapedRecipe(new NamespacedKey("uhc", "expert_seal"), expertSeal);
        expertSealRecipe.shape("dGd", "IdI", "dGd");
        expertSealRecipe.setIngredient('d', Material.DIAMOND);
        expertSealRecipe.setIngredient('G', Material.GOLD_BLOCK);
        expertSealRecipe.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(expertSealRecipe);

        ItemStack deusExMachina = itemStackDeusExMachina();
        ShapedRecipe deusExMachinaRecipe = new ShapedRecipe(new NamespacedKey("uhc", "deus_ex_machina"), deusExMachina);
        deusExMachinaRecipe.shape("E", "R", "g");
        deusExMachinaRecipe.setIngredient('E', Material.EMERALD);
        deusExMachinaRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        deusExMachinaRecipe.setIngredient('g', Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(deusExMachinaRecipe);

        ItemStack artemisBow = itemStackArtemisBow();
        ShapedRecipe artemisBowRecipe = new ShapedRecipe(new NamespacedKey("uhc", "artemis_bow"), artemisBow);
        artemisBowRecipe.shape("fEf", "fBf", "fDf");
        artemisBowRecipe.setIngredient('f', Material.FEATHER);
        artemisBowRecipe.setIngredient('E', Material.ENDER_EYE);
        artemisBowRecipe.setIngredient('B', Material.BOW);
        artemisBowRecipe.setIngredient('D', Material.DIAMOND);
        Bukkit.getServer().addRecipe(artemisBowRecipe);

        ItemStack deathScythe = itemStackDeathScythe();
        ShapedRecipe deathScytheRecipe = new ShapedRecipe(new NamespacedKey("uhc", "death_scythe"), deathScythe);
        deathScytheRecipe.shape("DDH", "DbC", "b B");
        deathScytheRecipe.setIngredient('D', Material.DIAMOND);
        deathScytheRecipe.setIngredient('H', Material.PLAYER_HEAD);
        deathScytheRecipe.setIngredient('b', Material.BONE);
        deathScytheRecipe.setIngredient('C', Material.CLOCK);
        deathScytheRecipe.setIngredient('B', Material.BLAZE_POWDER);
        Bukkit.getServer().addRecipe(deathScytheRecipe);

        ItemStack bookOfThoth = itemStackBookOfThoth();
        ShapedRecipe bookOfThothRecipe = new ShapedRecipe(new NamespacedKey("uhc", "book_of_thoth"), bookOfThoth);
        bookOfThothRecipe.shape("E  ", " pp", " pF");
        bookOfThothRecipe.setIngredient('E', Material.ENDER_PEARL);
        bookOfThothRecipe.setIngredient('p', Material.PAPER);
        bookOfThothRecipe.setIngredient('F', Material.FIRE_CHARGE);
        Bukkit.getServer().addRecipe(bookOfThothRecipe);

        ItemStack shoesOfVidar = itemStackShoesOfVidar();
        ShapedRecipe shoesOfVidarRecipe = new ShapedRecipe(new NamespacedKey("uhc", "shoes_of_vidar"), shoesOfVidar);
        shoesOfVidarRecipe.shape(" X ", "tBt", " r ");
        shoesOfVidarRecipe.setIngredient('X', new RecipeChoice.MaterialChoice(Material.AXOLOTL_BUCKET, Material.PUFFERFISH));
        shoesOfVidarRecipe.setIngredient('t', Material.TROPICAL_FISH);
        shoesOfVidarRecipe.setIngredient('B', Material.DIAMOND_BOOTS);
        shoesOfVidarRecipe.setIngredient('r', Material.FISHING_ROD);
        Bukkit.getServer().addRecipe(shoesOfVidarRecipe);

        ItemStack warlockPants = itemStackWarlockPants();
        ShapedRecipe warlockPantsRecipe = new ShapedRecipe(new NamespacedKey("uhc", "warlock_pants"), warlockPants);
        warlockPantsRecipe.shape("bLb", "iAi");
        warlockPantsRecipe.setIngredient('b', Material.BLAZE_ROD);
        warlockPantsRecipe.setIngredient('L', Material.DIAMOND_LEGGINGS);
        warlockPantsRecipe.setIngredient('i', Material.IRON_BLOCK);
        ItemStack potAwkward = new ItemStack(Material.POTION);
        PotionMeta pm1 = (PotionMeta) potAwkward.getItemMeta();
        pm1.setBasePotionData(new PotionData(PotionType.AWKWARD));
        potAwkward.setItemMeta(pm1);
        ItemStack potThick = new ItemStack(Material.POTION);
        PotionMeta pm2 = (PotionMeta) potThick.getItemMeta();
        pm2.setBasePotionData(new PotionData(PotionType.THICK));
        potThick.setItemMeta(pm2);
        warlockPantsRecipe.setIngredient('A', new RecipeChoice.ExactChoice(potAwkward, potThick));
        Bukkit.getServer().addRecipe(warlockPantsRecipe);

        ItemStack vitalityPotion = itemStackVitalityPotion();
        ShapedRecipe vitalityPotionRecipe = new ShapedRecipe(new NamespacedKey("uhc", "vitality_potion"), vitalityPotion);
        vitalityPotionRecipe.shape("GBG", "GbG", "GiG");
        vitalityPotionRecipe.setIngredient('G', Material.GLOWSTONE);
        vitalityPotionRecipe.setIngredient('b', Material.GLASS_BOTTLE);
        vitalityPotionRecipe.setIngredient('B', Material.BONE);
        vitalityPotionRecipe.setIngredient('i', Material.IRON_INGOT);
        Bukkit.getServer().addRecipe(vitalityPotionRecipe);

        ItemStack bloodlust = itemStackBloodlust();
        ShapedRecipe bloodlustRecipe = new ShapedRecipe(new NamespacedKey("uhc", "bloodlust"), bloodlust);
        bloodlustRecipe.shape("rDr", "rSr", "rOr");
        bloodlustRecipe.setIngredient('r', Material.REDSTONE_BLOCK);
        bloodlustRecipe.setIngredient('D', Material.DIAMOND);
        bloodlustRecipe.setIngredient('S', Material.DIAMOND_SWORD);
        bloodlustRecipe.setIngredient('O', Material.OBSIDIAN);
        Bukkit.getServer().addRecipe(bloodlustRecipe);
    }

    public static ItemStack getRandomUltimate() {
        ItemStack[] extraUltimates = {
                itemStackDragonSword(),
                itemStackExcalibur(),
                itemStackExodus(),
                itemStackAssassinBlade(),
                itemStackSevenLeagueBoots(),
                itemStackHermesBoots(),
                itemStackCorn(),
                itemStackHideOfLeviathan(),
                itemStackDragonArmor(),
                itemStackAnduril(),
                itemStackEnhancementBook(),
                itemStackExpertSeal(),
                itemStackDeusExMachina(),
                itemStackArtemisBow(),
                itemStackDeathScythe(),
                itemStackBookOfThoth(),
                itemStackShoesOfVidar(),
                itemStackWarlockPants(),
                itemStackVitalityPotion(),
                itemStackBloodlust(),
        };
        return extraUltimates[new Random().nextInt(extraUltimates.length)];
    }

}

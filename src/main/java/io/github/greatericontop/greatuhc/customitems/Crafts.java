package io.github.greatericontop.greatuhc.customitems;

import org.bukkit.Bukkit;
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
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public class Crafts implements Listener {

    public static void registerCrafts() {
        Bukkit.getServer().removeRecipe(new NamespacedKey("minecraft", "anduril"));

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
        excaliburRecipe.shape("s s", "sTs", "sSs");
        excaliburRecipe.setIngredient('T', Material.TNT);
        excaliburRecipe.setIngredient('S', Material.DIAMOND_SWORD);
        excaliburRecipe.setIngredient('s', Material.SOUL_SAND);
        Bukkit.getServer().addRecipe(excaliburRecipe);

        ItemStack exodus = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta im3 = exodus.getItemMeta();
        im3.addEnchant(Enchantment.DURABILITY, 3, false);
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
        im8.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
        im8.addEnchant(Enchantment.DURABILITY, 1, false);
        im8.setDisplayName("§6Hermes Boots");
        im8.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "UHC", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        im8.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "UHC", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        im8.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "UHC", 0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET));
        hermesBoots.setItemMeta(im8);
        ShapedRecipe hermesBootsRecipe = new ShapedRecipe(new NamespacedKey("uhc", "hermes_boots"), hermesBoots);
        hermesBootsRecipe.shape("d d", "bBb", "f f");
        hermesBootsRecipe.setIngredient('d', Material.DIAMOND);
        hermesBootsRecipe.setIngredient('b', Material.BLAZE_POWDER);
        hermesBootsRecipe.setIngredient('B', Material.DIAMOND_BOOTS);
        hermesBootsRecipe.setIngredient('f', Material.FEATHER);
        Bukkit.getServer().addRecipe(hermesBootsRecipe);

        ItemStack corn = new ItemStack(Material.GOLDEN_CARROT, 3);
        ItemMeta im9 = corn.getItemMeta();
        im9.setDisplayName("§6Cornucopia");
        im9.setLore(List.of("Corn", "§7Gives Regeneration and Saturation."));
        corn.setItemMeta(im9);
        ShapedRecipe cornRecipe = new ShapedRecipe(new NamespacedKey("uhc", "corn"), corn);
        cornRecipe.shape("ccc", "cGc", "ccc");
        cornRecipe.setIngredient('c', Material.CARROT);
        cornRecipe.setIngredient('G', Material.GOLDEN_APPLE);
        Bukkit.getServer().addRecipe(cornRecipe);

        ItemStack hideOfLeviathan = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemMeta im10 = hideOfLeviathan.getItemMeta();
        im10.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        im10.addEnchant(Enchantment.OXYGEN, 3, true);
        im10.addEnchant(Enchantment.DURABILITY, 1, true);
        im10.setDisplayName("§9Hide of Leviathan");
        hideOfLeviathan.setItemMeta(im10);
        ShapedRecipe hideOfLeviathanRecipe = new ShapedRecipe(new NamespacedKey("uhc", "hide_of_leviathan"), hideOfLeviathan);
        hideOfLeviathanRecipe.shape("lWl", "dLd", "p p");
        hideOfLeviathanRecipe.setIngredient('l', Material.LAPIS_BLOCK);
        hideOfLeviathanRecipe.setIngredient('W', Material.WATER_BUCKET);
        hideOfLeviathanRecipe.setIngredient('d', Material.DIAMOND);
        hideOfLeviathanRecipe.setIngredient('L', Material.DIAMOND_LEGGINGS);
        hideOfLeviathanRecipe.setIngredient('p', Material.LILY_PAD);
        Bukkit.getServer().addRecipe(hideOfLeviathanRecipe);

        ItemStack dragonArmor = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta im11 = dragonArmor.getItemMeta();
        im11.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false);
        im11.setDisplayName("§5Dragon Armor");
        im11.setLore(List.of("id: DRAGON_ARMOR", "§7WeaponMaster", "§7A very powerful chestplate."));
        dragonArmor.setItemMeta(im11);
        ShapedRecipe dragonArmorRecipe = new ShapedRecipe(new NamespacedKey("uhc", "dragon_armor"), dragonArmor);
        dragonArmorRecipe.shape(" m ", "MCM", "oAo");
        dragonArmorRecipe.setIngredient('M', Material.MAGMA_BLOCK);
        dragonArmorRecipe.setIngredient('m', Material.MAGMA_CREAM);
        dragonArmorRecipe.setIngredient('C', Material.DIAMOND_CHESTPLATE);
        dragonArmorRecipe.setIngredient('o', Material.OBSIDIAN);
        dragonArmorRecipe.setIngredient('A', Material.ANVIL);
        Bukkit.getServer().addRecipe(dragonArmorRecipe);

        ItemStack anduril = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta im12 = anduril.getItemMeta();
        im12.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
        im12.setDisplayName("§aAnduril");
        im12.setLore(List.of("id: ANDURIL", "§7WeaponMaster", "§7A very powerful sword."));
        anduril.setItemMeta(im12);
        ShapedRecipe andurilRecipe = new ShapedRecipe(new NamespacedKey("uhc", "anduril"), anduril);
        andurilRecipe.shape("fIf", "fIf", "fBf");
        andurilRecipe.setIngredient('f', Material.FEATHER);
        andurilRecipe.setIngredient('I', Material.IRON_BLOCK);
        andurilRecipe.setIngredient('B', Material.BLAZE_ROD);
        Bukkit.getServer().addRecipe(andurilRecipe);

        ItemStack flamingArtifact = new ItemStack(Material.BLAZE_ROD, 1);
        ShapedRecipe flamingArtifactRecipe = new ShapedRecipe(new NamespacedKey("uhc", "flaming_artifact"), flamingArtifact);
        flamingArtifactRecipe.shape("gLg", "gFg", "gLg");
        flamingArtifactRecipe.setIngredient('g', Material.ORANGE_STAINED_GLASS);
        flamingArtifactRecipe.setIngredient('L', Material.LAVA_BUCKET);
        flamingArtifactRecipe.setIngredient('F', Material.FIREWORK_ROCKET);
        Bukkit.getServer().addRecipe(flamingArtifactRecipe);

        ItemStack netherBlessing = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im13 = (EnchantmentStorageMeta) netherBlessing.getItemMeta();
        im13.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
        im13.addStoredEnchant(Enchantment.DURABILITY, 2, false);
        im13.addStoredEnchant(Enchantment.THORNS, 1, false);
        im13.setDisplayName("§4Nether Blessing");
        netherBlessing.setItemMeta(im13);
        ShapedRecipe netherBlessingRecipe = new ShapedRecipe(new NamespacedKey("uhc", "nether_blessing"), netherBlessing);
        netherBlessingRecipe.shape("nnn", "nMn", "nnn");
        netherBlessingRecipe.setIngredient('n', new RecipeChoice.MaterialChoice(Material.NETHER_WART_BLOCK, Material.WARPED_WART_BLOCK));
        netherBlessingRecipe.setIngredient('M', Material.MAGMA_BLOCK);
        Bukkit.getServer().addRecipe(netherBlessingRecipe);

        ItemStack enhancementBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im14 = (EnchantmentStorageMeta) enhancementBook.getItemMeta();
        im14.addStoredEnchant(Enchantment.MENDING, 1, false);
        im14.setDisplayName("§6Enhancement Book");
        im14.setLore(List.of("Enhancement Book", "§7Use this in an anvil to", "§7level 30 enchant it."));
        enhancementBook.setItemMeta(im14);
        ShapedRecipe enhancementBookRecipe = new ShapedRecipe(new NamespacedKey("uhc", "enhancement_book"), enhancementBook);
        enhancementBookRecipe.shape("rrr", "PdA", "bbb");
        enhancementBookRecipe.setIngredient('r', Material.REDSTONE_BLOCK);
        enhancementBookRecipe.setIngredient('P', Material.IRON_PICKAXE);
        enhancementBookRecipe.setIngredient('d', Material.DIAMOND);
        enhancementBookRecipe.setIngredient('A', Material.IRON_AXE);
        enhancementBookRecipe.setIngredient('b', Material.BOOKSHELF);
        Bukkit.getServer().addRecipe(enhancementBookRecipe);

        ItemStack protectionBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta im15 = (EnchantmentStorageMeta) protectionBook.getItemMeta();
        im15.setDisplayName("§cBook of Protection");
        im15.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
        protectionBook.setItemMeta(im15);
        ShapedRecipe protectionBookRecipe = new ShapedRecipe(new NamespacedKey("uhc", "protection_book"), protectionBook);
        protectionBookRecipe.shape("pp", "pI");
        protectionBookRecipe.setIngredient('p', Material.PAPER);
        protectionBookRecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.getServer().addRecipe(protectionBookRecipe);

        ItemStack expertSeal = new ItemStack(Material.NETHER_STAR, 3);
        ItemMeta im16 = expertSeal.getItemMeta();
        im16.setDisplayName("§6Expert Seal");
        im16.setLore(List.of("id: EXPERT_SEAL", "§7WeaponMaster", "§eMove this over another item", "§eto upgrade its enchantments."));
        expertSeal.setItemMeta(im16);
        ShapedRecipe expertSealRecipe = new ShapedRecipe(new NamespacedKey("uhc", "expert_seal"), expertSeal);
        expertSealRecipe.shape("dGd", "IdI", "dGd");
        expertSealRecipe.setIngredient('d', Material.DIAMOND);
        expertSealRecipe.setIngredient('G', Material.GOLD_BLOCK);
        expertSealRecipe.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(expertSealRecipe);

        ItemStack deusExMachina = new ItemStack(Material.POTION, 1);
        PotionMeta im17 = (PotionMeta) deusExMachina.getItemMeta();
        im17.setDisplayName("§cDeus Ex Machina");
        im17.setLore(List.of("§7Grants invincibility!", "§cCrafting costs half your health!"));
        im17.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 240, 4), true);
        deusExMachina.setItemMeta(im17);
        ShapedRecipe deusExMachinaRecipe = new ShapedRecipe(new NamespacedKey("uhc", "deus_ex_machina"), deusExMachina);
        deusExMachinaRecipe.shape("E", "R", "g");
        deusExMachinaRecipe.setIngredient('E', Material.EMERALD);
        deusExMachinaRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        deusExMachinaRecipe.setIngredient('g', Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(deusExMachinaRecipe);

        ItemStack chestOfFate = new ItemStack(Material.POTION, 1);
        PotionMeta im18 = (PotionMeta) chestOfFate.getItemMeta();
        im18.setDisplayName("§2Chest of Fate");
        im18.setLore(List.of("§7Tempt Fate and get a powerful", "§7blessing or curse."));
        im18.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 6), true); // 14 hearts
        im18.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0), true); // 1 heart
        im18.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 900, 1), true);
        im18.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 900, 4), true);
        chestOfFate.setItemMeta(im18);
        ShapedRecipe chestOfFateRecipe = new ShapedRecipe(new NamespacedKey("uhc", "chest_of_fate"), chestOfFate);
        chestOfFateRecipe.shape("ccc", "cHc", "ccc");
        chestOfFateRecipe.setIngredient('c', Material.CHEST);
        chestOfFateRecipe.setIngredient('H', Material.PLAYER_HEAD);
        Bukkit.getServer().addRecipe(chestOfFateRecipe);

        ItemStack holyWater = new ItemStack(Material.POTION, 1);
        PotionMeta im19 = (PotionMeta) holyWater.getItemMeta();
        im19.setDisplayName("§7Holy Water");
        im19.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 2), true);
        holyWater.setItemMeta(im19);
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

        ItemStack undeadBow = new ItemStack(Material.BOW, 1);
        ItemMeta im20 = undeadBow.getItemMeta();
        im20.setDisplayName("§9Undead Bow");
        im20.setLore(List.of("Undead", "§7Arrows deal more damage."));
        undeadBow.setItemMeta(im20);
        ShapedRecipe undeadBowRecipe = new ShapedRecipe(new NamespacedKey("uhc", "undead_bow"), undeadBow);
        undeadBowRecipe.shape(" tS", "tBS", " tS");
        undeadBowRecipe.setIngredient('t', Material.REDSTONE_TORCH);
        undeadBowRecipe.setIngredient('S', Material.STRING);
        undeadBowRecipe.setIngredient('B', Material.BONE);
        Bukkit.getServer().addRecipe(undeadBowRecipe);

        ItemStack artemisBow = new ItemStack(Material.BOW, 1);
        ItemMeta im21 = artemisBow.getItemMeta();
        im21.setDisplayName("§5Artemis Bow");
        im21.setLore(List.of("id: ARTEMIS_BOW", "§7WeaponMaster", "§7Arrows shot from this bow", "§7will always hit their target!"));
        im21.addEnchant(Enchantment.DURABILITY, 1, true);
        artemisBow.setItemMeta(im21);
        ShapedRecipe artemisBowRecipe = new ShapedRecipe(new NamespacedKey("uhc", "artemis_bow"), artemisBow);
        artemisBowRecipe.shape("fEf", "fBf", "fDf");
        artemisBowRecipe.setIngredient('f', Material.FEATHER);
        artemisBowRecipe.setIngredient('E', Material.ENDER_EYE);
        artemisBowRecipe.setIngredient('B', Material.BOW);
        artemisBowRecipe.setIngredient('D', Material.DIAMOND);
        Bukkit.getServer().addRecipe(artemisBowRecipe);

        ItemStack philosopherPickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        Damageable im22 = (Damageable) philosopherPickaxe.getItemMeta();
        im22.setDisplayName("§ePhilosopher Pickaxe");
        im22.addEnchant(Enchantment.DIG_SPEED, 4, true);
        im22.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 2, true);
        im22.setDamage(1561 - 3);
        philosopherPickaxe.setItemMeta(im22);
        ShapedRecipe philosopherPickaxeRecipe = new ShapedRecipe(new NamespacedKey("uhc", "philosopher_pickaxe"), philosopherPickaxe);
        philosopherPickaxeRecipe.shape("igi", "LsL", " s ");
        philosopherPickaxeRecipe.setIngredient('i', Material.IRON_INGOT);
        philosopherPickaxeRecipe.setIngredient('g', Material.GOLD_INGOT);
        philosopherPickaxeRecipe.setIngredient('L', Material.LAPIS_BLOCK);
        philosopherPickaxeRecipe.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(philosopherPickaxeRecipe);

    }

}

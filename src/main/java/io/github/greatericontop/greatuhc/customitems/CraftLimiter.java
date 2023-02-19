package io.github.greatericontop.greatuhc.customitems;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import io.github.greatericontop.greatuhc.util.TrueDamageHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CraftLimiter implements Listener {

    private Random random = null;
    private List<Material> craftMaterials = null;

    // maps an item craft (identified by its key) to a map of player UUID to number crafted
    private final Map<String, Map<UUID, Integer>> crafts = new HashMap<>();

    private final GreatUHCMain plugin;
    public CraftLimiter(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    public void clearCrafts() {
        crafts.clear();
    }

    public void init() {
        random = new Random();
        craftMaterials = new ArrayList<>();
        Bukkit.recipeIterator().forEachRemaining(recipe -> {
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                if (shapedRecipe.getKey().getNamespace().equals("uhc")) {
                    for (ItemStack stack : shapedRecipe.getIngredientMap().values()) {
                        if (stack != null) {
                            craftMaterials.add(stack.getType());
                        }
                    }
                }
            } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
                if (shapelessRecipe.getKey().getNamespace().equals("uhc")) {
                    for (ItemStack stack : shapelessRecipe.getIngredientList()) {
                        craftMaterials.add(stack.getType());
                    }
                }
            }
        });
    }

    private int findNumberInCraftingTable(CraftItemEvent event) {
        // the minimum number of all the items in the grid is the highest possible number of items that can be crafted
        // since each result requires 1 of every item in the grid
        int minNumber = 9999;
        for (ItemStack craftingStack : event.getInventory().getMatrix()) {
            if (craftingStack == null) {
                continue;
            }
            minNumber = Math.min(minNumber, craftingStack.getAmount());
        }
        return minNumber;
    }

    private void handleLimitedCraft(String craftMapKey, CraftItemEvent event, int craftLimit) {
        UUID uuid = event.getWhoClicked().getUniqueId();
        if (!crafts.containsKey(craftMapKey)) {
            crafts.put(craftMapKey, new HashMap<>());
        }
        Map<UUID, Integer> craftMap = crafts.get(craftMapKey);
        HumanEntity player = event.getWhoClicked();
        switch (event.getClick()) {

            case LEFT, RIGHT, DROP, NUMBER_KEY -> {
                // craft one
                int craftsAlready = craftMap.getOrDefault(uuid, 0);
                if (craftsAlready >= craftLimit) { // can't make more
                    event.setCancelled(true);
                    player.sendMessage(String.format("§cYou already crafted the limit of §e%d §cof this item.", craftLimit));
                } else {
                    craftMap.put(uuid, craftsAlready + 1);
                    player.sendMessage(String.format("§7You have crafted §e%d§7/§e%d §7of this item.", craftsAlready + 1, craftLimit));
                }
            }

            case SHIFT_LEFT, SHIFT_RIGHT, CONTROL_DROP -> {
                // craft a lot
                int craftsAlready = craftMap.getOrDefault(uuid, 0);
                int numberToMake = findNumberInCraftingTable(event);
                if (craftsAlready + numberToMake > craftLimit) { // can't make more
                    event.setCancelled(true);
                    player.sendMessage(String.format("§cYou have too many items in the crafting table! You can only make §e%d §cmore of this item.", craftLimit - craftsAlready));
                } else {
                    craftMap.put(uuid, craftsAlready + numberToMake);
                    player.sendMessage(String.format("§7You have crafted §e%d§7/§e%d §7of this item.", craftsAlready + numberToMake, craftLimit));
                }
            }

            case DOUBLE_CLICK -> {
                player.sendMessage("§cWe couldn't craft this due to a double click. Try crafting this item again.");
                event.setCancelled(true);
            }
            case MIDDLE, SWAP_OFFHAND, UNKNOWN, WINDOW_BORDER_LEFT, WINDOW_BORDER_RIGHT, CREATIVE -> {
                player.sendMessage("§cWe couldn't craft this. This should never happen, but it did. Try crafting this item again.");
                event.setCancelled(true);
            }
        }
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    @EventHandler()
    public void onCraft(CraftItemEvent event) {
        NamespacedKey key;
        if (event.getRecipe() instanceof ShapedRecipe recipe) {
            key = recipe.getKey();
        } else if (event.getRecipe() instanceof ShapelessRecipe recipe) {
            key = recipe.getKey();
        } else {
            return;
        }
        if (!key.getNamespace().equals("uhc")) {
            return;
        }
        String craftKey = key.getKey();

        switch (craftKey) {
            case "light_apple" -> handleLimitedCraft(craftKey, event, 2);
            case "sharp_book" -> handleLimitedCraft(craftKey, event, 4);
            case "apprentice_helmet" -> handleLimitedCraft(craftKey, event, 1);
            case "tarnhelm" -> handleLimitedCraft(craftKey, event, 1);
            case "corn" -> handleLimitedCraft(craftKey, event, 1);
            case "flaming_artifact" -> handleLimitedCraft(craftKey, event, 1);
            case "nether_blessing" -> handleLimitedCraft(craftKey, event, 1);
            case "enhancement_book" -> handleLimitedCraft(craftKey, event, 1);
            case "protection_book" -> handleLimitedCraft(craftKey, event, 4);
            case "expert_seal" -> handleLimitedCraft(craftKey, event, 1);
            case "deus_ex_machina" -> {
                handleLimitedCraft(craftKey, event, 1);
                if (!event.isCancelled()) {
                    TrueDamageHelper.dealTrueDamage(event.getWhoClicked(), event.getWhoClicked().getHealth() * 0.5);
                }
            }
            case "chest_of_fate" -> {
                handleLimitedCraft(craftKey, event, 1);
                if (Math.random() < 0.5 && !event.isCancelled()) { // 50% chance to blow up when successfully crafted
                    Player player = (Player) event.getWhoClicked();
                    event.getInventory().setResult(new ItemStack(Material.COAL, 2));
                    new BukkitRunnable() {
                        public void run() {
                            TrueDamageHelper.dealTrueDamage(player, 20.0);
                            player.getWorld().strikeLightningEffect(player.getLocation());
                        }
                    }.runTaskLater(plugin, 1L);
                }
            }
            case "holy_water" -> handleLimitedCraft(craftKey, event, 3);
            case "philosopher_pickaxe" -> handleLimitedCraft(craftKey, event, 2);
            case "arrow_economy" -> handleLimitedCraft(craftKey, event, 3);
            case "death_scythe" -> handleLimitedCraft(craftKey, event, 1);
            case "golden_head" -> handleLimitedCraft(craftKey, event, 2);
            case "book_of_thoth" -> handleLimitedCraft(craftKey, event, 1);
            case "power_book" -> handleLimitedCraft(craftKey, event, 3);
            case "apple_economy" -> handleLimitedCraft(craftKey, event, 3);
            case "apprentice_sword" -> handleLimitedCraft(craftKey, event, 1);
            case "dice_of_god" -> {
                if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) {
                    event.getWhoClicked().sendMessage("§cYou can only craft this item by left or right clicking.");
                    event.setCancelled(true);
                    return;
                }
                if (event.getWhoClicked().getItemOnCursor().getType() != Material.AIR) {
                    event.getWhoClicked().sendMessage("§cThere can't be an item already on your cursor!");
                    event.setCancelled(true);
                    return;
                }
                handleLimitedCraft(craftKey, event, 1000);
                if (!event.isCancelled()) {
                    ItemStack ultimate = Crafts.getRandomUltimate();
                    event.getInventory().setResult(ultimate);
                    event.getWhoClicked().sendMessage(Component.text("§aYou received ")
                            .append(ultimate.getItemMeta().displayName())
                            .append(Component.text("§a.")));
                }
            }
            case "velocity_potion" -> handleLimitedCraft(craftKey, event, 3);
            case "nectar" -> handleLimitedCraft(craftKey, event, 3);
            case "backpack" -> handleLimitedCraft(craftKey, event, 1);
            case "sugar_cookie" -> handleLimitedCraft(craftKey, event, 3);
            case "apprentice_bow" -> handleLimitedCraft(craftKey, event, 1);
            case "vitality_potion" -> handleLimitedCraft(craftKey, event, 1);
            case "toughness_potion" -> handleLimitedCraft(craftKey, event, 1);
            case "delicious_meal" -> handleLimitedCraft(craftKey, event, 3);
            case "glass_economy" -> handleLimitedCraft(craftKey, event, 3);
            case "fate_temptation" -> {
                Player player = (Player) event.getWhoClicked();
                if (!player.getLocation().getBlock().getType().isAir()) {
                    player.sendMessage("§cThe block you're currently in must be air!");
                    event.setCancelled(true);
                    return;
                }
                handleLimitedCraft(craftKey, event, 1);
                if (!event.isCancelled()) {
                    Block chestBlock = player.getLocation().getBlock();
                    chestBlock.setType(Material.CHEST, false);
                    org.bukkit.block.Chest chest = (org.bukkit.block.Chest) chestBlock.getState();
                    Inventory inv = chest.getInventory();
                    // fill the 7 items in the middle
                    for (int i = 10; i <= 16; i++) {
                        Material mat = craftMaterials.get(random.nextInt(craftMaterials.size()));
                        inv.setItem(i, new ItemStack(mat, 1));
                    }
                }
            }
        }
    }

}

package io.github.greatericontop.greatuhc.customitems;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CraftLimiter implements Listener {

    // maps an item craft (identified by its key) to a map of player UUID to number crafted
    private final Map<String, Map<UUID, Integer>> crafts = new HashMap<>();

    private final GreatUHCMain plugin;
    public CraftLimiter(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    public void clearCrafts() {
        crafts.clear();
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

        int craftsAlready = craftMap.getOrDefault(uuid, 0);
        if (craftsAlready >= craftLimit) { // can't make more
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(String.format("§cYou already crafted the limit of §e%d §cof this item.", craftLimit));
        } else {
            if (findNumberInCraftingTable(event) > 1) { // attempted to make too many
                event.setCancelled(true);
                event.getWhoClicked().sendMessage("§cYou have too many items in the crafting table.");
            } else {
                craftMap.put(uuid, craftsAlready + 1);
                event.getWhoClicked().sendMessage(String.format("§7You have crafted §e%d§7/§e%d §7of this item.", craftsAlready + 1, craftLimit));
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
                event.getWhoClicked().setHealth(event.getWhoClicked().getHealth() * 0.5);
            }
            case "chest_of_fate" -> {
                handleLimitedCraft(craftKey, event, 1);
                if (Math.random() < 0.5 && !event.isCancelled()) { // 50% chance to blow up when successfully crafted
                    Player player = (Player) event.getWhoClicked();
                    event.getInventory().setResult(new ItemStack(Material.COAL, 2));
                    new BukkitRunnable() {
                        public void run() {
                            player.setHealth(Math.max(player.getHealth() - 20.0, 0.0));
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
            case "bloodlust" -> handleLimitedCraft(craftKey, event, 1);
        }
    }

}

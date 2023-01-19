package io.github.greatericontop.greatuhc.customitems;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CraftLimiter implements Listener {

    private final Map<UUID, Integer> crafts_LightApple = new HashMap<>();
    private final Map<UUID, Integer> crafts_sharpBook = new HashMap<>();
    private final Map<UUID, Integer> crafts_corn = new HashMap<>();
    private final Map<UUID, Integer> crafts_flamingArtifact = new HashMap<>();
    private final Map<UUID, Integer> crafts_netherBlessing = new HashMap<>();
    private final Map<UUID, Integer> crafts_enhancementBook = new HashMap<>();
    private final Map<UUID, Integer> crafts_protectionBook = new HashMap<>();
    private final Map<UUID, Integer> crafts_expertSeal = new HashMap<>();

    public void clearCrafts() {
        crafts_LightApple.clear();
        crafts_sharpBook.clear();
        crafts_corn.clear();
        crafts_flamingArtifact.clear();
        crafts_netherBlessing.clear();
        crafts_enhancementBook.clear();
        crafts_protectionBook.clear();
        crafts_expertSeal.clear();
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

    private void handleLimitedCraft(CraftItemEvent event, Map<UUID, Integer> craftMap, int craftLimit) {
        UUID uuid = event.getWhoClicked().getUniqueId();
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

    @EventHandler()
    public void onCraft(CraftItemEvent event) {
        if (!(event.getRecipe() instanceof ShapedRecipe)) {
            return;
        }
        ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();
        NamespacedKey key = recipe.getKey();
        if (!key.getNamespace().equals("uhc")) {
            return;
        }
        UUID uuid = event.getWhoClicked().getUniqueId();

        switch (key.getKey()) {
            case "light_apple" -> handleLimitedCraft(event, crafts_LightApple, 2);
            case "sharp_book" -> handleLimitedCraft(event, crafts_sharpBook, 4);
            case "corn" -> handleLimitedCraft(event, crafts_corn, 1);
            case "flaming_artifact" -> handleLimitedCraft(event, crafts_flamingArtifact, 1);
            case "nether_blessing" -> handleLimitedCraft(event, crafts_netherBlessing, 1);
            case "enhancement_book" -> handleLimitedCraft(event, crafts_enhancementBook, 1);
            case "protection_book" -> handleLimitedCraft(event, crafts_protectionBook, 4);
            case "expert_seal" -> handleLimitedCraft(event, crafts_expertSeal, 1);
        }
    }

}
